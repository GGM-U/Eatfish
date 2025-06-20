package com.tex;

import javax.sound.sampled.*;
import java.io.File;

public class SoundUtils {
    public static void play(String path) {
        try {
            File file = new File(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
