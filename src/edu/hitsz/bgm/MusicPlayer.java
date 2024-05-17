package edu.hitsz.bgm;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;

public class MusicPlayer implements Runnable {

    private final String filename;
    private final boolean loop;
    private final ExecutorService executor;

    private AudioFormat audioFormat;
    private byte[] samples;
    private volatile boolean stopRequested;

    public MusicPlayer(String filename, boolean loop) {
        this.filename = filename;
        this.loop = loop;
        this.stopRequested = false;
        //
        this.executor = Executors.newSingleThreadExecutor();
        loadMusic();
    }

    private void loadMusic() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    private void play(InputStream source) {
        int bufferSize = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[bufferSize];
        SourceDataLine dataLine;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, bufferSize);
        } catch (LineUnavailableException e) {
            System.err.println("Unable to open the audio line");
            e.printStackTrace();
            return;
        }

        dataLine.start();

        try {
            while (!stopRequested) {
                int numBytesRead;
                source.reset();
                while ((numBytesRead = source.read(buffer, 0, buffer.length)) != -1 && !stopRequested) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
                if (!loop) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dataLine.drain();
            dataLine.stop();
            dataLine.close();
            System.out.println("Audio playback stopped");
        }
    }

    public void stopMusic() {
        stopRequested = true;
        executor.shutdownNow(); // Attempts to stop all actively executing tasks
    }

    @Override
    public void run() {
        InputStream stream = new ByteArrayInputStream(samples);
        try {
            stream.mark(samples.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        play(stream);
    }

    public void startMusic() {
        executor.execute(this);
    }
}
