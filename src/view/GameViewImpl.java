package view;

import controller.FeatureInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JTextField;
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

    this.setLayout(new BorderLayout());
    this.setResizable(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setMenuBar();

    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());

    this.gameBoard = new GameBoard(model);
    this.messages = new GameMessages(model, listener);

    container.setPreferredSize(new Dimension(1200, 900));
    this.gameBoard.setMinimumSize(new Dimension(800, 900));
    this.messages.setMinimumSize(new Dimension(400, 900));

    container.add(gameBoard, BorderLayout.CENTER);
    container.add(messages, BorderLayout.EAST);

    JScrollPane scrollPane2 = new JScrollPane(container,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    scrollPane2.setMinimumSize(new Dimension(300, 300));
    scrollPane2.setEnabled(true);

    add(scrollPane2);

    pack();

    this.addPlayerPopup = new AddPlayersPopup(this);
    this.addPlayerPopup.setVisible(true);
    this.setEnabled(false);
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
  public int showCommandOutcome(String title, String outcome) {
    return JOptionPane.showOptionDialog(getParent(), outcome,
            title, JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, null, null);
  }

  @Override
  public String showPickWeaponDialog() {

    JPanel panel = new JPanel(new GridLayout(0, 1));

    JLabel availableWeaponLabel = new JLabel(String.format("Weapons available in room: %s ",
            model.getCurrentPlayerRoomWeapons(false)));
    JLabel pickWeaponLabel = new JLabel("Enter weapon name to pick:");
    JTextField weaponField = new JTextField();

    panel.add(availableWeaponLabel);
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
            options[0]);

    if (result == JOptionPane.OK_OPTION) {
      return weaponField.getText();
    } else {
      return null;
    }

  }

  @Override
  public String showMovePetDialog() {
    JPanel panel = new JPanel(new GridLayout(0, 1));

    JLabel movePetLabel = new JLabel("Enter room name to move pet to:");
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
            options[0]);

    if (result == JOptionPane.OK_OPTION) {
      return roomNameField.getText();
    } else {
      return null;
    }
  }

  @Override
  public String showAttackTargetDialog() {
    JPanel panel = new JPanel(new GridLayout(0, 1));

    JLabel availableWeaponLabel = new JLabel(String.format("Weapons available with player: %s ",
            model.getCurrentPlayerWeapons()));
    JLabel attackWeaponLabel = new JLabel("Enter weapon name to attack with: ");
    JTextField weaponField = new JTextField();

    panel.add(availableWeaponLabel);
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
            options[0]);

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

      this.setMinimumSize(new Dimension(500, 200));
      this.setResizable(false);
      this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      this.setLocationRelativeTo(this);

      JPanel contentPane = new JPanel();
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
      contentPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

      JPanel panel = new JPanel(new GridLayout(0, 2));

      playerNameLabel = new JLabel("Enter player name");
      playerNameField = new JTextField();
      panel.add(playerNameLabel);
      panel.add(playerNameField);

      playerStartRoomLabel = new JLabel("Enter starting room name");
      playerStartRoomField = new JTextField();
      panel.add(playerStartRoomLabel);
      panel.add(playerStartRoomField);

      playerWeaponLimitLabel = new JLabel("Enter limit on weapons");
      playerWeaponLimitField = new JTextField();
      panel.add(playerWeaponLimitLabel);
      panel.add(playerWeaponLimitField);

      playerTypeLabel = new JLabel("Choose player type");
      playerTypeOptions = new JComboBox<>(items);
      panel.add(playerTypeLabel);
      panel.add(playerTypeOptions);

      errorMessage = new JLabel("");
      errorMessage.setForeground(Color.RED);
      panel.add(errorMessage);

      JPanel buttons = new JPanel(new GridLayout(1, 3));
      buttons.setSize(new Dimension(getWidth(), 80));

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

