package com.tex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame {
    static int state =0;//定义窗体状态
    Image offScreenImage;
    int width =1328;
    int height =830;

    double random;
    int time=0;
    Background  background = new Background();

    Enemy enemy;//敌方鱼类

    Enemy boss;

    boolean isboss= false;
    Prop prop = null; // 当前屏幕上的道具

    MyFish myFish = new MyFish();//自己的鱼类

    public void launch() {
        this.setVisible(true);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
       // this.setResizable( false);
        this.setTitle("大鱼吃小鱼");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseListener(new MouseAdapter(){
        @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==1&&state==0){
                    state=1;
                    repaint();
                }
        }
    });


        //键盘移动
        this.addKeyListener(new KeyAdapter() {
            @Override//按压键盘
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                //使用WASD进行移动
                if (e.getKeyCode()==87){
                    GameUtils.UP=true;
                }
                if (e.getKeyCode()==83){
                    GameUtils.DOWN=true;
                }
                if (e.getKeyCode()==65){
                    GameUtils.LEFT=true;
                }
                if (e.getKeyCode()==68){
                    GameUtils.RIGHT=true;
                }

            }

            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                //松开按键
                if (e.getKeyCode()==87){
                    GameUtils.UP=false;
                }
                if (e.getKeyCode()==83){
                    GameUtils.DOWN=false;
                }
                if (e.getKeyCode()==65){
                    GameUtils.LEFT=false;
                }
                if (e.getKeyCode()==68){
                    GameUtils.RIGHT=false;
                }
            }
        });


        while (true){
            repaint();
            time++;
            if (time % 300 == 0 && prop == null) {
                prop = PropFactory.createProp();
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}
    @Override
    public void paint(Graphics g) {
    offScreenImage=createImage(width,height);
    Graphics gImage = offScreenImage.getGraphics();
    background.paintSelf(gImage,myFish.level);

    switch (state){
        case 0:

            break;
        case 1:
            myFish.paintSelf(gImage);
            logic();
            for (Enemy enemy : GameUtils.enemyList){
                enemy.paintSelf(gImage);
            }
            if (isboss){
                boss.x = boss.x + boss.dir+boss.speed;
                boss.paintSelf(gImage);
            }
            if (prop != null) {
                prop.y += 5; // 道具下落速度
                if (prop.y > height) {
                    prop = null; // 超出屏幕则移除
                } else {
                    prop.paintSelf(gImage);
                }
            }

            break;
        case 2:
            for (Enemy enemy : GameUtils.enemyList){
                enemy.paintSelf(gImage);
            }
            if (isboss){
                boss.x = boss.x + boss.dir+boss.speed;
                boss.paintSelf(gImage);
                if (boss.x<0){
                    gImage.setColor(Color.red);
                    gImage.fillRect(boss.x,boss.y,2400,boss.height/30);
                }
            }
            break;
        case 3:
            myFish.paintSelf(gImage);
            break;
        case 4:
            if (time % 50 == 0 && !isboss) {
                boss = new EnemyBoss();
                isboss = true;
                GameUtils.enemyList.add(boss); // 添加到敌人列表
            }
            break;
            default:
    }
    g.drawImage(offScreenImage,0,0,null);
    }

    void logic(){
        if (GameUtils.count<5){
            GameUtils.level=0;
            myFish.level=1;
        }else if (GameUtils.count<15){
            GameUtils.level=1;
        }else if (GameUtils.count<50){
            GameUtils.level=2;
            myFish.level=2;
        }else if (GameUtils.count<150){
            GameUtils.level=3;
            myFish.level=3;
        }else if (GameUtils.count<300){
            GameUtils.level=4;
            myFish.level=3;
        }else if (GameUtils.count>=300){
            state=3;
        }




        random=Math.random();

        switch (GameUtils.level){
            case 4:
                if (time%50==0) {
                    if (random > 0.5) {
                        boss = new EnemyBoss();
                        isboss=true;
                    }
                }
            case 3:
            case 2:
                if (time%30==0) {
                    if (random > 0.5) {
                        enemy = new EnemyC();
                    } else {
                        enemy = new EnemyC_2();
                    }
                    GameUtils.enemyList.add(enemy);
                }

            case 1:
                if (time%20==0) {
                    if (random > 0.5) {
                        enemy = new EnemyB();
                    } else {
                        enemy = new EnemyB_2();
                    }
                    GameUtils.enemyList.add(enemy);
                }
            case 0:
                if (time%10==0) {
                    if (random > 0.5) {
                        enemy = new EnemyA();
                    } else {
                        enemy = new EnemyA_2();
                    }
                    GameUtils.enemyList.add(enemy);
                }


                break;
                default:


        }

        for (Enemy enemy : GameUtils.enemyList){
            enemy.x = enemy.x+ enemy.dir+ enemy.speed;
            if (isboss && boss != null) {
                if (boss.getRec().intersects(enemy.getRec())) {
                    enemy.x = -200;
                    enemy.y = -200;
                }
                if (boss.getRec().intersects(myFish.getRec())) {
                    state = 2;
                }
            }


            //碰撞检测
           if(myFish.getRec().intersects(enemy.getRec())) {
               if (myFish.level>=enemy.tybe){
                   System.out.println("碰撞了");
                   enemy.x=-200;
                   enemy.y=-200;
                   GameUtils.count=GameUtils.count+ enemy.count;
               }
               else {
                   state=2;
               }

           }
        }
        // 每隔一段时间生成一个道具
        if (time % 300 == 0 && prop == null) {
            prop = PropFactory.createProp();
        }
        // 道具碰撞检测
        if (prop != null && myFish.getRec().intersects(prop.getRec())) {
            applyPropEffect(prop.type); // 应用道具效果
            SoundUtils.play("sound/prop.wav"); // 可选：播放音效
            prop = null; // 使用后移除道具
        }


    }



    private void applyPropEffect(Prop.Type type) {
        switch (type) {
            case SPEED_UP:
                myFish.speed += 10; // 加速效果
                System.out.println("获得加速！");
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                myFish.speed -= 10; // 5秒后恢复速度
                            }
                        }, 5000); // 5秒持续时间
                break;

            case SHIELD:
                System.out.println("获得护盾！无敌5秒");
                // 假设你有 isShielded 变量来控制无敌状态
                myFish.isShielded = true;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                myFish.isShielded = false;
                            }
                        }, 5000);
                break;

            case SCORE_DOUBLE:
                System.out.println("得分翻倍！持续5秒");
                GameUtils.count += 10; // 示例：直接加分
                // 或者开启一个临时计数器，让每吃一个敌人 +2 分
                break;
        }
    }


    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
