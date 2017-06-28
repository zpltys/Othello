import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SetTurn extends JFrame implements ActionListener{
    private Label msg, hardChoose;
    private JRadioButton white, black, simple, midium, hard;
    private ButtonGroup bg, hc;
    private JButton choose, cancel;
    public MyMenu myMenu;

    public SetTurn() {
        white = new JRadioButton("White");
        black = new JRadioButton("Black", true);

        hard = new JRadioButton("hard");
        midium = new JRadioButton("medium");
        simple = new JRadioButton("simple", true);

        bg = new ButtonGroup();
        bg.add(white); 
        bg.add(black);

        hc = new ButtonGroup();
        hc.add(simple);
        hc.add(midium);
        hc.add(hard);

        msg = new Label("Please choose first turn");
        hardChoose = new Label("Please choose hard level");
        
        choose = new JButton("choose");
        choose.addActionListener(this);
        cancel = new JButton("cancel");
        cancel.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(msg);
        panel.add(black);
        panel.add(white);

        panel.add(hardChoose);
        panel.add(simple);
        panel.add(midium);
        panel.add(hard);

        panel.add(choose);
        panel.add(cancel);

        add(panel);
        setSize(225, 150);

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

            int level = 3;
            if(midium.isSelected()) level = 4;
            if(hard.isSelected()) level = 5;

            myMenu.level = level;

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
