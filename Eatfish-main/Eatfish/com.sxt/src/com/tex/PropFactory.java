// 声明该类所在的包名为 com.tex
package com.tex;

// 导入 Random 类，用于生成随机数
import java.util.Random;

/**
 * PropFactory 类用于创建道具对象。
 * 该类提供了一个静态方法 createProp，用于随机创建不同类型的道具。
 */
public class PropFactory {
    // 创建一个静态的 Random 对象，用于生成随机数
    // 静态变量在类的所有实例之间共享，使用同一个 Random 实例可以避免多次创建对象带来的开销
    private static final Random random = new Random();

    /**
     * 静态方法，用于创建一个随机类型的道具对象。
     *
     * 返回一个新创建的 Prop 对象，道具类型随机选取，位置 x 坐标随机在 0 - 1199 之间，y 坐标为 -50
     */
    public static Prop createProp() {
        // 获取 Prop 类中 Type 枚举的所有值，存储在 types 数组中
        Prop.Type[] types = Prop.Type.values();
        // 通过随机数生成器生成一个 0 到 types 数组长度之间的随机整数，作为索引
        // 然后从 types 数组中随机选取一个道具类型
        // 最后使用该类型和随机生成的 x 坐标（范围是 0 到 1199）、固定的 y 坐标 -50 创建一个新的 Prop 对象
        return new Prop(types[random.nextInt(types.length)], random.nextInt(1200), -50);
    }
}