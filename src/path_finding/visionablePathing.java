package path_finding;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class visionablePathing {
    private int lengthxy = 20;
    private int delay = 30;
    private double dense = 0.2;
    private double density = (lengthxy * lengthxy) * 0.2;
    private int sourcex = -1;
    private int sourcey = -1;
    private int targetx = -1;
    private int targety = -1;
    private int type = 0;
    private int number = 0;
    private int length = 0;
    private long startTime = 0;
    private int totalTime = 0;
    private int curAlg = 0;
    private int WIDTH = 1000;
    private final int HEIGHT = 750;
    private final int MSIZE = 700;
    private int CSIZE = MSIZE / (lengthxy+1);
    // 点的最短长度
    private int FLOOR_CSIZE=2;
    //实现算法
    private String[] algorithms = {"Dijkstra", "A*"};
    // 点的标记
    private String[] tools = {"source","target", "Wall", "path"};
    private boolean solving = false;
    JFrame frame;
    //滑块
    JSlider size = new JSlider(1,70,2);
    JSlider speed = new JSlider(0, 500, 30);
    //障碍物比重
    JSlider obstacles = new JSlider(1, 100, 50);
    JLabel algL = new JLabel("算法");
    JLabel toolL = new JLabel("起点终点");
    JLabel sizeL = new JLabel("大小:");
    JLabel cellsL = new JLabel(lengthxy + "x" + lengthxy);
    JLabel delayL = new JLabel("延迟:");
    JLabel msL = new JLabel(delay + "ms");
    JLabel obstacleL = new JLabel("密度:");
    JLabel densityL = new JLabel(obstacles.getValue() + "%");
    JLabel checkL = new JLabel("数量: " + number);
    JLabel timeL = new JLabel("耗时: " + totalTime + " 秒");
    //按钮类
    JButton searchB = new JButton("开始搜索");
    JButton resetB = new JButton("重置");
    JButton genMapB = new JButton("随机生成图");
    JButton clearMapB = new JButton("清空");
    JComboBox algorithmsBx = new JComboBox(algorithms);
    JComboBox toolBx = new JComboBox(tools);
    // 工具栏
    JPanel toolP = new JPanel();
    //BORDER
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    int[][]map;
    canvasMap canvas;
    public void startrun()
    {
        cleanMap();
        init();
    }
    private void init()
    {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        // 工具栏
        toolP.setBorder(BorderFactory.createTitledBorder(loweredetched, "菜单"));
        int space = 20;
        int buff = 45;
        toolP.setLayout(null);
        toolP.setBounds(10, 10, 210, 700);
        searchB.setBounds(40, space, 120, 25);
        toolP.add(searchB);
        space += buff;
        resetB.setBounds(40, space, 120, 25);
        toolP.add(resetB);
        space += buff;
        genMapB.setBounds(40, space, 120, 25);
        toolP.add(genMapB);
        space += buff;
        clearMapB.setBounds(40, space, 120, 25);
        toolP.add(clearMapB);
        space += 40;
        algL.setBounds(40, space, 120, 25);
        toolP.add(algL);
        space += 25;
        algorithmsBx.setBounds(40, space, 120, 25);
        toolP.add(algorithmsBx);
        space += 40;
        toolL.setBounds(40, space, 120, 25);
        toolP.add(toolL);
        space += 25;
        toolBx.setBounds(40, space, 120, 25);
        toolP.add(toolBx);
        space += buff;
        sizeL.setBounds(15, space, 40, 25);
        toolP.add(sizeL);
        size.setMajorTickSpacing(10);
        size.setBounds(50, space, 100, 25);
        toolP.add(size);
        cellsL.setBounds(160, space, 40, 25);
        toolP.add(cellsL);
        space += buff;
        delayL.setBounds(15, space, 50, 25);
        toolP.add(delayL);
        speed.setMajorTickSpacing(5);
        speed.setBounds(50,space,100,25);
        toolP.add(speed);
        msL.setBounds(160, space, 40, 25);
        toolP.add(msL);
        space += buff;
        obstacleL.setBounds(15, space, 100, 25);
        toolP.add(obstacleL);
        obstacles.setMajorTickSpacing(5);
        obstacles.setBounds(50, space, 100, 25);
        toolP.add(obstacles);
        densityL.setBounds(160, space, 100, 25);
        toolP.add(densityL);
        space += buff;
        checkL.setBounds(15, space, 120, 25);
        toolP.add(checkL);
        space += buff;
        timeL.setBounds(15, space, 100, 25);
        toolP.add(timeL);
        space += buff;
        frame.getContentPane().add(toolP);
        canvas = new canvasMap();
        canvas.setBounds(230, 10, MSIZE +1, MSIZE +1);
        frame.getContentPane().add(canvas);
        searchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reset();
                startSearch();
                if(sourcex >-1&& sourcey >-1&& targetx >-1&& targety >-1)
                {
                    solving=true;
                }
            }
        });
        resetB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetMap();
                Update();
            }
        });
        genMapB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createMap();
                Update();
            }
        });
        clearMapB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cleanMap();
                Update();
            }
        });
        algorithmsBx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                curAlg = algorithmsBx.getSelectedIndex();
                Update();
            }
        });
        toolBx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                type = toolBx.getSelectedIndex();
            }
        });
        size.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                lengthxy = size.getValue() * 5;
                cleanMap();
                reset();
                Update();
            }
        });
        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                delay = speed.getValue();
                Update();
            }
        });
        obstacles.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                dense = (double) obstacles.getValue() / 100;
                Update();
            }
        });

    }
    // 开始
    public void startSearch() {
        if (!solving) {
            startTime = System.currentTimeMillis();
            switch (curAlg) {
                //D
                case 0:
                 Dijkstra  dijkstra = new Dijkstra(sourcex, sourcey, targetx, targety);
                 dijkstra.initgragh(lengthxy,lengthxy);
                 loadmap_D(lengthxy,lengthxy, dijkstra, map);
                 dijkstra.setCurmap(map);
                 if(dijkstra.search()==false)
                 {
                     System.out.println("无到达路径");
                 }
                 map=dijkstra.getCurmap();
                 Update();
                    break;
                //A*
                case 1:
                A_Star a_star = new A_Star(sourcex, sourcey, targetx, targety);
                a_star.initgragh(lengthxy,lengthxy);
                loadmap_Astar(lengthxy,lengthxy, a_star, map);
                a_star.setCurmap(map);
                if(a_star.search()==false)
                {
                    System.out.println("无到达路径");
                }
                    a_star.printfpath();
                    map=a_star.getCurmap();
                    Update();
                    break;
                default:
                    break;
            }
        }
        //pause();    //PAUSE STATE
    }
    // 重置
    public void reset() {
        solving = false;
        length = 0;
        number = 0;
        totalTime = 0;
    }
    // 更新
    public void Update() {
        // 密度
        density = (lengthxy * lengthxy) * dense;
        CSIZE = MSIZE / lengthxy;
        canvas.repaint();
        cellsL.setText(lengthxy + "x" + lengthxy);
        msL.setText(delay + "ms");
        timeL.setText("时间: " + totalTime + " 秒");
        densityL.setText(obstacles.getValue() + "%");
        checkL.setText("数量: " + number);
    }
    public void delay()
    {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }
    public void pause()
    {
        int i = 0;
        while (!solving) {
            i++;
            if (i > 500) {
                i = 0;
            }
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
        startSearch();
    }
    public void cleanMap()
    {
        sourcex =-1;
        sourcey =-1;
        targetx =-1;
        targety =-1;
        map = new int[lengthxy+1][lengthxy+1];
        resetMap();
        reset();
    }
    public void createMap()
    {
            Random random = new Random();
            cleanMap();
            for (int i = 0; i < density; i++) {
                int x;
                int y;
                do {
                    x = random.nextInt(lengthxy + 1);
                    y = random.nextInt(lengthxy + 1);
                } while (map[x][y] == 1);
                map[x][y] = 1;
            }
    }
    public void resetMap()
    {
        for(int i=0;i<=lengthxy;i++)
            for (int j=0;j<=lengthxy;j++)
            {
                map[i][j]=0;
            }
    }
    //实现图的绘制
    class canvasMap extends JPanel implements MouseListener, MouseMotionListener
    {
        public canvasMap() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            for (int x = 1; x <=lengthxy; x++) {
                for (int y = 1; y <=lengthxy; y++) {
                    switch (map[x][y]) {
                        case 0:
                            graphics.setColor(Color.BLACK);
                            break;
                        case 1:
                            graphics.setColor(Color.WHITE);
                            break;
                        case 2:
                            graphics.setColor(Color.red);
                            break;
                        case 3:
                            graphics.setColor(Color.yellow);
                            break;
                        case 4:
                            graphics.setColor(Color.blue);
                            break;
                    }
                    if (CSIZE < FLOOR_CSIZE) {
                        CSIZE = FLOOR_CSIZE;
                    }
                    graphics.fillRect((x-1) * CSIZE, (y-1) * CSIZE, CSIZE, CSIZE);
                    // 绘制每个点的边框
                    graphics.setColor(Color.BLACK);
                    graphics.drawRect((x-1) * CSIZE, (y-1) * CSIZE, CSIZE, CSIZE);
                }
            }
        }
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            try {
                int x = (e.getX() / CSIZE)+1;
                int y = (e.getY() / CSIZE)+1;
                switch (type) {
                    case 0: {
                        if (map[x][y]==1) {
                            if (sourcex > -1 && sourcey > -1) {
                                map[sourcex][sourcey]=3;
                            }
                            sourcex = x;
                            sourcey = y;
                        }
                        break;
                    }
                    case 1: {
                        if (map[x][y]==1) {
                            if (targetx > -1 && targety > -1)
                            {
                                map[targetx][targety]=4;
                            }
                            targetx = x;
                            targety = y;
                        }
                        break;
                    }
                    default:
                        break;
                }
                Update();
            } catch (Exception z) {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }
    //将map装载到Astar算法中
    public static void loadmap_Astar(int length_x,int length_y,A_Star a_star,int[][]map)
    {
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
            {
                a_star.setVertex(i,j,map[i][j]);
            }
    }
    //将map装载到D算法中
    public static void loadmap_D(int length_x,int length_y,Dijkstra dijkstra,int[][]map)
    {
        for(int i=1;i<=length_x;i++)
            for(int j=1;j<=length_y;j++)
            {
                dijkstra.setVertex(i,j,map[i][j]);
            }
    }
}
