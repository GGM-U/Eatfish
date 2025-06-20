// 声明该类所属的包为 com.tex
package com.tex;
// 导入 javax.swing 包中的 JFrame 类，用于创建窗口
import javax.swing.*;
// 导入 java.awt 包中的相关类，用于图形绘制和事件处理
import java.awt.*;
// 导入键盘事件适配器类，用于处理键盘事件
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

/**
 * GameWin 类继承自 JFrame，用于创建游戏窗口并管理游戏的主循环和绘制逻辑。
 * 该类负责处理用户的鼠标和键盘输入，控制游戏的状态，生成敌人和道具，以及进行碰撞检测等操作。
 */
public class GameWin extends JFrame {

    static int state = 0; // 窗体状态：0=主菜单,1=游戏中,2=游戏结束,3=胜利
    // 双缓冲技术使用的离屏图像
    Image offScreenImage;
    // 游戏窗口的宽度
    int width = 1328;
    // 游戏窗口的高度
    int height = 830;
    // 随机数，用于随机生成敌方鱼类和道具
    double random;
    // 游戏运行的帧数计数器
    int time = 0;
    private boolean tutorialShown = false;
    // 游戏的背景对象
    Background background = new Background();
    // 敌方鱼类对象
    Enemy enemy;
    // Boss对象
    Enemy boss;
    // 表示是否出现Boss
    boolean isboss = false;

    // 当前屏幕上的道具对象
    Prop prop = null;
    // 我方鱼类对象
    MyFish myFish = new MyFish();
    private boolean isPaused = false;

    private JPanel cardPanel;
    private final String MENU_PANEL = "menu";
    private final String GAME_PANEL = "game";

