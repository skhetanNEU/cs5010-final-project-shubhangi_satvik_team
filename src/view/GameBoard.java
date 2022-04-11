package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.world.ReadOnlyWorldInterface;

public class GameBoard extends JPanel {

  public GameBoard(ReadOnlyWorldInterface model) {

    setBackground(Color.DARK_GRAY);

    JLabel welcomeMessage = new JLabel("Game Board");
    welcomeMessage.setForeground(Color.WHITE);
    welcomeMessage.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    welcomeMessage.setHorizontalAlignment(0);

    add(welcomeMessage);

  }
}
