package db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InsertNewClientForm extends JFrame{
    private JPanel rootPanel;
    private JTextField textSurname;
    private JTextField textName;
    private JTextField textPatronymic;
    private JFormattedTextField textDateBirth;
    private JFormattedTextField textSNILS;
    private JFormattedTextField textTelephone;
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
    private JFormattedTextField textIdDocSeries;
    private JFormattedTextField textIdDocNumber;
    private JTextField textIdDocGivenBy;
    private JFormattedTextField textIdDocDateStart;
    private JFormattedTextField textAttDocNumber;
    private JComboBox textAttDocTypeComboBox;
    private JTextField textAttDocName;
    private JFormattedTextField textAttDocDateStart;
    private JComboBox textOperAccOrgComboBox;
    private JFormattedTextField textOperAccDateStart;
    private JTextField textOperAccNumber;
    private JComboBox textCategoryClientComboBox;
    private JComboBox textCategoryMeasureComboBox;
    private JTable tableCategoryMeasure;
    private TableModelClients mdtm = new TableModelClients();
    private DefaultTableModel dtm;
    private int rowTable;

    private DatabaseConnection mdbc;
    private Statement stmt;

    InsertNewClientForm(JTable [] t){
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(900,600));
        setButtonGroup();
        setMasks();

        initModelCategoryMeasure();

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
        getComboBox(textAttDocTypeComboBox,t[6]);
        getComboBox(textCategoryClientComboBox,t[7]);

        textCategoryClientComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                textCategoryMeasureComboBox.removeAllItems();
                String item;
                for (int i = 0;i<tableCategoryMeasure.getRowCount();i++){
                    if(Integer.valueOf(tableCategoryMeasure.getValueAt(i, 0).toString())==getCategoryClient()) {
                        item = tableCategoryMeasure.getModel().getValueAt(i, 1).toString();
                        textCategoryMeasureComboBox.addItem(item);
                    }
                }
            }
        });
        textCategoryClientComboBox.setSelectedIndex(1);
    }


    String[] columnsCategoryMeasure = new String[]
            {"Код кат.клиента", "Название кат.льготы" };
    final Class[] columnClassCategoryMeasure = new Class[] {Integer.class, String.class};
    String sqlCreateTable="select moss.codeClientCategory as code, moss.nameSocialMeasure as name from measure_of_social_support moss";

    public void initModelCategoryMeasure(){
        mdtm.columnsCatMeasure=columnsCategoryMeasure;
        mdtm.columnClassCatMeasure=columnClassCategoryMeasure;
        mdtm.sqlQuery=sqlCreateTable;
        dtm=mdtm.MyTableModelMeasure();
        tableCategoryMeasure.setModel(dtm);
        dtm.fireTableDataChanged();
        rowTable = tableCategoryMeasure.getRowCount();
    }

    private void setMasks(){
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("+7-###-###-##-##");
            phoneFormatter.setPlaceholderCharacter('0');
            DefaultFormatterFactory phoneFactory = new DefaultFormatterFactory(phoneFormatter);

            MaskFormatter dateFormatter = new MaskFormatter("##.##.####");
            dateFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory dateFactory = new DefaultFormatterFactory(dateFormatter);

            MaskFormatter snilsFormatter = new MaskFormatter("###-###-### ##");
            snilsFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory snilsFactory = new DefaultFormatterFactory(snilsFormatter);

            MaskFormatter seriesDocFormatter = new MaskFormatter("####");
            seriesDocFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory seriesDocFactory = new DefaultFormatterFactory(seriesDocFormatter);

            MaskFormatter numberDocFormatter = new MaskFormatter("######");
            numberDocFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory numberDocFactory = new DefaultFormatterFactory(numberDocFormatter);

            textDateBirth.setFormatterFactory(dateFactory);
            textIdDocDateStart.setFormatterFactory(dateFactory);
            textTelephone.setFormatterFactory(phoneFactory);
            textIdDocSeries.setFormatterFactory(seriesDocFactory);
            textIdDocNumber.setFormatterFactory(numberDocFactory);
            textSNILS.setFormatterFactory(snilsFactory);
            textAttDocDateStart.setFormatterFactory(dateFactory);
            textOperAccDateStart.setFormatterFactory(dateFactory);

        } catch (ParseException e) {
            e.printStackTrace();
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
    private int getTypeAttDoc(){
        return textAttDocTypeComboBox.getSelectedIndex()+1;
    }
    private int getOperAccOrg(){
        return textOperAccOrgComboBox.getSelectedIndex()+1;
    }

    private int getCategoryClient(){
        return textCategoryClientComboBox.getSelectedIndex()+1;
    }
    private String getCategoryMeasure(){
        return textCategoryMeasureComboBox.getSelectedItem().toString();
    }

    public String quotate(String content){
        return "'"+content+"'";
    }

    public boolean fieldIsNull(String [] strs){
        boolean b = false;
        for(String str:strs){
            if(str == null || str.trim().length() == 0)
                b=true;
        }
        return b;
    }

    public void insertClientTable(){
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

        String attDocNumber;
        String attDocType;
        String attDocName;
        String attDocDateStart;
        String attDocStatus;

        String operAccNumber;
        String operAccDateStart;
        String operAccStatus;

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

        attDocNumber = textAttDocNumber.getText();
        attDocType = Integer.toString(getTypeAttDoc());
        attDocName = textAttDocName.getText();
        attDocDateStart = textAttDocDateStart.getText();
        attDocStatus = "действителен";

        operAccNumber = textOperAccNumber.getText();
        operAccDateStart = textOperAccDateStart.getText();
        operAccStatus = "действителен";

        String addressId ="1";
        String persNum = "1";

        try {
            mdbc=new DatabaseConnection();
            Connection conn=mdbc.getMyConnection();
            stmt= conn.createStatement();

            String sqlQuery1 = "insert into address(house, flat, numberIndex, numberRegion, numberDistrict, numberInhabitedLocality, numberStreet) values\n" +
                    "("+quotate(house)+","+quotate(flat)+","+quotate(index)+","+quotate(region)+","+quotate(district)+","+
                    quotate(inhabitedLoc)+","+quotate(street)+")";
            stmt.executeUpdate(sqlQuery1);
            ResultSet rs = stmt.executeQuery("select max(idAddress) as maxId from address");
            if(rs.next())
                addressId = rs.getString("maxId");
            ResultSet rs2 = stmt.executeQuery("select max(personalNumber) as maxId from social_client");
            if(rs2.next()){
                int personalNumber = rs2.getInt("maxId");
                personalNumber++;
                persNum = Integer.toString(personalNumber);
            }

            String sqlQuery2 = "insert into social_client(surname, name, patronymic, dateBirth, snils, telephone, email, numberGender, idAddress) values"+
                    "("+quotate(surname)+","+quotate(name)+","+quotate(patronymic)+","+quotate(stringToDate(dateBirth))+
                    ","+quotate(snils)+","+quotate(telephone)+","+quotate(email)+","+
                    quotate(gender)+","+quotate(addressId)+")";
            stmt.executeUpdate(sqlQuery2);

            String sqlQuery3 = "insert into identification_document(docSeries, docNumber, givenBy, dateStartIdDocument, statusIdDocument, numberTypeIdDocument, personalNumber) values" +
                    "("+quotate(idDocSeries)+","+quotate(idDocNumber)+","+quotate(idDocGivenBy)+","+quotate(stringToDate(idDocDateStart))+","+
                    quotate(idDocStatus)+","+quotate(typeIdDoc)+","+quotate(persNum)+")";
            stmt.executeUpdate(sqlQuery3);

            String sqlQuery4 = "insert into attached_document(numberAttachedDocument, nameAttachedDocument, dateStartAttachedDocument, statusAttachedDocument, numberTypeAttachedDocument, personalNumber) \n" +
                    " values ("+quotate(attDocNumber)+","+quotate(attDocName)+","+quotate(stringToDate(attDocDateStart))+","+quotate(attDocStatus)+","+quotate(attDocType)+","+quotate(persNum)+")";
            stmt.executeUpdate(sqlQuery4);

            String sqlQuery5 = "insert into operating_account(numberOperatingAccount, dateStartAccount, statusOperaingAccount, personalNumber) VALUES "+
                    "("+quotate(operAccNumber)+","+quotate(stringToDate(operAccDateStart))+","+quotate(operAccStatus)+","+quotate(persNum)+")";
            stmt.executeUpdate(sqlQuery5);

            String sqlQuery6 = operAccTypeOrg(operAccNumber);
            stmt.executeUpdate(sqlQuery6);

            String sqlQuery7 = "select moss.codeSocialMeasure from measure_of_social_support moss where moss.nameSocialMeasure=? ";
            PreparedStatement ps = conn.prepareStatement(sqlQuery7);
            ps.setString(1,getCategoryMeasure());
            ResultSet rs3 = ps.executeQuery();
            int codeMeasure = 1;
            if(rs3.next()) {
                codeMeasure = rs3.getInt("codeSocialMeasure");
            }
            String sqlQuery8 = "insert into client_measure(personalNumber, codeSocialMeasure, codeClientCategory) VALUES ("+
                    quotate(persNum)+","+quotate(String.valueOf(codeMeasure))+","+quotate(String.valueOf(getCategoryClient()))+")";
            stmt.executeUpdate(sqlQuery8);
            mdbc.close(stmt);
        }
        catch(Exception e){
            System.out.println("Failed to insert data");
            mdbc.close(stmt);
        }
    }
    private String stringToDate(String param){
//        String date = new SimpleDateFormat("yyyy-MM-dd").format(param);
//        return date;
        String [] date = param.split("\\.");
        String newDate = date[2]+"-"+date[1]+"-"+date[0];
        return newDate;
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
}
