package db;

import javax.swing.*;
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

    private DatabaseConnection mdbc;
    private Statement stmt;

    public AddRelative(final String persNum, JTable [] handbook){
        setContentPane(rootPanel);
        panelDateBirth.add(dcDateBirth);
        getComboBox(textRelDegreeComboBox,handbook[8]);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRelative(persNum);
                JOptionPane.showMessageDialog(AddRelative.this,"Успешно добавлено!","Добавление",JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                dispose();
            }
        });
        canselButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int rezult = JOptionPane.showConfirmDialog(AddRelative.this, "Вы уверены, что хотите отменить добавление члена семьи?", "Отмена добавления члена семьи", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });
    }

    private void addRelative(String personalNumber){
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int relationDegree(){
        return textRelDegreeComboBox.getSelectedIndex()+1;
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
