package db;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;

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
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField17;
    private JTextField textField18;
    private JTextField textField19;
    private JTextField textField20;
    private JButton updateButton;
    private JTable tableIdDocument;

    private DefaultTableModel dtmSocialClient;
    private DefaultTableModel dtmIdDocument;
    private ListSelectionModel selModel = tableSocialClients.getSelectionModel();

    private int rowTableSC;
    private int rowTableIdDoc;
    private int selRow;

    SocialProtectionForm(){
        super("CommonTable");
        setContentPane(rootPanel);
        initModelSocialClient();
        initModelIdDocument();
        glossaryJTree.setModel( new DefaultTreeModel(setGlossaryJTree()) );
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
        TableModelClients mdtmSocialClient = new TableModelClients();
        mdtmSocialClient.columns=columnsSocialClient;
        mdtmSocialClient.columnClass=columnClassSocialClient;
        mdtmSocialClient.sqlQuery=sqlQuery1;
        dtmSocialClient=mdtmSocialClient.MyTableModel();
        tableSocialClients.setModel(dtmSocialClient);
        dtmSocialClient.fireTableDataChanged();
        rowTableSC = tableSocialClients.getRowCount();
    }

    String[] columnsIdDocument = new String[]
            {"Серия","Номер","Кем выдан","Дата выдачи","Статус"};
    final Class[] columnClassIdDocument = new Class[]{
            String.class,String.class,String.class,String.class,String.class};
    //PreparedStatement сюда вставить
    String sqlQuery2 = "select idDoc.docSeries, idDoc.docNumber, idDoc.givenBy, idDoc.dateStart, idDoc.status\n" +
            "from identification_document idDoc, social_client sc\n" +
            "where idDoc.social_client_personalNumber = sc.personalNumber;";

    public void initModelIdDocument(){
        TableModelClients mdtmIdDocument = new TableModelClients();
        mdtmIdDocument.columns=columnsIdDocument;
        mdtmIdDocument.columnClass=columnClassIdDocument;
        mdtmIdDocument.sqlQuery=sqlQuery2;
        dtmIdDocument=mdtmIdDocument.MyTableModel();
        tableIdDocument.setModel(dtmIdDocument);
        dtmIdDocument.fireTableDataChanged();
        rowTableIdDoc = tableIdDocument.getRowCount();
    }

//    public void initModel(String [] columns, Class[] columnClass, String sqlQuery){
//        TableModelClients mdtmIdDocument = new TableModelClients();
//        mdtmIdDocument.columns=columns;
//        mdtmIdDocument.columnClass=columnClass;
//        mdtmIdDocument.sqlQuery=sqlQuery;
//        dtmIdDocument=mdtmIdDocument.MyTableModel();
//        tableIdDocument.setModel(dtmIdDocument);
//        dtmIdDocument.fireTableDataChanged();
//        rowTableIdDoc = tableIdDocument.getRowCount();
//    }

    public void getRowTableSC(){
        String persNum;
        String surname;
        String name;
        String patronymic;
        String dateBirth;
        String snils;
        String telephone;
        String email;

        selRow = tableSocialClients.getSelectedRow();
        persNum = tableSocialClients.getModel().getValueAt(selRow,0).toString();
        textPersNum.setText(persNum);
        surname = tableSocialClients.getModel().getValueAt(selRow,1).toString();
        textSurname.setText(surname);
        name = tableSocialClients.getModel().getValueAt(selRow,2).toString();
        textName.setText(name);
        patronymic = tableSocialClients.getModel().getValueAt(selRow,3).toString();
        textPatronymic.setText(patronymic);
        dateBirth = tableSocialClients.getModel().getValueAt(selRow,4).toString();
        textDateBirth.setText(dateBirth);
        snils = tableSocialClients.getModel().getValueAt(selRow,5).toString();
        textSNILS.setText(snils);
        telephone = tableSocialClients.getModel().getValueAt(selRow,6).toString();
        textTelephone.setText(telephone);
        email = tableSocialClients.getModel().getValueAt(selRow,7).toString();
        textEmail.setText(email);

//        String typeDoc;
//        String series;
//        String number;
//        String givenBy;
//        String dateStart;
//        String status;
    }

    public void getRowTableIdDoc(){

    }

    //для вкладки НСИ
    private DefaultMutableTreeNode setGlossaryJTree(){
        final String ROOT  = "Справочники";
        // Массив листьев деревьев
        final   String[]   nodes = new String[]  {"Пособия", "Организации","Адреса","Личная карточка","Прочие"};
        final   String[][] leafs = new String[][]{{"Виды пособий", "Категории пособия", "Правила расчёта"},
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
