package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AddOperatingAcc extends JFrame {
    private JPanel rootPanel;
    private JFormattedTextField textNumber;
    private JFormattedTextField textDateStart;
    private JComboBox textOrgComboBox;
    private JButton saveButton;
    private JPanel panelDateStart;
    private com.toedter.calendar.JDateChooser dcDateStart = new com.toedter.calendar.JDateChooser();

    private DatabaseConnection mdbc;
    private Statement stmt;

    public AddOperatingAcc(final String persNum){
        setContentPane(rootPanel);
        panelDateStart.add(dcDateStart);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addOperAcc(persNum);
                JOptionPane.showMessageDialog(AddOperatingAcc.this,"Успешно добавлено!","Добавление",JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                dispose();
            }
        });
    }
    private void addOperAcc(String personalNumber){
        String number = textNumber.getText();
//        String dateStart = textDateStart.getText();
        String status = "действителен";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = dateFormat.format(dcDateStart.getDate());

        try {
            mdbc = new DatabaseConnection();
            Connection conn = mdbc.getMyConnection();
            stmt = conn.createStatement();

            String sqlQuery1 = "insert into operating_account(numberOperatingAccount, dateStartAccount, statusOperaingAccount, personalNumber) VALUES "+
                    "("+quotate(number)+","+quotate(dateStart)+","+quotate(status)+","+quotate(personalNumber)+")";
            stmt.executeUpdate(sqlQuery1);

            String sqlQuery2 = operAccTypeOrg(number);
            stmt.executeUpdate(sqlQuery2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String operAccTypeOrg(String operAccNumber) throws SQLException {
        String code="";
        String sqlQuery="";
        switch (getOperAccOrg()){
            case 1:
                ResultSet rs1 = stmt.executeQuery("select po.postCode from post_organization po");
                if(rs1.next()){
                    int postCode = rs1.getInt("postCode");
                    code = String.valueOf(postCode);
                    sqlQuery = "insert into post_operating_account(numberOperatingAccount, postCode) VALUES ("+quotate(operAccNumber)+","+quotate(code)+")";
                }
                break;
            case 2:
                ResultSet rs2 = stmt.executeQuery("select bo.bic from bank_organization bo");
                if(rs2.next())
                    code = rs2.getString("bic");
                sqlQuery = "insert into bank_operating_account(numberOperatingAccount, bic) VALUES ("+quotate(operAccNumber)+","+quotate(code)+")";
                break;
            case 3:
                ResultSet rs3 = stmt.executeQuery("select co.tinCash from cash_organization co");
                if(rs3.next())
                    code = rs3.getString("tinCash");
                sqlQuery = "insert into cash_operating_account(numberOperatingAccount, tinCash) VALUES ("+quotate(operAccNumber)+","+quotate(code)+")";
                break;
        }
        return sqlQuery;
    }

    private int getOperAccOrg(){
        return textOrgComboBox.getSelectedIndex()+1;
    }
    private String quotate(String content){
        return "'"+content+"'";
    }
}
