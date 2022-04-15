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
    this.gameBoard.setMinimumSize(new Dimension(600, 900));

    JScrollPane scrollPane1 = new JScrollPane(this.gameBoard,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane1.setEnabled(true);
    scrollPane1.setPreferredSize(new Dimension(600,900));
    add(scrollPane1);
    container.add(scrollPane1, BorderLayout.CENTER);

    this.messages = new GameMessages(model);
    this.messages.setMinimumSize(new Dimension(400, 900));
    container.add(messages, BorderLayout.EAST);

    JScrollPane scrollPane2 = new JScrollPane(container,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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

  /**
   * Creates a menu bar for the game.
   */
  private void setMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu startMenu = new JMenu("Start");
    currentConfiguration = new JMenuItem("With current configuration");
    newConfiguration = new JMenuItem("Select new configuration");
    quitGame = new JMenuItem("Quit");
    startMenu.add(currentConfiguration);
    startMenu.add(newConfiguration);
    startMenu.add(quitGame);
    menuBar.add(startMenu);
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

    this.addKeyListener(
      new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_P -> features.pickWeapon();
            case KeyEvent.VK_A -> features.attackTarget();
            case KeyEvent.VK_M -> features.movePet();
            case KeyEvent.VK_L -> features.lookAround();
            default -> {
            }
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
          features.movePlayer(row, column);
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
  public void refresh(boolean isLookAround) {
    this.gameBoard.refreshWorldView(isLookAround);
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
    JLabel availableWeaponInfo = new JLabel(model.getCurrentPlayerRoomWeapons(true));
    availableWeaponLabel.setFont(availableWeaponInfo.getFont().deriveFont(Font.BOLD));
    panel.add(availableWeaponLabel);
    panel.add(availableWeaponInfo);

    JLabel pickWeaponLabel = new JLabel("Enter weapon name to pick:");
    JTextField weaponField = new JTextField();
    pickWeaponLabel.setFont(pickWeaponLabel.getFont().deriveFont(Font.BOLD));
    panel.add(pickWeaponLabel);
    panel.add(weaponField);

    Object[] options = {"Pick Weapon", "Cancel"};

    int result = JOptionPane.showOptionDialog(this,
            panel,
            "Pick Weapon",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            null);

    if (result == JOptionPane.OK_OPTION) {
      return weaponField.getText();
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

    JLabel movePetLabel = new JLabel("Enter room name to move pet to:");
    movePetLabel.setFont(movePetLabel.getFont().deriveFont(Font.BOLD));
    JTextField roomNameField = new JTextField();

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
      return roomNameField.getText();
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
    JLabel availableWeaponInfo = new JLabel(model.getCurrentPlayerWeapons());
    availableWeaponLabel.setFont(availableWeaponLabel.getFont().deriveFont(Font.BOLD));
    panel.add(availableWeaponLabel);
    panel.add(availableWeaponInfo);

    JLabel attackWeaponLabel = new JLabel("Enter weapon name to attack with: ");
    JTextField weaponField = new JTextField();
    attackWeaponLabel.setFont(attackWeaponLabel.getFont().deriveFont(Font.BOLD));
    panel.add(attackWeaponLabel);
    panel.add(weaponField);

    Object[] options = {"Attack Target", "Cancel"};

    int result = JOptionPane.showOptionDialog(this,
            panel,
            "Attack Target",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            null);

    if (result == JOptionPane.OK_OPTION) {
      return weaponField.getText();
    } else {
      return null;
    }

  }

  private class AddPlayersPopup extends JDialog {

    JLabel playerNameLabel;
    JTextField playerNameField;
    JLabel playerStartRoomLabel;
    JTextField playerStartRoomField;
    JLabel playerWeaponLimitLabel;
    JTextField playerWeaponLimitField;
    JLabel playerTypeLabel;
    JComboBox<String> playerTypeOptions;
    String[] items = {"Human", "Computer"};

    JLabel errorMessage;

    JButton clear;
    JButton addNext;
    JButton startGame;

    public AddPlayersPopup(GameViewImpl frame) {

      super(frame, "Add Players to Game");

      this.setResizable(false);
      this.setPreferredSize(new Dimension(500, 250));
      this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      this.setLocation(200,200);

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

      playerStartRoomLabel = new JLabel("Enter starting room name");
      playerStartRoomField = new JTextField();
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
      playerStartRoomField.setText(null);
      playerWeaponLimitField.setText(null);
      errorMessage.setText(null);
    }

    private boolean addPlayer(FeatureInterface listener) {
      String outcome = listener.addPlayer(playerNameField.getText(),
              playerStartRoomField.getText(),
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
          }
        }
      });
    }
  }

}

