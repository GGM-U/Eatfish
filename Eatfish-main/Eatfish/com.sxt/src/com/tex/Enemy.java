package com.tex;

import java.awt.*;

// 敌方鱼类的基类，定义了敌方鱼类的基本属性和方法
public class Enemy {
    // 敌方鱼的图片
    Image img;
    // 敌方鱼的横坐标
    int x, y;
    // 敌方鱼的移动速度
    int speed;
    // 敌方鱼的宽度和高度
    int width, height;
    // 敌方鱼的移动方向，默认为1
    int dir =1;
    // 敌方鱼的类型
    int tybe;
    // 被吃掉后获得的分数，默认为1
    int count=1;
    //绘制敌方鱼的方法
    public void paintSelf(Graphics g)
    {

        // 在指定位置绘制敌方鱼的图片
        g.drawImage(img,x,y,width,height,null);
    }
    //获取敌方鱼的矩形区域，用于碰撞检测
    public Rectangle getRec()
    {

        return new Rectangle(x,y,width,height);
    }

}
// 敌方A类鱼，从左边出现向右移动
class EnemyA extends Enemy
{
    /**
     * 构造函数，初始化敌方A类鱼的属性
     */
    EnemyA()
    {
        // 初始横坐标在屏幕左侧外
        this.x=-50;
        // 初始纵坐标随机在一定范围内
        this.y=(int)(Math.random()*600+100);
        // 鱼的宽度
        this.width=50;
        // 鱼的高度
        this.height=50;
        // 移动速度
        this.speed=10;
        // 鱼的类型
        this.tybe=1;
        // 被吃掉后获得的分数
        this.count=1;
        // 鱼的图片
        this.img=GameUtils.enamy1_img;
    }
}
// 敌方A类鱼，从右边出现向左移动
class EnemyA_2 extends Enemy
{
    /**
     * 构造函数，初始化敌方A类鱼（从右向左）的属性
     */
    EnemyA_2()
    {
        // 初始横坐标在屏幕右侧外
        this.x=1300;
        // 初始纵坐标随机在一定范围内
        this.y=(int)(Math.random()*600+100);
        // 鱼的宽度
        this.width=50;
        // 鱼的高度
        this.height=50;
        // 移动速度，负数表示向左移动
        this.speed=-10;
        // 鱼的类型
        this.tybe=1;
        // 被吃掉后获得的分数
        this.count=1;
        // 鱼的图片
        this.img=GameUtils.enamy2_img;
    }
}
// 敌方B类鱼，从左边出现向右移动
class EnemyB extends Enemy {
    /**
     * 构造函数，初始化敌方B类鱼的属性
     */
    EnemyB() {
        // 初始横坐标在屏幕左侧外
        this.x = -100;
        // 初始纵坐标随机在一定范围内
        this.y = (int) (Math.random() * 600 + 100);
        // 鱼的宽度
        this.width = 100;
        // 鱼的高度
        this.height = 100;
        // 移动速度
        this.speed = 5;
        // 鱼的类型
        this.tybe = 2;
        // 被吃掉后获得的分数
        this.count = 2;
        // 鱼的图片
        this.img = GameUtils.enamy1_2_img;
    }
}

// 敌方B类鱼，从右边出现向左移动
class EnemyB_2 extends Enemy {
    /**
     * 构造函数，初始化敌方B类鱼（从右向左）的属性
     */
    EnemyB_2() {
        // 初始横坐标在屏幕右侧外
        this.x = 1300;
        // 初始纵坐标随机在一定范围内
        this.y = (int) (Math.random() * 600 + 100);
        // 鱼的宽度
        this.width = 100;
        // 鱼的高度
        this.height = 100;
        // 移动速度，负数表示向左移动
        this.speed = -5;
        // 鱼的类型
        this.tybe = 2;
        // 被吃掉后获得的分数
        this.count = 2;
        // 鱼的图片
        this.img = GameUtils.enamy2_2_img;
    }
}

// 敌方C类鱼，从左边出现向右移动
class EnemyC extends Enemy {
    /**
     * 构造函数，初始化敌方C类鱼的属性
     */
    EnemyC() {
        // 初始横坐标在屏幕左侧外
        this.x = -200;
        // 初始纵坐标随机在一定范围内
        this.y = (int) (Math.random() * 600 + 100);
        // 鱼的宽度
        this.width = 200;
        // 鱼的高度
        this.height = 200;
        // 移动速度
        this.speed = 10;
        // 鱼的类型
        this.tybe = 3;
        // 鱼的图片
        this.img = GameUtils.enamy1_3_img;
    }

    /**
     * 重写获取矩形区域的方法，调整碰撞检测的范围
     * @return 调整后的矩形对象
     */
    @Override
    public Rectangle getRec() {
        return new Rectangle(x + 40, y + 30, width - 80, height - 60);
    }
}

// 敌方C类鱼，从右边出现向左移动
class EnemyC_2 extends Enemy {
    /**
     * 构造函数，初始化敌方C类鱼（从右向左）的属性
     */
    EnemyC_2() {
        // 初始横坐标在屏幕右侧外
        this.x = 1300;
        // 初始纵坐标随机在一定范围内
        this.y = (int) (Math.random() * 600 + 100);
        // 鱼的宽度
        this.width = 200;
        // 鱼的高度
        this.height = 200;
        // 移动速度，负数表示向左移动
        this.speed = -10;
        // 鱼的类型
        this.tybe = 3;
        // 鱼的图片
        this.img = GameUtils.enamy2_3_img;
    }
}

// 敌方Boss鱼
class EnemyBoss extends Enemy {
    /**
     * 构造函数，初始化敌方Boss鱼的属性
     */
    EnemyBoss() {
        // 初始横坐标在屏幕左侧外较远位置
        this.x = -1000;
        // 初始纵坐标随机在一定范围内
        this.y = (int) (Math.random() * 600 + 100);
        // 鱼的宽度
        this.width = 340;
        // 鱼的高度
        this.height = 340;
        // 移动速度
        this.speed = 80;
        // 鱼的类型
        this.tybe = 10;
        // 被吃掉后获得的分数，这里设为0
        this.count = 0;
        // 鱼的图片
        this.img = GameUtils.Boss_img;
    }
}

