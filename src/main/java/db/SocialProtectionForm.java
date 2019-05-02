package db;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.Date;
import java.text.*;
import javax.swing.JComboBox;

public class SocialProtectionForm extends JFrame implements TreeSelectionListener{
    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane2;
    private JButton editClientButton;
    private JComboBox comboBoxSCCategory;
    private JComboBox comboBoxBenefitCategory;
    private JButton findClientButton;
    private JTable tableSocialClients;
    private JTree glossaryJTree;
    private JButton addClientButton;
    private JButton deleteClientButton1;
    private JTextField textPersNum;
    private JTextField textSurname;
    private JTextField textName;
    private JTextField textPatronymic;
    private JTextField textDateBirth;
    private JTextField textSNILS;
    private JTextField textTelephone;
    private JTextField textEmail;
    private JTextField textDistrict;
    private JTextField textInhabitedLocality;
    private JTextField textStreet;
    private JTextField textHouse;
    private JTextField textFlat;
    private JTextField textIndex;
    private JButton updateButton;
    private JTable tableIdDocument;
    private JTable tableAttDocument;
    private JTextField textRegion;
    private JTable tableAddress;
    private JTable tableHandbook;
    private JTabbedPane reports;
    private JPanel saldoReport;
    private JTabbedPane tabbedPane3;
    private JTable tableChargeRequest;
    private JButton chargeButton;
    private JTable tablePayoff;
    private JButton PayoffButton;
    private JTabbedPane reportsPane;
    private JPanel otherReports;
    private JComboBox periodCombox;
    private JComboBox categoryCombox;
    private JComboBox employeeCombox;
    private JButton createSaldoReport;
    private JLabel empLabel;

    private JTable tableSaldoReport;
    private JButton saveReportBtn;
    private JScrollPane scrollPaneSaldo;
    private JTextField textGender;
    private JComboBox textRegionComboBox;
    private JComboBox textStreetComboBox;
    private JComboBox textDistrictComboBox;
    private JComboBox textInhabitedLocalityComboBox;
    private JComboBox textIndexComboBox;
    private JRadioButton radioButtonM;
    private JRadioButton radioButtonF;
    private JButton buttonSave;
    private ButtonGroup radioButtonGender;

    private JTable tableSCCategory=new JTable();
    private JTable tableBenefitCategory=new JTable();
    private JTable tableTypeBenCategory=new JTable();
    private JTable tableLaw=new JTable();
    private JTable tableVersionArticle=new JTable();
    private JTable tableArticle=new JTable();
    private JTable tableIndex=new JTable();
    private JTable tableRegion=new JTable();
    private JTable tableDistrict=new JTable();
    private JTable tableLocality=new JTable();
    private JTable tableStreet=new JTable();
    private JTable tableRelation=new JTable();
    private JTable tableIncome=new JTable();
    private JTable tableIndDoc=new JTable();
    private JTable tableDoc=new JTable();

    private JTable []box={tableIndex, tableRegion, tableDistrict, tableLocality, tableStreet,tableIndDoc};
    private DefaultTableModel dtmSocialClient;
    private DefaultTableModel dtmIdDocument;
    private DefaultTableModel dtmAttDocument;
    private DefaultTableModel dtmAddress;
    private DefaultTableModel dtmHandbook;
    private DefaultTableModel dtmSaldoReport;
    private ListSelectionModel selModelSC = tableSocialClients.getSelectionModel();
    private TableModelClients mdtmSocialClient = new TableModelClients();
    private EditClient editClient = new EditClient();

    private DatabaseConnection mdbc;
    private Statement stmt;

    private int rowTableSC;
    private int rowTableIdDoc;
    private int rowTableAttDoc;
    private int rowTableAddress;
    String treeSelect;

    private int selRowSC;

