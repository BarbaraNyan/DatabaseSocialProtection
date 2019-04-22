package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

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
    private int getIndex(){
    return comboBoxIndex.getSelectedIndex()+1;
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

//        district = comboBoxDistrict.getSelectedItem().toString();
//        inhabitedLoc = comboBoxInhabitedLoc.getSelectedItem().toString();
        index = Integer.toString(getIndex());
        region = Integer.toString(getRegion());
        district = Integer.toString(getDistrict());
        inhabitedLoc = Integer.toString(getInhabitedLoc());
        street = Integer.toString(getStreet());
        house = textHouse.getText();
        flat = textFlat.getText();

        String addressId = "5";

        String query1 = "insert into address(house, flat, `index`, regionNum, districtNum, inhabitedLocalityNum, streetNum) values"+
                "("+quotate(house)+","+quotate(flat)+","+quotate(index)+","+quotate(region)+","+quotate(district)+","+
                quotate(inhabitedLoc)+","+quotate(street)+")";

//        String idAdressQuery = "select @@identity";

        String query2 = "insert into social_client(personalNumber, surname, name, patronymic, dateBirth, snils, telephone, email, genderNum, addressId) values"+
                "("+quotate("3")+","+quotate(surname)+","+quotate(name)+","+quotate(patronymic)+","+"{d "+quotate(dateBirth)+" }"+
                ","+quotate(snils)+","+quotate(telephone)+","+quotate(email)+","+
                quotate(gender)+","+quotate(addressId)+")";

        try {
            mdbc=new DatabaseConnection();
            Connection conn=mdbc.getMyConnection();
            stmt= conn.createStatement();

            stmt.addBatch(query1);
//            stmt.addBatch(idAdressQuery);
            stmt.addBatch(query2);
            stmt.executeBatch();

//            stmt.executeUpdate(sqlQuery);
//            initModelSocialClient();
//            selModel = tableSocialClient.getSelectionModel();
//            selModel.setSelectionInterval(rowTableSC-1, rowTableSC-1);
            mdbc.close(stmt);
        }
        catch(Exception e){
            System.out.println("Failed to insert data");
            mdbc.close(stmt);
        }
    }
}
