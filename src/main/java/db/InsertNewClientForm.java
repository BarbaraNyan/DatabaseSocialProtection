package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class InsertNewClientForm extends JFrame{
    private JPanel rootPanel;
    private JTextField textSurname;
    private JTextField textName;
    private JTextField textPatronymic;
    private JTextField textDateBirth;
    private JTextField textSNILS;
    private JTextField textTelephone;
    private JTextField textEmail;
    private JTextField textStreet;
    private JTextField textHouse;
    private JTextField textFlat;
    private JTextField textIndex;
    private JComboBox comboBoxCategory;
    private JComboBox comboBoxDistrict;
    private JComboBox comboBoxInhabitedLoc;
    private ButtonGroup radioButtonGender;
    private JRadioButton radioButtonM;
    private JRadioButton radioButtonF;
    private JButton addButton;
    private JComboBox comboBoxStreet;
    private JComboBox comboBoxRegion;
    private JComboBox comboBoxIndex;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBoxTypeIdDoc;
    private JTextField textIdDocSeries;
    private JTextField textIdDocNumber;
    private JTextField textIdDocGivenBy;
    private JFormattedTextField textIdDocDateStart;

    private DatabaseConnection mdbc;
    private Statement stmt;
    InsertNewClientForm(JTable [] t){
        setContentPane(rootPanel);
        setButtonGroup();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertClientTable();
                setVisible(false);
            }
        });
        //передача данных из справочников в списки
        getComboBox(comboBoxIndex, t[0]);
        getComboBox(comboBoxRegion, t[1]);
        getComboBox(comboBoxDistrict, t[2]);
        getComboBox(comboBoxInhabitedLoc, t[3]);
        getComboBox(comboBoxStreet, t[4]);
        getComboBox(comboBoxTypeIdDoc, t[5]);
    }

    public void getComboBox(JComboBox cb, JTable tl){
        String item;
        int k=tl.getColumnCount()-1;
        for (int i = 0;i<tl.getRowCount();i++){
            item = tl.getModel().getValueAt(i, k).toString();
            cb.addItem(item);
        }
    }

//    public void setSelectedValueCategory(String value){
//        String item;
//        for (int i = 0;i<comboBoxCategory.getItemCount();i++){
//            item = comboBoxCategory.getItemAt(i).toString();
//            if (item.equalsIgnoreCase(value)) {
//                comboBoxCategory.setSelectedIndex(i);
//                break;
//            }
//        }
//    }

    private void setButtonGroup(){
        radioButtonGender = new ButtonGroup();
        radioButtonGender.add(radioButtonM);
        radioButtonGender.add(radioButtonF);
    }
    private String getGender(){
        if(radioButtonF.isSelected())
            return "1";
        else
            return "2";
    }

    //    private int getCategory(){
//        return comboBoxCategory.getSelectedIndex();
//    }
    private String getIndex(){
        return comboBoxIndex.getSelectedItem().toString();
    }
    private int getRegion(){
        return comboBoxRegion.getSelectedIndex()+1;
    }
    private int getDistrict(){
        return comboBoxDistrict.getSelectedIndex()+1;
    }
    private int getInhabitedLoc(){
        return comboBoxInhabitedLoc.getSelectedIndex()+1;
    }
    private int getStreet(){
        return comboBoxStreet.getSelectedIndex()+1;
    }

    private int getTypeIdDoc() {
        return comboBoxTypeIdDoc.getSelectedIndex()+1;
    }

    public String quotate(String content){
        return "'"+content+"'";
    }

    public void insertClientTable(){
//        String category;

        String surname;
        String name;
        String patronymic;
        String gender;
        String dateBirth;
        String snils;
        String telephone;
        String email;

        String region;
        String district;
        String inhabitedLoc;
        String street;
        String house;
        String flat;
        String index;

        String typeIdDoc;
        String idDocSeries;
        String idDocNumber;
        String idDocGivenBy;
        String idDocDateStart;
        String idDocStatus;

//        category = comboBoxCategory.getSelectedItem().toString();
//        category = Integer.toString(getCategory());

        surname = textSurname.getText();
        name = textName.getText();
        patronymic = textPatronymic.getText();
        gender = getGender();
        dateBirth = textDateBirth.getText();
        snils = textSNILS.getText();
        telephone = textTelephone.getText();
        email = textEmail.getText();

        index = getIndex();
        region = Integer.toString(getRegion());
        district = Integer.toString(getDistrict());
        inhabitedLoc = Integer.toString(getInhabitedLoc());
        street = Integer.toString(getStreet());
        house = textHouse.getText();
        flat = textFlat.getText();

        typeIdDoc = Integer.toString(getTypeIdDoc());
        idDocSeries = textIdDocSeries.getText();
        idDocNumber = textIdDocNumber.getText();
        idDocGivenBy = textIdDocGivenBy.getText();
        idDocDateStart = textIdDocDateStart.getText();
        idDocStatus = "действителен";

        String addressId ="1";
        String persNum = "1";

        try {
            mdbc=new DatabaseConnection();
            Connection conn=mdbc.getMyConnection();
            stmt= conn.createStatement();

            String sqlQuery1 = "insert into address(house, flat, indexNum, regionNum, districtNum, inhabitedLocalityNum, streetNum) values\n" +
                    "("+quotate(house)+","+quotate(flat)+","+quotate(index)+","+quotate(region)+","+quotate(district)+","+
                    quotate(inhabitedLoc)+","+quotate(street)+")";
            stmt.executeUpdate(sqlQuery1);
            ResultSet rs = stmt.executeQuery("select max(id) as maxId from address");
            if(rs.next())
                addressId = rs.getString("maxId");
            ResultSet rs2 = stmt.executeQuery("select max(personalNumber) as maxId from social_client");
            if(rs2.next()){
                int personalNumber = rs2.getInt("maxId");
                personalNumber++;
                persNum = Integer.toString(personalNumber);
            }
            String sqlQuery2 = "insert into social_client(personalNumber, surname, name, patronymic, dateBirth, snils, telephone, email, genderNum, addressId) values"+
                    "("+quotate(persNum)+","+quotate(surname)+","+quotate(name)+","+quotate(patronymic)+","+"{d "+quotate(dateBirth)+" }"+
                    ","+quotate(snils)+","+quotate(telephone)+","+quotate(email)+","+
                    quotate(gender)+","+quotate(addressId)+")";
            stmt.executeUpdate(sqlQuery2);
            String sqlQuery3 = "insert into identification_document(docSeries, docNumber, givenBy, dateStart, status, typeIdDocNum, social_client_personalNumber) values" +
                    "("+quotate(idDocSeries)+","+quotate(idDocNumber)+","+quotate(idDocGivenBy)+","+quotate(idDocDateStart)+","+
                    quotate(idDocStatus)+","+quotate(typeIdDoc)+","+quotate(persNum)+")";
            stmt.executeUpdate(sqlQuery3);

            mdbc.close(stmt);
        }
        catch(Exception e){
            System.out.println("Failed to insert data");
            mdbc.close(stmt);
        }
    }
}