    private void hideComboBox(){
        textRegionComboBox.setVisible(false);
        textStreetComboBox.setVisible(false);
        textIndexComboBox.setVisible(false);
        textInhabitedLocalityComboBox.setVisible(false);
        textDistrictComboBox.setVisible(false);
        radioButtonM.setVisible(false);
        radioButtonF.setVisible(false);
    }

    private void fillComboBox(){
        getComboBox(textRegionComboBox,tableRegion);
        getComboBox(textStreetComboBox,tableStreet);
        getComboBox(textIndexComboBox,tableIndex);
        getComboBox(textDistrictComboBox,tableDistrict);
        getComboBox(textInhabitedLocalityComboBox,tableLocality);
    }

    SocialProtectionForm(){
        super("CommonTable");
        setContentPane(rootPanel);
        tableAddress.setVisible(false);
        initModelSocialClient();
        glossaryJTree.setModel( new DefaultTreeModel(setGlossaryJTree()) );
        glossaryJTree.addTreeSelectionListener(this);
        listenerRowTableSC();
        initModelHandbook();

        getComboBox(comboBoxSCCategory, tableSCCategory);

        hideComboBox();
        fillComboBox();
        setButtonGroup();

        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame insertNewClientForm = new InsertNewClientForm(box);
                insertNewClientForm.pack();
                insertNewClientForm.setVisible(true);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initModelSocialClient();
                selModelSC = tableSocialClients.getSelectionModel();
                selModelSC.setSelectionInterval(rowTableSC-1, rowTableSC-1);
            }
        });

        comboBoxSCCategory.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                comboBoxBenefitCategory.removeAllItems();
                String item;
                int k=tableBenefitCategory.getColumnCount()-1;
                for (int i = 0;i<tableBenefitCategory.getRowCount();i++){
                    if(Integer.valueOf(tableBenefitCategory.getValueAt(i, 2).toString())==comboBoxSCCategory.getSelectedIndex()+1) {
                        item = tableBenefitCategory.getModel().getValueAt(i, k-1).toString();
                        comboBoxBenefitCategory.addItem(item);
                    }
                }
            }
        });

        createSaldoReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initModelSaldoReport();
            }
        });

        findClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                initModelPayoff();
            }
        });

        chargeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                setPayoff();
            }
        });
