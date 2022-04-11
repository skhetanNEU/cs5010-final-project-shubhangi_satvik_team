package view;

import controller.FeatureInterface;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.world.ReadOnlyWorldInterface;

public class GameMessages extends JPanel {

  public GameMessages(ReadOnlyWorldInterface model, FeatureInterface listener) {

    setBackground(Color.LIGHT_GRAY);

    JLabel welcomeCredits = new JLabel("Game Messages");
    welcomeCredits.setForeground(Color.WHITE);
    welcomeCredits.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    welcomeCredits.setHorizontalAlignment(0);

    add(welcomeCredits);

  }
}
