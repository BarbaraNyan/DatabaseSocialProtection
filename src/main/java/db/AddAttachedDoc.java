package db;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddAttachedDoc extends JFrame{
    private JTextField textNumber;
    private JComboBox textTypeDocComboBox;
    private JTextField textName;
    private JFormattedTextField textDateStart;
    private JPanel rootPanel;
    private JButton saveButton;
    private JPanel panelDateStart;
    private JButton canselButton;
    private com.toedter.calendar.JDateChooser dcDateStart = new com.toedter.calendar.JDateChooser();
    private DatabaseConnection mdbc;
    private Statement stmt;
    private JTable tb;

    AddAttachedDoc(final String persNum, JTable [] handbook){
        setContentPane(rootPanel);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        panelDateStart.add(dcDateStart);
        dcDateStart.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        getComboBox(textTypeDocComboBox,handbook[6]);
        tb=handbook[6];
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNullBorder();
                if(textNumber.getText().equals("")){
                    textNumber.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(textName.getText().equals("")) {
                    textName.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(dcDateStart.getDate()==null) {
                    dcDateStart.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else {
                    int rez=addAttDoc(persNum);
                    if(rez==1)
                        JOptionPane.showMessageDialog(AddAttachedDoc.this,"Успешно добавлено!","Добавление",JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(AddAttachedDoc.this,"Ошибка при добавлении","Добавление",JOptionPane.WARNING_MESSAGE);
                    setVisible(false);
                    dispose();
                }
            }
        });
        canselButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int rezult = JOptionPane.showOptionDialog(AddAttachedDoc.this, "Вы уверены, что хотите отменить добавление документа?", "Отмена добавления документа", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });
    }
    private void showMess(){
        JOptionPane.showMessageDialog(AddAttachedDoc.this, "Должны быть введены все поля", "Добавление", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setNullBorder(){
        textNumber.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        textName.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        dcDateStart.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
    }

    private int addAttDoc(String personalNumber){
        String typeDoc = String.valueOf(getTypeAttDoc());
        String number = textNumber.getText();
        String name = textName.getText();
//        String dateStart = textDateStart.getText();
        String status = "Действителен";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = dateFormat.format(dcDateStart.getDate());

        try {
            mdbc = new DatabaseConnection();
            Connection conn = mdbc.getMyConnection();
            stmt = conn.createStatement();

            String sqlQuery = "insert into attached_document(numberAttachedDocument, nameAttachedDocument, " +
                    "dateStartAttachedDocument, statusAttachedDocument, numberTypeAttachedDocument, personalNumber) VALUES " +
                    "("+quotate(number)+","+quotate(name)+","+quotate(dateStart)+","+quotate(status)+","+quotate(typeDoc)+","+quotate(personalNumber)+")";
            stmt.executeUpdate(sqlQuery);
            mdbc.close(stmt);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    private String quotate(String content){
        return "'"+content+"'";
    }
    private void getComboBox(JComboBox cb, JTable tl){
        String item;
        int k=tl.getColumnCount()-1;
        for (int i = 0;i<tl.getRowCount();i++){
            item = tl.getModel().getValueAt(i, k).toString();
            cb.addItem(item);
        }
    }
    private int getTypeAttDoc() {
        return Integer.parseInt(tb.getModel().getValueAt(textTypeDocComboBox.getSelectedIndex(), 0).toString());
    }
}
