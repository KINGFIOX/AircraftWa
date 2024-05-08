package UI;

import config.CONFIG;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static final CardLayout cardLayout = new CardLayout(0, 0);
    public static final JPanel cardPanel = new JPanel(cardLayout);

    public static final int WINDOW_WIDTH = CONFIG.Windows.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = CONFIG.Windows.WINDOW_HEIGHT;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加 startMenu
        StartMenu startMenu = new StartMenu();
        cardPanel.add(startMenu.getMainPanel(), "startMenu menu");

        ScoreBoard scoreBoard = new ScoreBoard();
        cardPanel.add(scoreBoard.getMainPanel(), "scoreBoard menu");

        frame.add(cardPanel);
        frame.setVisible(true);
        cardLayout.show(cardPanel, "startMenu menu");
    }
}