package com.tex;

import java.util.Random;

public class PropFactory {
    private static final Random random = new Random();

    public static Prop createProp() {
        Prop.Type[] types = Prop.Type.values();
        return new Prop(types[random.nextInt(types.length)], random.nextInt(1200), -50);
    }
}

