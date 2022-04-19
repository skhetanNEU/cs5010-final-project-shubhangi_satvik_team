package view;

import controller.FeatureInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.world.ReadOnlyWorldInterface;

public class GameViewImpl extends JFrame implements GameViewInterface {

  private final ReadOnlyWorldInterface model;
  private JMenuItem currentConfiguration;
  private JMenuItem newConfiguration;
  private JMenuItem quitGame;
  private JMenuItem helperItem;
  private final GameBoard gameBoard;
  private final GameMessages messages;
  private final AddPlayersPopup addPlayerPopup;

  public GameViewImpl(ReadOnlyWorldInterface model, FeatureInterface listener) {

    super("Game Started");

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (listener == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }

    this.model = model;

    setLayout(new BorderLayout());
    setMenuBar();

    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());

    this.gameBoard = new GameBoard(model);
    // this.gameBoard.setPreferredSize(new Dimension(600, 900));

    // JScrollPane scrollPane1 = new JScrollPane(this.gameBoard,
    //         JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
    //         JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    // scrollPane1.setEnabled(true);
    // scrollPane1.setPreferredSize(new Dimension(600, 900));
    // add(scrollPane1)
    container.add(gameBoard, BorderLayout.CENTER);

    this.messages = new GameMessages(model, listener);
    this.messages.setMinimumSize(new Dimension(400, 900));
    container.add(messages, BorderLayout.EAST);

    JScrollPane scrollPane2 = new JScrollPane(container,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane2.setEnabled(true);
    add(scrollPane2);

    this.setResizable(true);
    this.setMinimumSize(new Dimension(300, 300));
    this.setPreferredSize(new Dimension(1200, 900));
    this.setLocation(150, 100);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.addPlayerPopup = new AddPlayersPopup(this);
    this.addPlayerPopup.setVisible(true);
    this.setEnabled(false);

    pack();
  }

