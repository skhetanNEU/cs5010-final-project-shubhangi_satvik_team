package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.world.ReadOnlyWorldInterface;

/**
 * Board of the game representing the map of the world with all the rooms, players, weapons and
 * target character.
 */
public class GameBoard extends JPanel {

  private final ReadOnlyWorldInterface model;
  
  /**
   * Constructor for setting up the game board.
   * @param model Model based on which the board should be presented.
   * @throws IllegalArgumentException when the model is null.
   */
  public GameBoard(ReadOnlyWorldInterface model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    setBackground(Color.BLACK);
  }

  @Override
  protected void paintComponent(Graphics g) throws IllegalArgumentException {
    super.paintComponent(g);
    BufferedImage img = model.getWorldView();
    g.drawImage(img, 0, 0, null);
    if (model.isGameOver()) {
      int width = img.getWidth() / 2;
      int height = img.getHeight() / 2;
      try {
        BufferedImage gameOver = ImageIO.read(new File("res/images/GameOver.png"));
        Image scaledGameOver = gameOver.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.drawImage(scaledGameOver, width - 200, height - 200, null);
      } catch (IOException e) {
        throw new IllegalArgumentException("Error in fetching image.");
      }
    }
    this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
  }

}

