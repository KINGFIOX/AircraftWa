package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    static HeroAircraft heroAircraft;
    static List<BaseBullet> heroBullets;
    static List<BaseProp> props;

    @BeforeAll
    static void setUp() {
        System.out.println("**-- Execute before all test method in this class --**");
        heroAircraft = HeroAircraft.getInstace();
        heroBullets = new ArrayList<>();
        props = new ArrayList<>();
    }

    @AfterAll
    static void drop() {
        System.out.println("**-- Execute after all test method in this class --**");
        heroAircraft = null;
        heroBullets = null;
        props = null;
    }


    @Test
    void shoot() {
        int size1 = heroBullets.size();
        heroBullets.addAll(heroAircraft.shoot());
        int size2 = heroBullets.size();

        try {
            Field shootNumField = HeroAircraft.class.getDeclaredField("shootNum");
            shootNumField.setAccessible(true);  // 设置访问权限
            int shootNum = (Integer) shootNumField.get(heroAircraft);  // 获取shootNum的值

            assertEquals(shootNum, size2 - size1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection error: " + e.getMessage());
        }
    }

    @Test
    void addHp() {
        heroAircraft.addHp(-10);
        int size2 = heroAircraft.getHp();
        assert size2 == 1000 - 10;
    }

    @Test
    void addShootNum() {
        try {
            Field shootNumField = HeroAircraft.class.getDeclaredField("shootNum");
            shootNumField.setAccessible(true);  // 允许访问私有字段
            int size1 = (Integer) shootNumField.get(heroAircraft);  // 获取修改前的shootNum值
            heroAircraft.addShootNum(3);  // 增加shootNum的值
            int size2 = (Integer) shootNumField.get(heroAircraft);  // 获取修改后的shootNum值

            assertEquals(3, size2 - size1);  // 断言shootNum是否正确增加了3
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection error: " + e.getMessage());
        }
    }

    @Test
    void effect() {
    }

    @Test
    void forward() {
    }

    @Test
    void getInstace() {
    }
}