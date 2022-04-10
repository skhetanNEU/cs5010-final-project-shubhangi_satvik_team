package view;

import controller.GUIControllerInterface;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import model.world.ReadOnlyWorldInterface;

public class GameViewImpl extends JFrame implements GameViewInterface {

  private final PickWeaponPopup pickWeaponPopup;
  private final MovePetPopup movePetPopup;
  private final AttackTargetPopup attackTargetPopup;
  private final ReadOnlyWorldInterface model;

  public GameViewImpl(ReadOnlyWorldInterface model, GUIControllerInterface listener) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (listener == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    this.pickWeaponPopup = new PickWeaponPopup(this);
    this.movePetPopup = new MovePetPopup(this);
    this.attackTargetPopup = new AttackTargetPopup(this);
    this.model = model;

    // UI code
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void addClickListener(GUIControllerInterface listener) {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent event) {
        super.mouseClicked(event);
        int row = event.getY();
        int column = event.getX();
        listener.handleRoomClick(row, column);
      }
    };
    // gameBoard.addMouseListener(clickAdapter);
  }

  @Override
  public void setKeyBoardListeners(GUIControllerInterface listener) {
    KeyListener keyAdapter = new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        switch (e.getKeyCode()) {
          case KeyEvent.VK_P:
            pickWeaponPopup.setVisible(true);
            break;
          case KeyEvent.VK_A:
            attackTargetPopup.setVisible(true);
            break;
          case KeyEvent.VK_M:
            movePetPopup.setVisible(true);
            break;
          case KeyEvent.VK_L:
            listener.lookAround();
            break;
          default:
        }
      }
    };
     this.pickWeaponPopup.addClickListener(listener);
     this.movePetPopup.addClickListener(listener);
     this.attackTargetPopup.addClickListener(listener);
     this.addKeyListener(keyAdapter);
  }

  @Override
  public void makeVisible() {
     this.setVisible(true);
  }

  @Override
  public void close() {
     this.setVisible(false);
  }

  private class PickWeaponPopup extends JDialog {

    JButton pick;

    public PickWeaponPopup(GameViewImpl frame) {
      super(frame);
    }

    void addClickListener(GUIControllerInterface listener) {
      pick.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          listener.pickWeapon("");
          pickWeaponPopup.setVisible(false);
        }
      });
    }
  }

  private class MovePetPopup extends JDialog {

    JButton move;

    public MovePetPopup(GameViewImpl frame) {
      super(frame);
    }

    void addClickListener(GUIControllerInterface listener) {
      move.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          listener.movePet("");
          movePetPopup.setVisible(false);
        }
      });
    }
  }

  private class AttackTargetPopup extends JDialog {

    JButton attack;

    public AttackTargetPopup(GameViewImpl frame) {
      super(frame);
    }

    void addClickListener(GUIControllerInterface listener) {
      attack.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          listener.attackTarget("");
          attackTargetPopup.setVisible(false);
        }
      });
    }
  }


}
