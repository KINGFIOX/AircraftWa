package edu.hitsz.application;

import config.CONFIG;
import edu.hitsz.DataIO.IRankList;
import edu.hitsz.DataIO.TreeMapRankList;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 程序入口
 *
 * @author hitsz
 */
public class Main {

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
        Game game = new Game();

        frame.add(game);
        frame.setVisible(true);

        // 游戏的逻辑
        game.action();

        // TODO 显示排行榜 ？
        IRankList scoreboard = new TreeMapRankList();
        try {
            scoreboard.load("data/ScoreBoard.csv");
        } catch (IOException e) {
            System.out.println(e);
        }
//        ScoreBoardSJPanel sbp = new ScoreBoardSJPanel(scoreboard);
//        frame.setContentPane(sbp);
//        frame.revalidate();
    }
}
