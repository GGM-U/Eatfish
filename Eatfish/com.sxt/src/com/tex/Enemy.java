package com.tex;

import java.awt.*;

public class Enemy {
    Image img;
    int x, y;
    int speed;
    int width, height;
    int dir =1;
    int tybe;
    int count=1;
    public void paintSelf(Graphics g)
    {
        g.drawImage(img,x,y,width,height,null);
    }
    public Rectangle getRec()
    {
        return new Rectangle(x,y,width,height);
    }

}
class EnemyA extends Enemy
{
    EnemyA()
    {
        this.x=-50;
        this.y=(int)(Math.random()*600+100);
        this.width=50;
        this.height=50;
        this.speed=10;
        this.tybe=1;
        this.count=1;
        this.img=GameUtils.enamy1_img;
    }
}
class EnemyA_2 extends Enemy
{
    EnemyA_2()
    {
        this.x=1300;
        this.y=(int)(Math.random()*600+100);
        this.width=50;
        this.height=50;
        this.speed=-10;
        this.tybe=1;
        this.count=1;
        this.img=GameUtils.enamy2_img;
    }
}
class EnemyB extends Enemy
{
    EnemyB()
    {
        this.x=-100;
        this.y=(int)(Math.random()*600+100);
        this.width=100;
        this.height=100;
        this.speed=5;
        this.tybe=2;
        this.count=2;
        this.img=GameUtils.enamy1_2_img;
    }
}
class EnemyB_2 extends Enemy
{
    EnemyB_2()
    {
        this.x=1300;
        this.y=(int)(Math.random()*600+100);
        this.width=100;
        this.height=100;
        this.speed=-5;
        this.tybe=2;
        this.count=2;
        this.img=GameUtils.enamy2_2_img;
    }
}
class EnemyC extends Enemy
{
    EnemyC()
    {
        this.x=-200;
        this.y=(int)(Math.random()*600+100);
        this.width=200;
        this.height=200;
        this.speed=10;
        this.tybe=3;
        this.img=GameUtils.enamy1_3_img;

    }
    @Override
    public Rectangle getRec()
    {
        return new Rectangle(x+40,y+30,width-80,height-60);
    }
}
class EnemyC_2 extends Enemy
{
    EnemyC_2()
    {
        this.x=1300;
        this.y=(int)(Math.random()*600+100);
        this.width=200;
        this.height=200;
        this.speed=-10;
        this.tybe=3;
        this.img=GameUtils.enamy2_3_img;
    }

}
class EnemyBoss extends Enemy
{
    EnemyBoss()
    {
        this.x=-1000;
        this.y=(int)(Math.random()*600+100);
        this.width=340;
        this.height=340;
        this.speed=80;
        this.tybe=10;
        this.count=0;
        this.img=GameUtils.Boss_img;
    }
}
