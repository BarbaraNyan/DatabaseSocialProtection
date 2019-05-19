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

public class AddIncome extends JFrame{
    private JPanel root;
    private JComboBox givenComboBox;
    private JComboBox periodCombox;
    private JSpinner spinnerYear;
    private JComboBox typeIncomeComboBox;
    private JTextField textAmount;
    private JButton addButton;
    private JButton canselButton;

    private DatabaseConnection mdbc;
    private Statement stmt;

    public AddIncome(final String []client, final JTable [] table){
        setContentPane(root);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        getComboBox(typeIncomeComboBox, table[1]);
        getFIOComboBox(givenComboBox, table[0], client);
        SpinnerModel sm = new SpinnerNumberModel(2019, 2019, 2150, 1);
        spinnerYear.setModel(sm);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textAmount.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
                if(textAmount.getText().equals("")){
                    textAmount.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else {
                    int rez = addIncome(client[0], table);
                    if (rez == 1)
                        JOptionPane.showMessageDialog(AddIncome.this, "Успешно добавлено!", "Добавление", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(AddIncome.this, "Ошибка при добавлении", "Добавление", JOptionPane.WARNING_MESSAGE);

                    setVisible(false);
                    dispose();
                }
            }
        });
        canselButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int rezult = JOptionPane.showOptionDialog(AddIncome.this, "Вы уверены, что хотите отменить добавление дохода?", "Отмена добавления дохода", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });
    }

    private void showMess(){
        JOptionPane.showMessageDialog(AddIncome.this, "Должны быть введены все поля", "Добавление", JOptionPane.INFORMATION_MESSAGE);
    }

    private int addIncome(String personalNumber, JTable [] tl){

        String date=spinnerYear.getValue().toString()+"-"+(periodCombox.getSelectedIndex()+1)+"-01";
        String numTypeIncome=tl[1].getModel().getValueAt(typeIncomeComboBox.getSelectedIndex(),0).toString();
        Double sum=Double.parseDouble(textAmount.getText());
        String sqlQuery;
        try {
            mdbc = new DatabaseConnection();
            Connection conn = mdbc.getMyConnection();
            stmt = conn.createStatement();

            if(givenComboBox.getSelectedIndex()==0)
                sqlQuery="insert into income(periodIncome, amount, personalNumber, numberTypeIncone) " +
                        "VALUES ('"+date+"','"+sum+"','"+personalNumber+"','"+numTypeIncome+"')";
            else {
                String num=tl[0].getModel().getValueAt(givenComboBox.getSelectedIndex()-1,0).toString();
                sqlQuery = "insert into income(periodIncome, amount, personalNumber, numberTypeIncone, relativePersonalNumber) " +
                        "VALUES ('" + date + "','" + sum + "','" + personalNumber + "','" + numTypeIncome + "','" + num + "')";
            }

            stmt.executeUpdate(sqlQuery);
            mdbc.close(stmt);
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void getComboBox(JComboBox cb, JTable tl){
        String item;
        int k=tl.getColumnCount()-1;
        for (int i = 0;i<tl.getRowCount();i++){
            item = tl.getModel().getValueAt(i, k).toString();
            cb.addItem(item);
        }
    }

    public void getFIOComboBox(JComboBox cb, JTable tl, String [] client){
        cb.addItem(client[1]+" "+client[2]+" "+client[3]);
        for (int i = 0;i<tl.getRowCount();i++){
            StringBuffer item=new StringBuffer();
            item.append(tl.getModel().getValueAt(i, 1).toString()+" ");
            item.append(tl.getModel().getValueAt(i, 2).toString()+" ");
            item.append(tl.getModel().getValueAt(i, 3).toString());
            cb.addItem(item);
        }
    }
}
