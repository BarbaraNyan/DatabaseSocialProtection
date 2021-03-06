package db;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.sun.glass.ui.Cursor.setVisible;

public class AddCategoryMeasure extends JFrame{
    private JComboBox comboBoxCategory;
    private JComboBox comboBoxMeasure;
    private JButton addButton;
    private JButton canselButton;
    private JPanel rootPanel;

    private DatabaseConnection mdbc;
    private Statement stmt;
    SimpleDateFormat formatForSql= new SimpleDateFormat("yyyy-MM-dd");

    AddCategoryMeasure(final String persNum, final TableModel[] handbook){
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1250,150));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(100,dimension.height/2-this.getSize().height/2);


        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rez=insertClientMeasure(persNum, handbook);
                if(rez==1)
                    JOptionPane.showMessageDialog(AddCategoryMeasure.this,"Успешно добавлено!","Добавление",JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(AddCategoryMeasure.this,"Ошибка при добавлении","Добавление",JOptionPane.WARNING_MESSAGE);
                setVisible(false);
                dispose();
            }
        });

        canselButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int rezult = JOptionPane.showOptionDialog(AddCategoryMeasure.this, "Вы уверены, что хотите отменить добавление новой меры социальной поддержки?", "Отмена добавления меры социальной поддержки", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });
        //передача данных из справочников в списки
        getComboBox(comboBoxCategory, handbook[0]);

        comboBoxCategory.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                comboBoxMeasure.removeAllItems();
                String item;
                int k=comboBoxCategory.getSelectedIndex();
                for (int i = 0;i<handbook[1].getRowCount();i++){
                    if(Integer.valueOf(handbook[1].getValueAt(i, 1).toString())==handbook[0].getValueAt(k,0)) {
                        item = handbook[1].getValueAt(i, 2).toString();
                        comboBoxMeasure.addItem(item);
                    }
                }
            }
        });
        comboBoxCategory.setSelectedIndex(1);
    }
    public void getComboBox(JComboBox cb, TableModel tl){
        String item;
        int k=tl.getColumnCount()-1;
        for (int i = 0;i<tl.getRowCount();i++){
            item = tl.getValueAt(i, k).toString();
            cb.addItem(item);
        }
    }


    private int insertClientMeasure(String persNum, final TableModel[] handbook){
        String codeCategory=handbook[0].getValueAt(comboBoxCategory.getSelectedIndex(), 0).toString();
        String codeMeasure=handbook[1].getValueAt(comboBoxMeasure.getSelectedIndex(), 0).toString();
        String period="";

        String dateNow=formatForSql.format(new Date());
        String dateEnd="";
        SimpleDateFormat formatForMounth= new SimpleDateFormat("MM");
        SimpleDateFormat formatForYear= new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();

        if(period.equals("1")) {
            calendar.setTime(new Date());
            calendar.set(Calendar.MONTH, Integer.parseInt(formatForMounth.format(calendar.getTime())));
            dateEnd = formatForSql.format(calendar.getTime());
        }else if (period.equals("2")){
            calendar.setTime(new Date());
            calendar.set(Calendar.YEAR, Integer.parseInt(formatForYear.format(calendar.getTime()))+1);
            dateEnd = formatForSql.format(calendar.getTime());
        }else if(period.equals("3")){
            calendar.setTime(new Date());
            calendar.set(Calendar.YEAR, Integer.parseInt(formatForYear.format(calendar.getTime()))+50);
            dateEnd = formatForSql.format(calendar.getTime());
        }

        try {
            mdbc = new DatabaseConnection();
            Connection conn = mdbc.getMyConnection();
            stmt = conn.createStatement();

            String sqlQuery = "insert into client_measure(personalNumber, codeSocialMeasure, " +
                    "codeClientCategory, dateStartMeasure, dateEndMeasure, statusMeasure) " +
                    "VALUES ('"+persNum+"', '"+codeMeasure+"', '"+codeCategory+"', '"+dateNow+"', " +
                    "'"+dateEnd+"', 'Назначено')";
            stmt.executeUpdate(sqlQuery);
            mdbc.close(stmt);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

}
