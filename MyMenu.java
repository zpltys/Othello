import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyMenu extends JFrame {
    private JButton single, multi, setting, exit;
    private JPanel globalPanel, setPanel;
    private MainFrame mainFrame;

    MyMenu(String s) {
        super(s);
        
        globalPanel = new JPanel();
        setPanel = new JPanel();
        setPanel.setLayout(new FlowLayout());

        MenuActionListener listener = new MenuActionListener();

        single = new JButton("Single Player");
        single.addActionListener(listener);
        multi = new JButton("Multi Player");
        multi.addActionListener(listener);
        setting = new JButton("setting");
        setting.addActionListener(listener);
        exit = new JButton("exit");
        exit.addActionListener(listener);

        setPanel.add(setting);
        setPanel.add(exit);

        globalPanel.setLayout(new GridLayout(5, 1));

        JPanel sP, mP, eP;
        sP = new JPanel();
        sP.add(single);
        mP = new JPanel();
        mP.add(multi);
        eP = new JPanel();

        globalPanel.add(eP);
        globalPanel.add(sP);
 //       globalPanel.add(eP);
        globalPanel.add(mP);
        globalPanel.add(setPanel);

        setContentPane(globalPanel);
        setSize(300, 200);
        setLocation(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String args[]) {
        MyMenu m = new MyMenu("Othello");
    }

    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton)e.getSource();
            if(source == single || source == multi) {
                MyMenu.this.setVisible(false);
                mainFrame = new MainFrame();
                mainFrame.setAi(source == single);
                mainFrame.myMenu = MyMenu.this;
            } else {
                if(source == exit) {
                    MyMenu.this.dispose();
                }
            }
        }
    }
}
