package com.tex;

import java.awt.*;

public class Background {
    void paintSelf(Graphics g,int fishLevel){
        g.drawImage(GameUtils.bgimg,0,0,null);
        switch (GameWin.state){
            case 0:
                GameUtils.drawWord(g,"开始游戏",Color.red,50,600,400);
                break;
            case 1:
                GameUtils.drawWord(g,"得分"+GameUtils.count,Color.MAGENTA,50,180,120);
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.MAGENTA,50,600,120);
                GameUtils.drawWord(g,"等级"+fishLevel,Color.red,50,1000,120);
                break;
            case 2:
                GameUtils.drawWord(g,"得分"+GameUtils.count,Color.MAGENTA,50,180,120);
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.MAGENTA,50,600,120);
                GameUtils.drawWord(g,"等级"+fishLevel,Color.red,50,1000,120);
                break;
            case 3:
                GameUtils.drawWord(g,"得分"+GameUtils.count,Color.MAGENTA,50,180,120);
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.MAGENTA,50,600,120);
                GameUtils.drawWord(g,"等级"+fishLevel,Color.red,50,1000,120);
                GameUtils.drawWord(g,"胜利",Color.red,80,600,400);
                break;
            case 4:
                break;
            default:

        }

    }
}
