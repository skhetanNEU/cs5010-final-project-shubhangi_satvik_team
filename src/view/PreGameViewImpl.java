package view;

import controller.FeatureInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PreGameViewImpl extends JFrame implements PreGameViewInterface {

  private JMenuItem currentConfiguration;
  private JMenuItem newConfiguration;
  private JMenuItem quitGame;
  private JMenuItem helperItem;

  public PreGameViewImpl() {

    super("Welcome to Milestone 4");
    this.setLayout(new BorderLayout());
    this.setPreferredSize(new Dimension(600, 300));
    this.setResizable(false);
    this.setLocation(200, 200);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setMenuBar();

    JPanel container = new JPanel();
    container.setLayout(new GridLayout(2, 1));
    container.setBackground(Color.decode("#D7CCC8"));

    JPanel welcome = new JPanel();
    welcome.setLayout(new GridLayout(0, 1));

    JLabel welcomeMessage = new JLabel("Welcome to");
    welcomeMessage.setForeground(Color.decode("#4E342E"));
    welcomeMessage.setFont(new Font("Times New Roman", Font.BOLD, 30));
    welcomeMessage.setHorizontalAlignment(0);
    JLabel welcomeMessage2 = new JLabel("Kill Doctor Lucky");
    welcomeMessage2.setForeground(Color.decode("#4E342E"));
    welcomeMessage2.setFont(new Font("Times New Roman", Font.BOLD, 36));
    welcomeMessage2.setHorizontalAlignment(0);

    welcome.add(welcomeMessage);
    welcome.add(welcomeMessage2);

    JLabel welcomeCredits = new JLabel("Created by Satvik and Shubhangi");
    welcomeCredits.setForeground(Color.decode("#4E342E"));
    welcomeCredits.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    welcomeCredits.setHorizontalAlignment(0);

    container.add(welcome);
    container.add(welcomeCredits);

    this.add(container, BorderLayout.CENTER);

    pack();

  }

  private void setMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu startMenu = new JMenu("Start");
    JMenu helpMenu = new JMenu("Help");
    currentConfiguration = new JMenuItem("With current configuration");
    newConfiguration = new JMenuItem("Select new configuration");
    quitGame = new JMenuItem("Quit");
    helperItem = new JMenuItem("How to play game");
    startMenu.add(currentConfiguration);
    startMenu.add(newConfiguration);
    startMenu.add(quitGame);
    helpMenu.add(helperItem);
    menuBar.add(startMenu);
    menuBar.add(helpMenu);
    setJMenuBar(menuBar);
  }

  private File chooseFile() {
    File file = null;
    JFileChooser fc = new JFileChooser();
    fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
    fc.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
    int i = fc.showOpenDialog(this);
    if (i == JFileChooser.APPROVE_OPTION) {
      file = fc.getSelectedFile();
    }
    return file;
  }

  private void showHelperMenu() {

    UIManager.put("OptionPane.background", Color.decode("#E8F6F3"));
    UIManager.put("Panel.background", Color.TRANSLUCENT);
    UIManager.put("Label.background", Color.TRANSLUCENT);

    JPanel panel = new JPanel(new GridLayout(0, 2));

    panel.add(new JLabel("View Player Details"));
    panel.add(new JLabel(": Click on current player icon"));
    panel.add(new JLabel("Move Player"));
    panel.add(new JLabel(": Click on game board"));
    panel.add(new JLabel("Pick Weapon"));
    panel.add(new JLabel(": Press P"));
    panel.add(new JLabel("Look Around"));
    panel.add(new JLabel(": Press L"));
    panel.add(new JLabel("Attack Target"));
    panel.add(new JLabel(": Press A"));
    panel.add(new JLabel("Move Pet"));
    panel.add(new JLabel(": Press M"));

    JOptionPane.showMessageDialog(this, panel, "How to play game", JOptionPane.PLAIN_MESSAGE, null);

  }

  @Override
  public void addFeatures(FeatureInterface features) {
    currentConfiguration.addActionListener(l -> features.playGame(null));
    newConfiguration.addActionListener(l -> {
      File chosen = chooseFile();
      if (chosen != null) {
        features.playGame(chosen);
      }
    });
    quitGame.addActionListener(l -> features.quitGame());
    helperItem.addActionListener(l -> showHelperMenu());
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void close() {
    this.setVisible(false);
  }
}
