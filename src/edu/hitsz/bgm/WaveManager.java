package edu.hitsz.bgm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaveManager {
    private final Map<String, MusicPlayer> musicPlayers = new HashMap<>();
    private final Map<String, String> musicFilenames = new HashMap<>();
    private final Map<String, Boolean> musicLoopFlags = new HashMap<>();

    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // 饿汉模式
    private static WaveManager m_instance = new WaveManager();

    private WaveManager() {
        addMusic("bgm", "src/videos/bgm.wav", true);
        addMusic("bgm_boss", "src/videos/bgm_boss.wav", true);
        addMusic("bomb_explosion", "src/videos/bomb_explosion.wav", false);
        addMusic("bullet", "src/videos/bullet.wav", false);
        addMusic("bullet_hit", "src/videos/bullet_hit.wav", false);
        addMusic("game_over", "src/videos/game_over.wav", false);
        addMusic("get_supply", "src/videos/get_supply.wav", false);
    }

    public static WaveManager getM_instance() {
        return m_instance;
    }

    public void addMusic(String identifier, String filename, boolean loop) {
        if (!musicFilenames.containsKey(identifier)) {
            musicFilenames.put(identifier, filename);
            musicLoopFlags.put(identifier, loop);
        } else {
            System.out.println("Music with this identifier already exists: " + identifier);
        }
    }

    public void stopMusic(String identifier) {
        MusicPlayer player = musicPlayers.get(identifier);
        if (player != null) {
            player.stopMusic();
            musicPlayers.remove(identifier);
        } else {
            // 关闭的时候，可能没有这个歌曲
            System.out.println("No music found with this identifier: " + identifier);
        }
    }

    public void playMusic(String identifier) {
        stopMusic(identifier);

        String filename = musicFilenames.get(identifier);
        Boolean loop = musicLoopFlags.get(identifier);

        if (filename != null && loop != null) {
            MusicPlayer player = new MusicPlayer(filename, loop);
            musicPlayers.put(identifier, player);
            threadPool.submit(() -> {
                player.startMusic();
            });
            System.out.println("Playing music: " + identifier);
        } else {
            System.out.println("No music file associated with this identifier: " + identifier);
        }
    }

    public boolean isPlaying(String identifier) {
        return musicPlayers.containsKey(identifier);
    }

    public int activeMusicCount() {
        return musicPlayers.size();
    }
}
