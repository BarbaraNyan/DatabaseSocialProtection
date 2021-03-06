package db;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddIdDoc extends JFrame{
    private JComboBox typeDocComboBox;
    private JFormattedTextField textSeries;
    private JFormattedTextField textNumber;
    private JTextField textGivenBy;
    private JPanel rootPanel;
    private JButton saveButton;
    private JPanel panelDateStart;
    private JButton canselButton;
    private JTable tb;

    private DatabaseConnection mdbc;
    private Statement stmt;
    private com.toedter.calendar.JDateChooser dcDateStart = new com.toedter.calendar.JDateChooser();

    AddIdDoc(final String persNum, final String relPersNum, JTable [] handbook){
        setContentPane(rootPanel);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        panelDateStart.add(dcDateStart);
        dcDateStart.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        setMasks();
        getComboBox(typeDocComboBox,handbook[5]);
        tb=handbook[5];
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNullBorder();
                if(textSeries.getText().equals("****")){
                    textSeries.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(textNumber.getText().equals("******")) {
                    textNumber.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(textGivenBy.getText().equals("")) {
                    textGivenBy.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else if(dcDateStart.getDate()==null) {
                    dcDateStart.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.RED));
                    showMess();
                }else {
                    int rez = addIdDoc(persNum, relPersNum);
                    if (rez == 1)
                        JOptionPane.showMessageDialog(AddIdDoc.this, "Успешно добавлено!", "Добавление", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(AddIdDoc.this, "Ошибка при добавлении", "Добавление", JOptionPane.WARNING_MESSAGE);

                    setVisible(false);
                    dispose();
                }
//                dispatchEvent(new WindowEvent(AddIdDoc.this,WindowEvent.WINDOW_CLOSING));
            }
        });

        canselButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int rezult = JOptionPane.showOptionDialog(AddIdDoc.this, "Вы уверены, что хотите отменить добавление документа?", "Отмена добавления документа", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });
    }

    private void showMess(){
        JOptionPane.showMessageDialog(AddIdDoc.this, "Должны быть введены все поля", "Добавление", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setNullBorder(){
        textSeries.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        textNumber.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        textGivenBy.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        dcDateStart.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
    }

    private int addIdDoc(String personalNumber,String relPersonalNumber) {
        String typeDoc = String.valueOf(getTypeIdDoc());
        String series = textSeries.getText();
        String number = textNumber.getText();
        String givenBy = textGivenBy.getText();
        String status = "Действителен";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = dateFormat.format(dcDateStart.getDate());

        try {
            mdbc = new DatabaseConnection();
            Connection conn = mdbc.getMyConnection();
            stmt = conn.createStatement();

            String sqlQuery="";
            if(!relPersonalNumber.equals(""))
                sqlQuery = "insert into identification_document(docSeries, docNumber, givenBy, dateStartIdDocument, " +
                        "statusIdDocument, numberTypeIdDocument, relativePersonalNumber, personalNumber) VALUES (" +
                        quotate(series) + "," + quotate(number) + "," + quotate(givenBy) + "," + quotate(dateStart) + "," + quotate(status) +
                        "," + quotate(typeDoc) +","+quotate(relPersonalNumber)+ "," + quotate(personalNumber) + ")";
                else{
                sqlQuery = "insert into identification_document(docSeries, docNumber, givenBy, dateStartIdDocument, " +
                        "statusIdDocument, numberTypeIdDocument, personalNumber) VALUES (" +
                        quotate(series) + "," + quotate(number) + "," + quotate(givenBy) + "," + quotate(dateStart) + "," + quotate(status) +
                        "," + quotate(typeDoc) + "," + quotate(personalNumber) + ")";
            }
            stmt.executeUpdate(sqlQuery);
            mdbc.close(stmt);
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    private void setMasks(){
        try {
            MaskFormatter seriesDocFormatter = new MaskFormatter("####");
            seriesDocFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory seriesDocFactory = new DefaultFormatterFactory(seriesDocFormatter);

            MaskFormatter numberDocFormatter = new MaskFormatter("######");
            numberDocFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory numberDocFactory = new DefaultFormatterFactory(numberDocFormatter);

            textSeries.setFormatterFactory(seriesDocFactory);
            textNumber.setFormatterFactory(numberDocFactory);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    private int getTypeIdDoc() {
        return Integer.parseInt(tb.getValueAt(typeDocComboBox.getSelectedIndex(), 0).toString());
    }
}
