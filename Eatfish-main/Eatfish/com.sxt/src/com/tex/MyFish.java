package com.tex;

import java.awt.*;

public class MyFish {
    //我方鱼类当前显示的图片，初始为向左的图片
    Image img=GameUtils.Myfishing_L;

    //横坐标
    int x=600;


    //纵坐标
    int y=500;

    //初始宽度
    int width=50;
    //初始高度
    int height=50;
    //我方鱼的移动速度
    int speed=20;
    //我方鱼的等级
    int level=1;



    // 处理我方鱼类的移动逻辑，根据方向控制变量更新位置和图片
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
    // 绘制我方鱼类的方法，先处理移动逻辑，再绘制鱼类图片
    public void paintSelf(Graphics g)
    {

        logic();
        g.drawImage(img,x,y,width+GameUtils.count,height+GameUtils.count,null);
    }
    //定义获取矩形，用于碰撞检测
    public Rectangle getRec(){
        return new Rectangle(x,y,width+GameUtils.count,height+GameUtils.count);
    }
    // 表示我方鱼类是否处于护盾保护状态

    public boolean isShielded = false;


}
