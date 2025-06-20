
package com.tex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
    private GameWin gameWin;

    public GamePanel(GameWin gameWin) {
        this.gameWin = gameWin;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(gameWin.getKeyListeners()[0]); // 复用 GameWin 的键盘监听器
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameWin.paintGame(g); // 调用 GameWin 的绘图逻辑
    }
}
