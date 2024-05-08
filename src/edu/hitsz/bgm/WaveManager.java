package edu.hitsz.bgm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaveManager {
    // Maps to manage thread, filenames, and loop flags
    private final Map<String, MusicThread> musicThreads = new HashMap<>();
    private final Map<String, String> musicFilenames = new HashMap<>();
    private final Map<String, Boolean> musicLoopFlags = new HashMap<>();

    // Create a thread pool using the number of available processors
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // Singleton instance
    private static WaveManager instance;

    // Private constructor to prevent instantiation
    private WaveManager() {
        // Initialize and add your audio tracks
        addMusic("bgm", "src/videos/bgm.wav", true);
        addMusic("bgm_boss", "src/videos/bgm_boss.wav", true);
        addMusic("bomb_explosion", "src/videos/bomb_explosion.wav", false);
        addMusic("bullet", "src/videos/bullet.wav", false);
        addMusic("bullet_hit", "src/videos/bullet_hit.wav", false);
        addMusic("game_over", "src/videos/game_over.wav",false);
        addMusic("get_supply", "src/videos/get_supply.wav", false);
    }

    // Get the singleton instance
    public static WaveManager getInstance() {
        if (instance == null) {
            synchronized (WaveManager.class) {
                if (instance == null) {
                    instance = new WaveManager();
                }
            }
        }
        return instance;
    }

    // Adds a new music entry without starting it
    public void addMusic(String identifier, String filename, boolean loop) {
        if (!musicFilenames.containsKey(identifier)) {
            musicFilenames.put(identifier, filename);
            musicLoopFlags.put(identifier, loop);
        } else {
            System.out.println("Music with this identifier already exists: " + identifier);
        }
    }

    // Stops a specific music thread by identifier
    public void stopMusic(String identifier) {
        MusicThread musicThread = musicThreads.get(identifier);
        if (musicThread != null) {
            musicThread.stopMusic();
            // No need to call join because we're using the thread pool
            musicThreads.remove(identifier);
        } else {
            System.out.println("No music found with this identifier: " + identifier);
        }
    }

//    // Stops all music threads
//    public void stopAllMusic() {
//        for (String identifier : musicThreads.keySet()) {
//            stopMusic(identifier);
//        }
//        threadPool.shutdown();
//    }

    // Plays a specific music thread by identifier, creating a new thread if necessary
    public void playMusic(String identifier) {
        stopMusic(identifier); // Stop if currently playing

        // Retrieve the filename and loop flag from saved configurations
        String filename = musicFilenames.get(identifier);
        Boolean loop = musicLoopFlags.get(identifier);

        if (filename != null && loop != null) {
            // Create a new music thread as a runnable task
            MusicThread musicThread = new MusicThread(filename, loop);
            musicThreads.put(identifier, musicThread);

            // Submit the task to the thread pool
            threadPool.submit(musicThread);
            System.out.println("Playing music: " + identifier);
        } else {
            System.out.println("No music file associated with this identifier: " + identifier);
        }
    }

    // Checks if a particular music thread is playing
    public boolean isPlaying(String identifier) {
        return musicThreads.containsKey(identifier);
    }

    // Retrieves the current number of active music threads
    public int activeMusicCount() {
        return musicThreads.size();
    }
}
