package db;

import javax.swing.*;
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

    Map <String,String> auth = new HashMap<String, String>();
    String login;
    String password;

    AuthForm(){
        setContentPane(rootPanel);
        auth.put("admin","admin");
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login = loginTextField.getText();
                password = passwordField.getText();
                if(auth.containsKey(login)) {
                    if (auth.get(login).equals(password)) {
                        JFrame socialProtection = new SocialProtectionForm();
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

    public static void main(String[] args) {
        JFrame jFrame = new AuthForm();
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("База данных");
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}

