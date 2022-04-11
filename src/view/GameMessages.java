package view;

import controller.FeatureInterface;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.world.ReadOnlyWorldInterface;

public class GameMessages extends JPanel {

  // TODO: Add labels for displaying game information
  public GameMessages(ReadOnlyWorldInterface model, FeatureInterface listener) {

    setBackground(Color.LIGHT_GRAY);

    JLabel welcomeCredits = new JLabel("Game Messages");
    welcomeCredits.setForeground(Color.WHITE);
    welcomeCredits.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    welcomeCredits.setHorizontalAlignment(0);

    add(welcomeCredits);

  }

  // TODO: Show current player information
  public void setCurrentPlayerInformation(String result) {

  }

  // TODO: Show current player's room information
  public void setCurrentPlayerRoomInformation(String result) {

  }

  // TODO: Show target player information
  public void setTargetPlayerInformation(String result) {

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
