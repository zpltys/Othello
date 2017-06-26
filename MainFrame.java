import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private Board board;
    public JTextArea white, black, turn;

    public MainFrame() {
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());

        board = new Board();
        board.mainFrame = this;

        jp.add(board);

        white = new JTextArea("\tWhite:\t2");
        black = new JTextArea("\tBlack:\t2");
        turn = new JTextArea("\tNext:\tblack");
        white.setColumns(30);
        black.setColumns(30);
        turn.setColumns(30);
        white.setBackground(Color.gray);
        black.setBackground(Color.gray);
        turn.setBackground(Color.gray);
        white.setEditable(false);
        black.setEditable(false);
        turn.setEditable(false);

        jp.add(white);
        jp.add(black);
        jp.add(turn);
        jp.setBackground(Color.gray);

        setSize(350, 400);
        setLocation(500, 250);
        add(jp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String args[]) {
        new MainFrame();
    }
}
