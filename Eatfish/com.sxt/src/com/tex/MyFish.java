package com.tex;

import java.awt.*;

public class MyFish {
    Image img=GameUtils.Myfishing_L;

    //横坐标
    int x=600;


    //纵坐标
    int y=500;

    int width=50;
    int height=50;
    int speed=20;
    int level=1;



    void logic(){
        if (GameUtils.UP){
            y-=speed;
        }
        if (GameUtils.DOWN){
            y+=speed;
        }
        if (GameUtils.LEFT){
            x-=speed;
            img=GameUtils.Myfishing_L;
        }
        if (GameUtils.RIGHT){
            x+=speed;
            img=GameUtils.Myfishing_R;
        }
    }
    //定义鱼的方法
    public void paintSelf(Graphics g)
    {

        logic();
        g.drawImage(img,x,y,width+GameUtils.count,height+GameUtils.count,null);
    }
    //定义获取矩形，用于碰撞检测
    public Rectangle getRec(){
        return new Rectangle(x,y,width+GameUtils.count,height+GameUtils.count);
    }
    public boolean isShielded = false;


}
