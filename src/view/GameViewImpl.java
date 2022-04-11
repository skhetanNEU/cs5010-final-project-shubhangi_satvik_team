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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
    this.setMinimumSize(new Dimension(300, 300));
    this.setPreferredSize(new Dimension(600, 600));
    this.setResizable(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setMenuBar();

    JPanel container = new JPanel();
    container.setLayout(new GridLayout(1, 2));

    this.gameBoard = new GameBoard(model);
    this.messages = new GameMessages(model, listener);

    container.add(gameBoard);
    container.add(messages);

    JScrollPane scrollPane2 = new JScrollPane(container);
    add(scrollPane2);

    pack();

    this.pickWeaponPopup = new PickWeaponPopup(this);
    this.movePetPopup = new MovePetPopup(this);
    this.attackTargetPopup = new AttackTargetPopup(this);
    this.model = model;

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
    fc.setFileFilter(new FileNameExtensionFilter("txt"));
    int i = fc.showOpenDialog(this);
    if (i == JFileChooser.APPROVE_OPTION) {
      file = fc.getSelectedFile();
    }
    return file;
  }

  @Override
  public void addFeatures(FeatureInterface features) {

    currentConfiguration.addActionListener(l -> features.playGame(null));
    newConfiguration.addActionListener(l -> features.playGame(chooseFile()));
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

  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void close() {
    this.setVisible(false);
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

}
