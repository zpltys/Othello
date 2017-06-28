import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SetTurn extends JFrame implements ActionListener{
    private Label msg;
    private JRadioButton white, black;
    private ButtonGroup bg;
    private JButton choose, cancel;
    public MyMenu myMenu;

    public SetTurn() {
        white = new JRadioButton("White");
        black = new JRadioButton("Black", true);

        bg = new ButtonGroup();
        bg.add(white); 
        bg.add(black);

        msg = new Label("Please choose first turn");
        
        choose = new JButton("choose");
        choose.addActionListener(this);
        cancel = new JButton("cancel");
        cancel.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(msg);
        panel.add(black);
        panel.add(white);
        panel.add(choose);
        panel.add(cancel);

        add(panel);
        setSize(200, 100);

        setLocation(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton s = (JButton)e.getSource();
        if(s == choose) {
            int turn;
            if(black.isSelected()) turn = 1;
            else turn = 2;
            myMenu.turn = turn;

            myMenu.setVisible(true);
            dispose();
        } else {
            myMenu.setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        SetTurn s = new SetTurn();
    }
}
