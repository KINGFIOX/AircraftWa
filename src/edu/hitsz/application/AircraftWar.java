package edu.hitsz.application;

import config.CONFIG;
import edu.hitsz.game.AbstractGame;
import edu.hitsz.game.EasyGame;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 *
 * @author hitsz
 */
public class AircraftWar {

    public static final int WINDOW_WIDTH = CONFIG.Windows.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = CONFIG.Windows.WINDOW_HEIGHT;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化 game
        AbstractGame abstractGame = new EasyGame();

        frame.add(abstractGame);
        frame.setVisible(true);

        // 游戏的逻辑
        abstractGame.action();

    }
}
