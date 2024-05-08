package edu.hitsz.aircraft.bgm;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends Thread {

    // Audio file name and other member variables
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;
    private boolean loop;
    private volatile boolean stopRequested;

    public MusicThread(String filename, boolean loop) {
        // Initialize filename and loop flag
        this.filename = filename;
        this.loop = loop;
        this.stopRequested = false;
        reverseMusic();
    }

    // Load the audio data into memory
    public void reverseMusic() {
        try {
            // Use AudioInputStream to receive audio data from the specified file
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            // Get the audio format from the AudioInputStream
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    // Read samples from the audio input stream
    public byte[] getSamples(AudioInputStream stream) {
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

    // Play the audio stream with loop and stop support
    public void play(InputStream source) {
        int bufferSize = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[bufferSize];
        SourceDataLine dataLine = null;
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
        System.out.println("Audio playback started");

        try {
            while (!stopRequested) {
                int numBytesRead;
                // Reset the stream if looping
                source.reset();
                // Inner loop to read and write audio data
                while ((numBytesRead = source.read(buffer, 0, buffer.length)) != -1 && !stopRequested) {
                    dataLine.write(buffer, 0, numBytesRead);
                    System.out.println("Playing audio data...");
                }
                // Break if looping isn't required
                if (!loop) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Always close the data line gracefully
            dataLine.drain();
            dataLine.stop();
            dataLine.close();
            System.out.println("Audio playback stopped");
        }
    }

    // Method to request stopping the music
    public void stopMusic() {
        stopRequested = true;
    }

    @Override
    public void run() {
        InputStream stream = new ByteArrayInputStream(samples);
        try {
            // Mark the stream for resetting when looping
            stream.mark(samples.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        play(stream);
        System.out.println("end void run()");
    }
}
