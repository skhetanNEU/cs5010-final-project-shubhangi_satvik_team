package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.world.ReadOnlyWorldInterface;

public class GameBoard extends JPanel {

  private final ReadOnlyWorldInterface model;
  private JLabel world;

  public GameBoard(ReadOnlyWorldInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;

    setBackground(Color.BLACK);

    world = new JLabel();
    add(world);
    refreshWorldView(false);
  }

  protected void refreshWorldView(boolean isLookAround) {
    BufferedImage img = model.displayWorld(isLookAround);
    world.setIcon(new ImageIcon(img));
  }

}