//        tabbedPane2.addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                if(tabbedPane2.getSelectedIndex()==1)
//                    initModelIdDocument(persNum);
//            }
//        });


    //Вкладка "Личные дела", кнопка "Редактирование"
        editClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Перс.данные
                textSurname.setEditable(true);
                textName.setEditable(true);
                textPatronymic.setEditable(true);
                textGender.setVisible(false);
                radioButtonF.setVisible(true);
                radioButtonM.setVisible(true);
                textDateBirth.setEditable(true);
                textSNILS.setEditable(true);
                textTelephone.setEditable(true);
                textEmail.setEditable(true);

                //Адрес
                textRegion.setVisible(false);
                textRegionComboBox.setVisible(true);
                textStreet.setVisible(false);
                textStreetComboBox.setVisible(true);
                textIndex.setVisible(false);
                textIndexComboBox.setVisible(true);
                textDistrict.setVisible(false);
                textDistrictComboBox.setVisible(true);
                textInhabitedLocality.setVisible(false);
                textInhabitedLocalityComboBox.setVisible(true);
                textHouse.setEditable(true);
                textFlat.setEditable(true);

            }
        });
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String [] params = {textSurname.getText(),textName.getText(), textPatronymic.getText(),textSNILS.getText(),
                textTelephone.getText(),textEmail.getText(),textPersNum.getText()};
                String sqlUpdate = "update social_client set surname = ?, name = ?, patronymic = ?, snils = ?,\n" +
                        "  telephone = ?, email = ? where personalNumber = ?";
                editClient.update(sqlUpdate,params);
            }
        });
    }

    private void setButtonGroup(){
        radioButtonGender = new ButtonGroup();
        radioButtonGender.add(radioButtonM);
        radioButtonGender.add(radioButtonF);
    }

    private void listenerRowTableSC(){
        selModelSC.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    getRowTableSC();
                }
            });
        }

    //Персональные данные (Вкладка "Личные дела")
    String[] columnsSocialClient = new String[]
            {"Личный счёт", "Фамилия", "Имя", "Отчество", "Пол", "Дата рождения","СНИЛС","Телефон","Email" };
    final Class[] columnClassSocialClient = new Class[] {
            Integer.class, String.class,String.class,
            String.class, String.class, String.class, String.class, String.class, String.class};
    String sqlQuery1="select sc.personalNumber, sc.surname, sc.name, sc.patronymic, g.nameGender, DATE_FORMAT(sc.dateBirth,'%d.%m.%Y'), sc.snils, sc.telephone, sc.email from social_client sc inner join gender g on g.numberGender=sc.numberGender";

    public void initModelSocialClient(){
        mdtmSocialClient.columnsSC=columnsSocialClient;
        mdtmSocialClient.columnClassSC=columnClassSocialClient;
        mdtmSocialClient.sqlQuery=sqlQuery1;
        dtmSocialClient=mdtmSocialClient.MyTableModelClients(1);
        tableSocialClients.setModel(dtmSocialClient);
        dtmSocialClient.fireTableDataChanged();
        rowTableSC = tableSocialClients.getRowCount();
    }

    //Уд. личности (Вкладка "Личные дела")
    String[] columnsIdDocument = new String[]
            {"Тип документа","Серия","Номер","Кем выдан","Дата выдачи","Статус"};
    final Class[] columnClassIdDocument = new Class[]{
            String.class, Integer.class, Integer.class, String.class, String.class, String.class};
    String sqlQuery2 = "select typeDoc.nameTypeIdDocument, idDoc.docSeries, idDoc.docNumber, idDoc.givenBy, DATE_FORMAT(idDoc.dateStartIdDocument,'%d.%m.%Y'), idDoc.statusIdDocument\n" +
            "from identification_document idDoc inner join social_client sc on idDoc.personalNumber = sc.personalNumber\n" +
            "inner join type_identification_document typeDoc on idDoc.numberTypeIdDocument = typeDoc.numberTypeIdDocument\n" +
            "where sc.personalNumber=?";

    public void initModelIdDocument(String persNum){
        mdtmSocialClient.columnsIdDoc=columnsIdDocument;
        mdtmSocialClient.columnClassIdDoc=columnClassIdDocument;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQuery2;
        dtmIdDocument=mdtmSocialClient.MyTableModelDocument(1);
        tableIdDocument.setModel(dtmIdDocument);
        dtmIdDocument.fireTableDataChanged();
        rowTableIdDoc = tableIdDocument.getRowCount();
    }

    //Доп. документы (Вкладка "Личные дела")
    String[] columnsAttDocument = new String[]
            {"Номер","Тип документа","Название","Дата выдачи","Статус"};
    final Class[] columnClassAttDocument = new Class[]{
            Integer.class, String.class, String.class, String.class, String.class};
    String sqlQuery3 = "select typeDoc.nameTypeAttachedDocument, attDoc.numberAttachedDocument, attDoc.nameAttachedDocument, " +
            "attDoc.dateStartAttachedDocument, attDoc.statusAttachedDocument\n" +
            "from attached_document attDoc inner join social_client sc on attDoc.personalNumber = sc.personalNumber\n" +
            "inner join type_attached_document typeDoc on attDoc.numberTypeAttachedDocument = typeDoc.numberTypeAttachedDocument\n" +
            "where sc.personalNumber=?";

    public void initModelAttDocument(String persNum){
        mdtmSocialClient.columnsAttDoc=columnsAttDocument;
        mdtmSocialClient.columnClassAttDoc=columnClassAttDocument;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQuery3;
        dtmAttDocument=mdtmSocialClient.MyTableModelDocument(2);
        tableAttDocument.setModel(dtmAttDocument);
        dtmAttDocument.fireTableDataChanged();
        rowTableAttDoc = tableAttDocument.getRowCount();
    }

    // Адрес (Вкладка 'Личные дела')
    String[] columnsAddress = new String[]
            {"Индекс","Регион","Район","Нас.пункт","Улица","Дом","Квартира" };
    final Class[] columnClassAddress = new Class[] {
            Integer.class, String.class,
            String.class, String.class,String.class,Integer.class,Integer.class};
    String sqlQuery4="select ia.numberIndex, ra.nameRegion, da.nameDistrict, ila.nameInhabitedLocality, sa.nameStreet, a.house, a.flat\n" +
            "from address a inner join social_client sc on a.idAddress = sc.idAddress\n" +
            "inner join region_address ra on a.numberRegion = ra.numberRegion\n" +
            "inner join district_address da on a.numberDistrict = da.numberDistrict\n" +
            "inner join inhabited_locality_address ila on a.numberInhabitedLocality = ila.numberInhabitedLocality\n" +
            "inner join street_address sa on a.numberStreet = sa.numberStreet\n" +
            "inner join index_address ia on a.numberIndex = ia.numberIndex\n" +
            "where sc.idAddress=(SELECT sc.idAddress from social_client sc where sc.personalNumber=?);";

    public void initModelAddress(String persNum){
        mdtmSocialClient.columnsAddress=columnsAddress;
        mdtmSocialClient.columnClassAddress=columnClassAddress;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQuery4;
        dtmAddress=mdtmSocialClient.MyTableModelDocument(3);
        tableAddress.setModel(dtmAddress);
        dtmAddress.fireTableDataChanged();
        rowTableAddress = tableAddress.getRowCount();
    }

    // Оборотно-сальдовая ведомосоть (Вкладка 'Отчёты')
    String[] columnsSaldoReport = new String[]
            {"Личный счет","Фамилия","Имя","Отчество", "Входное сальдо", "Начислено"};
    final Class[] columnClassSaldoReport =  new Class[] {
            Integer.class, String.class,String.class,String.class, Integer.class, Integer.class
    };

    String sqlQuerySaldoReport ="select pa.numberPersonalAccount, sc.surname, sc.name,sc.patronymic, pa.inputBalance, pa.accrued\n" +
            "from personal_account pa inner join payoff p on pa.numberPersonalAccount = p.numberPersonalAccount\n" +
            "inner join request_for_cash_settlement rfcs on p.numberPayoff = rfcs.numberPayoff\n" +
            "inner join operating_account oa on rfcs.numberOperatingAccount = oa.numberOperatingAccount\n" +
            "inner join social_client sc on oa.personalNumber = sc.personalNumber\n" +
            "where sc.personalNumber = pa.numberPersonalAccount;";

    public void initModelSaldoReport() {
        mdtmSocialClient.columnsSalRep = columnsSaldoReport;
        mdtmSocialClient.columnClassSalRep = columnClassSaldoReport;
        mdtmSocialClient.sqlQuery = sqlQuerySaldoReport;
        dtmSaldoReport=mdtmSocialClient.MyTableModelReports(1);
        tableSaldoReport.setModel(dtmSaldoReport);
        dtmSocialClient.fireTableDataChanged();
        rowTableSC = tableSocialClients.getRowCount();
    }

    // Начисления (Вкладка 'Начисления и выплаты')
    String[] columnsChargeReq = new String[] {"Номер личного счета", "Фамилия", "Имя", "Отчество", "Категория гражданина", "Категория льготы"};
    final Class[] columnClassChargeReq = new Class[] {Integer.class, String.class, String.class, String.class, String.class, String.class};
    String sqlQueryChargeReq="select sc.personalNumber, sc.surname, sc.name, sc.patronymic, scc.nameClientCategory, bc.nameBenefitCategory " +
            "from social_client sc inner join client_category cc on sc.personalNumber=cc.personalNumber " +
            "inner join social_client_category scc on cc.numberClientCategory=scc.numberClientCategory " +
            "inner join benefit_category bc on scc.numberClientCategory=bc.numberClientCategory " +
            "where scc.nameClientCategory=? and bc.nameBenefitCategory=?";

    public void initModelPayoff() {
        mdtmSocialClient.columnsChargeReq = columnsChargeReq;
        mdtmSocialClient.columnClassChargeReq = columnClassChargeReq;
        mdtmSocialClient.cat = comboBoxSCCategory.getItemAt(comboBoxSCCategory.getSelectedIndex()).toString();
        mdtmSocialClient.ben = comboBoxBenefitCategory.getItemAt(comboBoxBenefitCategory.getSelectedIndex()).toString();
        mdtmSocialClient.sqlPreparedStatement=sqlQueryChargeReq;
        dtmHandbook = mdtmSocialClient.MyTableModelPayoff(1);
        tableChargeRequest.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();
    }

    public void setPayoff(){
        try {
            mdbc=new DatabaseConnection();
            Connection conn=mdbc.getMyConnection();
            stmt= conn.createStatement();
            String amount="";
            String date;
            String persNum="";

            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
            date=formatForDateNow.format(dateNow).toString();

            String ben=comboBoxBenefitCategory.getItemAt(comboBoxBenefitCategory.getSelectedIndex()).toString();
            String sqlAm="select vota.payoutAmount from version_of_the_article vota " +
                    "inner join law l on vota.numberLaw=l.numberLaw " +
                    "inner join benefit_law bl on l.numberLaw=bl.numberLaw " +
                    "inner join benefit_category bc on bl.codeBenefitCategory=bc.codeBenefitCategory " +
                    "where bc.nameBenefitCategory='"+ben+"'";
            ResultSet rs = stmt.executeQuery(sqlAm);
            if(rs.next())
                amount = rs.getString(1);

            for(int i=0; i<tableChargeRequest.getRowCount(); i++){
                persNum=tableChargeRequest.getModel().getValueAt(i,0).toString();
                String sqlPayoff="insert into payoff(amountPayoff, datePayoff, numberPersonalAccount)" +
                        "values ('"+amount+"', '"+date+"', '"+persNum+"')";
                stmt.executeUpdate(sqlPayoff);
            }

            mdbc.close(stmt);
        }
        catch(Exception e){
            System.out.println("Failed to set payoff");
            mdbc.close(stmt);
        }
    }

    String persNum;
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
    String inhabitedLocality;
    String street;
    String house;
    String flat;
    String index;

    //Отображение даннных во вкладках Перс.данные, Адрес, Уд. личнсоти, Доп.документы
    public void getRowTableSC(){
        selRowSC = tableSocialClients.getSelectedRow();
        persNum = tableSocialClients.getModel().getValueAt(selRowSC,0).toString();
        textPersNum.setText(persNum);
        surname = tableSocialClients.getModel().getValueAt(selRowSC,1).toString();
        textSurname.setText(surname);
        name = tableSocialClients.getModel().getValueAt(selRowSC,2).toString();
        textName.setText(name);
        patronymic = tableSocialClients.getModel().getValueAt(selRowSC,3).toString();
        textPatronymic.setText(patronymic);
        gender = tableSocialClients.getModel().getValueAt(selRowSC,4).toString();
        textGender.setText(gender);
        setSelectedGender(gender);
        dateBirth = tableSocialClients.getModel().getValueAt(selRowSC,5).toString();
        textDateBirth.setText(dateBirth);
        snils = tableSocialClients.getModel().getValueAt(selRowSC,6).toString();
        textSNILS.setText(snils);
        telephone = tableSocialClients.getModel().getValueAt(selRowSC,7).toString();
        textTelephone.setText(telephone);
        email = tableSocialClients.getModel().getValueAt(selRowSC,8).toString();
        textEmail.setText(email);

        initModelAddress(persNum);

        region = tableAddress.getModel().getValueAt(0,1).toString();
        textRegion.setText(region);
        setSelectedValue(region,textRegionComboBox);
        district = tableAddress.getModel().getValueAt(0,2).toString();
        textDistrict.setText(district);
        setSelectedValue(district,textDistrictComboBox);
        inhabitedLocality = tableAddress.getModel().getValueAt(0,3).toString();
        textInhabitedLocality.setText(inhabitedLocality);
        setSelectedValue(inhabitedLocality,textInhabitedLocalityComboBox);
        street = tableAddress.getModel().getValueAt(0,4).toString();
        textStreet.setText(street);
        setSelectedValue(street,textStreetComboBox);
        house = tableAddress.getModel().getValueAt(0,5).toString();
        textHouse.setText(house);
        flat = tableAddress.getModel().getValueAt(0,6).toString();
        textFlat.setText(flat);
        index = tableAddress.getModel().getValueAt(0,0).toString();
        textIndex.setText(index);
        setSelectedValue(index,textIndexComboBox);

        initModelIdDocument(persNum);
        initModelAttDocument(persNum);
    }

        private void setSelectedValue(String value,JComboBox comboBox) {
            String item;
            for (int i = 0; i < comboBox.getItemCount(); i++) {
                item = comboBox.getItemAt(i).toString();
                if (item.equalsIgnoreCase(value)) {
                    comboBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        private void setSelectedGender(String gender){
            if(gender.equals("М"))
                radioButtonM.setSelected(true);
            else
                radioButtonF.setSelected(true);
        }


    //для вкладки назначения и выплаты
    public void getComboBox(JComboBox cb, JTable tl){
        String item;
        int k=tl.getColumnCount()-1;
        for (int i = 0;i<tl.getRowCount();i++){
            item = tl.getModel().getValueAt(i, k).toString();
            cb.addItem(item);
        }
    }

    //для вкладки НСИ
    private DefaultMutableTreeNode setGlossaryJTree(){
        final String ROOT  = "Справочники";
        // Массив листьев деревьев
        final   String[]   nodes = new String[]  {"Пособия","Адреса","Личная карточка","Прочие"};
        final   String[][] leafs = new String[][]{{"Категории гражданина", "Категории пособия", "Виды категории пособия", "Законы", "Версии статей", "Статьи", "Правила расчёта"},
                {"Индексы","Регионы","Районы","Населённые пункты","Улицы"},
                {"Родственные отношения","Виды доходов"},
                {"Документы удостоверения","Документы"}};

        // Создание древовидной структуры
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);
        // Ветви первого уровня
        DefaultMutableTreeNode benefit = new DefaultMutableTreeNode(nodes[0]);
        DefaultMutableTreeNode address = new DefaultMutableTreeNode(nodes[1]);
        DefaultMutableTreeNode personalCard = new DefaultMutableTreeNode(nodes[2]);
        DefaultMutableTreeNode other = new DefaultMutableTreeNode(nodes[3]);
        // Добавление ветвей к корневой записи
        root.add(benefit);
        root.add(address);
        root.add(personalCard);
        root.add(other);
        // Добавление листьев
        for ( int i = 0; i < leafs[0].length; i++)
            benefit.add(new DefaultMutableTreeNode(leafs[0][i], false));
        for ( int i = 0; i < leafs[1].length; i++)
            address.add(new DefaultMutableTreeNode(leafs[1][i], false));
        for ( int i = 0; i < leafs[2].length; i++)
            personalCard.add(new DefaultMutableTreeNode(leafs[2][i], false));
        for ( int i = 0; i < leafs[3].length; i++)
            other.add(new DefaultMutableTreeNode(leafs[3][i], false));
        return root;
    }

    String[] columnsIndex = new String[] {"Наименование"};
    final Class[] columnClassIndex = new Class[] {String.class};
    String[] columnsHandbook = new String[] {"№", "Наименование"};
    final Class[] columnClassHandbook = new Class[] { Integer.class, String.class};
    String[] columnsBenefit = new String[] {"Код категории льготы", "Наименование", "Номер категории льготника"};
    final Class[] columnClassBenefit = new Class[] { Integer.class, String.class, Integer.class};
    String[] columnsTypeBenefitFull = new String[] {"Код вида категории льготы", "Наименование", "Код категории"};
    String[] columnsTypeBenefit = new String[] {"Код вида категории льготы", "Наименование"};
    String[] columnsLaw = new String[] {"№ закона", "Дата утверждения", "Наименование"};
    final Class[] columnClassLaw = new Class[] {Integer.class, Date.class, String.class};
    String[] columnsVersionFull = new String[] {"№ версии", "Сумма выплаты", "Дата принятия", "Номер закона", "Номер статьи"};
    final Class[] columnClassVersionFull = new Class[] {Integer.class, Integer.class, Date.class, Integer.class, Integer.class};
    String[] columnsVersion = new String[] {"№ версии", "Сумма выплаты", "Дата принятия"};

    String sqlQuerySCCat="select * from social_client_category";
    String sqlQueryBenCat="select * from benefit_category";
    String sqlQueryTypeBenCat="select * from type_benefit_category";
    String sqlQueryLaw="select * from law";
    String sqlQueryVerArt="select * from version_of_the_article";
    String sqlQueryArt="select * from article";
    String sqlQueryInd="select * from index_address";
    String sqlQueryReg="select * from region_address";
    String sqlQueryDist="select * from district_address";
    String sqlQueryLoc="select * from inhabited_locality_address";
    String sqlQueryStr="select * from street_address";
    String sqlQueryRel="select * from relation_degree";
    String sqlQueryInc="select * from type_income";
    String sqlQueryIdDoc="select * from type_identification_document";
    String sqlQueryDoc="select * from type_attached_document";

    //запонение справочников (Вкладка 'НСИ')
    public void initModelHandbook(){
        mdtmSocialClient.columnsSCCategory = columnsHandbook;
        mdtmSocialClient.columnClassSCCategory = columnClassHandbook;
        mdtmSocialClient.sqlQuery = sqlQuerySCCat;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(1);
        tableSCCategory.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsBenCategory = columnsBenefit;
        mdtmSocialClient.columnClassBenCategory = columnClassBenefit;
        mdtmSocialClient.sqlQuery = sqlQueryBenCat;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(2);
        tableBenefitCategory.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsTypeBenCategory = columnsTypeBenefitFull;
        mdtmSocialClient.columnClassTypeBenCategory = columnClassBenefit;
        mdtmSocialClient.sqlQuery = sqlQueryTypeBenCat;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(3);
        tableTypeBenCategory.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsLaw = columnsLaw;
        mdtmSocialClient.columnClassLaw = columnClassLaw;
        mdtmSocialClient.sqlQuery = sqlQueryLaw;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(4);
        tableLaw.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsVersionArt = columnsVersionFull;
        mdtmSocialClient.columnClassVersionArt = columnClassVersionFull;
        mdtmSocialClient.sqlQuery = sqlQueryVerArt;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(5);
        tableVersionArticle.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsArticle = columnsHandbook;
        mdtmSocialClient.columnClassArticle = columnClassHandbook;
        mdtmSocialClient.sqlQuery = sqlQueryArt;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(6);
        tableArticle.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsIndex = columnsIndex;
        mdtmSocialClient.columnClassIndex = columnClassIndex;
            mdtmSocialClient.sqlQuery = sqlQueryInd;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(7);
            tableIndex.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsRegion = columnsHandbook;
            mdtmSocialClient.columnClassRegion = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryReg;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(8);
            tableRegion.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsDistrict = columnsHandbook;
            mdtmSocialClient.columnClassDistrict = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryDist;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(9);
            tableDistrict.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsLocality = columnsHandbook;
            mdtmSocialClient.columnClassLocality = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryLoc;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(10);
            tableLocality.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsStreet = columnsHandbook;
            mdtmSocialClient.columnClassStreet = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryStr;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(11);
            tableStreet.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsRelation = columnsHandbook;
            mdtmSocialClient.columnClassRelation = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryRel;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(12);
            tableRelation.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsTypeIncome = columnsHandbook;
            mdtmSocialClient.columnClassTypeIncome = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryInc;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(13);
            tableIncome.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsTypeIndDoc = columnsHandbook;
            mdtmSocialClient.columnClassTypeIndDoc = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryIdDoc;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(14);
            tableIndDoc.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsTypeDoc = columnsHandbook;
            mdtmSocialClient.columnClassTypeDoc = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryDoc;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(15);
            tableDoc.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

    }

    private DefaultTableModel getModelHandbook(JTable t, String[] col){
        DefaultTableModel mod = new DefaultTableModel();
        mod.setColumnIdentifiers(col);
        for(int i=0; i<t.getRowCount(); i++) {
            if(t==tableVersionArticle) {
                Object[] o = {t.getValueAt(i, 0), t.getValueAt(i, 1), t.getValueAt(i, 2)};
                mod.addRow(o);
            }
            else {
                Object[] ob = {t.getValueAt(i, 0), t.getValueAt(i, 1)};
                mod.addRow(ob);
            }
        }
        return mod;
    }
    //выделение пункта дерева - новая таблица справочника
    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
        TreePath[] paths=glossaryJTree.getSelectionPaths();
        treeSelect=paths[0].getLastPathComponent().toString();

        if ("Категории гражданина".equals(treeSelect)) {
            tableHandbook.setModel(tableSCCategory.getModel());
        }else
        if ("Категории пособия".equals(treeSelect)) {
            tableHandbook.setModel(getModelHandbook(tableBenefitCategory, columnsHandbook));
        }else
        if ("Виды категории пособия".equals(treeSelect)) {
            tableHandbook.setModel(getModelHandbook(tableTypeBenCategory, columnsTypeBenefit));
        }else
        if ("Законы".equals(treeSelect)) {
            tableHandbook.setModel(tableLaw.getModel());
        }else
        if ("Версии статей".equals(treeSelect)) {
            tableHandbook.setModel(getModelHandbook(tableVersionArticle, columnsVersion));
        }else
        if ("Статьи".equals(treeSelect)) {
            tableHandbook.setModel(tableArticle.getModel());
        }else
        if ("Индексы".equals(treeSelect)) {
            tableHandbook.setModel(tableIndex.getModel());
        }else
        if ("Регионы".equals(treeSelect)) {
            tableHandbook.setModel(tableRegion.getModel());
        }else
        if ("Районы".equals(treeSelect)) {
            tableHandbook.setModel(tableDistrict.getModel());
        }else
        if ("Населённые пункты".equals(treeSelect)) {
            tableHandbook.setModel(tableLocality.getModel());
        }else
        if ("Улицы".equals(treeSelect)) {
            tableHandbook.setModel(tableStreet.getModel());
        }else
        if ("Родственные отношения".equals(treeSelect)) {
            tableHandbook.setModel(tableRelation.getModel());
        }else
        if ("Виды доходов".equals(treeSelect)) {
            tableHandbook.setModel(tableIncome.getModel());
        }else
        if ("Документы удостоверения".equals(treeSelect)) {
            tableHandbook.setModel(tableIndDoc.getModel());
        }else
        if ("Документы".equals(treeSelect)) {
            tableHandbook.setModel(tableDoc.getModel());
        }else
            tableHandbook.setModel(new DefaultTableModel());

    }
}
