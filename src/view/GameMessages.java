package view;

import controller.FeatureInterface;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import model.world.ReadOnlyWorldInterface;

public class GameMessages extends JPanel {

  ReadOnlyWorldInterface model;
  JLabel welcomeText;
  JLabel targetPlayerLabel;
  JLabel currentPlayerLabel;
  JLabel currentPlayerRoomLabel;
  JTextArea currentPlayerInfo;
  JTextArea currentPlayerRoomInfo;
  JTextArea targetPlayerInfo;

  public GameMessages(ReadOnlyWorldInterface model, FeatureInterface listener) {

    this.model = model;

    GridBagLayout gbc = new GridBagLayout();
    setLayout(gbc);

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.anchor = GridBagConstraints.NORTHWEST;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(5, 0, 10, 0);
    c.ipady = 10;

    setBackground(Color.BLACK);
    setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

    Font f0 = new Font("Calibri Light", Font.BOLD, 20);
    Font f = new Font("Calibri Light", Font.BOLD, 18);
    Font f2 = new Font("Calibri Light", Font.BOLD, 14);

    welcomeText = new JLabel(String.format("Welcome to %s!", model.getWorldName()));
    welcomeText.setOpaque(true);
    welcomeText.setBackground(Color.BLUE);
    welcomeText.setForeground(Color.WHITE);
    welcomeText.setFont(f0);


    targetPlayerLabel = new JLabel("Target Player Details");
    currentPlayerLabel = new JLabel("Current Player Details");
    currentPlayerRoomLabel = new JLabel("Current Player Room Details");

    targetPlayerLabel.setOpaque(true);
    currentPlayerLabel.setOpaque(true);
    currentPlayerRoomLabel.setOpaque(true);

    targetPlayerLabel.setForeground(Color.WHITE);
    currentPlayerLabel.setForeground(Color.WHITE);
    currentPlayerRoomLabel.setForeground(Color.WHITE);

    targetPlayerLabel.setBackground(Color.GRAY);
    currentPlayerLabel.setBackground(Color.GRAY);
    currentPlayerRoomLabel.setBackground(Color.GRAY);

    targetPlayerLabel.setFont(f);
    currentPlayerLabel.setFont(f);
    currentPlayerRoomLabel.setFont(f);

    currentPlayerInfo = new JTextArea("-");
    currentPlayerRoomInfo = new JTextArea("-");
    targetPlayerInfo = new JTextArea("-");

    targetPlayerInfo.setForeground(Color.WHITE);
    currentPlayerInfo.setForeground(Color.WHITE);
    currentPlayerRoomInfo.setForeground(Color.WHITE);

    targetPlayerInfo.setFont(f2);
    currentPlayerInfo.setFont(f2);
    currentPlayerRoomInfo.setFont(f2);

    targetPlayerInfo.setEditable(false);
    targetPlayerInfo.setCursor(null);
    targetPlayerInfo.setOpaque(false);
    targetPlayerInfo.setFocusable(false);
    targetPlayerInfo.setLineWrap(true);
    targetPlayerInfo.setWrapStyleWord(true);

    currentPlayerInfo.setEditable(false);
    currentPlayerInfo.setCursor(null);
    currentPlayerInfo.setOpaque(false);
    currentPlayerInfo.setFocusable(false);
    currentPlayerInfo.setLineWrap(true);
    currentPlayerInfo.setWrapStyleWord(true);

    currentPlayerRoomInfo.setEditable(false);
    currentPlayerRoomInfo.setCursor(null);
    currentPlayerRoomInfo.setOpaque(false);
    currentPlayerRoomInfo.setFocusable(false);
    currentPlayerRoomInfo.setLineWrap(true);
    currentPlayerRoomInfo.setWrapStyleWord(true);

    c.gridy = 0;
    add(welcomeText, c);
    c.gridy = 1;
    add(targetPlayerLabel, c);
    c.gridy = 2;
    add(targetPlayerInfo, c);
    c.gridy = 3;
    add(currentPlayerLabel, c);
    c.gridy = 4;
    add(currentPlayerInfo, c);
    c.gridy = 5;
    add(currentPlayerRoomLabel, c);
    c.gridy = 6;
    add(currentPlayerRoomInfo, c);

  }

  public void updateGameDetails() {
    targetPlayerInfo.setText(model.getTargetPlayerDetails());
    currentPlayerInfo.setText(model.getPlayerInformation());
    currentPlayerRoomInfo.setText(model.getCurrentPlayerRoomInformation());
  }

}
