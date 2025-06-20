// 声明该类所在的包为 com.tex
package com.tex;

// 导入用于文件输入输出操作的类
import java.io.*;
// 导入 Scanner 类，用于从文件中读取数据
import java.util.Scanner;

/**
 * Ranking 类用于管理游戏的排行榜功能，主要实现获取最高分和保存最高分的功能。
 * 最高分数据会存储在指定的文件中。
 */
public class Ranking {
    // 定义一个常量，用于存储排行榜数据的文件路径
    // 这里将文件命名为 ranking.txt，用于持久化保存游戏的最高分信息
    private static final String FILE_PATH = "ranking.txt";

    /**
     * 获取游戏的历史最高分。
     * 该方法会尝试从指定文件中读取最高分，如果文件不存在或读取过程中出现异常，则返回 0。
     *
     * @return 返回从文件中读取到的最高分，如果文件不存在或读取失败则返回 0
     */
    public static int getHighScore() {
        try {
            // 创建一个 File 对象，指向存储排行榜数据的文件

            File file = new File(FILE_PATH);
            // 检查文件是否存在，如果文件不存在，则直接返回 0

            if (!file.exists()) return 0;
            // 创建一个 Scanner 对象，用于从文件中读取数据

            Scanner sc = new Scanner(file);
            // 检查文件中是否有下一个整数，如果有则返回该整数，否则返回 0
            return sc.hasNextInt() ? sc.nextInt() : 0;
        } catch (Exception e) {
            // 如果在读取文件过程中出现异常，捕获该异常并返回 0
            return 0;
        }
    }

    /**
     * 保存游戏的最高分。
     * 该方法会将传入的分数写入到指定的文件中，如果写入过程中出现异常则忽略。
     *
     * @param score 要保存的游戏分数
     */
    public static void saveHighScore(int score) {
        try {
            // 创建一个 FileWriter 对象，用于将数据写入到指定文件中
            // 如果文件不存在，FileWriter 会自动创建该文件

            FileWriter writer = new FileWriter(FILE_PATH);
            // 将传入的分数转换为字符串并写入文件

            writer.write(score + "");
            // 关闭 FileWriter 对象，释放相关资源
            writer.close();
        } catch (IOException ignored) {
            // 如果在写入文件过程中出现 IO 异常，忽略该异常
        }
    }
}