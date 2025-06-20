package com.tex;

import java.awt.*;

public class Background {
    /*
     * 绘制背景和游戏状态信息的方法
     * g 用于绘制的 Graphics 对象
     * fishLevel 玩家鱼的等级
    */

    void paintSelf(Graphics g,int fishLevel){
        // 绘制背景图片，将背景图片绘制在窗口的左上角
        g.drawImage(GameUtils.bgimg,0,0,null);

        // 根据游戏的不同状态，绘制不同的信息
        switch (GameWin.state){
            case 0:
               // 游戏开始界面状态
              // 在指定位置绘制“开始游戏”文字，颜色为红色，字体大小为 50
                GameUtils.drawWord(g,"开始游戏",Color.red,50,600,400);
                break;
            case 1:
                // 游戏进行中状态
                // 绘制得分信息，颜色为洋红色，字体大小为 50
                GameUtils.drawWord(g,"得分"+GameUtils.count,Color.MAGENTA,50,180,120);
                // 绘制难度信息，颜色为洋红色，字体大小为 50
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.MAGENTA,50,600,120);
                // 绘制难度信息，颜色为洋红色，字体大小为 50
                GameUtils.drawWord(g,"等级"+fishLevel,Color.red,50,1000,120);
                break;
            case 2:
                // 游戏失败状态
                // 绘制得分信息，颜色为洋红色，字体大小为 50
                GameUtils.drawWord(g,"得分"+GameUtils.count,Color.MAGENTA,50,180,120);
                // 绘制难度信息，颜色为洋红色，字体大小为 50
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.MAGENTA,50,600,120);
                // 绘制玩家鱼的等级信息，颜色为红色，字体大小为 50
                GameUtils.drawWord(g,"等级"+fishLevel,Color.red,50,1000,120);
                break;
            case 3:
                // 游戏胜利状态
                // 绘制得分信息，颜色为洋红色，字体大小为 50
                GameUtils.drawWord(g,"得分"+GameUtils.count,Color.MAGENTA,50,180,120);
                // 绘制难度信息，颜色为洋红色，字体大小为 50
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.MAGENTA,50,600,120);
                // 绘制玩家鱼的等级信息，颜色为红色，字体大小为 50
                GameUtils.drawWord(g,"等级"+fishLevel,Color.red,50,1000,120);
                // 绘制“胜利”文字，颜色为红色，字体大小为 80
                GameUtils.drawWord(g,"胜利",Color.red,80,600,400);
                break;
            case 4:
                // 此状态下不进行额外绘制
                break;
            default:
                // 默认情况，不进行额外处理

        }

    }
}
