package db;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;

public class SocialProtectionForm extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane2;
    private JButton editClientButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton начислитьВыплатуButton;
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
    private JTextArea docViewPanel;
    private JTextPane docViewPane;

    private DefaultTableModel dtmSocialClient;
    private DefaultTableModel dtmIdDocument;
    private DefaultTableModel dtmAttDocument;
    private DefaultTableModel dtmAddress;
    private ListSelectionModel selModel = tableSocialClients.getSelectionModel();
    private TableModelClients mdtmSocialClient = new TableModelClients();

    private int rowTableSC;
    private int rowTableIdDoc;
    private int rowTableAttDoc;
    private int rowTableAddress;

    private int selRowSC;
//    private int selRowAddress;


    SocialProtectionForm(){
        super("CommonTable");
        setContentPane(rootPanel);
        tableAddress.setVisible(false);
        initModelSocialClient();
        //initModelIdDocument();
        glossaryJTree.setModel( new DefaultTreeModel(setGlossaryJTree()) );

        glossaryJTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        docViewPanel.setEditable(false);
        docViewPane.setEditable(false);
        docViewPane.setText("");

        // по выделенному листу дерева должен открываться справочный файл
        glossaryJTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) glossaryJTree.getLastSelectedPathComponent();
                Object nodePath = glossaryJTree.getLastSelectedPathComponent();
                if (selectedNode.isLeaf()) {
                    System.out.println("you selected: "+nodePath);
                    File folder = new File("./glossary");
                    File[] files = folder.listFiles();
                    for(int i=0; i<files.length;i++) {
                    if(files[i].getName().equals(nodePath+".txt")) {
                        // здесь происходит вывод данных из файла на docViewPanel\docViewPane
                        // не знаю пока, какая лучше
                        StringBuilder sb = new StringBuilder();
                        try {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(new FileInputStream(files[i]), "Cp1251"));
                        String s;
                        while((s=in.readLine())!=null) {
                            sb.append(s);
                            sb.append("\n");
                            docViewPane.setText(sb.toString());
                            docViewPanel.setText(sb.toString());
                        }
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    }
                }
                }
            }
        });




        listenerRowTable();
//        getRowTable();
        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame insertNewClientForm = new InsertNewClientForm();
                insertNewClientForm.pack();
                insertNewClientForm.setVisible(true);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initModelSocialClient();
                selModel = tableSocialClients.getSelectionModel();
                selModel.setSelectionInterval(rowTableSC-1, rowTableSC-1);
            }
        });
