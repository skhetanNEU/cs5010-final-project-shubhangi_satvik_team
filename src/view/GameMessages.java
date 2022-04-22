package view;

import controller.FeatureInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import model.world.ReadOnlyWorldInterface;

public class GameMessages extends JPanel {

  ReadOnlyWorldInterface model;
  JLabel currentTurnName;
  JTextArea targetPlayerInfo;
  JTextArea currentPlayerRoomInfo;


  public GameMessages(ReadOnlyWorldInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    initialize();
  }

  private void initialize() {

    setLayout(new BorderLayout());
    setBackground(Color.decode("#2C3E50"));

    JPanel content = new JPanel();
    content.setLayout(new GridBagLayout());
    content.setOpaque(false);
    content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 10;

    JLabel welcomeText = new JLabel(String.format("WELCOME TO %s!",
            model.getWorldName().toUpperCase()));
    welcomeText.setOpaque(true);
    welcomeText.setBackground(Color.decode("#BBDEFB"));
    welcomeText.setForeground(Color.decode("#01579B"));
    welcomeText.setFont(welcomeText.getFont().deriveFont(Font.BOLD, 18));
    welcomeText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    c.gridy = 0;
    content.add(welcomeText, c);

    c.insets = new Insets(5, 0, 0, 0);

    try {
      // Key for target player
      BufferedImage target = ImageIO.read(new File("res/images/TargetPlayer.png"));
      Image resultingTarget = target.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      JLabel targetPic = new JLabel(new ImageIcon(resultingTarget));
      targetPic.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

      JLabel targetLabel = new JLabel("Target Player");
      targetLabel.setForeground(Color.WHITE);
      targetLabel.setFont(targetLabel.getFont().deriveFont(Font.BOLD));

      JPanel targetKey = new JPanel();
      targetKey.setLayout(new BorderLayout());
      targetKey.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
      targetKey.setOpaque(false);
      targetKey.add(targetPic, BorderLayout.WEST);
      targetKey.add(targetLabel, BorderLayout.CENTER);

      c.gridy = 1;
      content.add(targetKey, c);

      // Key for current player
      BufferedImage player = ImageIO.read(new File("res/images/CurrentPlayer.png"));
      Image resultingPlayer = player.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      JLabel playerPic = new JLabel(new ImageIcon(resultingPlayer));
      playerPic.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

      JLabel playerLabel = new JLabel("Current Player");
      playerLabel.setForeground(Color.WHITE);
      playerLabel.setFont(playerLabel.getFont().deriveFont(Font.BOLD));

      JPanel currentPlayerKey = new JPanel();
      currentPlayerKey.setLayout(new BorderLayout());
      currentPlayerKey.setBorder(BorderFactory.createEmptyBorder(-5, 5, 0, 5));
      currentPlayerKey.setOpaque(false);
      currentPlayerKey.add(playerPic, BorderLayout.WEST);
      currentPlayerKey.add(playerLabel, BorderLayout.CENTER);

      c.gridy = 2;
      content.add(currentPlayerKey, c);

      // Key for room weapons
      BufferedImage weapon = ImageIO.read(new File("res/images/Weapons.png"));
      Image resultingWeapon = weapon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      JLabel weaponPic = new JLabel(new ImageIcon(resultingWeapon));
      weaponPic.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

      JLabel weaponLabel = new JLabel("Weapons Available");
      weaponLabel.setForeground(Color.WHITE);
      weaponLabel.setFont(weaponLabel.getFont().deriveFont(Font.BOLD));

      JPanel weaponKey = new JPanel();
      weaponKey.setLayout(new BorderLayout());
      weaponKey.setBorder(BorderFactory.createEmptyBorder(-5, 5, 0, 5));
      weaponKey.setOpaque(false);
      weaponKey.add(weaponPic, BorderLayout.WEST);
      weaponKey.add(weaponLabel, BorderLayout.CENTER);

      c.gridy = 3;
      content.add(weaponKey, c);

      // Key for other players
      BufferedImage other = ImageIO.read(new File("res/images/OtherPlayers.png"));
      Image resultingOther = other.getScaledInstance(50, 30, Image.SCALE_SMOOTH);
      JLabel otherPic = new JLabel(new ImageIcon(resultingOther));
      otherPic.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

      JLabel otherLabel = new JLabel("Other Players in Room");
      otherLabel.setForeground(Color.WHITE);
      otherLabel.setFont(otherLabel.getFont().deriveFont(Font.BOLD));

      JPanel otherKey = new JPanel();
      otherKey.setLayout(new BorderLayout());
      otherKey.setBorder(BorderFactory.createEmptyBorder(-5, 5, 0, 5));
      otherKey.setOpaque(false);
      otherKey.add(otherPic, BorderLayout.WEST);
      otherKey.add(otherLabel, BorderLayout.CENTER);

      c.gridy = 4;
      content.add(otherKey, c);

    } catch (IOException e) {
      // TODO: Do nothing?
    }

    JLabel currentTurnLabel = new JLabel("Current Turn");
    currentTurnLabel.setOpaque(true);
    currentTurnLabel.setForeground(Color.decode("#B9770E"));
    currentTurnLabel.setBackground(Color.decode("#FFF9C4"));
    currentTurnLabel.setFont(currentTurnLabel.getFont().deriveFont(Font.BOLD, 14));
    currentTurnLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    currentTurnName = new JLabel("-");
    currentTurnName.setForeground(Color.WHITE);
    currentTurnName.setFont(currentTurnName.getFont().deriveFont(Font.BOLD, 12));
    currentTurnName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    JLabel targetPlayerLabel = new JLabel("Target Player Details");
    targetPlayerLabel.setOpaque(true);
    targetPlayerLabel.setForeground(Color.decode("#7B241C"));
    targetPlayerLabel.setBackground(Color.decode("#F2D7D5"));
    targetPlayerLabel.setFont(targetPlayerLabel.getFont().deriveFont(Font.BOLD, 14));
    targetPlayerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    targetPlayerInfo = new JTextArea("-");
    targetPlayerInfo.setForeground(Color.WHITE);
    targetPlayerInfo.setFont(targetPlayerInfo.getFont().deriveFont(Font.BOLD, 12));
    targetPlayerInfo.setEditable(false);
    targetPlayerInfo.setCursor(null);
    targetPlayerInfo.setOpaque(false);
    targetPlayerInfo.setFocusable(false);
    targetPlayerInfo.setLineWrap(true);
    targetPlayerInfo.setWrapStyleWord(true);
    targetPlayerInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    JLabel currentPlayerRoomLabel = new JLabel("Current Player Room Details");
    currentPlayerRoomLabel.setOpaque(true);
    currentPlayerRoomLabel.setForeground(Color.decode("#33691E"));
    currentPlayerRoomLabel.setBackground(Color.decode("#DCEDC8"));
    currentPlayerRoomLabel.setFont(currentPlayerRoomLabel.getFont().deriveFont(Font.BOLD, 14));
    currentPlayerRoomLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    currentPlayerRoomInfo = new JTextArea("-");
    currentPlayerRoomInfo.setForeground(Color.WHITE);
    currentPlayerRoomInfo.setFont(currentPlayerRoomInfo.getFont().deriveFont(Font.BOLD, 12));
    currentPlayerRoomInfo.setEditable(false);
    currentPlayerRoomInfo.setCursor(null);
    currentPlayerRoomInfo.setOpaque(false);
    currentPlayerRoomInfo.setFocusable(false);
    currentPlayerRoomInfo.setLineWrap(true);
    currentPlayerRoomInfo.setWrapStyleWord(true);
    currentPlayerRoomInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    c.insets = new Insets(10, 0, 5, 0);
    c.gridy = 5;
    content.add(currentTurnLabel, c);
    c.insets = new Insets(5, 0, 5, 0);
    c.gridy = 6;
    content.add(currentTurnName, c);
    c.gridy = 7;
    content.add(targetPlayerLabel, c);
    c.gridy = 8;
    content.add(targetPlayerInfo, c);
    c.gridy = 9;
    content.add(currentPlayerRoomLabel, c);
    c.gridy = 10;
    content.add(currentPlayerRoomInfo, c);

    add(content, BorderLayout.NORTH);
  }

  public void updateGameDetails() {
    currentTurnName.setText(model.getCurrentPlayerName());
    targetPlayerInfo.setText(model.getTargetPlayerDetails());
    currentPlayerRoomInfo.setText(model.getCurrentPlayerRoomInformation());
  }

}
