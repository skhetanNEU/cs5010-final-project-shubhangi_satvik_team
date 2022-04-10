package view;

import controller.FeatureInterface;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

  public GameViewImpl(ReadOnlyWorldInterface model, FeatureInterface listener) {
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
  public void addFeatures(FeatureInterface features) {

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

    //    gameBoard.addMouseListener(
    //            new MouseAdapter() {
    //              @Override
    //              public void mouseClicked(MouseEvent event) {
    //                super.mouseClicked(event);
    //                int row = event.getY();
    //                int column = event.getX();
    //                features.handleRoomClick(row, column);
    //              }
    //            }
    //    );

    this.pickWeaponPopup.addClickListener(features);
    this.movePetPopup.addClickListener(features);
    this.attackTargetPopup.addClickListener(features);

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

    void addClickListener(FeatureInterface listener) {
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

    void addClickListener(FeatureInterface listener) {
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

    void addClickListener(FeatureInterface listener) {
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