//        tabbedPane2.addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                if(tabbedPane2.getSelectedIndex()==1)
//                    initModelIdDocument(persNum);
//            }
//        });

    }

    private void listenerRowTable(){
//        ListSelectionModel selModel = tableSocialClients.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    getRowTableSC();
                }
            });
        }

    String[] columnsSocialClient = new String[]
            {"Личный счёт", "Фамилия", "Имя", "Отчество","Дата рождения","СНИЛС","Телефон","Email" };
    final Class[] columnClassSocialClient = new Class[] {
            Integer.class, String.class,
            String.class, String.class,String.class,String.class,String.class,String.class};
    String sqlQuery1="select * from social_client";

    public void initModelSocialClient(){
        mdtmSocialClient.columnsSC=columnsSocialClient;
        mdtmSocialClient.columnClassSC=columnClassSocialClient;
        mdtmSocialClient.sqlQuery=sqlQuery1;
        dtmSocialClient=mdtmSocialClient.MyTableModelClients(1);
        tableSocialClients.setModel(dtmSocialClient);
        dtmSocialClient.fireTableDataChanged();
        rowTableSC = tableSocialClients.getRowCount();
    }

    String[] columnsIdDocument = new String[]
            {"Тип документа","Серия","Номер","Кем выдан","Дата выдачи","Статус"};
    final Class[] columnClassIdDocument = new Class[]{
            String.class,String.class,String.class,String.class,String.class,String.class};
    String sqlQuery2 = "select typeDoc.name, idDoc.docSeries, idDoc.docNumber, idDoc.givenBy, idDoc.dateStart, idDoc.status\n" +
            "from identification_document idDoc inner join social_client sc on idDoc.social_client_personalNumber = sc.personalNumber\n" +
            "inner join type_identification_document typeDoc on idDoc.typeIdDocNum = typeDoc.number\n" +
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

    String[] columnsAttDocument = new String[]
            {"Тип документа","Номер","Название","Дата выдачи","Статус"};
    final Class[] columnClassAttDocument = new Class[]{
            String.class,String.class,String.class,String.class,String.class};
    String sqlQuery3 = "select typeDoc.name, attDoc.number, attDoc.name, attDoc.dateStart, attDoc.status\n" +
            "from attached_document attDoc inner join social_client sc on attDoc.socialClient = sc.personalNumber\n" +
            "inner join type_attached_document typeDoc on attDoc.typeAttachedDocNum = typeDoc.number\n" +
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

    String[] columnsAddress = new String[]
            {"Регион","Район","Нас.пункт","Улица","Дом","Квартира","Индекс" };
    final Class[] columnClassAddress = new Class[] {
            String.class,
            String.class, String.class,String.class,Integer.class,Integer.class,Integer.class};
    // Дописать SQL
    String sqlQuery4="select ra.name, da.name, ia.name, sa.name, a.house, a.flat, a.`index`\n" +
            "from address a inner join social_client sc on a.id = sc.addressId\n" +
            "inner join region_address ra on a.regionNum = ra.number\n" +
            "inner join district_address da on a.districtNum = da.number\n" +
            "inner join inhabited_locality_address ia on a.inhabitedLocalityNum = ia.number\n" +
            "inner join street_address sa on a.streetNum = sa.number\n" +
            "where sc.addressId=(SELECT sc.addressId from social_client sc where sc.personalNumber=?)";

    public void initModelAddress(String persAddress){
        mdtmSocialClient.columnsAddress=columnsAddress;
        mdtmSocialClient.columnClassAddress=columnClassAddress;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQuery4;
        dtmAddress=mdtmSocialClient.MyTableModelDocument(3);
        tableAddress.setModel(dtmAddress);
        dtmAddress.fireTableDataChanged();
        rowTableAddress = tableAddress.getRowCount();
    }

    String persNum;
    String surname;
    String name;
    String patronymic;
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
        dateBirth = tableSocialClients.getModel().getValueAt(selRowSC,4).toString();
        textDateBirth.setText(dateBirth);
        snils = tableSocialClients.getModel().getValueAt(selRowSC,5).toString();
        textSNILS.setText(snils);
        telephone = tableSocialClients.getModel().getValueAt(selRowSC,6).toString();
        textTelephone.setText(telephone);
        email = tableSocialClients.getModel().getValueAt(selRowSC,7).toString();
        textEmail.setText(email);

        initModelAddress(persNum);

        region = tableAddress.getModel().getValueAt(0,0).toString();
        textRegion.setText(region);
        district = tableAddress.getModel().getValueAt(0,1).toString();
        textDistrict.setText(district);
        inhabitedLocality = tableAddress.getModel().getValueAt(0,2).toString();
        textInhabitedLocality.setText(inhabitedLocality);
        street = tableAddress.getModel().getValueAt(0,3).toString();
        textStreet.setText(street);
        house = tableAddress.getModel().getValueAt(0,4).toString();
        textHouse.setText(house);
        flat = tableAddress.getModel().getValueAt(0,5).toString();
        textFlat.setText(flat);
        index = tableAddress.getModel().getValueAt(0,6).toString();
        textIndex.setText(index);

        initModelIdDocument(persNum);
//        initModelAddress();
        initModelAttDocument(persNum);

//        String typeDoc;
//        String series;
//        String number;
//        String givenBy;
//        String dateStart;
//        String status;
    }

    //для вкладки НСИ
    private DefaultMutableTreeNode setGlossaryJTree(){
        final String ROOT  = "Справочники";
        // Массив листьев деревьев
        final   String[]   nodes = new String[]  {"Пособия", "Организации","Адреса","Личная карточка","Прочие"};
        final   String[][] leafs = new String[][]{
                {"Виды пособий", "Категории пособия", "Правила расчёта"},
                {"Типы организаций", "Организации"},
                {"Районы","Населённые пункты","Улицы"},
                {"Родственные отношения","Виды доходов"},
                {"Документы удостоверения","Документы"}};

        // Создание древовидной структуры
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);
        // Ветви первого уровня
        DefaultMutableTreeNode benefit = new DefaultMutableTreeNode(nodes[0]);
        DefaultMutableTreeNode organization = new DefaultMutableTreeNode(nodes[1]);
        DefaultMutableTreeNode address = new DefaultMutableTreeNode(nodes[2]);
        DefaultMutableTreeNode personalCard = new DefaultMutableTreeNode(nodes[3]);
        DefaultMutableTreeNode other = new DefaultMutableTreeNode(nodes[4]);
        // Добавление ветвей к корневой записи
        root.add(benefit);
        root.add(organization);
        root.add(address);
        root.add(personalCard);
        root.add(other);
        // Добавление листьев
        for ( int i = 0; i < leafs[0].length; i++)
            benefit.add(new DefaultMutableTreeNode(leafs[0][i], false));
        for ( int i = 0; i < leafs[1].length; i++)
            organization.add(new DefaultMutableTreeNode(leafs[1][i], false));
        for ( int i = 0; i < leafs[2].length; i++)
            address.add(new DefaultMutableTreeNode(leafs[2][i], false));
        for ( int i = 0; i < leafs[3].length; i++)
            personalCard.add(new DefaultMutableTreeNode(leafs[3][i], false));
        for ( int i = 0; i < leafs[4].length; i++)
            other.add(new DefaultMutableTreeNode(leafs[4][i], false));

        return root;
    }

}
