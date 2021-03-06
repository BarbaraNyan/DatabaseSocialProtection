package db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AddRelative extends JFrame{
    private JPanel rootPanel;
    private JTextField textSurname;
    private JFormattedTextField textDateBirth;
    private JComboBox textRelDegreeComboBox;
    private JTextField textName;
    private JButton saveButton;
    private JTextField textPatronymic;
    private JPanel panelDateBirth;
    private JButton canselButton;
    private com.toedter.calendar.JDateChooser dcDateBirth = new com.toedter.calendar.JDateChooser();

    private JTable tb;
    private DatabaseConnection mdbc;
    private Statement stmt;

    public AddRelative(final String persNum, JTable [] handbook){
        setContentPane(rootPanel);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        panelDateBirth.add(dcDateBirth);
        dcDateBirth.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        getComboBox(textRelDegreeComboBox,handbook[8]);
        tb=handbook[8];

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNullBorder();
                if(textSurname.getText().equals("")){
                    textSurname.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(textName.getText().equals("")) {
                    textName.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(textPatronymic.getText().equals("")) {
                    textPatronymic.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(dcDateBirth.getDate()==null) {
                    dcDateBirth.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else {
                    int rez = addRelative(persNum);
                    if (rez == 1)
                        JOptionPane.showMessageDialog(AddRelative.this, "Успешно добавлено!", "Добавление", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(AddRelative.this, "Ошибка при добавлении", "Добавление", JOptionPane.WARNING_MESSAGE);
                    setVisible(false);
                    dispose();
                }
            }
        });
        canselButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int rezult = JOptionPane.showOptionDialog(AddRelative.this, "Вы уверены, что хотите отменить добавление члена семьи?", "Отмена добавления члена семьи", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });
    }

    private void showMess(){
        JOptionPane.showMessageDialog(AddRelative.this, "Должны быть введены все поля", "Добавление", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setNullBorder(){
        textSurname.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        textName.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        textSurname.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        dcDateBirth.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
    }

    private int addRelative(String personalNumber){
        String surname = textSurname.getText();
        String name = textName.getText();
        String patronymic = textPatronymic.getText();
//        String dateBirth = textDateBirth.getText();
        String relDegree = String.valueOf(relationDegree());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateBirth = dateFormat.format(dcDateBirth.getDate());
        try {
            mdbc = new DatabaseConnection();
            Connection conn = mdbc.getMyConnection();
            stmt = conn.createStatement();

            String sqlQuery ="insert into relatives(personalNumber, surnameRelative," +
                    " nameRelative, patronymicRelative, dateBirthRelative, numberRelationDegree) VALUES ("+
                    quotate(personalNumber)+","+quotate(surname)+","+quotate(name)+","+quotate(patronymic)+","+
                    quotate(dateBirth)+","+quotate(relDegree)+")";
            stmt.executeUpdate(sqlQuery);
            mdbc.close(stmt);
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int relationDegree(){
        return Integer.parseInt(tb.getValueAt(textRelDegreeComboBox.getSelectedIndex(), 0).toString());
    }
    private String quotate(String content){
        return "'"+content+"'";
    }
    public void getComboBox(JComboBox cb, JTable tl){
        String item;
        int k=tl.getColumnCount()-1;
        for (int i = 0;i<tl.getRowCount();i++){
            item = tl.getModel().getValueAt(i, k).toString();
            cb.addItem(item);
        }
    }
}
