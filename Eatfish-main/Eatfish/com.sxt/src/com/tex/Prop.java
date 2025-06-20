package com.tex;

import java.awt.*;

// 定义道具类，代表游戏中的各种道具
public class Prop {
    // 定义道具的类型枚举，包含加速、护盾和得分翻倍三种类型
    enum Type {
        SPEED_UP,  // 加速道具类型
        SHIELD,    // 护盾道具类型
        SCORE_DOUBLE // 得分翻倍道具类型
    }

    // 道具的类型，使用上面定义的枚举类型
    Type type;
    // 道具对应的图片，用于在游戏中显示
    Image img;
    // 道具在游戏界面中的横坐标
    int x;
    // 道具在游戏界面中的纵坐标
    int y;
    // 道具的宽度，默认为 40
    int width = 40;
    // 道具的高度，默认为 40
    int height = 40;

    /**
     * 构造函数，用于创建一个新的道具对象
     * type 道具的类型，通过枚举 Type 指定
     * x 道具的初始横坐标
     * y 道具的初始纵坐标
     */
    public Prop(Type type, int x, int y) {
        this.type = type; // 初始化道具类型
        this.x = x; // 初始化道具的横坐标
        this.y = y; // 初始化道具的纵坐标
        // 根据不同的道具类型，为道具分配对应的图片
        switch (type) {
            case SPEED_UP:
                img = GameUtils.propSpeed; // 加速道具对应的图片
                break;
            case SHIELD:
                img = GameUtils.propShield; // 护盾道具对应的图片
                break;
            case SCORE_DOUBLE:
                img = GameUtils.propScore; // 得分翻倍道具对应的图片
                break;
        }
    }

    /**
     * 绘制道具的方法，将道具图片绘制到游戏界面上
     * g 用于绘制的 Graphics 对象
     */
    public void paintSelf(Graphics g) {
        // 在指定的位置（x, y）绘制道具图片，宽度和高度为定义的 width 和 height
        g.drawImage(img, x, y, width, height, null);
    }

    /**
     * 获取道具的矩形区域，用于碰撞检测
     * 包含道具位置和大小的矩形对象
     */
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}


