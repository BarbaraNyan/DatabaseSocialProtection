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
import java.util.*;
import java.util.Date;

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
    private JFormattedTextField textAttDocNumber1;
    private JComboBox textAttDocTypeComboBox1;
    private JTextField textAttDocName1;
    private JFormattedTextField textAttDocDateStart;
    private JComboBox textOperAccOrgComboBox;
    private JFormattedTextField textOperAccDateStart;
    private JTextField textOperAccNumber;
    private JComboBox textCategoryClientComboBox;
    private JComboBox textCategoryMeasureComboBox;
    private JTable tableCategoryMeasure;
    private JButton cancelButton;
    private JPanel panelDateBirth;
    private JPanel panelIdDocDateStart;
    private JPanel panelAttDocDateStart1;
    private JPanel panelOperAccDateStart;
    private JTabbedPane tabbedPane2;
    private JFormattedTextField textAttDocNumber2;
    private JComboBox textAttDocTypeComboBox2;
    private JTextField textAttDocName2;
    private JPanel panelAttDocDateStart2;
    private JFormattedTextField textAttDocNumber3;
    private JComboBox textAttDocTypeComboBox3;
    private JTextField textAttDocName3;
    private JPanel panelAttDocDateStart3;
    private JPanel panelTabAttDoc1;
    private JPanel panelTabAttDoc2;
    private JPanel panelTabAttDoc3;
    private JButton addNewAttDoc;
    private TableModelClients mdtm = new TableModelClients();
    private DefaultTableModel dtm;
    private int rowTable;
    JLabel labelPlusAttDoc = new JLabel();
    //Установить плюсик на Button
    ImageIcon icon = new ImageIcon("src\\plus.png");
    Image image = icon.getImage();
    Image newimg = image.getScaledInstance(20,20,Image.SCALE_SMOOTH);

    private com.toedter.calendar.JDateChooser dcDateBirth = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcIdDocDateStart = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcAttDocDateStart1 = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcAttDocDateStart2 = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcAttDocDateStart3 = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcOperAccDateStart = new com.toedter.calendar.JDateChooser();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private DatabaseConnection mdbc;
    private Statement stmt;
    private JTable tb[];
    InsertNewClientForm(JTable [] t){
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1100,600));
//        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        //передача данных из справочников в списки
        getComboBox(comboBoxIndex, t[0]);
        getComboBox(comboBoxRegion, t[1]);
        getComboBox(comboBoxDistrict, t[2]);
        getComboBox(comboBoxInhabitedLoc, t[3]);
        getComboBox(comboBoxStreet, t[4]);
        getComboBox(comboBoxTypeIdDoc, t[5]);
        getComboBox(textAttDocTypeComboBox1,t[6]);
        getComboBox(textAttDocTypeComboBox2,t[6]);
        getComboBox(textAttDocTypeComboBox3,t[6]);
        getComboBox(textCategoryClientComboBox,t[7]);
        tb=t;

        setButtonGroup();
        setMasks();
        panelDateBirth.add(dcDateBirth);
        panelIdDocDateStart.add(dcIdDocDateStart);
        panelAttDocDateStart1.add(dcAttDocDateStart1);
        panelAttDocDateStart2.add(dcAttDocDateStart2);
        panelAttDocDateStart3.add(dcAttDocDateStart3);
        panelOperAccDateStart.add(dcOperAccDateStart);
        tabbedPane2.setEnabledAt(1,false);
        tabbedPane2.setEnabledAt(2,false);
        icon = new ImageIcon(newimg);
        labelPlusAttDoc.setIcon(icon);
        addNewAttDoc.add(labelPlusAttDoc);

        initModelCategoryMeasure();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertClientTable();
                setVisible(false);

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int result = JOptionPane.showOptionDialog(InsertNewClientForm.this, "Вы уверены, что хотите отменить добавление клиента?", "Отмена добавления клиента", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(result==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });


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

        addNewAttDoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(tabbedPane2.isEnabledAt(1)==false)
                    tabbedPane2.setEnabledAt(1,true);
                else {
                    tabbedPane2.setEnabledAt(2, true);
                    addNewAttDoc.setEnabled(false);
                }
            }
        });
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

        String attDocNumber1;
        String attDocType1;
        String attDocName1;
        String attDocDateStart1;
        String attDocStatus1;
        String attDocNumber2="";
        String attDocType2="";
        String attDocName2="";
        String attDocDateStart2="";
        String attDocStatus2="";
        String attDocNumber3="";
        String attDocType3="";
        String attDocName3="";
        String attDocDateStart3="";
        String attDocStatus3="";

        String operAccNumber;
        String operAccDateStart;
        String operAccStatus;
        String dateStartMeasure;
        String dateEndMeasure;
        String statusMeasure="Назначено";

        surname = textSurname.getText();
        name = textName.getText();
        patronymic = textPatronymic.getText();
        gender = getGender();
        dateBirth = dateFormat.format(dcDateBirth.getDate());
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
        idDocDateStart = dateFormat.format(dcIdDocDateStart.getDate());
        idDocStatus = "Действителен";

        attDocNumber1 = textAttDocNumber1.getText();
        attDocType1 = Integer.toString(getTypeAttDoc());
        attDocName1 = textAttDocName1.getText();
        attDocDateStart1 = dateFormat.format(dcAttDocDateStart1.getDate());
        attDocStatus1 = "Действителен";
        if(tabbedPane2.isEnabledAt(1)) {
            attDocNumber2 = textAttDocNumber2.getText();
            attDocType2 = Integer.toString(getTypeAttDoc());
            attDocName2 = textAttDocName2.getText();
            attDocDateStart2 = dateFormat.format(dcAttDocDateStart2.getDate());
            attDocStatus2 = "Действителен";
        }
        if(tabbedPane2.isEnabledAt(2)) {
            attDocNumber3 = textAttDocNumber3.getText();
            attDocType3 = Integer.toString(getTypeAttDoc());
            attDocName3 = textAttDocName3.getText();
            attDocDateStart3 = dateFormat.format(dcAttDocDateStart3.getDate());
            attDocStatus3 = "Действителен";
        }

        operAccNumber = textOperAccNumber.getText();
        operAccDateStart = dateFormat.format(dcOperAccDateStart.getDate());
        operAccStatus = "Действителен";

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
                    "("+quotate(surname)+","+quotate(name)+","+quotate(patronymic)+","+quotate(dateBirth)+
                    ","+quotate(snils)+","+quotate(telephone)+","+quotate(email)+","+
                    quotate(gender)+","+quotate(addressId)+")";
            stmt.executeUpdate(sqlQuery2);

            String sqlQuery3 = "insert into identification_document(docSeries, docNumber, givenBy, dateStartIdDocument, statusIdDocument, numberTypeIdDocument,relativePersonalNumber, personalNumber) values" +
                    "("+quotate(idDocSeries)+","+quotate(idDocNumber)+","+quotate(idDocGivenBy)+","+quotate(idDocDateStart)+","+
                    quotate(idDocStatus)+","+quotate(typeIdDoc)+","+"NULL"+","+quotate(persNum)+")";
            stmt.executeUpdate(sqlQuery3);

            String sqlQuery4_1 = "insert into attached_document(numberAttachedDocument, nameAttachedDocument, dateStartAttachedDocument, statusAttachedDocument, numberTypeAttachedDocument, personalNumber) \n" +
                    " values ("+quotate(attDocNumber1)+","+quotate(attDocName1)+","+quotate(attDocDateStart1)+","+quotate(attDocStatus1)+","+quotate(attDocType1)+","+quotate(persNum)+")";
            stmt.executeUpdate(sqlQuery4_1);
            if(tabbedPane2.isEnabledAt(1)) {
                String sqlQuery4_2 = "insert into attached_document(numberAttachedDocument, nameAttachedDocument, dateStartAttachedDocument, statusAttachedDocument, numberTypeAttachedDocument, personalNumber) \n" +
                        " values (" + quotate(attDocNumber2) + "," + quotate(attDocName2) + "," + quotate(attDocDateStart2) + "," + quotate(attDocStatus2) + "," + quotate(attDocType2) + "," + quotate(persNum) + ")";
                stmt.executeUpdate(sqlQuery4_2);
            }
            if(tabbedPane2.isEnabledAt(2)) {
                String sqlQuery4_3 = "insert into attached_document(numberAttachedDocument, nameAttachedDocument, dateStartAttachedDocument, statusAttachedDocument, numberTypeAttachedDocument, personalNumber) \n" +
                        " values (" + quotate(attDocNumber3) + "," + quotate(attDocName3) + "," + quotate(attDocDateStart3) + "," + quotate(attDocStatus3) + "," + quotate(attDocType3) + "," + quotate(persNum) + ")";
                stmt.executeUpdate(sqlQuery4_3);
            }

            String sqlQuery5 = "insert into operating_account(numberOperatingAccount, dateStartAccount, statusOperaingAccount, personalNumber) VALUES "+
                    "("+quotate(operAccNumber)+","+quotate(operAccDateStart)+","+quotate(operAccStatus)+","+quotate(persNum)+")";
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
            String sqlQuery9 = "select moss.termOfMeasure from measure_of_social_support moss where codeSocialMeasure=? and codeClientCategory=?";
            PreparedStatement ps2 = conn.prepareStatement(sqlQuery9);
            ps2.setInt(1,codeMeasure);
            ps2.setInt(2,getCategoryClient());
            ResultSet rs4 = ps2.executeQuery();
            int termOfMeasure=0;
            if(rs4.next()) {
                termOfMeasure=rs4.getInt("termOfMeasure");
            }
            Date dateForTerm = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateStartMeasure = dateFormat.format(dateForTerm);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateForTerm);

            switch(termOfMeasure){
                case 1:
                    calendar.add(Calendar.MONTH,1);
                    break;
                case 2:
                    calendar.add(Calendar.YEAR,1);
                    break;
                case 3:
                    calendar.add(Calendar.YEAR,50);
                    break;
            }
            dateEndMeasure = dateFormat.format(calendar.getTime());

            String sqlQuery8 = "insert into client_measure(personalNumber, codeSocialMeasure, codeClientCategory," +
                    " dateStartMeasure, dateEndMeasure, statusMeasure) VALUES ("+
                    quotate(persNum)+","+quotate(codeMeasure)+","+quotate(getCategoryClient())+
                    "," + quotate(dateStartMeasure)+","+quotate(dateEndMeasure)+","+quotate(statusMeasure)+")";
            stmt.executeUpdate(sqlQuery8);
            mdbc.close(stmt);
        }
        catch(Exception e){
            System.out.println("Failed to insert data");
            mdbc.close(stmt);
        }
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

            MaskFormatter snilsFormatter = new MaskFormatter("###-###-### ##");
            snilsFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory snilsFactory = new DefaultFormatterFactory(snilsFormatter);

            MaskFormatter seriesDocFormatter = new MaskFormatter("****");
            seriesDocFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory seriesDocFactory = new DefaultFormatterFactory(seriesDocFormatter);

            MaskFormatter numberDocFormatter = new MaskFormatter("######");
            numberDocFormatter.setPlaceholderCharacter('*');
            DefaultFormatterFactory numberDocFactory = new DefaultFormatterFactory(numberDocFormatter);

            textTelephone.setFormatterFactory(phoneFactory);
            textIdDocSeries.setFormatterFactory(seriesDocFactory);
            textIdDocNumber.setFormatterFactory(numberDocFactory);
            textSNILS.setFormatterFactory(snilsFactory);

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
        return Integer.parseInt(tb[1].getModel().getValueAt(comboBoxRegion.getSelectedIndex(), 0).toString());
    }
    private int getDistrict(){
        return Integer.parseInt(tb[2].getModel().getValueAt(comboBoxDistrict.getSelectedIndex(), 0).toString());
    }
    private int getInhabitedLoc(){
        return Integer.parseInt(tb[3].getModel().getValueAt(comboBoxInhabitedLoc.getSelectedIndex(), 0).toString());
    }
    private int getStreet(){
        return Integer.parseInt(tb[4].getModel().getValueAt(comboBoxStreet.getSelectedIndex(), 0).toString());
    }
    private int getTypeIdDoc() {
        return Integer.parseInt(tb[5].getModel().getValueAt(comboBoxTypeIdDoc.getSelectedIndex(), 0).toString());
    }
    private int getTypeAttDoc(){
        return Integer.parseInt(tb[6].getModel().getValueAt(textAttDocTypeComboBox1.getSelectedIndex(), 0).toString());
    }
    private int getOperAccOrg(){
        return textOperAccOrgComboBox.getSelectedIndex()+1;
    }
    private int getCategoryClient(){
        return Integer.parseInt(tb[7].getModel().getValueAt(textCategoryClientComboBox.getSelectedIndex(), 0).toString());
    }

    private String getCategoryMeasure(){
        return textCategoryMeasureComboBox.getSelectedItem().toString();
    }
    public String quotate(String content){
        return "'"+content+"'";
    }

    public String quotate(int content){
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
