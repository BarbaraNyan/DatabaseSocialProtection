package db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class AuthForm extends JFrame{
    private JPanel rootPanel;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JLabel labelOne;

    Map <String,String> auth = new HashMap<String, String>();
    String login;
    String password;

    public AuthForm(){
        super("Окно авторизации");

        setContentPane(rootPanel);
        pack();
        setVisible(true);
        setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        auth.put("admin","admin");
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login = loginTextField.getText();
                password = passwordField.getText();
                if(auth.containsKey(login)) {
                    if (auth.get(login).equals(password)) {
                        JFrame socialProtection = new SocialProtectionForm();
//
//                        JMenuBar menuBar = new JMenuBar();
//                        JMenu fileMenuHelp = new JMenu("Помощь");
//                        JMenu fileMenuAbout = new JMenu("Справка");
//                        fileMenuHelp.add(fileMenuAbout);
//                        menuBar.add(fileMenuHelp);
//
//                        socialProtection.setJMenuBar(menuBar);

                        socialProtection.setVisible(true);
                        setVisible(false);

                        socialProtection.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                super.windowClosing(e);
                                System.exit(0);
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(AuthForm.this,
                                "Неправильно введены логин или пароль!", "Ошибка входа", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(AuthForm.this,
                            "Логина не существует!", "Ошибка входа", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

//    public static void main(String[] args) {
//        JFrame jFrame = new AuthForm();
//        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
//        jFrame.setTitle("База данных");
//        jFrame.setResizable(false);
//        jFrame.setLocationRelativeTo(null);
////        try {
////            //here you can put the selected theme class name in JTattoo
////            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
////        } catch (ClassNotFoundException ex) {
////            java.util.logging.Logger.getLogger(AuthForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (InstantiationException ex) {
////            java.util.logging.Logger.getLogger(AuthForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (IllegalAccessException ex) {
////            java.util.logging.Logger.getLogger(AuthForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
////            java.util.logging.Logger.getLogger(AuthForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        }
//        jFrame.pack();
//        jFrame.setVisible(true);
//    }
}

