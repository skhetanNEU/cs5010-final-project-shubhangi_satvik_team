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

/**
 * Main game view showing the map and all the other details in the game such as players, target
 * character, pet, weapons, etc.
 */
public class MainGameView extends JFrame implements MainGameViewInterface {

  private JMenuItem currentConfiguration;
  private JMenuItem newConfiguration;
  private JMenuItem quitGame;
  private JMenuItem helperItem;
  private final GameBoard gameBoard;
  private final GameMessages messages;
  private final AddPlayersPopup addPlayerPopup;

  /**
   * Constructor for setting up the main game view.
   *
   * @param model    Model of the game.
   * @param listener Features object using which we can assign the listeners for each event.
   * @param roomList List of all the rooms in the world.
   * @throws IllegalArgumentException when the model or the listener or the list of rooms is null.
   */
  public MainGameView(ReadOnlyWorldInterface model,
                      FeatureInterface listener,
                      List<String> roomList) throws IllegalArgumentException {

    super("Game Started");

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (listener == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    if (roomList == null) {
      throw new IllegalArgumentException("Room list cannot be null.");
    }

    setLayout(new BorderLayout());
    setMenuBar();

    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());

    this.gameBoard = new GameBoard(model);
    container.add(gameBoard, BorderLayout.CENTER);

    this.messages = new GameMessages(model);
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

    this.addPlayerPopup = new AddPlayersPopup(this, roomList);
    this.addPlayerPopup.setVisible(true);
    this.setEnabled(false);

    pack();
  }

  /**
   * Sets the menu bar with Start and Help options.
   */
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

  /**
   * Choose the configuration file.
   *
   * @return File object for the configuration file chosen
   */
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

  /**
   * Showing the help information to the player.
   */
  private void showHelperMenu() {

    UIManager.put("OptionPane.background", Color.decode("#E8F6F3"));
    UIManager.put("Panel.background", Color.TRANSLUCENT);
    UIManager.put("Label.background", Color.TRANSLUCENT);
    UIManager.put("Label.foreground", Color.BLACK);

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

    if (features == null) {
      throw new IllegalArgumentException("Listener cannot be null.");
    }

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
    this.dispose();
  }

  @Override
  public void refresh() {
    try {
      this.gameBoard.repaint();
      this.messages.updateGameDetails();
    } catch (IllegalArgumentException iae) {
      showCommandOutcome("ERROR", iae.getMessage(), false);
    }
  }