  private void setMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu startMenu = new JMenu("Start");
    JMenu helpMenu = new JMenu("Help");
    currentConfiguration = new JMenuItem("With current configuration");
    newConfiguration = new JMenuItem("Select new configuration");
    quitGame = new JMenuItem("Quit");
    helperItem = new JMenuItem("How to play game");
    startMenu.add(currentConfiguration);
    startMenu.add(newConfiguration);
    startMenu.add(quitGame);
    helpMenu.add(helperItem);
    menuBar.add(startMenu);
    menuBar.add(helpMenu);
    setJMenuBar(menuBar);
  }

  private File chooseFile() {
    File file = null;
    JFileChooser fc = new JFileChooser();
    fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
    fc.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
    int i = fc.showOpenDialog(this);
    if (i == JFileChooser.APPROVE_OPTION) {
      file = fc.getSelectedFile();
    }
    return file;
  }

  private void showHelperMenu() {

    UIManager.put("OptionPane.background", Color.decode("#E8F6F3"));
    UIManager.put("Panel.background", Color.TRANSLUCENT);
    UIManager.put("Label.background", Color.TRANSLUCENT);

    JPanel panel = new JPanel(new GridLayout(0, 2));

    panel.add(new JLabel("View Player Details"));
    panel.add(new JLabel(": Click on current player icon"));
    panel.add(new JLabel("Move Player"));
    panel.add(new JLabel(": Click on game board"));
    panel.add(new JLabel("Pick Weapon"));
    panel.add(new JLabel(": Press P"));
    panel.add(new JLabel("Look Around"));
    panel.add(new JLabel(": Press L"));
    panel.add(new JLabel("Attack Target"));
    panel.add(new JLabel(": Press A"));
    panel.add(new JLabel("Move Pet"));
    panel.add(new JLabel(": Press M"));

    JOptionPane.showMessageDialog(this, panel, "How to play game", JOptionPane.PLAIN_MESSAGE, null);

  }

  @Override
  public void addFeatures(FeatureInterface features) {

    currentConfiguration.addActionListener(l -> features.playGame(null));
    newConfiguration.addActionListener(l -> {
      File chosen = chooseFile();
      if (chosen != null) {
        features.playGame(chosen);
      }
    });
    quitGame.addActionListener(l -> features.quitGame());
    helperItem.addActionListener(l -> showHelperMenu());

    this.addKeyListener(
      new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_P:
              features.pickWeapon();
              break;
            case KeyEvent.VK_A:
              features.attackTarget();
              break;
            case KeyEvent.VK_M:
              features.movePet();
              break;
            case KeyEvent.VK_L:
              features.lookAround();
              break;
            default:
              break;
          }
        }
      }
    );
    gameBoard.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent event) {
          super.mouseClicked(event);
          int row = event.getY();
          int column = event.getX();
          features.handleRoomClick(row, column);
        }
      }
    );

    this.addPlayerPopup.addClickListener(features);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void close() {
    this.setVisible(false);
  }

  @Override
  public void refresh() {
    this.gameBoard.repaint();
    this.messages.updateGameDetails();
  }

  @Override
  public int showCommandOutcome(String title, String outcome, boolean isLookAround) {

    if (isLookAround) {
      UIManager.put("OptionPane.background", Color.decode("#D6DBDF"));
      UIManager.put("Panel.background", Color.decode("#D6DBDF"));
      UIManager.put("Button.background", Color.WHITE);
      JTextArea textArea = new JTextArea(outcome, 20, 60);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
      JScrollPane sp = new JScrollPane(textArea);
      sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      return JOptionPane.showOptionDialog(getParent(), sp, title, JOptionPane.DEFAULT_OPTION,
              JOptionPane.PLAIN_MESSAGE, null, null, null);

    }
    UIManager.put("OptionPane.background", Color.decode("#FCF3CF"));
    UIManager.put("Panel.background", Color.decode("#FCF3CF"));
    UIManager.put("Button.background", Color.WHITE);
    return JOptionPane.showOptionDialog(getParent(), outcome, title, JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE, null, null, null);
  }

  @Override
  public String showPickWeaponDialog() {

    UIManager.put("OptionPane.background", Color.decode("#512E5F"));
    UIManager.put("Panel.background", Color.decode("#512E5F"));
    UIManager.put("Label.foreground", Color.WHITE);

    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.setOpaque(false);

    JLabel availableWeaponLabel = new JLabel("Weapons available in room:");
    List<String> weapons = model.getCurrentPlayerRoomWeapons(true);
    JTextArea availableWeaponInfo = new JTextArea(weapons.size() > 0 ? String.join("\n", weapons) : "No weapons available!");

    availableWeaponLabel.setFont(availableWeaponInfo.getFont().deriveFont(Font.BOLD));
    availableWeaponInfo.setForeground(Color.WHITE);
    availableWeaponInfo.setOpaque(false);
    availableWeaponInfo.setEditable(false);
    availableWeaponInfo.setCursor(null);
    availableWeaponInfo.setFocusable(false);
    availableWeaponInfo.setLineWrap(true);
    availableWeaponInfo.setWrapStyleWord(true);

    panel.add(availableWeaponLabel);
    panel.add(availableWeaponInfo);

    JLabel pickWeaponLabel = new JLabel("Select weapon to pick:");
    String[] weaponList = model.getCurrentPlayerRoomWeapons(false).toArray(new String[0]);
    JComboBox<String> weaponField = new JComboBox<>(weaponList);
    pickWeaponLabel.setFont(pickWeaponLabel.getFont().deriveFont(Font.BOLD));
    panel.add(pickWeaponLabel);
    panel.add(weaponField);

    Object[] options = {"Pick Weapon", "Cancel"};

    panel.setMaximumSize(new Dimension(100, panel.getHeight()));

    int result = JOptionPane.showOptionDialog(this,
            panel,
            "Pick Weapon",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            null);

    if (result == JOptionPane.OK_OPTION) {
      return weaponList.length > 0 ? weaponList[weaponField.getSelectedIndex()] : "";
    } else {
      return null;
    }

  }

  @Override
  public String showMovePetDialog() {

    UIManager.put("OptionPane.background", Color.decode("#006064"));
    UIManager.put("Panel.background", Color.decode("#006064"));
    UIManager.put("Label.foreground", Color.WHITE);

    JPanel panel = new JPanel(new GridLayout(0, 1));

    JLabel movePetLabel = new JLabel("Select room to move pet to:");
    movePetLabel.setFont(movePetLabel.getFont().deriveFont(Font.BOLD));
    String[] roomList = model.getListOfRooms().toArray(new String[0]);
    JComboBox<String> roomNameField = new JComboBox<>(roomList);

    panel.add(movePetLabel);
    panel.add(roomNameField);

    Object[] options = {"Move Pet", "Cancel"};

    int result = JOptionPane.showOptionDialog(this,
            panel,
            "Move Pet",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            null);

    if (result == JOptionPane.OK_OPTION) {
      return roomList[roomNameField.getSelectedIndex()];
    } else {
      return null;
    }
  }

  @Override
  public String showAttackTargetDialog() {

    UIManager.put("OptionPane.background", Color.decode("#AF601A"));
    UIManager.put("Panel.background", Color.decode("#AF601A"));
    UIManager.put("Label.foreground", Color.WHITE);

    JPanel panel = new JPanel(new GridLayout(0, 1));

    JLabel availableWeaponLabel = new JLabel("Weapons available with player:");
    List<String> weapons = model.getCurrentPlayerWeapons(true);
    JTextArea availableWeaponInfo = new JTextArea(weapons.size() > 0 ? String.join("\n", weapons) : "No weapons available!");

    availableWeaponLabel.setFont(availableWeaponLabel.getFont().deriveFont(Font.BOLD));
    availableWeaponInfo.setForeground(Color.WHITE);
    availableWeaponInfo.setOpaque(false);
    availableWeaponInfo.setEditable(false);
    availableWeaponInfo.setCursor(null);
    availableWeaponInfo.setFocusable(false);
    availableWeaponInfo.setLineWrap(true);
    availableWeaponInfo.setWrapStyleWord(true);

    panel.add(availableWeaponLabel);
    panel.add(availableWeaponInfo);

    JLabel attackWeaponLabel = new JLabel("Select weapon to attack with: ");
    String[] weaponList = model.getCurrentPlayerWeapons(false).toArray(new String[0]);
    JComboBox<String> weaponField = new JComboBox<>(weaponList);

    attackWeaponLabel.setFont(attackWeaponLabel.getFont().deriveFont(Font.BOLD));
    panel.add(attackWeaponLabel);
    panel.add(weaponField);

    Object[] options = {"Attack Target", "Cancel"};

    panel.setSize(200, panel.getHeight());

    int result = JOptionPane.showOptionDialog(this,
            panel,
            "Attack Target",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            null);

    if (result == JOptionPane.OK_OPTION) {
      return weaponList.length > 0 ? weaponList[weaponField.getSelectedIndex()] : "";
    } else {
      return null;
    }

  }

  private class AddPlayersPopup extends JDialog {

    JLabel playerNameLabel;
    JTextField playerNameField;
    JLabel playerStartRoomLabel;
    String[] roomList = model.getListOfRooms().toArray(new String[0]);
    JComboBox<String> playerStartRoomField;
    JLabel playerWeaponLimitLabel;
    JTextField playerWeaponLimitField;
    JLabel playerTypeLabel;
    String[] items = {"Human", "Computer"};
    JComboBox<String> playerTypeOptions;

    JLabel errorMessage;

    JButton clear;
    JButton addNext;
    JButton startGame;

    public AddPlayersPopup(GameViewImpl frame) {

      super(frame, "Add Players to Game");

      this.setResizable(false);
      this.setPreferredSize(new Dimension(500, 250));
      this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      this.setLocation(200, 200);

      JPanel contentPane = new JPanel();
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
      contentPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
      contentPane.setBackground(Color.decode("#003366"));

      JPanel panel = new JPanel(new GridLayout(0, 2));
      panel.setOpaque(false);

      playerNameLabel = new JLabel("Enter player name");
      playerNameField = new JTextField();
      playerNameLabel.setForeground(Color.WHITE);
      panel.add(playerNameLabel);
      panel.add(playerNameField);

      playerStartRoomLabel = new JLabel("Select starting room");
      playerStartRoomField = new JComboBox<>(roomList);

      playerStartRoomLabel.setForeground(Color.WHITE);
      panel.add(playerStartRoomLabel);
      panel.add(playerStartRoomField);

      playerWeaponLimitLabel = new JLabel("Enter limit on weapons");
      playerWeaponLimitField = new JTextField();
      playerWeaponLimitLabel.setForeground(Color.WHITE);
      panel.add(playerWeaponLimitLabel);
      panel.add(playerWeaponLimitField);

      playerTypeLabel = new JLabel("Choose player type");
      playerTypeOptions = new JComboBox<>(items);
      playerTypeLabel.setForeground(Color.WHITE);
      panel.add(playerTypeLabel);
      panel.add(playerTypeOptions);

      errorMessage = new JLabel("");
      errorMessage.setFont(errorMessage.getFont().deriveFont(Font.BOLD));
      errorMessage.setForeground(Color.RED);
      panel.add(errorMessage);

      JPanel buttons = new JPanel(new GridLayout(1, 3));
      buttons.setSize(new Dimension(getWidth(), 70));
      buttons.setOpaque(false);

      clear = new JButton("Clear Details");
      buttons.add(clear);

      addNext = new JButton("Add Next Player");
      buttons.add(addNext);

      startGame = new JButton("Add and Start Game");
      buttons.add(startGame);

      contentPane.add(panel);
      contentPane.add(new JLabel());
      contentPane.add(buttons);

      add(contentPane);

      pack();
    }

    private void clearForm() {
      playerNameField.setText(null);
      playerWeaponLimitField.setText(null);
      errorMessage.setText(null);
    }

    private boolean addPlayer(FeatureInterface listener) {
      String outcome = listener.addPlayer(playerNameField.getText(),
              roomList[playerStartRoomField.getSelectedIndex()],
              playerWeaponLimitField.getText(),
              playerTypeOptions.getSelectedIndex() == 1);
      if (!"".equalsIgnoreCase(outcome)) {
        errorMessage.setText(outcome);
        return false;
      } else {
        errorMessage.setText(null);
        return true;
      }
    }

    public void addClickListener(FeatureInterface listener) {

      clear.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          clearForm();
        }
      });

      addNext.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (addPlayer(listener)) {
            clearForm();
          }
        }
      });

      startGame.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (addPlayer(listener)) {
            clearForm();
            getParent().setEnabled(true);
            addPlayerPopup.setVisible(false);
            listener.checkAndPlayTurnForComputerPlayer();
          }
        }
      });
    }
  }

}

