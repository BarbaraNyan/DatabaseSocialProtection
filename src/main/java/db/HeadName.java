package db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HeadName extends JFrame{
    private JPanel rootPanel;
    private JButton buttonStart;
    private JPanel panelButton;
    private JLabel label;
    ImageIcon icon;

    public HeadName(){
        super("Заставка");
        setContentPane(rootPanel);
        icon = new ImageIcon("src\\headWindow.jpg");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(1300,700,Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        label.setIcon(icon);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(300,100);
        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame authForm = new AuthForm();
                authForm.setVisible(true);
                setVisible(false);

//                authForm.addWindowListener(new WindowAdapter() {
//                    @Override
//                    public void windowClosing(WindowEvent e) {
//                        super.windowClosing(e);
//                        System.exit(0);
//                    }
//                });
            }
        });
    }

    public static void main(String[] args) {
//        JFrame jFrame = new HeadName();
//        jFrame.setVisible(true);
//        jFrame.pack();
//        jFrame.setPreferredSize(new Dimension(1200,700));

        JFrame jFrame = new HeadName();
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("База данных");
        jFrame.setResizable(false);
//        jFrame.setLocationRelativeTo(null);
        try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AuthForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AuthForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AuthForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AuthForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setPreferredSize(new Dimension(1200,700));

    }
}
