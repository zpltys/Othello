import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class Board extends Canvas {

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Color.red);
        g2.draw(new Line2D.Float(1, 1, 300, 100));
    }

    public Board() {
        setSize(200, 200);
        setBackground(Color.green);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.add(new Board());
        f.setSize(400, 400);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