    // 启动游戏窗口的方法，设置窗口的可见性、大小、标题等属性，并添加鼠标和键盘事件监听器
    public void launch() {
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("大鱼吃小鱼");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        // 👇 先添加键盘监听器到 this（JFrame）
        this.setFocusable( true);
        this.requestFocusInWindow();
        // 添加键盘事件监听器，用于控制玩家鱼类的移动
        this.addKeyListener(new KeyAdapter() {
            @Override
            // 处理键盘按键按下事件
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                // 使用 WASD 键进行移动控制
                // 如果按下的是 W 键
                if (code == KeyEvent.VK_W) GameUtils.UP = true;
                // 如果按下的是 S 键
                if (code == KeyEvent.VK_S) GameUtils.DOWN = true;
                // 如果按下的是 A 键
                if (code == KeyEvent.VK_A) GameUtils.LEFT = true;
                // 如果按下的是 D 键
                if (code == KeyEvent.VK_D) GameUtils.RIGHT = true;
                //// 如果按下的是 P 键
                if (code == KeyEvent.VK_P) togglePause();
            }

            @Override
            // 处理键盘按键释放事件
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                // 松开按键后，相应的移动标志设置为 false
                // 如果松开的是 W 键
                if (code == KeyEvent.VK_W) GameUtils.UP = false;

                // 松开按键后，相应的移动标志设置为 false
                // 如果松开的是 S 键
                if (code == KeyEvent.VK_S) GameUtils.DOWN = false;
                // 松开按键后，相应的移动标志设置为 false
                // 如果松开的是 A 键
                if (code == KeyEvent.VK_A) GameUtils.LEFT = false;

                // 松开按键后，相应的移动标志设置为 false
                // 如果松开的是 D 键
                if (code == KeyEvent.VK_D) GameUtils.RIGHT = false;
            }
        });

        // 👇 再创建 GamePanel
        GamePanel gamePanel = new GamePanel(this);

        // 初始化 CardLayout 面板
        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(gamePanel, GAME_PANEL);
        cardPanel.add(createMenuPanel(), MENU_PANEL);
        this.setContentPane(cardPanel);
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, MENU_PANEL);

        // 鼠标点击开始游戏
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1 && state == 0) {
                    state = 1;
                    repaint();
                }
            }
        });

        // 定时器刷新画面
        Timer timer = new Timer(40, e -> {
            if (!isPaused) {
                repaint();
                time++;
                if (time % 300 == 0 && prop == null) {
                    prop = PropFactory.createProp();
                }
            }
        });
        timer.start();

        this.setVisible(true);
    }

    public void paintGame(Graphics g) {
        // 创建离屏图像
        offScreenImage = createImage(width, height);
        Graphics gImage = offScreenImage.getGraphics();
        // 绘制背景
        background.paintSelf(gImage, myFish.level);

        // 根据游戏状态绘制不同的元素
        switch (state) {
            case 0:
                // 未开始状态，不绘制其他元素
                break;
            case 1:
                // 游戏中状态，绘制我方鱼类、敌方鱼类、Boss和道具
                myFish.paintSelf(gImage);
                logic();
                for (Enemy enemy : GameUtils.enemyList) {
                    enemy.paintSelf(gImage);
                }
                if (isboss && boss != null) {
                    boss.x += boss.dir + boss.speed;
                    boss.paintSelf(gImage);
                }
                if (prop != null) {
                    prop.y += 5;
                    if (prop.y > height) {
                        prop = null;
                    } else {
                        prop.paintSelf(gImage);
                    }
                }
                break;
            case 2:

                // 游戏失败状态
                // 遍历敌方鱼类列表，绘制所有敌方鱼类
                for (Enemy enemy : GameUtils.enemyList) {
                    enemy.paintSelf(gImage);
                }
                if (isboss && boss != null) {
                    // 更新 Boss 的位置
                    boss.x += boss.dir + boss.speed;
                    // 绘制 Boss
                    boss.paintSelf(gImage);
                }

                GameUtils.drawWord(gImage, "游戏结束", Color.RED, 80, 500, 300);
                GameUtils.drawWord(gImage, "你的得分：" + GameUtils.count, Color.MAGENTA, 50, 550, 400);

                int highScore = Ranking.getHighScore();
                if (GameUtils.count > highScore) {
                    Ranking.saveHighScore(GameUtils.count);
                    GameUtils.drawWord(gImage, "恭喜！你打破了记录！", Color.YELLOW, 40, 500, 500);
                } else {
                    GameUtils.drawWord(gImage, "历史最高分：" + highScore, Color.CYAN, 40, 500, 500);
                }

                JButton backBtn = new JButton("🏠 返回主菜单");
                backBtn.setFont(new Font("微软雅黑", Font.BOLD, 30));
                backBtn.setBackground(Color.DARK_GRAY);
                backBtn.setForeground(Color.WHITE);
                backBtn.setBounds(500, 600, 300, 60);
                this.add(backBtn);
                backBtn.addActionListener(e -> {
                    ((CardLayout) cardPanel.getLayout()).show(cardPanel, MENU_PANEL);
                    resetGame();
                    this.remove(backBtn);
                    this.revalidate();
                    this.repaint();
                });
                break;
            case 3:
                // 游戏胜利状态，绘制我方鱼类
                myFish.paintSelf(gImage);
                GameUtils.drawWord(gImage, "胜利", Color.RED, 80, 600, 400);
                JButton winBtn = new JButton("🏠 返回主菜单");
                winBtn.setFont(new Font("微软雅黑", Font.BOLD, 30));
                winBtn.setBackground(Color.DARK_GRAY);
                winBtn.setForeground(Color.WHITE);
                winBtn.setBounds(500, 600, 300, 60);
                this.add(winBtn);
                winBtn.addActionListener(e -> {
                    ((CardLayout) cardPanel.getLayout()).show(cardPanel, MENU_PANEL);
                    resetGame();
                    this.remove(winBtn);
                    this.revalidate();
                    this.repaint();
                });
                break;
            default:
        }

        // 将离屏图像绘制到屏幕上
        g.drawImage(offScreenImage, 0, 0, null);
    }

    private void resetGame() {
        GameUtils.count = 0;
        myFish.level = 1;
        GameUtils.enemyList.clear();
        isboss = false;
        prop = null;
        time = 0;
        state = 0;
    }

    /**
     * 处理游戏逻辑的方法。
     * 根据玩家的得分计算游戏关卡等级和玩家鱼类的等级。
     * 按照不同的关卡等级，以一定的时间间隔随机生成不同类型的敌方鱼类。
     * 进行碰撞检测，处理玩家鱼类与敌方鱼类、道具的碰撞事件。
     */
    void logic() {
        // 根据得分判断关卡等级和我方鱼类等级
        if (GameUtils.count < 5) {
            GameUtils.level = 0;
            myFish.level = 1;
        } else if (GameUtils.count < 15) {
            GameUtils.level = 1;
        } else if (GameUtils.count < 50) {
            GameUtils.level = 2;
            myFish.level = 2;
        } else if (GameUtils.count < 150) {
            GameUtils.level = 3;
            myFish.level = 3;
        } else if (GameUtils.count < 300) {
            GameUtils.level = 4;
            myFish.level = 3;
        } else if (GameUtils.count >= 300) {
            state = 3;
        }

        // 生成随机数
        random = Math.random();

        // 根据关卡等级生成不同类型的敌方鱼类
        switch (GameUtils.level) {
            case 4:
                // 关卡等级为 4 时，每 50 帧有 50% 的概率生成 Boss
                if (time % 50 == 0 && Math.random() > 0.5) {
                    boss = new EnemyBoss();
                    isboss = true;
                    GameUtils.enemyList.add(boss);
                }
            case 3:
            case 2:
                // 关卡等级为 2 或 3 时，每 30 帧有 50% 的概率生成 EnemyC 或 EnemyC_2
                if (time % 30 == 0) {
                    if (Math.random() > 0.5) {
                        enemy = new EnemyC();
                    } else {
                        enemy = new EnemyC_2();
                    }
                    // 将生成的敌方鱼类添加到敌方鱼类列表中
                    GameUtils.enemyList.add(enemy);
                }
            case 1:
                // 关卡等级为 1 时，每 20 帧有 50% 的概率生成 EnemyB 或 EnemyB_2
                if (time % 20 == 0) {
                    if (Math.random() > 0.5) {
                        enemy = new EnemyB();
                    } else {
                        enemy = new EnemyB_2();
                    }
                    // 将生成的敌方鱼类添加到敌方鱼类列表中
                    GameUtils.enemyList.add(enemy);
                }
            case 0:
                // 关卡等级为 0 时，每 10 帧有 50% 的概率生成 EnemyA 或 EnemyA_2

                if (time % 10 == 0) {
                    if (Math.random() > 0.5) {
                        enemy = new EnemyA();
                    } else {
                        enemy = new EnemyA_2();
                    }
                    // 将生成的敌方鱼类添加到敌方鱼类列表中
                    GameUtils.enemyList.add(enemy);
                }
                break;
        }

        // 处理敌方鱼类的移动和碰撞检测
        for (Enemy enemy : GameUtils.enemyList) {
            enemy.x += enemy.dir + enemy.speed;

            // 如果出现 Boss
            if (isboss && boss != null) {
                // 如果 Boss 与敌方鱼类发生碰撞
                if (boss.getRec().intersects(enemy.getRec())) {
                    enemy.x = -200;
                    enemy.y = -200;
                }
                // 如果 Boss 与玩家鱼类发生碰撞
                if (boss.getRec().intersects(myFish.getRec())) {
                    // 游戏失败
                    state = 2;
                }
            }

            // 碰撞检测
            if (myFish.getRec().intersects(enemy.getRec())) {
                // 如果玩家鱼类的等级大于等于敌方鱼类的等级
                if (myFish.level >= enemy.tybe) {
                    // 打印碰撞信息
                    System.out.println("碰撞了");
                    // 将被吃掉的敌方鱼类移出屏幕

                    enemy.x = -200;
                    enemy.y = -200;
                    // 增加玩家的得分
                    GameUtils.count += enemy.count;
                } else {
                    // 玩家鱼类等级小于敌方鱼类等级，游戏失败
                    state = 2;
                }
            }
        }

        // 每隔一段时间生成一个道具
        if (time % 300 == 0 && prop == null) {
            prop = PropFactory.createProp();
        }
        // 道具碰撞检测
        if (prop != null && myFish.getRec().intersects(prop.getRec())) {
            // 应用道具效果
            applyPropEffect(prop.type);
            // 播放道具获取音效
            SoundUtils.play("sound/prop.wav");
            // 使用后移除道具
            prop = null;
        }
    }

    /**
     * 应用道具效果的方法。
     * 根据道具的类型，为玩家鱼类提供不同的效果，如加速、护盾、得分翻倍等。
     * 部分效果具有持续时间，使用 Timer 类在一定时间后恢复原状。
     *
     *  type 道具的类型，包括 SPEED_UP（加速）、SHIELD（护盾）、SCORE_DOUBLE（得分翻倍）
     */
    private void applyPropEffect(Prop.Type type) {
        switch (type) {
            case SPEED_UP:
                // 加速效果，玩家鱼类速度增加 10
                myFish.speed += 10;
                // 使用 Timer 类在 5 秒后恢复玩家鱼类的速度
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                myFish.speed -= 10;
                            }
                        }, 5000);
                break;
            case SHIELD:
                // 护盾效果，玩家鱼类获得无敌状态 5 秒
                System.out.println("获得护盾！无敌5秒");
                // 开启护盾状态
                myFish.isShielded = true;
                // 5秒后关闭护盾状态
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                myFish.isShielded = false;
                            }
                        }, 5000);
                break;
            case SCORE_DOUBLE:
                // 得分翻倍效果，示例中直接增加 10 分
                GameUtils.count += 10;
                // 或者可以开启一个临时计数器，让每吃一个敌人得分翻倍
                break;
        }
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            showPauseMenu();
        }
    }

    private void showPauseMenu() {
        JPanel mask = new JPanel();
        mask.setBackground(new Color(0, 0, 0, 150));
        mask.setBounds(0, 0, width, height);
        this.add(mask);

        JPanel panel = new JPanel(new GridLayout(4, 1, 20, 20));
        panel.setBackground(new Color(30, 30, 30));
        JButton resumeBtn = new JButton("▶ 继续");
        JButton restartBtn = new JButton("🔁 重新开始");
        JButton rankingBtn = new JButton("🏆 排行榜");
        JButton mainMenuBtn = new JButton("🏠 返回主菜单");

        Font btnFont = new Font("微软雅黑", Font.BOLD, 28);
        for (JButton btn : new JButton[]{resumeBtn, restartBtn, rankingBtn, mainMenuBtn}) {
            btn.setFont(btnFont);
            btn.setBackground(Color.DARK_GRAY);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }

        resumeBtn.addActionListener(e -> {
            isPaused = false;
            this.remove(mask);
            this.remove(panel);
            this.revalidate();
            this.repaint();
        });

        restartBtn.addActionListener(e -> {
            GameUtils.count = 0;
            myFish.level = 1;
            state = 1;
            isPaused = false;
            GameUtils.enemyList.clear();
            isboss = false;
            this.remove(mask);
            this.remove(panel);
            this.revalidate();
            this.repaint();
        });

        rankingBtn.addActionListener(e -> showRanking());

        mainMenuBtn.addActionListener(e -> {
            ((CardLayout) cardPanel.getLayout()).show(cardPanel, MENU_PANEL);
            resetGame();
            isPaused = false;
            this.remove(mask);
            this.remove(panel);
            this.revalidate();
            this.repaint();
        });

        panel.add(resumeBtn);
        panel.add(restartBtn);
        panel.add(rankingBtn);
        panel.add(mainMenuBtn);
        panel.setBounds(width / 2 - 200, height / 2 - 150, 400, 300);
        this.add(panel);
        this.revalidate();
        this.repaint();
    }

    private void showRanking() {
        try {
            File file = new File("ranking.txt");
            StringBuilder content = new StringBuilder("当前排行榜：\n\n");

            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    content.append(sc.nextLine()).append("\n");
                }
                sc.close();
            } else {
                content.append("暂无记录");
            }

            JTextArea textArea = new JTextArea(content.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("微软雅黑", Font.PLAIN, 20));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 400));

            JOptionPane.showMessageDialog(this, scrollPane, "排行榜", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "读取排行榜失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTutorial() {
        String message = "<html><h2>欢迎来到大鱼吃小鱼！</h2><br/>"
                + "🎮 <b>操作说明：</b><br/>"
                + "&nbsp;&nbsp;W: 向上移动<br/>"
                + "&nbsp;&nbsp;S: 向下移动<br/>"
                + "&nbsp;&nbsp;A: 向左移动<br/>"
                + "&nbsp;&nbsp;D: 向右移动<br/>"
                + "&nbsp;&nbsp;P: 暂停游戏<br/><br/>"
                + "🖱️ 点击鼠标左键开始游戏<br/>"
                + "🐟 吃掉比你小的鱼来变大！<br/>"
                + "⚠️ 注意不要碰到比你大的敌人！</html>";

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(message);
        textPane.setEditable(false);
        textPane.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "新手教程", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel bgLabel = new JLabel(new ImageIcon("image/menu.png"));
        panel.add(bgLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 20, 20));
        buttonPanel.setOpaque(false);

        JButton startBtn = new JButton("🎮 开始游戏");
        JButton tutorialBtn = new JButton("📖 新手教程");
        JButton rankingBtn = new JButton("🏆 排行榜");
        JButton exitBtn = new JButton("🚪 退出");

        Font btnFont = new Font("微软雅黑", Font.BOLD, 30);
        for (JButton btn : new JButton[]{startBtn, tutorialBtn, rankingBtn, exitBtn}) {
            btn.setFont(btnFont);
            btn.setBackground(new Color(30, 30, 30));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        startBtn.addActionListener(e -> {
            state = 1;
            ((CardLayout) cardPanel.getLayout()).show(cardPanel, GAME_PANEL);
        });

        tutorialBtn.addActionListener(e -> showTutorial());
        rankingBtn.addActionListener(e -> showRanking());
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(startBtn);
        buttonPanel.add(tutorialBtn);
        buttonPanel.add(rankingBtn);
        buttonPanel.add(exitBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * 程序的入口点。
     * 创建 GameWin 类的实例，并调用 launch 方法启动游戏。
     *
     * 命令行参数
     */
    public static void main(String[] args) {

        GameWin gameWin = new GameWin();

        gameWin.launch();
    }
}
