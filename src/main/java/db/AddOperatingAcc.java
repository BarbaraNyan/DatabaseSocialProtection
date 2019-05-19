package db;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddOperatingAcc extends JFrame {
    private JPanel rootPanel;
    private JFormattedTextField textNumber;
    private JFormattedTextField textDateStart;
    private JComboBox textOrgComboBox;
    private JButton saveButton;
    private JPanel panelDateStart;
    private JButton canselButton;
    private com.toedter.calendar.JDateChooser dcDateStart = new com.toedter.calendar.JDateChooser();

    private DatabaseConnection mdbc;
    private Statement stmt;

    public AddOperatingAcc(final String persNum, JTable b, JTable p, JTable c){
        setContentPane(rootPanel);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        panelDateStart.add(dcDateStart);
        dcDateStart.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        getComboBoxOrganization(textOrgComboBox, b, p, c);
        setMasks();

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNullBorder();
                if(textNumber.getText().equals("********************")) {
                    textNumber.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(dcDateStart.getDate()==null) {
                    dcDateStart.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else {
                    int rez = addOperAcc(persNum);
                    if (rez == 1)
                        JOptionPane.showMessageDialog(AddOperatingAcc.this, "Успешно добавлено!", "Добавление", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(AddOperatingAcc.this, "Ошибка при добавлении", "Добавление", JOptionPane.WARNING_MESSAGE);
                    setVisible(false);
                    dispose();
                }
            }
        });
        canselButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int rezult = JOptionPane.showOptionDialog(AddOperatingAcc.this, "Вы уверены, что хотите отменить добавление расчетного счета?", "Отмена добавления расчетного счета", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });
    }

    private void setNullBorder(){
        textNumber.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        dcDateStart.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
    }
    private void showMess(){
        JOptionPane.showMessageDialog(AddOperatingAcc.this, "Должны быть введены все поля", "Добавление", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setMasks(){
        try {
            MaskFormatter numberDocFormatter = new MaskFormatter("####################");
            numberDocFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory numberDocFactory = new DefaultFormatterFactory(numberDocFormatter);

            textNumber.setFormatterFactory(numberDocFactory);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private int addOperAcc(String personalNumber){
        String number = textNumber.getText();
//        String dateStart = textDateStart.getText();
        String status = "Действителен";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = dateFormat.format(dcDateStart.getDate());
        String org = textOrgComboBox.getItemAt(textOrgComboBox.getSelectedIndex()).toString();
        int numLine = org.indexOf("|");
        String numOrg=org.substring(0, numLine-1);

        try {
            mdbc = new DatabaseConnection();
            Connection conn = mdbc.getMyConnection();
            stmt = conn.createStatement();

            String sqlQuery1 = "insert into operating_account(numberOperatingAccount, dateStartAccount, statusOperaingAccount, personalNumber) VALUES "+
                    "("+quotate(number)+","+quotate(dateStart)+","+quotate(status)+","+quotate(personalNumber)+")";
            stmt.executeUpdate(sqlQuery1);

            String sqlQuery2 = operAccTypeOrg(number, numOrg);
            stmt.executeUpdate(sqlQuery2);
            mdbc.close(stmt);
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private String operAccTypeOrg(String operAccNumber, String num) throws SQLException {
        String code="";
        String sqlQuery="";
        ResultSet rs1 = stmt.executeQuery("select po.postCode from post_organization po where po.postCode = '"+num+"'");
        if(rs1.next()){
            int postCode = rs1.getInt("postCode");
            code = String.valueOf(postCode);
            sqlQuery = "insert into post_operating_account(numberOperatingAccount, postCode) VALUES ("+quotate(operAccNumber)+","+quotate(code)+")";
        }else{
            ResultSet rs2 = stmt.executeQuery("select bo.bic from bank_organization bo where bo.bic = '"+num+"'");
            if(rs2.next()) {
                code = rs2.getString("bic");
                sqlQuery = "insert into bank_operating_account(numberOperatingAccount, bic) VALUES (" + quotate(operAccNumber) + "," + quotate(code) + ")";
            }else {
                ResultSet rs3 = stmt.executeQuery("select co.tinCash from cash_organization co where co.tinCash = '" + num + "'");
                if (rs3.next()) {
                    code = rs3.getString("tinCash");
                    sqlQuery = "insert into cash_operating_account(numberOperatingAccount, tinCash) VALUES (" + quotate(operAccNumber) + "," + quotate(code) + ")";
                }
            }
        }
        return sqlQuery;
    }

    private String quotate(String content){
        return "'"+content+"'";
    }

    private void getComboBoxOrganization(JComboBox cb, JTable b, JTable p, JTable c){
        cb.removeAllItems();
        String item;
        for (int i = 0;i<b.getRowCount();i++){
            item = b.getModel().getValueAt(i, 0).toString()+" | "+b.getModel().getValueAt(i,2).toString();
            cb.addItem(item);
        }
        for (int i = 0;i<p.getRowCount();i++){
            item = p.getModel().getValueAt(i, 0).toString()+" | "+p.getModel().getValueAt(i,3).toString();
            cb.addItem(item);
        }
        for (int i = 0;i<c.getRowCount();i++){
            item = c.getModel().getValueAt(i, 0).toString()+" | "+c.getModel().getValueAt(i,2).toString();
            cb.addItem(item);
        }
    }
}
