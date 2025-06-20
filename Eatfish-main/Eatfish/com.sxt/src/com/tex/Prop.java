package com.tex;

import java.awt.*;

public class Prop {
    enum Type { SPEED_UP, SHIELD, SCORE_DOUBLE }
    Type type;
    Image img;
    int x, y;
    int width = 40, height = 40;

    public Prop(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        switch (type) {
            case SPEED_UP: img = GameUtils.propSpeed; break;
            case SHIELD: img = GameUtils.propShield; break;
            case SCORE_DOUBLE: img = GameUtils.propScore; break;
        }
    }

    public void paintSelf(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}

