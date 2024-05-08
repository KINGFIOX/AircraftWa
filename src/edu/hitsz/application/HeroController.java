package edu.hitsz.application;

import edu.hitsz.game.AbstractGame;
import edu.hitsz.aircraft.HeroAircraft;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 英雄机控制类
 * 监听鼠标，控制英雄机的移动
 *
 * @author hitsz
 */
public class HeroController {
    private AbstractGame abstractGame;
    private HeroAircraft heroAircraft;
    private MouseAdapter mouseAdapter;

    private KeyAdapter keyAdapter; // FIXME

    public HeroController(AbstractGame abstractGame, HeroAircraft heroAircraft) {
        this.abstractGame = abstractGame;
        this.heroAircraft = heroAircraft;

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int x = e.getX();
                int y = e.getY();
                if (x < 0 || x > AircraftWar.WINDOW_WIDTH || y < 0 || y > AircraftWar.WINDOW_HEIGHT) {
                    // 防止超出边界
                    return;
                }
                heroAircraft.setLocation(x, y);
            }
        };

        abstractGame.addMouseListener(mouseAdapter);
        abstractGame.addMouseMotionListener(mouseAdapter);

        // 键盘事件监听器
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W: // 向上移动
                        moveHeroAircraft(heroAircraft.getLocationX(), heroAircraft.getLocationY() - 10);
                        break;
                    case KeyEvent.VK_A: // 向左移动
                        moveHeroAircraft(heroAircraft.getLocationX() - 10, heroAircraft.getLocationY());
                        break;
                    case KeyEvent.VK_S: // 向下移动
                        moveHeroAircraft(heroAircraft.getLocationX(), heroAircraft.getLocationY() + 10);
                        break;
                    case KeyEvent.VK_D: // 向右移动
                        moveHeroAircraft(heroAircraft.getLocationX() + 10, heroAircraft.getLocationY());
                        break;
                    default:
                        break;
                }
            }
        };

        abstractGame.addKeyListener(keyAdapter);
        abstractGame.setFocusable(true); // 确保游戏窗口能够接收键盘事件
    }

    // 移动HeroAircraft，并检查边界
    private void moveHeroAircraft(int x, int y) {
        if (x < 0 || x > AircraftWar.WINDOW_WIDTH || y < 0 || y > AircraftWar.WINDOW_HEIGHT) {
            // 防止超出边界
            return;
        }
        heroAircraft.setLocation(x, y);
    }
}
