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


    InsertRequset(final TableModel [] tm, final int rowNum){
        setContentPane(rootPanel);
        Date dateNow = new Date();
        SimpleDateFormat formatForMounth= new SimpleDateFormat("MM");

        dcDateOfPreparation.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        dcDateOfPreparation.setDate(dateNow);
        pnDate.add(dcDateOfPreparation);
        if(rowNum==-1)
            textTotalAmount.setText(tm[0].getValueAt(0,8).toString());
        else
            textTotalAmount.setText(tm[0].getValueAt(rowNum,8).toString());


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
                int rezult = JOptionPane.showOptionDialog(InsertRequset.this, "Вы уверены, что хотите отменить назначение выплаты?", "Отменить назначение выплаты", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertNewRequest(tm, rowNum);
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

    public void insertNewRequest(TableModel []dtm, int row){
        int rowCount;
        int rowStart;
        String status;
        String codeMeasure;
        String codeCategory;
        if(row!=-1){
            rowCount=row+1;
            rowStart=row;
        }else{
            rowCount=dtm[0].getRowCount();
            rowStart=0;
        }
        SimpleDateFormat formatForSql= new SimpleDateFormat("yyyy-MM-dd");
        String datePrep=formatForSql.format(dcDateOfPreparation.getDate());
        String perFrom=formatForSql.format(dcPeriodFrom.getDate());
        String perTo=formatForSql.format(dcPeriodTo.getDate());
        String amount=textTotalAmount.getText();
        String state="Назначено";
        String numEmp=dtm[1].getValueAt(comboBoxEmployee.getSelectedIndex(),0).toString();

        try {
            mdbc=new DatabaseConnection();
            Connection conn=mdbc.getMyConnection();
            stmt= conn.createStatement();

            for(int i=rowStart; i<rowCount; i++){
                if(row!=-1){
                    status=dtm[0].getValueAt(i,7).toString();
                    codeMeasure=dtm[0].getValueAt(i,2).toString();
                    codeCategory=dtm[0].getValueAt(i,1).toString();
                }else{
                    status=dtm[0].getValueAt(i,10).toString();
                    codeMeasure=dtm[0].getValueAt(i,6).toString();
                    codeCategory=dtm[0].getValueAt(i,4).toString();
                }
                String persNum=dtm[0].getValueAt(i,0).toString();
                String sqlQueryNumOA = "select oa.numberOperatingAccount from operating_account oa inner join social_client sc " +
                        "on oa.personalNumber=sc.personalNumber where oa.statusOperaingAccount='Действителен' and sc.personalNumber='"+persNum+"'";
                ResultSet rs=stmt.executeQuery(sqlQueryNumOA);
                if(rs.next()) {
                    String numOA=rs.getString(1);
                    String sqlRequest = "insert into request_for_cash_settlement(dateOfPreparation, periodFrom, periodTo, totalAmount, stateRequest, numberOperatingAccount, personnelNumberEmployee, codeClientCategory, codeSocialMeasure) values\n" +
                            "('" + datePrep + "', '" + perFrom + "', '" + perTo + "', '" + amount + "', '" + state + "', '" + numOA + "', '" + numEmp + "', '"+codeCategory+"', '"+codeMeasure+"')";
                    stmt.executeUpdate(sqlRequest);
                    if(status.equals("1")) {
                        String sqlUpdateStatus="update client_measure set statusMeasure='Закончено' where personalNumber='"+persNum+"' and codeSocialMeasure='"+codeMeasure+"' and codeClientCategory='"+codeCategory+"'";
                        stmt.executeUpdate(sqlUpdateStatus);
                    }
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
