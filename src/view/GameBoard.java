package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import model.world.ReadOnlyWorldInterface;

public class GameBoard extends JPanel {

  private final ReadOnlyWorldInterface model;

  public GameBoard(ReadOnlyWorldInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    BufferedImage img = model.getWorldView();
    g.drawImage(img, 0, 0, null);
    this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
  }

}

