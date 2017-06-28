import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class Board extends Canvas {
    private State state; //the state of chess now
    private State nextFrame;
    private int scale; //the size of one check
    private int lineWidth;
    private Color colors[];
    private int PosX, PosY, bePosX, bePosY;
    private int BeforeClickedX, BeforeClickedY;
    public int turn;
    public int initTurn;
    private long beforeTime;
    public boolean testAi;
    public MainFrame mainFrame;
    public int level;


    public boolean sync;

    int r2L(int a) {
        if(a % (scale + lineWidth) == 0) return -1;
        return a / (scale + lineWidth);
    }
    Point2D real2Logistic(int x, int y) {
        return new Point2D.Float(r2L(x), r2L(y));
    }

    int l2R (int a) {
        return lineWidth - 1 + (scale + 1) / 2 + (scale + lineWidth) * a;
    }
    Point2D logistic2Real(int x, int y) {
        return new Point2D.Float(l2R(x), l2R(y));
    }

    int calBoard(int x) {
        return x * (scale + lineWidth);
    }
    void drawBorder(int i, int j, Graphics2D g2, Color c) {
        if(i < 0 || i > 7 || j < 0 || j > 7) return;
        g2.setPaint(c);
        int length = lineWidth + scale;
        g2.draw(new Rectangle.Float(calBoard(i), calBoard(j), length, length));
    }

    void drawChess(int i, int j, Graphics2D g2, Color c) {
        g2.setColor(c);
        g2.fillOval(calBoard(i) + 1, calBoard(j) + 1, scale - 2, scale - 2);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        int i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
            drawBorder(i, j, g2, Color.black);
        }
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
            if(state.s[i][j] != 0) drawChess(i, j, g2, colors[state.s[i][j]]);
        }
    }

    @Override
    public void update(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        int i, j;
        for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
            if(nextFrame.s[i][j] != state.s[i][j]) {
                drawChess(i, j, g2, colors[nextFrame.s[i][j]]);
                state.s[i][j] = nextFrame.s[i][j];
            }
        }
        if(bePosX != PosX || bePosY != PosY) {
            drawBorder(bePosX, bePosY, g2, Color.black);
            drawBorder(PosX, PosY, g2, colors[3]);
        }

        int white, black;
        white = black = 0;
        for(i = 0; i < 7; i++) for(j = 0; j < 7; j++) {
            if(nextFrame.s[i][j] == 1) black++;
            if(nextFrame.s[i][j] == 2) white++;
        }
        mainFrame.white.setText("\tWhite:\t" + white);
        mainFrame.black.setText("\tBlack:\t" + black);
        String nTurn;
        if(turn == 1) nTurn = "black";
        else nTurn = "white";
        mainFrame.turn.setText("\tNext:\t" + nTurn);
    }



    public Board() {
        scale = 39;
        lineWidth = 1;
        colors = new Color[4];
        colors[2] = Color.white;
        colors[1] = Color.black;
        colors[0] = Color.gray;
        colors[3] = Color.green;
        setBackground(colors[0]);
        setSize(330, 330);
        state = new State();
        state.init();
        nextFrame = new State(state);
        turn = 1;
        level = 3;

        sync = true;

        //  testAi = true;

        MyMouseListener m = new MyMouseListener();
        addMouseListener(m);
        addMouseMotionListener(m);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.add(new Board());
        f.setSize(400, 400);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            long nowTime = System.currentTimeMillis();
            if(nowTime - beforeTime < 500) return;
            beforeTime = nowTime;

            int x, y;
            if(turn == 2 && testAi) {
                AI ai = new Dfs(level);
                Point p = ai.calStep(state, 2);
                x = (int)p.getX();
                y = (int)p.getY();
            } else {
                x = e.getX();
                y = e.getY();
                x = r2L(x); y = r2L(y);
            }
            if(x == -1 || y == -1) return;


            nextFrame = new State(state);
            /*
               System.out.println("x: " + x + "  y:" + y);
               if(x == 7 && y == 7) {
               nextFrame.init();
               System.out.println("repaint");
               repaint();
               }
            */

            if(nextFrame.test(x, y, turn) == -1) return;
            nextFrame.insert(x, y, turn);
            int i, j;
            boolean ok1, ok2;
            ok1 = ok2 = false;
            for(i = 0; i < 8; i++) {
                for(j = 0; j < 8; j++) {
                    if(nextFrame.s[i][j] == 0 
                            && nextFrame.test(i, j, 3 - turn) > 0) {
                        ok1 = true;
                        break;
                            }
                }
                if(ok1) break;
            }
            if(ok1) {
                turn = 3 - turn;
                repaint();
            } else {
                for(i = 0; i < 8; i++) {
                    for(j = 0; j < 8; j++) {
                        if(nextFrame.s[i][j] == 0 && 
                                nextFrame.test(i, j, turn) > 0) {
                            ok2 = true;
                            break;
                                }
                    }
                    if(ok2) break;
                }
                if(!ok2) {
                    repaint();
                    String s;
                    int white = 0, black = 0;
                    for(i = 0; i < 8; i++) for(j = 0; j < 8; j++) {
                        if(nextFrame.s[i][j] == 1) black++;
                        if(nextFrame.s[i][j] == 2) white++;
                    }
                    if(black > white) s = "Black";
                    else s = "White";

                    int type = JOptionPane.showConfirmDialog(null, s + " win!\nwill you play it again?", "tip", JOptionPane.YES_NO_OPTION);
                    if(type == JOptionPane.YES_OPTION) {
                        nextFrame.init();
                        turn = initTurn;
                        repaint();
                    } else {
                        mainFrame.myMenu.setVisible(true);
                        mainFrame.dispose();
                    }
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            x = r2L(x); y = r2L(y);
            if(x == -1 || y == -1) return;
            if(PosX != x || PosY != y) {
                bePosX = PosX; bePosY = PosY;
                PosX = x; PosY = y;
                repaint();
            }

            if(testAi && turn == 2) {
                long nowTime = System.currentTimeMillis();
                if(nowTime - beforeTime < 500) return;
                if(sync) {
                    sync = false;
//                    System.out.println("ai");
                    mouseClicked(e);
                    sync = true;
                }
            }
        }
    }
}
