package UI;

import edu.hitsz.game.AbstractGame;
import edu.hitsz.bgm.WaveManager;
import edu.hitsz.game.EasyGame;
import edu.hitsz.game.HardGame;
import edu.hitsz.game.NormalGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {

    private JPanel mainPanel;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    private JCheckBox BGMCheckBox;

    public StartMenu() {

//        easyButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Main.cardPanel.add(new SimpleCalculator().getMainPanel());
//                Main.cardLayout.last(Main.cardPanel);
//            }
//        });
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractGame game = new EasyGame();  // 创建带有难度参数的新游戏
                startNewGame(game);
            }
        });
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractGame game = new NormalGame();  // 创建带有难度参数的新游戏
                startNewGame(game);
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractGame game = new HardGame();  // 创建带有难度参数的新游戏
                startNewGame(game);
            }
        });
        BGMCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 检查复选框是否已被选中
                // FIXME
                if (BGMCheckBox.isSelected()) {
                    // 启用背景音乐
                    WaveManager.getInstance().playMusic("bgm");
                } else {
                    // 停止背景音乐
                    WaveManager.getInstance().stopMusic("bgm");
                }
            }
        });
    }

    /**
     * TODO 重启游戏
     */
    private void startNewGame(AbstractGame game) {
        // 停止先前的游戏任务
        Main.cardPanel.add(game, "GamePanel");
        Main.cardLayout.show(Main.cardPanel, "GamePanel");
        game.action();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
