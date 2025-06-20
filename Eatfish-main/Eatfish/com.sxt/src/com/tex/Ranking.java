package com.tex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ranking {
    private static final String FILE_PATH = "ranking.txt";

    // 获取当前最高分
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

    // 保存最新分数
    public static void saveHighScore(int score) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write(score + "");
            writer.close();
        } catch (IOException ignored) {}
    }

    // 获取排行榜列表
    public static List<String> getTopScores() {
        List<String> scores = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(FILE_PATH))) {
            while (sc.hasNextLine()) {
                scores.add(sc.nextLine());
            }
        } catch (Exception ignored) {}
        return scores;
    }

    // 新增：保存多个分数（可扩展 Top N）
    public static void saveScore(int score) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
            writer.newLine();
            writer.write(score + "");
            writer.close();
        } catch (IOException ignored) {}
    }
}
