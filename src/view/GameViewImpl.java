package view;

import controller.FeatureInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
  private final PickWeaponPopup pickWeaponPopup;
  private final MovePetPopup movePetPopup;
  private final AttackTargetPopup attackTargetPopup;

  public GameViewImpl(ReadOnlyWorldInterface model, FeatureInterface listener) {

    super("Game Started");

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (listener == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }

    this.setLayout(new BorderLayout());
    this.setResizable(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setMenuBar();

    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());

    this.gameBoard = new GameBoard(model);
    this.messages = new GameMessages(model, listener);

    container.setSize(64 * 40, 1000);
    this.gameBoard.setSize(64 * 30, 1000);
    this.messages.setSize(64 * 10, 1000);

    container.add(gameBoard, BorderLayout.CENTER);
    container.add(messages, BorderLayout.EAST);

    JScrollPane scrollPane2 = new JScrollPane(container,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane2.setEnabled(true);

    scrollPane2.setMinimumSize(new Dimension(300, 300));
    scrollPane2.setPreferredSize(new Dimension(1200, 900));

    add(scrollPane2);

    pack();

    this.addPlayerPopup = new AddPlayersPopup(this);
    this.pickWeaponPopup = new PickWeaponPopup(this);
    this.movePetPopup = new MovePetPopup(this);
    this.attackTargetPopup = new AttackTargetPopup(this);
    this.model = model;

    this.setEnabled(false);
    this.addPlayerPopup.setVisible(true);

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
            case KeyEvent.VK_P -> pickWeaponPopup.setVisible(true);
            case KeyEvent.VK_A -> attackTargetPopup.setVisible(true);
            case KeyEvent.VK_M -> movePetPopup.setVisible(true);
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
          features.handleRoomClick(row, column);
        }
      }
    );

    this.pickWeaponPopup.addClickListener(features);
    this.movePetPopup.addClickListener(features);
    this.attackTargetPopup.addClickListener(features);
    this.addPlayerPopup.addClickListener(features);

  }

  @Override
  public void refresh(boolean isLookAround) {
    this.gameBoard.refreshWorldView(isLookAround);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void close() {
    this.setVisible(false);
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

  private class AddPlayersPopup extends JDialog {

    JLabel playerTypeLabel;
    ButtonGroup  playerTypeBtnGrp;
    JRadioButton radioBtnHuman;
    JRadioButton radioBtnComputer;

    JLabel playerNameLabel;
    JTextField playerNameField;

    JLabel playerStartRoomLabel;
    JTextField playerStartRoomField;

    JLabel playerWeaponLimitLabel;
    JTextField playerWeaponLimitField;

    JButton startGameBtn;
    JButton addPlayerBtn;

    public AddPlayersPopup(GameViewImpl frame) {
      super(frame, "Add Players");

      this.setMinimumSize(new Dimension(500,200));
      this.setResizable(false);
      this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      this.setLocationRelativeTo(this);

      JPanel form = new JPanel(new GridLayout(6,2));
      form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

      playerTypeLabel = new JLabel("Select player type");
      playerTypeBtnGrp = new ButtonGroup();
      radioBtnHuman  = new JRadioButton("Human");
      radioBtnHuman.setActionCommand("0");
      radioBtnComputer = new JRadioButton("Computer");
      radioBtnComputer.setActionCommand("1");
      playerTypeBtnGrp.add(radioBtnHuman);
      playerTypeBtnGrp.add(radioBtnComputer);

      playerNameLabel= new JLabel("Enter player name");
      playerNameField = new JTextField();

      playerStartRoomLabel = new JLabel("Enter starting room name");
      playerStartRoomField = new JTextField();

      playerWeaponLimitLabel = new JLabel("Enter limit on weapons");
      playerWeaponLimitField = new JTextField();

      startGameBtn = new JButton("Play Game");
      addPlayerBtn = new JButton("Add Another Player");

      form.add(playerNameLabel);
      form.add(playerNameField);

      form.add(playerStartRoomLabel);
      form.add(playerStartRoomField);

      form.add(playerWeaponLimitLabel);
      form.add(playerWeaponLimitField);

      form.add(playerTypeLabel);
      form.add(new JLabel());
      form.add(radioBtnHuman);
      form.add(radioBtnComputer);

      form.add(startGameBtn);
      form.add(addPlayerBtn);

      add(form);

      pack();
    }

    void clearForm(){
      playerTypeBtnGrp.clearSelection();
      playerNameField.setText(null);
      playerStartRoomField.setText(null);
      playerWeaponLimitField.setText(null);
    }

    void addPlayer(FeatureInterface listener){
//      listener.addPlayer(playerNameField.getText(),
//        playerStartRoomField.getText(),
//        playerWeaponLimitField.getText(),
//        Objects.equals(playerTypeBtnGrp.getSelection().getActionCommand(), "1"));
    }

    void addClickListener(FeatureInterface listener) {
      addPlayerBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          addPlayer(listener);
          clearForm();
        }
      });

      startGameBtn.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          addPlayer(listener);
          getParent().setEnabled(true);
          addPlayerPopup.setVisible(false);
        }
      });
    }
  }

  // TODO: Show helper text for the pick weapon command
  private class PickWeaponPopup extends JDialog {

    JLabel label;
    JTextField weaponName;
    JButton pick;

    public PickWeaponPopup(GameViewImpl frame) {
      super(frame, "Pick Weapon");
      setLayout(new FlowLayout());
      label = new JLabel();
      label.setText("Enter weapon name: ");
      weaponName = new JTextField(15);
      pick = new JButton("Pick");
      add(label);
      add(weaponName);
      add(pick);
      this.setSize(300, 150);
      pack();
    }

    void addClickListener(FeatureInterface listener) {
      pick.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          listener.pickWeapon(weaponName.getText());
          pickWeaponPopup.setVisible(false);
        }
      });
    }
  }

  // TODO: Show helper text for the move pet command
  private class MovePetPopup extends JDialog {

    JLabel label;
    JTextField roomName;
    JButton move;

    public MovePetPopup(GameViewImpl frame) {
      super(frame, "Move Pet");
      setLayout(new FlowLayout());
      label = new JLabel();
      label.setText("Enter room name: ");
      roomName = new JTextField(15);
      move = new JButton("Move");
      add(label);
      add(roomName);
      add(move);
      this.setSize(300, 150);
      pack();
    }

    void addClickListener(FeatureInterface listener) {
      move.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          listener.movePet(roomName.getText());
          movePetPopup.setVisible(false);
        }
      });
    }
  }

  // TODO: Show helper text for the attack target command
  private class AttackTargetPopup extends JDialog {

    JLabel label;
    JTextField weaponName;
    JButton attack;

    public AttackTargetPopup(GameViewImpl frame) {
      super(frame, "Attack Target");
      setLayout(new FlowLayout());
      label = new JLabel();
      label.setText("Enter weapon name: ");
      weaponName = new JTextField(15);
      attack = new JButton("Attack");
      add(label);
      add(weaponName);
      add(attack);
      this.setSize(300, 150);
      pack();
    }

    void addClickListener(FeatureInterface listener) {
      attack.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          listener.attackTarget(weaponName.getText());
          attackTargetPopup.setVisible(false);
        }
      });
    }
  }

  // TODO: Show result of the move player command
  public void setMovePlayerResult(String result) {

  }

  // TODO: Show result of the move pet command
  public void setMovePet(String result) {

  }

  // TODO: Show result of the pick weapon command
  public void setPickWeapon(String result) {

  }

  // TODO: Show result of the attack target command
  public void setAttackTargetResult(String result) {

  }

  // TODO: Show result of the look around command
  public void setLookAround(String result) {

  }

}

