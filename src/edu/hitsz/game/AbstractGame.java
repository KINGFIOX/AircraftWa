package edu.hitsz.game;

import UI.Main;
import edu.hitsz.application.RANDOM;
import edu.hitsz.config.CONFIG;
import edu.hitsz.aircraft.*;
import edu.hitsz.DataIO.Entry;
import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.bgm.WaveManager;
import edu.hitsz.aircraft.enemy.BossAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.aircraft.enemy.EnemyAircraft;
import edu.hitsz.enemyfactory.BossEnemyFactory;
import edu.hitsz.enemyfactory.IEnemyAircraftFactory;
import edu.hitsz.observe.PropGenerator;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.observe.EnemyAircraftGenerator;

import edu.hitsz.observe.ScoreNotifier;
import edu.hitsz.prop.bomb.BombProp;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class AbstractGame extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = CONFIG.Game.TIME_INTERVAL;

    private final List<BaseBullet> heroBullets = new LinkedList<>();
    private final List<EnemyAircraft> enemyAircrafts = new LinkedList<>();
    private final List<BaseBullet> enemyBullets = new LinkedList<>();
    private final List<BaseProp> props = new LinkedList<>();

    /**
     * TODO 这个可能随着时间的推移而改变
     */
    private int enemyMaxNumber = CONFIG.Game.ENEMY_MAX_NUMBER;

    /**
     * 当前得分
     */
    private int score = 0;

    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private final int cycleDuration = CONFIG.Game.CYCLE_DURATION;
    private int cycleTime = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    /* ---------- ---------- 模板 begin ---------- ---------- */

    /**
     * 分数的观察者, 去掉单例模式，一定要在子类中添加 scoreObserver.addSubscriber(enemyGenerator);
     */
    private final ScoreNotifier scoreObserver = new ScoreNotifier();

    protected EnemyAircraftGenerator enemyGenerator;

    abstract protected void initEnemyGen();

    protected PropGenerator propGenerator;

    abstract protected void initPropGen();

    abstract protected void initBackground();

    protected HeroAircraft heroAircraft;

    abstract protected void initHero();

    // TODO 构造函数是 模板函数
    public AbstractGame() {

        // 1. 初始化 英雄飞机
        initHero();

        // 2. 初始化背景
        initBackground();

        // 3. 初始化一系列 Generator
        initEnemyGen();
        initPropGen();

        // 4. 将 enemyGenerator 放到 notifier 中
        scoreObserver.addSubscriber(enemyGenerator);

        // 5. 启动英雄飞机的监听
        new HeroController(this, heroAircraft);

    }

    /* ---------- ---------- 模板 end ---------- ---------- */

    /**
     * 这里是 boss
     */
    private final static IEnemyAircraftFactory bossFactory = new BossEnemyFactory();
    private int lastBossScore = 0;

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            // 当前时间
            this.time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生

                if (enemyAircrafts.size() < enemyMaxNumber) {

                    // enemyAircrafts.add();
                    // boss 产生器
                    genBoss();

                    // EnemyAircraftGenerator::generateEnemy();
                    enemyAircrafts.add(enemyGenerator.generateEnemy());
                }

                scoreObserver.update(score);

                // 飞机射出子弹
                shootAction();

            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理，清理战场
            postProcessAction();

            // 每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {

                // 悄咪咪的写一条数据
                File file = new File(CONFIG.Game.DATA_PATH);
                try (FileWriter fileWriter = new FileWriter(file, true)) {
                    Entry e = new Entry(score, System.currentTimeMillis());
                    // 写入新数据并换行
                    fileWriter.write(e.toString() + System.lineSeparator());
                    System.out.println("Data has been successfully appended to the file.");
                } catch (IOException e) {
                    // 捕获并打印异常
                    System.err.println("An error occurred while appending data to the file: " + e.getMessage());
                }

                // FIXME 游戏结束
                this.executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");

                // 关闭 bgm
                if (WaveManager.m_instance.isPlaying("bgm_boss")) {
                    WaveManager.m_instance.stopMusic("bgm_boss");
                }
                if (WaveManager.m_instance.isPlaying("bgm")) {
                    WaveManager.m_instance.stopMusic("bgm");
                }

                WaveManager.m_instance.playMusic("game_over");
                SwingUtilities.invokeLater(() -> {
                    // Main.cardPanel.add();
                    // 切换到 SimpleTable 面板
                    SwingUtilities.invokeLater(() -> {
                        Main.cardLayout.show(Main.cardPanel, "scoreBoard menu");
                    });
                });

            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        this.executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    // ***********************
    // Action 各部分
    // ***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }

        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    /**
     * 子弹移动
     */
    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }

        // TODO 敌人的子弹移动
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void propMoveAction() {
        for (BaseProp p : props) {
            p.forward();
        }
    }

    /**
     * 这里是同步的 刷新 boss
     */
    private void genBoss() {
        if (score - lastBossScore >= CONFIG.Game.BOSS_EVERY_SCORE) {
            // 保证战场上只有一个 boss
            boolean existed = enemyAircrafts.stream().anyMatch(x -> (x instanceof BossAircraft));
            if (!existed) {
                int locationX = (int) (Math.random()
                        * (CONFIG.Windows.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())); // 假设游戏宽度为300
                int locationY = (int) (Math.random() * CONFIG.Windows.WINDOW_HEIGHT * 0.05);
                int speedX = RANDOM.getRandom(CONFIG.Game.speedX);
                int speedY = RANDOM.getRandom(CONFIG.Game.speedY); // 假设速度在5到14之间
                enemyAircrafts
                        .add(bossFactory.createEnemyAircraft(locationX, locationY, speedX >> 2, speedY >> 2, 500, 100));
                // 播放音频
                WaveManager.m_instance.playMusic("bgm_boss");
                lastBossScore = score;
            }
        }
    }

    /**
     * 敌机移动
     */
    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 敌机子弹撞击到英雄机
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        // 感觉很操蛋的一点，因为 hero 一定会发射子弹，因此其实可以把这个 for 当做一定会执行
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    // 音乐
                    WaveManager.m_instance.playMusic("bullet_hit");

                    if (enemyAircraft.notValid()) { // 敌人的飞机被击毁
                        score += enemyAircraft.getScore();

                        if (enemyAircraft instanceof BossAircraft) {
                            // 关闭音乐
                            WaveManager.m_instance.stopMusic("bgm_boss");
                        }

                        // 听说你喜欢奖励
                        List<BaseProp> p = enemyAircraft.award(propGenerator);
                        props.addAll(p);
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        for (BaseProp p : props) {
            if (p.notValid()) {
                continue;
            }
            if (heroAircraft.crash(p)) {
                // 敌机子弹撞击到英雄机
                // 英雄机损失一定生命值
                if (p instanceof BombProp) {
                    ((BombProp) p).emitNotify(enemyBullets, enemyAircrafts);
                } else {
                    heroAircraft.effect(p);
                }
                p.vanish();
            }
            score += p.getScore(); // 加分
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    // ***********************
    // Paint 各部分
    // ***********************

    protected BufferedImage bg;

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(bg, 0, this.backGroundTop - CONFIG.Windows.WINDOW_HEIGHT, null);
        g.drawImage(bg, 0, this.backGroundTop, null);
        // 循环滚动图片
        this.backGroundTop += 1;
        if (this.backGroundTop == CONFIG.Windows.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        // 单独 draw 英雄机
        g.drawImage(ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2,
                null);

        // 绘制得分和生命值
        paintScoreAndLife(g);

    }

    /**
     * @param g
     * @param objects
     */
    // List<? extends AbstractFlyingObject> 是一种约束
    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    /**
     * 显示 得分 和 颜色
     *
     * @param g
     */
    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

}