  @Override
  public void showCommandOutcome(String title, String outcome, boolean isLookAround) {
    if (title == null || "".equals(title)) {
      showCommandOutcome("ERROR", "Outcome title cannot be null/empty.", false);
    }
    if (outcome == null || "".equals(outcome)) {
      showCommandOutcome("ERROR", "Outcome result cannot be null/empty.", false);
    }

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
      JOptionPane.showOptionDialog(getParent(), sp, title, JOptionPane.DEFAULT_OPTION,
              JOptionPane.PLAIN_MESSAGE, null, null, null);
      return;

    }
    UIManager.put("OptionPane.background", Color.decode("#FCF3CF"));
    UIManager.put("Panel.background", Color.decode("#FCF3CF"));
    UIManager.put("Button.background", Color.WHITE);
    JOptionPane.showOptionDialog(getParent(), outcome, title, JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE, null, null, null);
  }

  @Override
  public String showPickWeaponDialog(List<String> roomWeapons) throws IllegalArgumentException {

    if (roomWeapons == null) {
      throw new IllegalArgumentException("Room weapons cannot be null.");
    }

    UIManager.put("OptionPane.background", Color.decode("#512E5F"));
    UIManager.put("Panel.background", Color.decode("#512E5F"));
    UIManager.put("Label.foreground", Color.WHITE);

    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.setOpaque(false);

    JLabel availableWeaponLabel = new JLabel("Weapons available in room:");
    JTextArea availableWeaponInfo = new JTextArea(roomWeapons.size() > 0
            ? String.join("\n", roomWeapons)
            : "No weapons available!");

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
    String[] weaponList = roomWeapons.stream()
            .map(w -> w.split("(?=\\s\\(Damage)"))
            .map(parts -> parts[0].trim())
            .toArray(String[]::new);
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
  public String showMovePetDialog(List<String> roomList) throws IllegalArgumentException {
    if (roomList == null || roomList.size() == 0) {
      throw new IllegalArgumentException("Room list cannot be null/empty.");
    }

    UIManager.put("OptionPane.background", Color.decode("#006064"));
    UIManager.put("Panel.background", Color.decode("#006064"));
    UIManager.put("Label.foreground", Color.WHITE);

    JPanel panel = new JPanel(new GridLayout(0, 1));

    JLabel movePetLabel = new JLabel("Select room to move pet to:");
    movePetLabel.setFont(movePetLabel.getFont().deriveFont(Font.BOLD));
    JComboBox<String> roomNameField = new JComboBox<>(roomList.toArray(new String[0]));

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

    String res;
    if (result == JOptionPane.OK_OPTION) {
      res = roomList.get(roomNameField.getSelectedIndex());
    } else {
      res = null;
    }
    return res;
  }

  @Override
  public String showAttackTargetDialog(List<String> playerWeapons) throws IllegalArgumentException {

    if (playerWeapons == null) {
      throw new IllegalArgumentException("Player weapons cannot be null.");
    }

    UIManager.put("OptionPane.background", Color.decode("#AF601A"));
    UIManager.put("Panel.background", Color.decode("#AF601A"));
    UIManager.put("Label.foreground", Color.WHITE);

    JPanel panel = new JPanel(new GridLayout(0, 1));

    JLabel availableWeaponLabel = new JLabel("Weapons available with player:");
    JTextArea availableWeaponInfo = new JTextArea(playerWeapons.size() > 0
            ? String.join("\n", playerWeapons)
            : "No weapons available!");

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
    String[] weaponList = playerWeapons.stream()
            .map(w -> w.split("(?=\\s\\(Damage)"))
            .map(parts -> parts[0].trim())
            .toArray(String[]::new);
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

  /**
   * Pop up for adding the players.
   */
  private class AddPlayersPopup extends JDialog {

    private final JLabel playerNameLabel;
    private final JTextField playerNameField;
    private final JLabel playerStartRoomLabel;
    private final String[] roomList;
    private final JComboBox<String> playerStartRoomField;
    private final JLabel playerWeaponLimitLabel;
    private final JTextField playerWeaponLimitField;
    private final JLabel playerTypeLabel;
    private final String[] items;
    private final JComboBox<String> playerTypeOptions;
    private final JLabel errorMessage;
    private final JButton clear;
    private final JButton addNext;
    private final JButton startGame;

    /**
     * Constructor for setting up the dialog for adding players.
     *
     * @param frame Main game view frame.
     * @param rooms List of rooms present in the world.
     * @throws IllegalArgumentException When the list of rooms is null.
     */
    public AddPlayersPopup(MainGameView frame, List<String> rooms) throws IllegalArgumentException {

      super(frame, "Add Players to Game");

      if (rooms == null) {
        throw new IllegalArgumentException("Room list cannot be null.");
      }

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
      roomList = rooms.toArray(new String[0]);
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
      items = new String[]{"Human", "Computer"};
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

    /**
     * Clears the add players form.
     */
    private void clearForm() {
      playerNameField.setText(null);
      playerWeaponLimitField.setText(null);
      errorMessage.setText(null);
    }

    /**
     * Adds the player to the world.
     *
     * @param listener Listener object for the event.
     * @return Boolean representing whether the addition of player was successful or not.
     */
    private boolean addPlayer(FeatureInterface listener) {
      if (listener == null) {
        throw new IllegalArgumentException("Listener cannot be null.");
      }
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

    /**
     * Adding the click listener for the form.
     *
     * @param listener Listener object for the event.
     */
    public void addClickListener(FeatureInterface listener) {
      if (listener == null) {
        throw new IllegalArgumentException("Listener cannot be null.");
      }

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