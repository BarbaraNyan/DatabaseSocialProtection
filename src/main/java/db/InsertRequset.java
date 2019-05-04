package db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertRequset extends JFrame{
    private JTextField textTotalAmount;
    private JButton addButton;
    private JButton resetButton;
    private JPanel rootPanel;
    private JComboBox comboBoxEmployee;
    private JPanel pnDate;
    private JPanel pnPeriodFrom;
    private JPanel pnPeriodTo;
    private com.toedter.calendar.JDateChooser dcDateOfPreparation = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcPeriodFrom = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcPeriodTo = new com.toedter.calendar.JDateChooser();
    private DatabaseConnection mdbc;
    private Statement stmt;
    private TableModel [] dtm;


    InsertRequset(TableModel [] tm){
        dtm=tm;
        setContentPane(rootPanel);
        Date dateNow = new Date();
        SimpleDateFormat formatForMounth= new SimpleDateFormat("MM");

        dcDateOfPreparation.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        dcDateOfPreparation.setDate(dateNow);
        pnDate.add(dcDateOfPreparation);
        textTotalAmount.setText(tm[0].getValueAt(0,6).toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, Integer.parseInt(formatForMounth.format(calendar.getTime())));
        dcPeriodFrom.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        dcPeriodFrom.setDate(calendar.getTime());
        pnPeriodFrom.add(dcPeriodFrom);

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        dcPeriodTo.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        dcPeriodTo.setDate(calendar.getTime());
        pnPeriodTo.add(dcPeriodTo);

        getComboBox(comboBoxEmployee, tm[1]);

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rezult = JOptionPane.showConfirmDialog(InsertRequset.this, "Вы уверены, что хотите отменить назначение выплаты?", "Отменить назначение выплаты", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertNewRequest();
                setVisible(false);
            }
        });
    }

    public void getComboBox(JComboBox cb, TableModel tl){
        for (int i = 0;i<tl.getRowCount();i++){
            StringBuffer item=new StringBuffer();
            item.append(tl.getValueAt(i, 1).toString()+" ");
            item.append(tl.getValueAt(i, 2).toString().substring(0,1)+"., ");
            item.append(tl.getValueAt(i, 3).toString().substring(0,1)+".");
            cb.addItem(item);
        }
    }

    public void insertNewRequest(){
        try {
            mdbc=new DatabaseConnection();
            Connection conn=mdbc.getMyConnection();
            stmt= conn.createStatement();
            SimpleDateFormat formatForSql= new SimpleDateFormat("yyyy-MM-dd");
            String datePrep=formatForSql.format(dcDateOfPreparation.getDate());
            String perFrom=formatForSql.format(dcPeriodFrom.getDate());
            String perTo=formatForSql.format(dcPeriodTo.getDate());
            String amount=textTotalAmount.getText();
            String state="Назначено";
            String numEmp=dtm[1].getValueAt(comboBoxEmployee.getSelectedIndex(),0).toString();

            for(int i=0; i<dtm[0].getRowCount(); i++){
                String persNum=dtm[0].getValueAt(i,0).toString();
                String sqlQueryNumOA = "select oa.numberOperatingAccount from operating_account oa inner join social_client sc " +
                        "on oa.personalNumber=sc.personalNumber where oa.statusOperaingAccount='Действителен' and sc.personalNumber='"+persNum+"'";
                ResultSet rs=stmt.executeQuery(sqlQueryNumOA);
                if(rs.next()) {
                    String numOA=rs.getString(1);
                    String sqlRequest = "insert into request_for_cash_settlement(dateOfPreparation, periodFrom, periodTo, totalAmount, stateRequest, numberOperatingAccount, personnelNumberEmployee) values\n" +
                            "('" + datePrep + "', '" + perFrom + "', '" + perTo + "', '" + amount + "', '" + state + "', '" + numOA + "', '" + numEmp + "')";
                    stmt.executeUpdate(sqlRequest);
                }
            }
            JOptionPane.showMessageDialog(InsertRequset.this, "Выплаты назначены", "Окно сообщения", JOptionPane.INFORMATION_MESSAGE);
            mdbc.close(stmt);
        }
        catch(Exception e){
            System.out.println("Failed to insert data");
            mdbc.close(stmt);
        }
    }
}
