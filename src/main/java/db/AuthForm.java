package db;

import javax.swing.*;

public class AuthForm extends JFrame{
    private JPanel rootPanel;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JButton authButton;

    AuthForm(){
        setContentPane(rootPanel);
    }

    public static void main(String[] args) {
        JFrame jFrame = new AuthForm();
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("База данных");
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
