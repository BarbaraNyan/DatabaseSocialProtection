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

    private DatabaseConnection mdbc;
    private Statement stmt;
    private com.toedter.calendar.JDateChooser dcDateStart = new com.toedter.calendar.JDateChooser();

    AddIdDoc(final String persNum, final String relPersNum, JTable [] handbook){
        setContentPane(rootPanel);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        panelDateStart.add(dcDateStart);
        setMasks();
        getComboBox(typeDocComboBox,handbook[5]);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addIdDoc(persNum,relPersNum);
                JOptionPane.showMessageDialog(AddIdDoc.this,"Успешно добавлено!","Добавление",JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                dispose();
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

    private void addIdDoc(String personalNumber,String relPersonalNumber) {
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

        } catch (SQLException e) {
            e.printStackTrace();
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
        return typeDocComboBox.getSelectedIndex()+1;
    }
}
