package com.tex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    //方向
    static boolean UP=false;
    static boolean DOWN=false;
    static boolean LEFT=false;
    static boolean RIGHT=false;

    //分数
    static int count=0;

    //敌方鱼类集合
    public static List<Enemy> enemyList =  new ArrayList<>();

    //关卡等级
    static int level=0;


    //背景图片
    public static Image bgimg = Toolkit.getDefaultToolkit().getImage("image/sea.jpg");

    //敌1图片
    public static Image enamy1_img = Toolkit.getDefaultToolkit().getImage("image/enemyFish/level1.1.png");
    public static Image enamy2_img = Toolkit.getDefaultToolkit().getImage("image/enemyFish/level1.2.png");

    //敌2图片
    public static Image enamy1_2_img = Toolkit.getDefaultToolkit().getImage("image/enemyFish/level2.1.png");
    public static Image enamy2_2_img = Toolkit.getDefaultToolkit().getImage("image/enemyFish/level2.2.png");

    //敌3图片
    public static Image enamy1_3_img = Toolkit.getDefaultToolkit().getImage("image/enemyFish/level3.1.png");
    public static Image enamy2_3_img = Toolkit.getDefaultToolkit().getImage("image/enemyFish/level3.2.png");

    //Boss
    public static Image Boss_img = Toolkit.getDefaultToolkit().getImage("image/enemyFish/boss.png");

    //我方鱼类
    public static Image Myfishing_L= Toolkit.getDefaultToolkit().getImage("image/myFish/left.png");
    public static Image Myfishing_R= Toolkit.getDefaultToolkit().getImage("image/myFish/right.png");

    //绘制文字的工具类
    public static void drawWord(Graphics g,String str,Color color,int size,int x,int y){
        g.setColor(color);
        g.setFont(new Font("微软雅黑",Font.BOLD,size));
        g.drawString(str,x,y);
    }
    //道具图片
    public static Image propSpeed = Toolkit.getDefaultToolkit().getImage("image/prop/speedUp.png");
    public static Image propShield= Toolkit.getDefaultToolkit().getImage("image/prop/shield.png");
    public static Image propScore = Toolkit.getDefaultToolkit().getImage("image/prop/scoreDouble.png");
}
