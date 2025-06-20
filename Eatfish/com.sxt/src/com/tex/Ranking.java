package com.tex;

import java.io.*;
import java.util.Scanner;

public class Ranking {
    private static final String FILE_PATH = "ranking.txt";

    public static int getHighScore() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return 0;
            Scanner sc = new Scanner(file);
            return sc.hasNextInt() ? sc.nextInt() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static void saveHighScore(int score) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write(score + "");
            writer.close();
        } catch (IOException ignored) {}
    }
}

