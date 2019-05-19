package db;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.JComboBox;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class SocialProtectionForm extends JFrame implements TreeSelectionListener{

    // объявление элементов формы
    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane2;
    private JButton editClientButton;
    private JComboBox comboBoxSCCategory;
    private JComboBox comboBoxMeasure;
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
    private JTable tableFindRequest;
    private JButton payoffButton;
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
    private JTable tableOperatingAccount;
    private JTextField textNumOperatingAccount;
    private JTextField textDateStartAccount;
    private JTextField textBICOrg;
    private JTextField textNameOrg;
    private JTextField textAdressOrg;
    private JTextField textTin;
    private JRadioButton rbStatusTrue;
    private JRadioButton rbStatusFalse;
    private JLabel lbNumOA;
    private JLabel lbDateOA;
    private JLabel lbStOA;
    private JLabel lbBICOA;
    private JLabel lbNameOA;
    private JLabel lbAdOA;
    private JLabel lbTinOA;
    private JTable tableRelatives;
    private JTable tableIdDocRelatives;
    private JLabel lbSum;
    private ButtonGroup radioButtonGender;
    private ButtonGroup radioButtonStatusAccount;


    // инициализация таблиц
    private JTable tableSCCategory=new JTable();
    private JTable tableMeasure=new JTable();
    private JTable tableLaw=new JTable();
    private JTable tableArticle=new JTable();
    private JTable tableIndex=new JTable();
    private JTable tableRegion=new JTable();
    private JTable tableDistrict=new JTable();
    private JTable tableLocality=new JTable();
    private JTable tableStreet=new JTable();
    private JTable tableRelation=new JTable();
    private JTable tableTypeIncome=new JTable();
    private JTable tableEmployee=new JTable();
    private JTable tableDepartament=new JTable();
    private JTable tableJob=new JTable();
    private JTable tableBank=new JTable();
    private JTable tablePost=new JTable();
    private JTable tableCash=new JTable();

    // объявление элементов формы
    private JTable tableCategoryMeasure;
    private JTabbedPane tabbedPane4;
    private JTable tableRequest;
    private JTable tablePayoffClient;
    private JTable tablePA;
    private JComboBox textIdDocTypeComboBox;
    private JComboBox textIdDocStatusComboBox;
    private JFormattedTextField textIdDocSeries;
    private JFormattedTextField textIdDocNumber;
    private JTextField textIdDocGivenBy;
    private JFormattedTextField textIdDocDateStart;
    private JLabel labelIdDocType;
    private JLabel labelIdDocSeries;
    private JLabel labelIdDocNumber;
    private JLabel labelIdDocGivenBy;
    private JLabel labelIdDocDateStart;
    private JLabel labelIdDocStatus;
    private JComboBox textAttDocTypeComboBox;
    private JFormattedTextField textAttDocDateStart;
    private JComboBox textAttDocStatusComboBox;
    private JTextField textAttDocName;
    private JLabel labelAttDocType;
    private JLabel labelAttDocName;
    private JLabel labelAttDocDateStart;
    private JLabel labelAttDocStatus;
    private JTextField textAttDocNumber;
    private JLabel labelAttDocNumber;
    private JTable tableIncome;
    private JButton findPayoffButton;
    private JPanel pnPeriodPayoff;
    private JTextField textRelTypeDoc;
    private JTextField textRelSeries;
    private JTextField textRelNumber;
    private JRadioButton rbRelativeDocStatus1;
    private JRadioButton rbRelativeDocStatus2;
    private JLabel lbRelTypeDoc;
    private JLabel lbRelSeries;
    private JLabel lbRelNumber;
    private JLabel lbRelStatus;
    private JButton addIdDocButton;
    private JButton addAttDocButton;
    private JButton addOperAccButton;
    private JButton addRelativeButton;
    private JButton addRelativeIdDoc;
    private JButton addCategoryMeasureButton;
    private JButton addRequestButton;
    private JButton deleteCategoryMeasureButton;
    private JButton deleteRequestButton;
    private JLabel lbRelGivenBy;
    private JLabel lbRelDateStart;
    private JTextField textRelGivenBy;
    private JTextField textRelDateStart;
    private JButton deleteIdDocButton;
    private JButton deleteAttDocButton;
    private JButton deleteOperAccButton;
    private JButton deleteRelativeButton;
    private JButton deleteRelativeIdDoc;
    private JButton buttonFind;
    private JSpinner spinnerYear;
    private JPanel panelDateBirth;
    private JPanel panelIdDocDateStart;
    private JPanel panelAttDocDateStart;
    private JPanel panelOperAccDateStart;
    private JPanel panelRelDateStart;
    private JFormattedTextField textTelephone;
    private JFormattedTextField textSNILS;
    private JButton addIncomeButton;
    private JButton deleteIncomeButton;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JTextField textNewItemInHandbook;
    private JButton addNewItemButton;
    private JLabel labelNewItem;
    private JComboBox organizationCombox;
    private JComboBox mspCombox;
    private JTable reestrTable;
    private JButton createReestrButton;
    private JButton saveReestrButton;
    private JPanel pnPeriodReestr;
    private JTable tableIndDoc=new JTable();
    private JTable tableDoc=new JTable();

    private JTable []box={tableIndex, tableRegion, tableDistrict, tableLocality, tableStreet,tableIndDoc,tableDoc,tableSCCategory,tableRelation, tableBank, tablePost, tableCash};
    private DefaultTableModel dtmSocialClient;
    private DefaultTableModel dtmIdDocument;
    private DefaultTableModel dtmAttDocument;
    private DefaultTableModel dtmAddress;
    private DefaultTableModel dtmOpAcc;
    private DefaultTableModel dtmCategoryMeasure;
    private DefaultTableModel dtmRelatives;
    private DefaultTableModel dtmIdDocRelatives;
    private DefaultTableModel dtmIncome;
    private DefaultTableModel dtmRequest;
    private DefaultTableModel dtmPayoff;
    private DefaultTableModel dtmPersAcc;
    private DefaultTableModel dtmClientCategoryMeasure;
    private DefaultTableModel dtmHandbook;
    private DefaultTableModel dtmSaldoReport;
    private DefaultTableModel dtmFindRequest;
    private ListSelectionModel selModelSC = tableSocialClients.getSelectionModel();
    private ListSelectionModel selModelSC_IdDoc = tableIdDocument.getSelectionModel();
    private ListSelectionModel selModelSC_AttDoc = tableAttDocument.getSelectionModel();
    private ListSelectionModel selModelSC_RelativeDoc = tableIdDocRelatives.getSelectionModel();
    private TableModelClients mdtmSocialClient = new TableModelClients();
    private DeleteClient deleteClient;
    private EditClient editClient = new EditClient();
    private com.toedter.calendar.JDateChooser dcPeriodPayoff = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcPeriodReestr = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcDateBirth = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcIdDocDateStart= new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcAttDocDateStart= new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcOperAccDateStart= new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser dcRelDateStart= new com.toedter.calendar.JDateChooser();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private DatabaseConnection mdbc;
    private Statement stmt;

    private int rowTableSC;
    private int rowTableIdDoc;
    private int rowTableAttDoc;
    private int rowTableAddress;
    private int rowTableOpAcc;
    private int rowTableRelatives;
    String treeSelect;

    private int selRowSC;
    private int selRowSC_IdDoc;
    private int selRowSC_AttDoc;
    private int selRowSC_RelativePersNum;
    private int selRowCatMeasure;
    private int selRowRequest;
    private int selRowIdDoc;
    private int selRowAttDoc;
    private int selRowRel;
    private int selRowRelIdDoc;
    private int selRowOperAcc;
    private int selRowItemInHandbook;
    private int selRowIncome;
    private int numHand;

    // это для отчета в Эксель
    private ArrayList<Integer> persAc;
    private ArrayList<String> s_name;
    private ArrayList<String> my_name;
    private ArrayList<String> p_mic;
    private ArrayList<Integer> in_saldo;
    private ArrayList<Integer> acc;
    private ArrayList<Integer> paid;
    private ArrayList<Integer> out_saldo;

   // для реестра
    private ArrayList<String> rasAcc;
    private ArrayList<Double> summa;

    SimpleDateFormat formatForSql= new SimpleDateFormat("yyyy-MM-dd");

    SocialProtectionForm(){
        super("АИС 'Социальная защита населения'");

        JMenuBar menuBar = new JMenuBar();
        JMenu menuHelp = new JMenu("Помощь");
        JMenu menuWindow = new JMenu("Окно");
        JMenuItem menuItemAbout = new JMenuItem("Справка");
        JMenuItem menuItemClose = new JMenuItem("Закрыть");

        menuItemClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame menuItemAbout = new MenuItemAbout();

                menuItemAbout.setTitle("Справка");
                menuItemAbout.pack();
                menuItemAbout.setVisible(true);
            }
        });

        menuWindow.add(menuItemClose);
        menuHelp.add(menuItemAbout);
        menuBar.add(menuWindow);
        menuBar.add(menuHelp);
        menuBar.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        this.setJMenuBar(menuBar);

        setContentPane(rootPanel);
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);

        tableAddress.setVisible(false);
        initModelSocialClient(sqlQuery1);
        glossaryJTree.setModel( new DefaultTreeModel(setGlossaryJTree()) );
        glossaryJTree.addTreeSelectionListener(this);
        listenerRowTableSC();
        initModelHandbook();
        Date dt=new Date();
        mdtmSocialClient.updateStatusMeasure(formatForSql.format(dt));

        JLabel labelIdDocPlus = new JLabel();
        JLabel labelAttDocPlus = new JLabel();
        JLabel labelOperAccPlus = new JLabel();
        JLabel labelRelativePlus = new JLabel();
        JLabel labelRelativeIdDocPlus = new JLabel();
        JLabel labelIdDocMinus= new JLabel();
        final JLabel labelAttDocMinus = new JLabel();
        JLabel labelOperAccMinus = new JLabel();
        JLabel labelRelativeMinus = new JLabel();
        JLabel labelRelativeIdDocMinus = new JLabel();
        JLabel labelCategoryMeasurePlus = new JLabel();
        JLabel labelRequestPlus = new JLabel();
        JLabel labelCategoryMeasureMinus = new JLabel();
        JLabel labelRequestMinus = new JLabel();
        JLabel labelIncomePlus = new JLabel();
        JLabel labelIncomeMinus = new JLabel();
        JLabel labelNewItemPlus = new JLabel();
        JLabel labelNewItemMinus = new JLabel();
        JLabel labelSearch = new JLabel();
//Установить плюсик на Button
        ImageIcon iconPlus = new ImageIcon("src\\plus.png");
        Image imagePlus = iconPlus.getImage();
        Image newimgPlus = imagePlus.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        iconPlus = new ImageIcon(newimgPlus);

        ImageIcon iconMinus = new ImageIcon("src\\minus.png");
        Image imageMinus = iconMinus.getImage();
        Image newimgMinus = imageMinus.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        iconMinus = new ImageIcon(newimgMinus);

        ImageIcon iconSearch = new ImageIcon("src\\pupa.png");
        Image imageSearch = iconSearch.getImage();
        Image newimgSearch = imageSearch.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        iconSearch = new ImageIcon(newimgSearch);

        labelIdDocPlus.setIcon(iconPlus);
        labelAttDocPlus.setIcon(iconPlus);
        labelOperAccPlus.setIcon(iconPlus);
        labelRelativePlus.setIcon(iconPlus);
        labelRelativeIdDocPlus.setIcon(iconPlus);
        labelCategoryMeasurePlus.setIcon(iconPlus);
        labelRequestPlus.setIcon(iconPlus);
        labelIncomePlus.setIcon(iconPlus);
        labelNewItemPlus.setIcon(iconPlus);
        addIdDocButton.add(labelIdDocPlus);
        addAttDocButton.add(labelAttDocPlus);
        addOperAccButton.add(labelOperAccPlus);
        addRelativeButton.add(labelRelativePlus);
        addRelativeIdDoc.add(labelRelativeIdDocPlus);
        addCategoryMeasureButton.add(labelCategoryMeasurePlus);
        addRequestButton.add(labelRequestPlus);
        addIncomeButton.add(labelIncomePlus);
        addItemButton.add(labelNewItemPlus);

        labelIdDocMinus.setIcon(iconMinus);
        labelAttDocMinus.setIcon(iconMinus);
        labelOperAccMinus.setIcon(iconMinus);
        labelRelativeMinus.setIcon(iconMinus);
        labelRelativeIdDocMinus.setIcon(iconMinus);
        labelCategoryMeasureMinus.setIcon(iconMinus);
        labelRequestMinus.setIcon(iconMinus);
        labelIncomeMinus.setIcon(iconMinus);
        labelNewItemMinus.setIcon(iconMinus);
        deleteAttDocButton.add(labelAttDocMinus);
        deleteIdDocButton.add(labelIdDocMinus);
        deleteOperAccButton.add(labelOperAccMinus);
        deleteRelativeButton.add(labelRelativeMinus);
        deleteRelativeIdDoc.add(labelRelativeIdDocMinus);
        deleteCategoryMeasureButton.add(labelCategoryMeasureMinus);
        deleteRequestButton.add(labelRequestMinus);
        deleteIncomeButton.add(labelIncomeMinus);
        deleteItemButton.add(labelNewItemMinus);

        labelSearch.setIcon(iconSearch);
        buttonFind.add(labelSearch);


        openFieldsForEdit(false);
        editClientButton.setEnabled(false);
        deleteClientButton1.setEnabled(false);
        fillComboBox();
        setButtonGroup();
        setCBMeasure();

        hideOperAcc();
        setButtonGroupStatus();
        listenerRowTableOpAcc();
        listenerRowTableIdDocument();
        listenerRowTableAttDocument();
        listenerRowTableRel();
        listenerRowTableRelIdDoc();
        listenerRowTableOperAcc();
        listenerRowTableHandbook();
//        listenerRowTableIdDocument();

        listenerRowTableRelatives();
        dcPeriodPayoff.setDate(new Date());
        pnPeriodPayoff.add(dcPeriodPayoff);
        dcPeriodPayoff.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));

        dcPeriodReestr.setDate(new Date());
        pnPeriodReestr.add(dcPeriodReestr);
        dcPeriodReestr.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));

        panelDateBirth.add(dcDateBirth);
        dcDateBirth.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        panelIdDocDateStart.add(dcIdDocDateStart);
        dcIdDocDateStart.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        panelAttDocDateStart.add(dcAttDocDateStart);
        dcAttDocDateStart.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        panelOperAccDateStart.add(dcOperAccDateStart);
        dcOperAccDateStart.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        panelRelDateStart.add(dcRelDateStart);
        dcRelDateStart.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));

        SpinnerModel sm = new SpinnerNumberModel(2019, 2019, 2150, 1);
        spinnerYear.setModel(sm);

        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame insertNewClientForm = new InsertNewClientForm(box);
                insertNewClientForm.setTitle("Добавление нового клиента");
                insertNewClientForm.pack();
                insertNewClientForm.setVisible(true);
                getRowTableSC();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initModelSocialClient(sqlQuery1);
                tableSocialClients.setRowSelectionInterval(0,0);
            }
        });

        comboBoxSCCategory.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                setCBMeasure();
            }
        });

        createSaldoReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initModelSaldoReport();
            }
        });

        saveReportBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveReportToExcel();
            }
        });

        createReestrButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initModelReestrReport();
            }
        });

        saveReestrButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveReestrToExcel();
            }
        });

        findClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                initModelClientCategoryMeasure();
                if(tableChargeRequest.getRowCount()>0)
                    chargeButton.setEnabled(true);
                else
                    chargeButton.setEnabled(false);
            }
        });

        chargeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                TableModel[] boxTM ={tableChargeRequest.getModel(), tableEmployee.getModel()};
                JFrame insertRequest = new InsertRequset(boxTM, -1);
                insertRequest.setTitle("Добавление новой заявки на выплату");
                insertRequest.pack();
                insertRequest.setVisible(true);
            }
        });

        findPayoffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                initModelFindRequest();
                if(tableFindRequest.getRowCount()>0)
                    payoffButton.setEnabled(true);
                else
                    payoffButton.setEnabled(false);
            }
        });

        payoffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                setPayoff();
            }
        });

    //Вкладка "Личные дела", кнопка "Редактирование"
        editClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFieldsForEdit(true);
                listenerRowTableSC_IdDoc();
                if(tableIdDocument.getRowCount()!=0)
                    tableIdDocument.setRowSelectionInterval(0,0);
                listenerRowTableSC_AttDoc();
                if(tableAttDocument.getRowCount()!=0)
                    tableAttDocument.setRowSelectionInterval(0,0);
                if(tableOperatingAccount.getRowCount()!=0)
                    tableOperatingAccount.setRowSelectionInterval(0,0);
                if(tableRelatives.getRowCount()!=0)
                    tableRelatives.setRowSelectionInterval(0,0);
                listenerRowTableSC_RelativeDoc();
                if(tableIdDocRelatives.getRowCount()!=0)
                    tableIdDocRelatives.setRowSelectionInterval(0,0);
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Перс.данные
                String [] paramsPersInf = {textSurname.getText(),textName.getText(), textPatronymic.getText(),dateFormat.format(dcDateBirth.getDate()),textSNILS.getText(),
                        textTelephone.getText(),textEmail.getText(),getGender(),textPersNum.getText()};
                String sqlUpdatePersInf = "update social_client set surname = ?, name = ?, patronymic = ?, dateBirth=?, snils = ?,\n" +
                        "  telephone = ?, email = ?, numberGender = ? where personalNumber = ?";
                editClient.update(sqlUpdatePersInf,paramsPersInf);

                //Адрес
                Integer [] paramsAddress = {Integer.parseInt(textHouse.getText()), Integer.parseInt(textFlat.getText()),Integer.parseInt(getIndex()),
                        getRegion(),getDistrict(),getInhabitedLoc(),getStreet(),Integer.parseInt(textPersNum.getText())};
                String sqlUpdateAddress = "update address set house=?, flat=?, numberIndex=?, numberRegion=?, numberDistrict=?," +
                        "numberInhabitedLocality=?, numberStreet=? where idAddress=(select sc.idAddress from social_client sc\n" +
                        "where sc.personalNumber=?)";
                editClient.update(sqlUpdateAddress,paramsAddress);

                //Уд.личности
                Object [] paramsIdDoc = {textIdDocGivenBy.getText(), dateFormat.format(dcIdDocDateStart.getDate()),getIdDocStatus(),getTypeIdDoc(),
                        Integer.parseInt(textIdDocSeries.getText()), Integer.parseInt(textIdDocNumber.getText()),textPersNum.getText()};
                String sqlUpdateIdDoc = "update identification_document set givenBy=?, dateStartIdDocument=?," +
                        "statusIdDocument=?,numberTypeIdDocument=? where docSeries=? && docNumber=? && personalNumber=?";
                editClient.update(sqlUpdateIdDoc,paramsIdDoc);

                //Доп.документы
                Object [] paramsAttDoc = {textAttDocName.getText(),dateFormat.format(dcAttDocDateStart.getDate()),getAttDocStatus(),
                        getTypeAttDoc(), textAttDocNumber.getText(), textPersNum.getText()}; //вставить поля attDoc
                String sqlUpdateAttDoc = "update attached_document set nameAttachedDocument=?, dateStartAttachedDocument=?,"+
                        "statusAttachedDocument=?,numberTypeAttachedDocument=? where numberAttachedDocument = ? && personalNumber=?";
                editClient.update(sqlUpdateAttDoc,paramsAttDoc);

                //Расчётный счёт
                String[] paramsOperatingAcc = {dateFormat.format(dcOperAccDateStart.getDate()),getOperAccStatus(),textPersNum.getText()};
                String sqlUpdateOperAcc = "update operating_account set dateStartAccount=?, statusOperaingAccount=? where personalNumber=?";
                editClient.update(sqlUpdateOperAcc,paramsOperatingAcc);

                //Документ родственника
                Object[] paramsRelIdDoc = {textRelGivenBy.getText(), dateFormat.format(dcRelDateStart.getDate()), getRelativeDocStatus(),
                        textRelSeries.getText(), textRelNumber.getText()};
                String sqlUpdateRelIdDoc = "update identification_document set givenBy=?, dateStartIdDocument=?," +
                            "statusIdDocument=? where docSeries=? && docNumber=?";
                editClient.update(sqlUpdateRelIdDoc, paramsRelIdDoc);

//                //Документ родственника
//                int colRowSC_Relative = tableRelatives.getRowCount();
//                if(colRowSC_Relative!=0) {
//                    selRowSC_RelativePersNum = tableRelatives.getSelectedRow();
//                    String relativePersNum = tableRelatives.getModel().getValueAt(selRowSC_RelativePersNum, 0).toString();
//                    String[] paramsRelativeDoc = {getRelativeDocStatus(), textPersNum.getText(), relativePersNum};
//                    String sqlUpdateRelativeDoc = "update identification_document set statusIdDocument=? where personalNumber=? and relativePersonalNumber=?";
//                    editClient.update(sqlUpdateRelativeDoc, paramsRelativeDoc);
//                }
//                openFieldsForEdit(false);
                initModelSocialClient(sqlQuery1);
                initModelIdDocument(textPersNum.getText());
                initModelAttDocument(textPersNum.getText());
                initModelAddress(textPersNum.getText());
                initModelOperAcc(textPersNum.getText());
                initModelIdDocRelatives(textRelNumber.getText());

                openFieldsForEdit(false);
            }
        });
        addIdDocButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addIdDoc = new AddIdDoc(textPersNum.getText(),"",box);
                addIdDoc.setTitle("Добавление нового документа, удостоверяющего личность");
                addIdDoc.pack();
                addIdDoc.setVisible(true);
                addIdDoc.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        initModelIdDocument(textPersNum.getText());
                    }
                });
            }
        });
        addAttDocButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addAttDoc = new AddAttachedDoc(textPersNum.getText(),box);
                addAttDoc.setTitle("Добавление нового документа");
                addAttDoc.pack();
                addAttDoc.setVisible(true);
                addAttDoc.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        initModelAttDocument(textPersNum.getText());
                    }
                });
            }
        });
        addOperAccButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addOperAcc = new AddOperatingAcc(textPersNum.getText(), tableBank, tablePost, tableCash);
                addOperAcc.setTitle("Добавление нового расчетного счета");
                addOperAcc.pack();
                addOperAcc.setVisible(true);
                addOperAcc.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        initModelOperAcc(textPersNum.getText());
                    }
                });
            }
        });
        addRelativeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addRelative = new AddRelative(textPersNum.getText(),box);
                addRelative.setTitle("Добавление нового члена семьи");
                addRelative.pack();
                addRelative.setVisible(true);
                addRelative.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        initModelRelatives(textPersNum.getText());
                    }
                });
            }
        });
        addRelativeIdDoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = tableRelatives.getSelectedRow();
                if(selRow>=0) {
                    String idRel = tableRelatives.getModel().getValueAt(selRow, 0).toString();
                    JFrame addRelativeIdDoc = new AddIdDoc(textPersNum.getText(), idRel, box);
                    addRelativeIdDoc.setTitle("Добавление нового документа, удостоверяющего личность");
                    addRelativeIdDoc.pack();
                    addRelativeIdDoc.setVisible(true);
                    addRelativeIdDoc.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            initModelIdDocRelatives(textRelNumber.getText());
                        }
                    });
                }
            }
        });
        addCategoryMeasureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TableModel [] tm={tableSCCategory.getModel(), tableMeasure.getModel()};
                JFrame addCatMeasure = new AddCategoryMeasure(textPersNum.getText(), tm);
                addCatMeasure.setTitle("Добавление новой меры социальной поддержки");
                addCatMeasure.pack();
                addCatMeasure.setVisible(true);
                addCatMeasure.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        initModelCategoryMeasure(textPersNum.getText());
                    }
                });
            }
        });

        listenerRowTableCategoryMeasure();

        addRequestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                TableModel[] boxTM ={tableCategoryMeasure.getModel(), tableEmployee.getModel()};
                JFrame insertOneRequest = new InsertRequset(boxTM, selRowCatMeasure);
                insertOneRequest.setTitle("Добавление новой заявки на выплату");
                insertOneRequest.pack();
                insertOneRequest.setVisible(true);
                insertOneRequest.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        initModelRequest(textPersNum.getText());
                    }
                });
            }
        });

        listenerRowTableRequest();

        addIncomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String client [] = {textPersNum.getText(), textSurname.getText(), textName.getText(), textPatronymic.getText()};
                JTable [] table={tableRelatives, tableTypeIncome};
                JFrame addIncome = new AddIncome(client, table);
                addIncome.setTitle("Добавление нового дохода");
                addIncome.pack();
                addIncome.setVisible(true);
                addIncome.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        initModelIncome(textPersNum.getText());
                    }
                });
            }
        });

        listenerRowTableIncome();

        deleteCategoryMeasureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить меру?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION) {
                    TableModel tm = tableCategoryMeasure.getModel();
                    String sqlDeleteMeasure = "delete from client_measure where personalNumber='" + tm.getValueAt(selRowCatMeasure, 0) + "' and " +
                            "codeSocialMeasure='" + tm.getValueAt(selRowCatMeasure, 2) + "' and codeClientCategory='" + tm.getValueAt(selRowCatMeasure, 1) + "'";
                    mdtmSocialClient.sqlQuery = sqlDeleteMeasure;
                    mdtmSocialClient.deleteRow();
                    initModelCategoryMeasure(textPersNum.getText());
                    deleteCategoryMeasureButton.setEnabled(false);
                }
            }
        });

        deleteRequestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить заявку?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION) {
                    TableModel tm = tableRequest.getModel();
                    String sqlDeleteMeasure = "delete from request_for_cash_settlement where requestNumber='" + tm.getValueAt(selRowRequest, 0) + "'";
                    mdtmSocialClient.sqlQuery = sqlDeleteMeasure;
                    mdtmSocialClient.deleteRow();
                    initModelRequest(textPersNum.getText());
                    deleteRequestButton.setEnabled(false);
                }
            }
        });

        deleteIncomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить доход?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION) {
                    TableModel tm = tableIncome.getModel();
                    String sqlDeleteMeasure = "delete from income where IdIncome='" + tm.getValueAt(selRowIncome, 0) + "'";
                    mdtmSocialClient.sqlQuery = sqlDeleteMeasure;
                    mdtmSocialClient.deleteRow();
                    initModelIncome(textPersNum.getText());
                    deleteIncomeButton.setEnabled(false);
                }
            }
        });

        deleteClientButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.setDefaultLocale(new Locale("ru","RU"));
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить клиента?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION){
                    deleteClient = new DeleteClient(textPersNum.getText());
                    TableModel tm = tableOperatingAccount.getModel();
                    deleteClient.delete(tm.getValueAt(selRowOperAcc,0).toString());
                    initModelSocialClient(sqlQuery1);
                    tableSocialClients.setRowSelectionInterval(0,0);
//                    getRowTableSC();
                }
            }
        });
        deleteIdDocButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить документ?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION){
                    deleteClient = new DeleteClient(textPersNum.getText());
                    TableModel tm = tableIdDocument.getModel();
                    deleteClient.deleteIdDoc(tm.getValueAt(selRowIdDoc,1).toString(),
                            tm.getValueAt(selRowIdDoc,2).toString());
                    initModelIdDocument(textPersNum.getText());
                    deleteIdDocButton.setEnabled(false);
                }
//                listenerRowTableIdDocument();
//                TableModel tm = tableIdDocument.getModel();
            }
        });
        deleteAttDocButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                listenerRowTableAttDocument();
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить документ?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION) {
                    deleteClient = new DeleteClient(textPersNum.getText());
                    TableModel tm = tableAttDocument.getModel();
                    deleteClient.deleteAttDoc(tm.getValueAt(selRowAttDoc, 0).toString());
                    initModelAttDocument(textPersNum.getText());
                    deleteAttDocButton.setEnabled(false);
                }
            }
        });
        deleteOperAccButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить Р/С?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION) {
                    deleteClient = new DeleteClient(textPersNum.getText());
                    TableModel tm = tableOperatingAccount.getModel();
                    deleteClient.deleteOperAcc(tm.getValueAt(selRowOperAcc,0).toString());
                    initModelOperAcc(textPersNum.getText());
                    deleteOperAccButton.setEnabled(false);
                }
            }
        });
        deleteRelativeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить родственника?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION) {
                    deleteClient = new DeleteClient(textPersNum.getText());
                    TableModel tm = tableRelatives.getModel();
                    deleteClient.deleteRelative(tm.getValueAt(selRowRel, 0).toString());
                    initModelRelatives(textPersNum.getText());
                    deleteRelativeButton.setEnabled(false);
                }
            }
        });
        deleteRelativeIdDoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, что хотите удалить документ родственника?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION) {
                    deleteClient = new DeleteClient(textPersNum.getText());
                    TableModel tm = tableIdDocRelatives.getModel();
                    deleteClient.deleteIdDoc(tm.getValueAt(selRowRelIdDoc,1).toString(),
                            tm.getValueAt(selRowRelIdDoc,2).toString());
                    initModelIdDocRelatives(textRelNumber.getText());
                    deleteRelativeIdDoc.setEnabled(false);
                }
            }
        });

        buttonFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                TableModel [] tm={tableIndex.getModel(), tableRegion.getModel(), tableDistrict.getModel(),
                        tableLocality.getModel(), tableStreet.getModel(), tableSCCategory.getModel(), tableMeasure.getModel()};
                JFrame findClient = new FilterClient(tm, SocialProtectionForm.this);
                findClient.setTitle("Поиск клиента");
                findClient.pack();
                findClient.setVisible(true);
            }
        });
        addNewItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //addItemInGlossary();
                labelNewItem.setVisible(false);
                textNewItemInHandbook.setVisible(false);
                addNewItemButton.setVisible(false);
            }
        });
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTable [] tb={tableSCCategory, tableArticle, tableLaw, tableDepartament, tableJob};
                JFrame addHand = new AddHandbook(numHand, tb);
                addHand.setTitle("Добавление элемента справочника");
                addHand.pack();
                addHand.setVisible(true);
                addHand.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        initModelHandbook();
                        updateHandbook();
                    }
                });
                /*labelNewItem.setVisible(true);
                textNewItemInHandbook.setVisible(true);
                addNewItemButton.setVisible(true);*/
            }
        });
        deleteItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showOptionDialog(SocialProtectionForm.this, "Вы уверены, " +
                                "что хотите удалить этот элемент из справочника?",
                        "Подтверждение удаления",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,null,
                        new Object[]{"Да", "Нет", "Отмена"},
                        "Да");
                if(res==JOptionPane.YES_OPTION){
//                    listenerRowTableHandbook();
                    String params = deleteItemInGlossary();
                    deleteClient = new DeleteClient();
                    int rez=deleteClient.deleteItemInGlossary(params);
                    if(rez==1) {
                        initModelHandbook();
                        updateHandbook();
                        deleteItemButton.setEnabled(false);
                        switch (numHand){
                            case 1:
                                getComboBox(comboBoxSCCategory, tableSCCategory);
                                break;
                            case 5:
                                getComboBox(textIndexComboBox,tableIndex);
                                break;
                            case 6:
                                getComboBox(textRegionComboBox,tableRegion);
                                break;
                            case 7:
                                getComboBox(textDistrictComboBox,tableDistrict);
                                break;
                            case 8:
                                getComboBox(textInhabitedLocalityComboBox,tableLocality);
                                break;
                            case 9:
                                getComboBox(textStreetComboBox,tableStreet);
                                break;
                            case 12:
                                getComboBox(textIdDocTypeComboBox,tableIndDoc);
                                break;
                            case 13:
                                getComboBox(textAttDocTypeComboBox,tableDoc);
                                break;
                            case 17:
                            case 18:
                            case 19:
                                getComboBoxOrganization(organizationCombox,tableBank, tablePost, tableCash);
                                break;
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(SocialProtectionForm.this,"Невозможно удалить используемый элемент справочника","Удаление",JOptionPane.WARNING_MESSAGE);
//                    initModelIdDocument(textPersNum.getText());
                }
            }
        });

        setToolText(tableSocialClients);
        setToolText(tableIdDocument);
        setToolText(tableAttDocument);
        setToolText(tableOperatingAccount);
        setToolText(tableCategoryMeasure);
        setToolText(tableRequest);
        setToolText(tablePayoffClient);
        setToolText(tablePA);
        setToolText(tableRelatives);
        setToolText(tableIdDocRelatives);
        setToolText(tableIncome);
        setToolText(tableChargeRequest);
        setToolText(tableFindRequest);
        setToolText(tableHandbook);
        setToolText(tableSaldoReport);
        setToolText(reestrTable);
    }

    private void setToolText(JTable tb){
        tb.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setToolTipText(getText());
                return this;
            }
        });
    }

    private void setCBMeasure(){
        comboBoxMeasure.removeAllItems();
        String item;
        int k=comboBoxSCCategory.getSelectedIndex();
        for (int i = 0;i<tableMeasure.getRowCount();i++){
            if(Integer.valueOf(tableMeasure.getValueAt(i, 1).toString())==tableSCCategory.getModel().getValueAt(k,0)) {
                item = tableMeasure.getModel().getValueAt(i, 2).toString();
                comboBoxMeasure.addItem(item);
            }
        }
    }

    private void setMask(JFormattedTextField text,int type){
        try {
            switch(type){
                case 1:  //Маска для телефона
                    MaskFormatter phoneFormatter = new MaskFormatter("+7-###-###-##-##");
                    phoneFormatter.setPlaceholderCharacter('0');
                    DefaultFormatterFactory phoneFactory = new DefaultFormatterFactory(phoneFormatter);
                    text.setFormatterFactory(phoneFactory);
                    break;
                case 2:
                    MaskFormatter snilsFormatter = new MaskFormatter("###-###-### ##");
                    snilsFormatter.setPlaceholderCharacter('*');
                    DefaultFormatterFactory snilsFactory = new DefaultFormatterFactory(snilsFormatter);
                    text.setFormatterFactory(snilsFactory);
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void saveReportToExcel() {
        String date = (String) periodCombox.getSelectedItem();
        String employee = (String) employeeCombox.getSelectedItem();
        String category = (String) categoryCombox.getSelectedItem();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Оборотная ведомость");
        int column = 0; // текущий столбец
        int row = 0;// текущая строка
        HSSFRow newRow = sheet.createRow(row);
        Cell cell = newRow.createCell(column, CellType.STRING);
        cell.setCellValue("ГБУ РК \"Центр по предоставению государственных услуг в сфере социальной защиты населения города Ухты\"");

        row++; // row = 1
        newRow = sheet.createRow(row);
        cell = newRow.createCell(column, CellType.STRING);
        cell.setCellValue("ОБОРОТНАЯ ВЕДОМОСТЬ по проводкам за период: " + date);
        // HSSFCellStyle styleForReport = createStyleForReport(workbook);
        CellRangeAddress region;
        region = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(region);
        region = new CellRangeAddress(1, 1, 0, 7);
        sheet.addMergedRegion(region);

        row++; // row = 2
        newRow = sheet.createRow(row);
        cell = newRow.createCell(column, CellType.STRING);
        cell.setCellValue("Соц.выплата: ");
        cell = newRow.createCell(column + 1, CellType.STRING);
        cell.setCellValue(category);
        region = new CellRangeAddress(2, 2, 1, 7);
        sheet.addMergedRegion(region);

        row++; // row = 3
        newRow = sheet.createRow(row);
        cell = newRow.createCell(column, CellType.STRING);
        cell.setCellValue("Составлено сотрудником: ");
        cell = newRow.createCell(column + 1, CellType.STRING);
        cell.setCellValue(employee);
        region = new CellRangeAddress(3, 3, 1, 7);
        sheet.addMergedRegion(region);


        row++; // row = 4
        newRow = sheet.createRow(row);
        cell = newRow.createCell(column, CellType.STRING);
        cell.setCellValue("Л/с");
        cell = newRow.createCell(column + 1, CellType.STRING);
        cell.setCellValue("Фамилия");
        cell = newRow.createCell(column + 2, CellType.STRING);
        cell.setCellValue("Имя");
        cell = newRow.createCell(column + 3, CellType.STRING);
        cell.setCellValue("Отчество");
        cell = newRow.createCell(column + 4, CellType.STRING);
        cell.setCellValue("Входное сальдо");
        cell = newRow.createCell(column + 5, CellType.STRING);
        cell.setCellValue("Начислено");
        cell = newRow.createCell(column + 6, CellType.STRING);
        cell.setCellValue("Оплачено");
        cell = newRow.createCell(column + 7, CellType.STRING);
        cell.setCellValue("Исходящее сальдо");

        for (int i = 0; i < persAc.size(); i++) {
            row++;
            newRow = sheet.createRow(row);
            cell = newRow.createCell(column, CellType.NUMERIC); // лицевой счет
            cell.setCellValue(persAc.get(i));
            cell = newRow.createCell(column + 1, CellType.STRING);
            cell.setCellValue(s_name.get(i)); //
            cell = newRow.createCell(column + 2, CellType.STRING);
            cell.setCellValue(my_name.get(i)); // Имя
            cell = newRow.createCell(column + 3, CellType.STRING);
            cell.setCellValue(p_mic.get(i)); //Отчество
            cell = newRow.createCell(column + 4, CellType.STRING);
            cell.setCellValue(in_saldo.get(i)); // Вх.сальдо
            cell = newRow.createCell(column + 5, CellType.STRING);
            cell.setCellValue(acc.get(i)); // Начислено
            cell = newRow.createCell(column + 6, CellType.STRING);
            cell.setCellValue(acc.get(i)); // Оплачено
            cell = newRow.createCell(column + 7, CellType.STRING);
            cell.setCellValue(acc.get(i)); // Исх сальдо
        }
        region = new CellRangeAddress(row+1, row+1, 0,3);
        sheet.addMergedRegion(region);
        row++;
        newRow = sheet.createRow(row);
        cell = newRow.createCell(column, CellType.STRING);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        cell.setCellStyle(style);
        cell.setCellValue("ИТОГО: ");
        cell = newRow.createCell(column+4, CellType.FORMULA);
        String sumFormula1 = "SUM(E6:E"+row+")";
        cell.setCellFormula(sumFormula1);
        cell = newRow.createCell(column+5, CellType.FORMULA);
        String sumFormula2 = "SUM(F6:F"+row+")";
        cell.setCellFormula(sumFormula2);


        File file = new File("C:/test repos/Оборотная ведомость-"+date+"-"+category+".xls");
        if(file.exists()) file.delete();
        file.getParentFile().mkdirs();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
           // System.out.println("Created file: "+file.getAbsolutePath());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream input_doc = new FileInputStream(
                    new File("C:/test repos/Оборотная ведомость-"+date+"-"+category+".xls"));
            HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_doc);
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
            // my_worksheet.rowIterator();

            Document iText_xlstopdf = new Document();
            String fileName = "C:/test repos/Оборотная ведомость за "+date+".pdf";
            PdfWriter.getInstance(iText_xlstopdf, new FileOutputStream(fileName));
            iText_xlstopdf.open();
            BaseFont bf_russian = BaseFont.createFont("src\\FreeSans.ttf", "CP1251", BaseFont.EMBEDDED);
            com.itextpdf.text.Font russian;
            russian = new com.itextpdf.text.Font(bf_russian, 11);
            PdfPTable my_table = new PdfPTable(8);
            Paragraph p = new Paragraph();
            Chunk chunk = new Chunk("ГБУ РК \"Центр по предоставению" +
                    " государственных услуг в сфере социальной защиты населения города Ухты\"\n", russian);
            p.add(chunk);
            chunk = new Chunk("ОБОРОТНАЯ ВЕДОМОСТЬ по проводкам за период: " + date+"\n", russian);
            p.add(chunk);
            chunk = new Chunk("Соц.выплата: "+category+"\n", russian);
            p.add(chunk);
            chunk = new Chunk("Составлено сотрудником: "+employee+"\n\n", russian);
            p.add(chunk);
            iText_xlstopdf.add(p);
            PdfPCell table_cell;
            double value;
            String string_value;
            int start = 4;
            int end = my_worksheet.getLastRowNum();
            for(int i = start; i<end; i++){
                Row row_it = my_worksheet.getRow(i);
                Iterator<Cell> cellIterator = row_it.cellIterator();

                while(cellIterator.hasNext()) {
                    //привязываем клетку из xls
                    Cell cell1 = cellIterator.next();
                    CellType type = cell1.getCellType();
                    switch(type) {
                        case STRING:
                            chunk = new Chunk(cell1.getStringCellValue(),russian);
                            table_cell = new PdfPCell(new Phrase(chunk));
                            my_table.addCell(table_cell);
                            break;
                        case NUMERIC:
                            value = cell1.getNumericCellValue();
                            string_value = String.valueOf(value);
                            chunk = new Chunk(string_value);
                            table_cell = new PdfPCell(new Phrase(chunk));
                            my_table.addCell(table_cell);
                            break;
                        case FORMULA:
                            value = cell1.getNumericCellValue();
                            string_value = String.valueOf(value);
                            chunk = new Chunk(string_value);
                            table_cell = new PdfPCell(new Phrase(chunk));
                            my_table.addCell(table_cell);
                            break;
                    }
                }
            }
            iText_xlstopdf.add(my_table);

            iText_xlstopdf.add( Chunk.NEWLINE );
            iText_xlstopdf.add( Chunk.NEWLINE );

            p.clear();
            chunk = new Chunk("Директор          _____________________Толстой А.К.\n\n", russian);
            p.add(chunk);
            chunk = new Chunk("Главный бухгалтер _____________________Белозерская Л.Е.\n\n", russian);
            p.add(chunk);
            iText_xlstopdf.add(p);
            iText_xlstopdf.close(); // закрываем документ
            input_doc.close(); //закрываем xls

            // открываем файл pdf в отдельном окне
            File output_file = new File(fileName);
            Desktop desk = Desktop.getDesktop();
            desk.open(output_file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    private void saveReestrToExcel(){
        SimpleDateFormat formatForReestr= new SimpleDateFormat("dd.MM.yyyy");
        String date = formatForReestr.format(dcPeriodReestr.getDate());
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Заявка на оплату расходов");
        int column = 0; // текущий столбец
        int row = 0;// текущая строка
        CellRangeAddress region;
        HSSFRow newRow = sheet.createRow(row);
        Cell cell = newRow.createCell(column, CellType.STRING);
        cell.setCellValue("Заявка на оплату расходов");
        region = new CellRangeAddress(0,0,0,1);
        sheet.addMergedRegion(region);

        row++; // row = 1
        newRow = sheet.createRow(row);
        cell = newRow.createCell(column, CellType.STRING);
        String orgz = (String)organizationCombox.getSelectedItem();
        cell.setCellValue("Организация " + orgz);
        region = new CellRangeAddress(1, 1, 0, 1);
        sheet.addMergedRegion(region);

        row++; // row = 2
        newRow = sheet.createRow(row);
        cell = newRow.createCell(column, CellType.STRING);
        cell.setCellValue("Р/с");
        cell = newRow.createCell(column + 1, CellType.STRING);
        cell.setCellValue("Сумма");

        for (int i = 0; i < rasAcc.size(); i++) {
            row++;
            newRow = sheet.createRow(row);
            cell = newRow.createCell(column, CellType.STRING); // расчетный счет
            cell.setCellValue(rasAcc.get(i));
            cell = newRow.createCell(column + 1, CellType.NUMERIC);
            cell.setCellValue(summa.get(i)); //

        }
        row++;
        newRow = sheet.createRow(row);
        cell = newRow.createCell(column, CellType.STRING);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        cell.setCellStyle(style);
        cell.setCellValue("ИТОГО: ");
        cell = newRow.createCell(column+1, CellType.FORMULA);
        String sumFormula1 = "SUM(B4:B"+row+")";
        cell.setCellFormula(sumFormula1);

        String file_path_xls = "C:/test repos/Реестр для организации.xls";
        File file = new File(file_path_xls);
        if(file.exists()) file.delete();
        file.getParentFile().mkdirs();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream input_doc = new FileInputStream(
                    new File(file_path_xls));
            HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_doc);
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
            Document iText_xlstopdf = new Document();
            String file_name_pdf = "C:/test repos/Реестр для организации.pdf";
            PdfWriter.getInstance(iText_xlstopdf, new FileOutputStream(file_name_pdf));
            iText_xlstopdf.open();
            BaseFont bf_russian = BaseFont.createFont("src\\FreeSans.ttf",
                    "CP1251", BaseFont.EMBEDDED);
            com.itextpdf.text.Font russian;
            russian = new com.itextpdf.text.Font(bf_russian, 11);
            Paragraph p = new Paragraph();
            Chunk chunk = new Chunk("Заявка на оплату расходов "+date+"\n\n", russian);
            p.add(chunk);
            chunk = new Chunk("Организация: " +orgz, russian);
            p.add(chunk);
            iText_xlstopdf.add(p);
            PdfPTable my_table = new PdfPTable(2);
            iText_xlstopdf.add( Chunk.NEWLINE );
            PdfPCell table_cell;
            double value;
            String string_value;
            int start = 2;
            int end = my_worksheet.getLastRowNum();
            for(int i = start; i<end; i++){
                Row row_it = my_worksheet.getRow(i);
                Iterator<Cell> cellIterator = row_it.cellIterator();

                while(cellIterator.hasNext()) {
                    //привязываем клетку из xls
                    Cell cell1 = cellIterator.next();
                    CellType type = cell1.getCellType();
                    switch(type) {
                        case STRING:
                            chunk = new Chunk(cell1.getStringCellValue(),russian);
                            table_cell = new PdfPCell(new Phrase(chunk));
                            my_table.addCell(table_cell);
                            break;
                        case NUMERIC:
                            value = cell1.getNumericCellValue();
                            string_value = String.valueOf(value);
                            chunk = new Chunk(string_value);
                            table_cell = new PdfPCell(new Phrase(chunk));
                            my_table.addCell(table_cell);
                            break;
                        case FORMULA:
                            value = cell1.getNumericCellValue();
                            string_value = String.valueOf(value);
                            chunk = new Chunk(string_value);
                            table_cell = new PdfPCell(new Phrase(chunk));
                            my_table.addCell(table_cell);
                            break;
                    }
                }
            }
            p.clear();
            iText_xlstopdf.add(my_table);
            iText_xlstopdf.add( Chunk.NEWLINE );
            chunk = new Chunk("Главный бухгалтер ____________________Белозерская Л.Е.\n\n", russian);
            p.add(chunk);
            iText_xlstopdf.add(p);
            iText_xlstopdf.close(); // закрываем документ
            input_doc.close(); //закрываем xls

            // открываем файл pdf в отдельном окне
            File output_file = new File(file_name_pdf);
            Desktop desk = Desktop.getDesktop();
            desk.open(output_file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    //сделать потом через один метод и скрывать и показывать true-false
    private void openFieldsForEdit(boolean yes){
        buttonSave.setVisible(yes);
        editClientButton.setEnabled(!yes);
//        editClientButton.setVisible(!yes);
        //Перс.данные
        textSurname.setEditable(yes);
        textName.setEditable(yes);
        textPatronymic.setEditable(yes);
        radioButtonF.setVisible(yes);
        radioButtonM.setVisible(yes);
        //textDateBirth.setEditable(yes);
        //panelDateBirth.setEnabled(yes);
        textSNILS.setEditable(yes);
        textTelephone.setEditable(yes);
        textEmail.setEditable(yes);


        //Адрес
        textRegionComboBox.setVisible(yes);
        textStreetComboBox.setVisible(yes);
        textIndexComboBox.setVisible(yes);
        textInhabitedLocalityComboBox.setVisible(yes);
        textDistrictComboBox.setVisible(yes);
        radioButtonM.setVisible(yes);
        radioButtonF.setVisible(yes);
        textHouse.setEditable(yes);
        textFlat.setEditable(yes);

        //Уд.личности
        labelIdDocType.setVisible(yes);
        labelIdDocSeries.setVisible(yes);
        labelIdDocNumber.setVisible(yes);
        labelIdDocGivenBy.setVisible(yes);
        labelIdDocDateStart.setVisible(yes);
        //panelIdDocDateStart.setEnabled(yes);
        labelIdDocStatus.setVisible(yes);
        textIdDocTypeComboBox.setVisible(yes);
        textIdDocSeries.setVisible(yes);
        textIdDocNumber.setVisible(yes);
        textIdDocGivenBy.setVisible(yes);
//        textIdDocDateStart.setVisible(yes);
        textIdDocStatusComboBox.setVisible(yes);

        labelAttDocDateStart.setVisible(yes);
        labelAttDocName.setVisible(yes);
        labelAttDocStatus.setVisible(yes);
        labelAttDocType.setVisible(yes);
        labelAttDocNumber.setVisible(yes);
//        textAttDocDateStart.setVisible(yes);
        //panelAttDocDateStart.setEnabled(yes);
        textAttDocName.setVisible(yes);
        textAttDocStatusComboBox.setVisible(yes);
        textAttDocTypeComboBox.setVisible(yes);
        textAttDocNumber.setVisible(yes);

        //Р/C
//        textDateStartAccount.setEditable(yes);
        //panelOperAccDateStart.setEnabled(yes);
        rbStatusFalse.setEnabled(yes);
        rbStatusTrue.setEnabled(yes);

        //Родственник документ +остальные поля
        lbRelNumber.setVisible(yes);
        lbRelSeries.setVisible(yes);
        lbRelStatus.setVisible(yes);
        lbRelTypeDoc.setVisible(yes);
        lbRelDateStart.setVisible(yes);
        lbRelGivenBy.setVisible(yes);
        textRelNumber.setVisible(yes);
        textRelSeries.setVisible(yes);
        textRelTypeDoc.setVisible(yes);
//        textRelDateStart.setVisible(yes);
        //panelRelDateStart.setEnabled(yes);
        textRelGivenBy.setVisible(yes);
        rbRelativeDocStatus1.setEnabled(yes);
        rbRelativeDocStatus2.setEnabled(yes);
        rbRelativeDocStatus2.setVisible(yes);
        rbRelativeDocStatus1.setVisible(yes);

        textGender.setVisible(!yes);

        textRegion.setVisible(!yes);
        textStreet.setVisible(!yes);
        textIndex.setVisible(!yes);
        textDistrict.setVisible(!yes);
        textInhabitedLocality.setVisible(!yes);

        dcDateBirth.setEnabled(yes);
        dcIdDocDateStart.setVisible(yes);
        dcAttDocDateStart.setVisible(yes);
        dcOperAccDateStart.setEnabled(yes);
        dcRelDateStart.setVisible(yes);
//        panelDateBirth.setEnabled(yes);
//        panelIdDocDateStart.setEnabled(yes);
//        panelAttDocDateStart.setEnabled(yes);
//        panelOperAccDateStart.setEnabled(yes);
//        panelRelDateStart.setEnabled(yes);
//        textDateBirth.setVisible(!yes);
//        textIdDocDateStart.setVisible(!yes);
//        textAttDocDateStart.setVisible(!yes);
//        textDateStartAccount.setVisible(!yes);
//        textRelDateStart.setVisible(!yes);
    }

    private void fillComboBox(){
        getComboBox(textRegionComboBox,tableRegion);
        getComboBox(textStreetComboBox,tableStreet);
        getComboBox(textIndexComboBox,tableIndex);
        getComboBox(textDistrictComboBox,tableDistrict);
        getComboBox(textInhabitedLocalityComboBox,tableLocality);
        getComboBox(textIdDocTypeComboBox,tableIndDoc);
        getComboBox(textAttDocTypeComboBox,tableDoc);
        getComboBox(comboBoxSCCategory, tableSCCategory);
        getComboBoxOrganization(organizationCombox,tableBank, tablePost, tableCash);

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
        return textIndexComboBox.getSelectedItem().toString();
    }
    private int getRegion(){
        return Integer.parseInt(tableRegion.getModel().getValueAt(textRegionComboBox.getSelectedIndex(), 0).toString());
    }
    private int getDistrict(){
        return Integer.parseInt(tableDistrict.getModel().getValueAt(textDistrictComboBox.getSelectedIndex(), 0).toString());
    }
    private int getInhabitedLoc(){
        return Integer.parseInt(tableLocality.getModel().getValueAt(textInhabitedLocalityComboBox.getSelectedIndex(), 0).toString());
    }
    private int getStreet(){
        return Integer.parseInt(tableStreet.getModel().getValueAt(textStreetComboBox.getSelectedIndex(), 0).toString());
    }
    private String getIdDocStatus(){
        return textIdDocStatusComboBox.getSelectedItem().toString();
    }
    private int getTypeIdDoc(){
        return Integer.parseInt(tableIndDoc.getModel().getValueAt(textIdDocTypeComboBox.getSelectedIndex(), 0).toString());
    }
    private String getAttDocStatus(){
        return textAttDocStatusComboBox.getSelectedItem().toString();
    }
    private int getTypeAttDoc(){
        return Integer.parseInt(tableDoc.getModel().getValueAt(textAttDocTypeComboBox.getSelectedIndex(), 0).toString());
    }
    private String getOperAccStatus(){
        if(rbStatusTrue.isSelected())
            return "Действителен";
        else
            return "Недействителен";
    }
    private String getRelativeDocStatus(){
        if(rbRelativeDocStatus1.isSelected())
            return "Действителен";
        else
            return "Недействителен";
    }

    private void listenerRowTableSC(){
        selModelSC.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting() == false) {
                        getRowTableSC();
                        editClientButton.setEnabled(true);
                        deleteClientButton1.setEnabled(true);
//                        deleteClient = new DeleteClient(textPersNum.getText());

                    }
                }
            });
        }
    private void listenerRowTableSC_IdDoc(){
        selModelSC_IdDoc.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    getRowTableSC_IdDoc();
                }
            }
        });
    }
    private void listenerRowTableSC_AttDoc(){
        selModelSC_AttDoc.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    getRowTableSC_AttDoc();
                }
            }
        });
    }
    private void listenerRowTableSC_RelativeDoc(){
        selModelSC_RelativeDoc.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    getRowTableSC_RelativeDocStatus();
                }
            }
        });
    }

    String typeIdDoc;
    String idDocSeries;
    String idDocNumber;
    String idDocGivenBy;
    String idDocDateStart;
    String idDocStatus;
    java.util.Date newIdDocDateStart;

    private void getRowTableSC_IdDoc(){
        int selRow = tableIdDocument.getSelectedRow();
        if(selRow>=0) {
            tableIdDocument.setRowSelectionInterval(0, 0);
            selRowSC_IdDoc = tableIdDocument.getSelectedRow();
            typeIdDoc = tableIdDocument.getModel().getValueAt(selRowSC_IdDoc, 0).toString();
            setSelectedValue(typeIdDoc, textIdDocTypeComboBox);
            idDocSeries = tableIdDocument.getModel().getValueAt(selRowSC_IdDoc, 1).toString();
            textIdDocSeries.setText(idDocSeries);
            idDocNumber = tableIdDocument.getModel().getValueAt(selRowSC_IdDoc, 2).toString();
            textIdDocNumber.setText(idDocNumber);
            idDocGivenBy = tableIdDocument.getModel().getValueAt(selRowSC_IdDoc, 3).toString();
            textIdDocGivenBy.setText(idDocGivenBy);
            idDocDateStart = tableIdDocument.getModel().getValueAt(selRowSC_IdDoc, 4).toString();
            //textIdDocDateStart.setText(idDocDateStart);
            //новая дата
            try {
                newIdDocDateStart = new SimpleDateFormat("dd-MM-yyyy").parse(idDocDateStart.replace(".","-"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dcIdDocDateStart.setDate(newIdDocDateStart);

            idDocStatus = tableIdDocument.getModel().getValueAt(selRowSC_IdDoc, 5).toString();
            setSelectedValue(idDocStatus, textIdDocStatusComboBox);
        }
    }

    String attDocNumber;
    String attDocType;
    String attDocName;
    String attDocDateStart;
    String attDocStatus;
    java.util.Date newAttDocDateStart;

    private void getRowTableSC_AttDoc(){
        int selRow = tableAttDocument.getSelectedRow();
        if(selRow>=0) {
            selRowSC_AttDoc = tableAttDocument.getSelectedRow();
            attDocNumber = tableAttDocument.getModel().getValueAt(selRowSC_AttDoc, 0).toString();
            textAttDocNumber.setText(attDocNumber);
            attDocType = tableAttDocument.getModel().getValueAt(selRowSC_AttDoc, 1).toString();
            setSelectedValue(attDocType, textAttDocTypeComboBox);
            attDocName = tableAttDocument.getModel().getValueAt(selRowSC_AttDoc, 2).toString();
            textAttDocName.setText(attDocName);
            attDocDateStart = tableAttDocument.getModel().getValueAt(selRowSC_AttDoc, 3).toString();
            //textAttDocDateStart.setText(attDocDateStart);
            //новая дата
            try {
                newAttDocDateStart = new SimpleDateFormat("dd-MM-yyyy").parse(attDocDateStart.replace(".","-"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dcAttDocDateStart.setDate(newAttDocDateStart);

            attDocStatus = tableAttDocument.getModel().getValueAt(selRowSC_AttDoc, 4).toString();
            setSelectedValue(attDocStatus, textAttDocStatusComboBox);
        }
    }

    String relativeDocStatus;
    String relativeTypeDoc;
    String relativeSeries;
    String relativeNumber;
    String relativeGivenBy;
    String relativeDateStart;
    java.util.Date newRelDateStart;

    private void getRowTableSC_RelativeDocStatus(){
        int selRow = tableIdDocRelatives.getSelectedRow();
        if(selRow>=0) {
            relativeTypeDoc = tableIdDocRelatives.getModel().getValueAt(0, 0).toString();
            textRelTypeDoc.setText(relativeTypeDoc);
            relativeSeries = tableIdDocRelatives.getModel().getValueAt(0, 1).toString();
            textRelSeries.setText(relativeSeries);
            relativeNumber = tableIdDocRelatives.getModel().getValueAt(0, 2).toString();
            textRelNumber.setText(relativeNumber);
            relativeGivenBy = tableIdDocRelatives.getModel().getValueAt(0, 3).toString();
            textRelGivenBy.setText(relativeGivenBy);
            relativeDateStart = tableIdDocRelatives.getModel().getValueAt(0, 4).toString();
            //textRelDateStart.setText(relativeDateStart);
            //новая дата
            try {
                newRelDateStart = new SimpleDateFormat("dd-MM-yyyy").parse(relativeDateStart.replace(".","-"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dcRelDateStart.setDate(newRelDateStart);

//        selRowSC_RelativeDoc = tableIdDocRelatives.getSelectedRow();
            relativeDocStatus = tableIdDocRelatives.getModel().getValueAt(0, 5).toString();
            if (relativeDocStatus.equals("Действителен")) {
                rbRelativeDocStatus1.setSelected(true);
            } else {
                rbRelativeDocStatus2.setSelected(true);
            }
        }
    }
    //Персональные данные (Вкладка "Личные дела")
    String[] columnsSocialClient = new String[]
            {"Личный счёт", "Фамилия", "Имя", "Отчество", "Пол", "Дата рождения","СНИЛС","Телефон","Email" };
    final Class[] columnClassSocialClient = new Class[] {
            Integer.class, String.class,String.class,
            String.class, String.class, String.class, String.class, String.class, String.class};
    String sqlQuery1="select sc.personalNumber, sc.surname, sc.name, sc.patronymic, g.nameGender, DATE_FORMAT(sc.dateBirth,'%d.%m.%Y'), sc.snils, sc.telephone, sc.email from social_client sc inner join gender g on g.numberGender=sc.numberGender";

    public void initModelSocialClient(String sql){
        mdtmSocialClient.columnsSC=columnsSocialClient;
        mdtmSocialClient.columnClassSC=columnClassSocialClient;
        mdtmSocialClient.sqlQuery=sql;
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
            "where sc.personalNumber=? and idDoc.relativePersonalNumber is null";

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
    String sqlQuery3 = "select attDoc.numberAttachedDocument, typeDoc.nameTypeAttachedDocument, attDoc.nameAttachedDocument, " +
            "DATE_FORMAT(attDoc.dateStartAttachedDocument,'%d.%m.%Y'), attDoc.statusAttachedDocument\n" +
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


    //Расчетный счет (вкладка "Личные дела")
    String[] columnsOperAcc = new String[]
            {"Номер расчетного счета","Дата открытия","Статус"};
    final Class[] columnClassOperAcc = new Class[] {
            Integer.class, String.class, String.class};
    String sqlQueryOperAcc="select oa.numberOperatingAccount, DATE_FORMAT(oa.dateStartAccount,'%d.%m.%Y'), oa.statusOperaingAccount\n" +
            "from operating_account oa inner join social_client sc on oa.personalNumber = sc.personalNumber\n" +
            "where sc.personalNumber=?;";

    private void setButtonGroupStatus(){
        radioButtonStatusAccount = new ButtonGroup();
        radioButtonStatusAccount.add(rbStatusTrue);
        radioButtonStatusAccount.add(rbStatusFalse);
    }

    public void hideOperAcc(){
        lbNumOA.setVisible(false);
        textNumOperatingAccount.setVisible(false);
        lbDateOA.setVisible(false);
//        textDateStartAccount.setVisible(false);
        dcOperAccDateStart.setVisible(false);
        lbStOA.setVisible(false);
        rbStatusFalse.setVisible(false);
        rbStatusTrue.setVisible(false);
        lbBICOA.setVisible(false);
        textBICOrg.setVisible(false);
        lbNameOA.setVisible(false);
        textNameOrg.setVisible(false);
        lbAdOA.setVisible(false);
        textAdressOrg.setVisible(false);
        lbTinOA.setVisible(false);
        textTin.setVisible(false);

        tableIdDocRelatives.setModel(new DefaultTableModel());
    }

    public void initModelOperAcc(String persNum){
        mdtmSocialClient.columnsOpAcc=columnsOperAcc;
        mdtmSocialClient.columnClassOpAcc=columnClassOperAcc;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQueryOperAcc;
        dtmOpAcc=mdtmSocialClient.MyTableModelDocument(4);
        tableOperatingAccount.setModel(dtmOpAcc);
        dtmOpAcc.fireTableDataChanged();
        rowTableOpAcc = tableOperatingAccount.getRowCount();
    }

    String numAcc;
    String dateAcc;
    String statAcc;
    String BICAcc;
    String nameAcc;
    String adrAcc;
    String tinAcc;
    java.util.Date newOperAccDateStart;

    public void setOrganization(){
        String sqlAm="select bo.bic, bo.nameBank, bo.addressBank, bo.tinBank from bank_organization bo " +
                "inner join bank_operating_account boa on bo.bic=boa.bic " +
                "where boa.numberOperatingAccount='"+numAcc+"'";
        String sqlAm2="select co.tinCash, co.nameCash, co.addressCash from cash_organization co " +
                "inner join cash_operating_account coa on co.tinCash=coa.tinCash " +
                "where coa.numberOperatingAccount='"+numAcc+"'";
        String sqlAm3="select po.postCode, po.namePost, po.addressPost, po.tinPost from post_organization po " +
                "inner join post_operating_account poa on po.postCode=poa.postCode " +
                    "where poa.numberOperatingAccount='"+numAcc+"'";
        String [] comp={"", sqlAm, sqlAm2, sqlAm3, BICAcc, nameAcc, adrAcc, tinAcc};
        comp=mdtmSocialClient.setComponentOrganization(comp);
            if(comp[0].equals("1")){
                lbBICOA.setVisible(true);
                lbBICOA.setText("БИК");
                textBICOrg.setVisible(true);
                textBICOrg.setText(comp[4]);
                lbNameOA.setVisible(true);
                textNameOrg.setVisible(true);
                textNameOrg.setText(comp[5]);
                lbAdOA.setVisible(true);
                textAdressOrg.setVisible(true);
                textAdressOrg.setText(comp[6]);
                lbTinOA.setVisible(true);
                textTin.setVisible(true);
                textTin.setText(comp[7]);
            } else if (comp[0].equals("2")){
                lbBICOA.setVisible(true);
                lbBICOA.setText("ИНН");
                textBICOrg.setVisible(true);
                textBICOrg.setText(comp[4]);
                lbNameOA.setVisible(true);
                textNameOrg.setVisible(true);
                textNameOrg.setText(comp[5]);
                lbAdOA.setVisible(true);
                textAdressOrg.setVisible(true);
                textAdressOrg.setText(comp[6]);
            } else if (comp[0].equals("3")){
                lbBICOA.setVisible(true);
                lbBICOA.setText("Почтовый индекс");
                textBICOrg.setVisible(true);
                textBICOrg.setText(comp[4]);
                lbNameOA.setVisible(true);
                textNameOrg.setVisible(true);
                textNameOrg.setText(comp[5]);
                lbAdOA.setVisible(true);
                textAdressOrg.setVisible(true);
                textAdressOrg.setText(comp[6]);
                lbTinOA.setVisible(true);
                textTin.setVisible(true);
                textTin.setText(comp[7]);
            }
    }

    private void listenerRowTableOpAcc(){
        tableOperatingAccount.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false)
                {
                    hideOperAcc();
                    int selRow = tableOperatingAccount.getSelectedRow();
                    if(selRow>=0) {
                        lbNumOA.setVisible(true);
                        textNumOperatingAccount.setVisible(true);
                        lbDateOA.setVisible(true);
//                        textDateStartAccount.setVisible(true);
                        lbStOA.setVisible(true);
                        rbStatusFalse.setVisible(true);
                        rbStatusTrue.setVisible(true);
                        dcOperAccDateStart.setVisible(true);
                        numAcc = tableOperatingAccount.getModel().getValueAt(selRow, 0).toString();
                        textNumOperatingAccount.setText(numAcc);
                        dateAcc = tableOperatingAccount.getModel().getValueAt(selRow, 1).toString();
                        //textDateStartAccount.setText(dateAcc);
                        //новая дата
                        try {
                            newOperAccDateStart = new SimpleDateFormat("dd-MM-yyyy").parse(dateAcc.replace(".","-"));
                        } catch (ParseException ep) {
                            ep.printStackTrace();
                        }
                        dcOperAccDateStart.setDate(newOperAccDateStart);

                        statAcc = tableOperatingAccount.getModel().getValueAt(selRow, 2).toString();
                        if (statAcc.equals("Действителен"))
                            rbStatusTrue.setSelected(true);
                        else
                            rbStatusFalse.setSelected(true);
                        setOrganization();
                    }
                }
            }
        });
    }

    private void listenerRowTableIdDocument(){
        tableIdDocument.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    int selRow = tableIdDocument.getSelectedRow();
                    if(selRow>=0) {
                        deleteIdDocButton.setEnabled(true);
                        selRowIdDoc = tableIdDocument.getSelectedRow();
                    }
                }
            }
        });
    }
    private void listenerRowTableAttDocument(){
        tableAttDocument.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    int selRow = tableAttDocument.getSelectedRow();
                    if(selRow>=0) {
                        deleteAttDocButton.setEnabled(true);
                        selRowAttDoc = tableAttDocument.getSelectedRow();
                    }
                }
            }
        });
    }
    private void listenerRowTableRel(){
        tableRelatives.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    int selRow = tableRelatives.getSelectedRow();
                    if(selRow>=0) {
                        deleteRelativeButton.setEnabled(true);
                        addRelativeIdDoc.setVisible(true);
                        deleteRelativeIdDoc.setVisible(true);
                        selRowRel = tableRelatives.getSelectedRow();
                    }
                }
            }
        });
    }
    private void listenerRowTableRelIdDoc(){
        tableIdDocRelatives.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    int selRow = tableRelatives.getSelectedRow();
                    if(selRow>=0) {
                        deleteRelativeIdDoc.setEnabled(true);
                        selRowRelIdDoc = tableIdDocRelatives.getSelectedRow();
                    }
                    }
            }
        });
    }
    private void listenerRowTableOperAcc(){
        tableOperatingAccount.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    int selRow = tableOperatingAccount.getSelectedRow();
                    if(selRow>=0) {
                        deleteOperAccButton.setEnabled(true);
                        selRowOperAcc = tableOperatingAccount.getSelectedRow();
                    }
                }
            }
        });
    }
    private void listenerRowTableRequest() {
        tableRequest.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (listSelectionEvent.getValueIsAdjusting() == false) {
                    deleteRequestButton.setEnabled(true);
                    selRowRequest = tableRequest.getSelectedRow();
                }
            }
        });
    }
    private void listenerRowTableIncome() {
        tableIncome.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (listSelectionEvent.getValueIsAdjusting() == false) {
                    deleteIncomeButton.setEnabled(true);
                    selRowIncome = tableIncome.getSelectedRow();
                }
            }
        });
    }
    private void listenerRowTableCategoryMeasure() {
        tableCategoryMeasure.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (listSelectionEvent.getValueIsAdjusting() == false ) {
                    if(tableCategoryMeasure.getSelectedRow()>=0)
                    if(tableCategoryMeasure.getModel().getValueAt(tableCategoryMeasure.getSelectedRow(), 7).toString().equalsIgnoreCase("Назначено"))
                        addRequestButton.setEnabled(true);
                    deleteCategoryMeasureButton.setEnabled(true);
                    selRowCatMeasure = tableCategoryMeasure.getSelectedRow();
                }
            }
        });
    }
    private void listenerRowTableHandbook(){
        tableHandbook.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    int selRow = tableHandbook.getSelectedRow();
                    if(selRow>=0) {
                        deleteItemButton.setEnabled(true);
                        selRowItemInHandbook = tableHandbook.getSelectedRow();
                    }
                }
            }
        });
    }

    //Соц.выплаты (Вкладка "Личные дела")
    String [] columnsCategoryMeasure = new String []
            {"Личный номер", "Код категории", "Код меры социальной поддержки", "Категория гражданина", "Название меры социальной поддержки", "Дата начала назначения меры", "Дата окончания назначения меры", "Статус меры", "Сумма"};
    final Class[] columnClassCategoryMeasure = new Class[]
            {Integer.class, Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class, Double.class};
    String sqlQueryCatMeasure = "select sc.personalNumber, cm.codeClientCategory, cm.codeSocialMeasure, scc.nameClientCategory, " +
            "moss.nameSocialMeasure, DATE_FORMAT(cm.dateStartMeasure, '%d.%m.%Y'), DATE_FORMAT(cm.dateEndMeasure, '%d.%m.%Y'), " +
            "cm.statusMeasure, moss.amountOfSupport " +
            "from social_client sc inner join client_measure cm on sc.personalNumber=cm.personalNumber " +
            "inner join measure_of_social_support moss on (cm.codeSocialMeasure=moss.codeSocialMeasure and cm.codeClientCategory=moss.codeClientCategory) " +
            "inner join social_client_category scc on moss.codeClientCategory=scc.codeClientCategory " +
            "where sc.personalNumber=?";

    public void initModelCategoryMeasure(String persNum){
        mdtmSocialClient.columnsCategoryMeasure=columnsCategoryMeasure;
        mdtmSocialClient.columnClassCategoryMeasure=columnClassCategoryMeasure;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQueryCatMeasure;
        dtmCategoryMeasure=mdtmSocialClient.MyTableModelDocument(7);
        tableCategoryMeasure.setModel(dtmCategoryMeasure);
        tableCategoryMeasure.removeColumn(tableCategoryMeasure.getColumnModel().getColumn(8));
        tableCategoryMeasure.removeColumn(tableCategoryMeasure.getColumnModel().getColumn(2));
        tableCategoryMeasure.removeColumn(tableCategoryMeasure.getColumnModel().getColumn(1));
        tableCategoryMeasure.removeColumn(tableCategoryMeasure.getColumnModel().getColumn(0));
        dtmCategoryMeasure.fireTableDataChanged();
    }

    String [] columnsRequest = new String []
            {"Номер заявки", "Дата составления", "Дата начала", "Дата окончания", "Сумма", "Состояние"};
    final Class[] columnClassRequest = new Class[]
            {Integer.class, String.class, String.class, String.class, Double.class, String.class};
    String sqlQueryRequest = "select rfcs.requestNumber, DATE_FORMAT(rfcs.dateOfPreparation, '%d.%m.%Y'), DATE_FORMAT(rfcs.periodFrom, '%d.%m.%Y'), DATE_FORMAT(rfcs.periodTo, '%d.%m.%Y'), rfcs.totalAmount, rfcs.stateRequest " +
            "from request_for_cash_settlement rfcs inner join operating_account oa on rfcs.numberOperatingAccount=oa.numberOperatingAccount " +
            "inner join social_client sc on oa.personalNumber=sc.personalNumber " +
            "where sc.personalNumber=?";

    public void initModelRequest(String persNum){
        mdtmSocialClient.columnsRequest=columnsRequest;
        mdtmSocialClient.columnClassRequest=columnClassRequest;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQueryRequest;
        dtmRequest=mdtmSocialClient.MyTableModelDocument(8);
        tableRequest.setModel(dtmRequest);
        tableRequest.removeColumn(tableRequest.getColumnModel().getColumn(0));
        dtmRequest.fireTableDataChanged();
    }

    String [] columnsPayoffClient = new String []
            {"Дата выплаты", "Сумма", "Номер расчетного счета"};
    final Class[] columnClassPayoffClient = new Class[]
            {String.class, Double.class, Integer.class};
    String sqlQueryPayoffClient = "select DISTINCTROW DATE_FORMAT(p.datePayoff, '%d.%m.%Y'), p.amountPayoff, oa.numberOperatingAccount " +
            "from payoff p inner join request_for_cash_settlement rfcs on p.numberPayoff=rfcs.numberPayoff " +
            "inner join operating_account oa on rfcs.numberOperatingAccount=oa.numberOperatingAccount " +
            "inner join social_client sc on oa.personalNumber=sc.personalNumber " +
            "where sc.personalNumber=?";

    public void initModelPayoffClient(String persNum){
        mdtmSocialClient.columnsPayoff=columnsPayoffClient;
        mdtmSocialClient.columnClassPayoff=columnClassPayoffClient;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQueryPayoffClient;
        dtmPayoff=mdtmSocialClient.MyTableModelDocument(9);
        tablePayoffClient.setModel(dtmPayoff);
        dtmPayoff.fireTableDataChanged();
    }

    String [] columnsPersAcc = new String []
            {"Период", "Входное сальдо", "Начислено", "Оплачено", "Исходящее сальдо"};
    final Class[] columnClassPersAcc = new Class[]
            {String.class, Double.class, Double.class, Double.class, Double.class};
    String sqlQueryPersAcc = "select DISTINCTROW DATE_FORMAT(pa.periodPayoff, '%m.%Y'), pa.inputBalance, pa.accrued, pa.paid, pa.outputBalance " +
            "from personal_account pa inner join payoff p on pa.numberPersonalAccount=p.numberPersonalAccount " +
            "inner join request_for_cash_settlement rfcs on p.numberPayoff=rfcs.numberPayoff " +
            "inner join operating_account oa on rfcs.numberOperatingAccount=oa.numberOperatingAccount " +
            "inner join social_client sc on oa.personalNumber=sc.personalNumber " +
            "where sc.personalNumber=?";

    public void initModelPersAcc(String persNum){
        mdtmSocialClient.columnsPersonalAccount=columnsPersAcc;
        mdtmSocialClient.columnClassPersonalAccount=columnClassPersAcc;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQueryPersAcc;
        dtmPersAcc=mdtmSocialClient.MyTableModelDocument(10);
        tablePA.setModel(dtmPersAcc);
        dtmPersAcc.fireTableDataChanged();
    }

    //Родственники (Вкладка "Личные дела")
    String[] columnsRelatives = new String[]
            {"Номер","Фамилия","Имя","Отчество","Дата рождения","Степень родства"};
    final Class[] columnClassRelatives = new Class[]{
            Integer.class, String.class, String.class, String.class, String.class, String.class};
    String sqlQueryRelatives = "select r.relativePersonalNumber, r.surnameRelative, r.nameRelative, r.patronymicRelative, DATE_FORMAT(r.dateBirthRelative,'%d.%m.%Y'), rd.nameRelationDegree " +
            "from relatives r inner join relation_degree rd on r.numberRelationDegree = rd.numberRelationDegree \n" +
            "inner join social_client sc on r.personalNumber = sc.personalNumber\n" +
            "where sc.personalNumber=?";

    public void initModelRelatives(String persNum){
        mdtmSocialClient.columnsRelatives=columnsRelatives;
        mdtmSocialClient.columnClassRelatives=columnClassRelatives;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQueryRelatives;
        dtmRelatives=mdtmSocialClient.MyTableModelDocument(5);
        tableRelatives.setModel(dtmRelatives);
        tableRelatives.removeColumn(tableRelatives.getColumnModel().getColumn(0));
        dtmRelatives.fireTableDataChanged();
        rowTableRelatives = tableRelatives.getRowCount();
    }

    String sqlQueryIdDocRelatives = "select typeDoc.nameTypeIdDocument, idDoc.docSeries, idDoc.docNumber, idDoc.givenBy, DATE_FORMAT(idDoc.dateStartIdDocument,'%d.%m.%Y'), idDoc.statusIdDocument\n" +
            "from identification_document idDoc inner join relatives r on idDoc.relativePersonalNumber = r.relativePersonalNumber\n" +
            "inner join social_client sc on r.personalNumber = sc.personalNumber\n" +
            "inner join type_identification_document typeDoc on idDoc.numberTypeIdDocument = typeDoc.numberTypeIdDocument\n" +
            "where sc.personalNumber=? and r.relativePersonalNumber=?";

    public void initModelIdDocRelatives(String idRel){
        mdtmSocialClient.columnsIdDocRelatives=columnsIdDocument;
        mdtmSocialClient.columnClassIdDocRelatives=columnClassIdDocument;
        mdtmSocialClient.cat = persNum;
        mdtmSocialClient.ben = idRel;
        mdtmSocialClient.sqlPreparedStatement=sqlQueryIdDocRelatives;
        dtmIdDocRelatives=mdtmSocialClient.MyTableModelPayoff(3);
        tableIdDocRelatives.setModel(dtmIdDocRelatives);
        dtmIdDocRelatives.fireTableDataChanged();
    }

    private void listenerRowTableRelatives(){
        tableRelatives.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false)
                {
                    int selRow = tableRelatives.getSelectedRow();
                    if(selRow>=0) {
                        String idRel=tableRelatives.getModel().getValueAt(selRow,0).toString();
                        addRelativeIdDoc.setEnabled(true);
                        initModelIdDocRelatives(idRel);
                    }
                }
            }
        });
    }

    //Доход (Вкладка "Личные дела")
    String[] columnsIncome = new String[]
            {"Номер","Период","Вид","Сумма"};
    final Class[] columnClassIncome = new Class[]{
           Integer.class, String.class, String.class, Double.class};

    String sqlQueryIncome = "select i.IdIncome, DATE_FORMAT(i.periodIncome,'%m.%Y'), ti.nameTypeIncone, i.amount " +
            "from income i inner join type_income ti on i.numberTypeIncone = ti.numberTypeIncone \n" +
            "inner join social_client sc on i.personalNumber = sc.personalNumber\n" +
            "where sc.personalNumber=?";

    public void initModelIncome(String persNum){
        mdtmSocialClient.columnsIncome=columnsIncome;
        mdtmSocialClient.columnClassIncome=columnClassIncome;
        mdtmSocialClient.persNum = Integer.parseInt(persNum);
        mdtmSocialClient.sqlPreparedStatement=sqlQueryIncome;
        dtmIncome=mdtmSocialClient.MyTableModelDocument(6);
        tableIncome.setModel(dtmIncome);
        tableIncome.removeColumn(tableIncome.getColumnModel().getColumn(0));
        dtmIncome.fireTableDataChanged();
        String sqlQueryCount = "select count(DISTINCT i.periodIncome)\n" +
                "from income i inner join social_client sc on i.personalNumber=sc.personalNumber\n" +
                "where sc.personalNumber=?";
        int row=mdtmSocialClient.getMonthIncome(sqlQueryCount);
        double sum=0;
        for (int i=0; i<tableIncome.getRowCount(); i++){
            sum+=Double.valueOf(tableIncome.getModel().getValueAt(i,3).toString());
        }
        if(row!=0)
            lbSum.setText(String.valueOf(sum/row/(tableRelatives.getRowCount()+1)));
        else
            lbSum.setText("");
    }


    // Оборотно-сальдовая ведомосоть (Вкладка 'Отчёты')
    String[] columnsSaldoReport = new String[]
            {"Личный счет","Фамилия","Имя","Отчество", "Входное сальдо", "Начислено"};
    final Class[] columnClassSaldoReport =  new Class[] {
            Integer.class, String.class,String.class,String.class, Integer.class, Integer.class
    };

    String dateStart="";
    String dateEnd="";
    private void getPeriodOSV(int mouth){
        String year=spinnerYear.getValue().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, mouth);
        dateStart=formatForSql.format(calendar.getTime());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        dateEnd=formatForSql.format(calendar.getTime());
    }

    public void initModelSaldoReport() {
        int numMounth=periodCombox.getSelectedIndex()+1;
        getPeriodOSV(numMounth);

        String[] columnsSaldoReport = new String[]
                {"Личный счет","Фамилия","Имя","Отчество", "Входное сальдо", "Начислено", "Оплачено", "Исходящее сальдо"};
        final Class[] columnClassSaldoReport =  new Class[] {
                Integer.class, String.class,String.class,String.class, Double.class, Double.class, Double.class, Double.class
        };

        //String sqlQueryFirst = "select cm.personalNumber from client_measure cm where codeClientCategory=?";
        String sqlQuerySaldoReport ="select sc.personalNumber, sc.surname, sc.name,sc.patronymic, pa.inputBalance, sum(rfcs.totalAmount), sum(rfcs.totalAmount), pa.outputBalance\n" +
                "from personal_account pa inner join payoff p on pa.numberPersonalAccount = p.numberPersonalAccount\n" +
                "inner join request_for_cash_settlement rfcs on p.numberPayoff = rfcs.numberPayoff\n" +
                "inner join operating_account oa on rfcs.numberOperatingAccount = oa.numberOperatingAccount\n" +
                "inner join social_client sc on oa.personalNumber = sc.personalNumber\n" +
                "where pa.periodPayoff between '"+dateStart+"' and '"+dateEnd+"' and rfcs.codeClientCategory=? " +
                "group by sc.personalNumber " +
                "order by sc.personalNumber";
        //или sum(rfcs.totalAmount) и group by sc.personalNumber, если клиент должен показываться только 1 раз с итоговой суммой за месяц

        int categoryCode = Integer.parseInt(tableSCCategory.getModel().getValueAt(categoryCombox.getSelectedIndex(), 0).toString());
        mdtmSocialClient.columnsSalRep = columnsSaldoReport;
        mdtmSocialClient.columnClassSalRep = columnClassSaldoReport;
        //mdtmSocialClient.sqlQuery = sqlQueryFirst;
        mdtmSocialClient.sqlPreparedStatement=sqlQuerySaldoReport;
        dtmSaldoReport = mdtmSocialClient.MyTableModelReports(categoryCode);
        tableSaldoReport.setModel(dtmSaldoReport);
        dtmSocialClient.fireTableDataChanged();
        rowTableSC = tableSocialClients.getRowCount();

        // для отправки данных в эксель
        persAc = mdtmSocialClient.persAcc;
        s_name = mdtmSocialClient.surname;
        my_name = mdtmSocialClient.name;
        p_mic = mdtmSocialClient.patronimyc;
        in_saldo = mdtmSocialClient.inSaldo;
        acc = mdtmSocialClient.accrued;
        paid = mdtmSocialClient.paidOff;
        out_saldo = mdtmSocialClient.outSaldo;
    }

    private void getPeriodReestr(Date dateReestr){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateReestr);
        calendar.set(Calendar.DATE, 1);
        dateStart=formatForSql.format(calendar.getTime());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        dateEnd=formatForSql.format(calendar.getTime());
    }

    public void initModelReestrReport() {
        getPeriodReestr(dcPeriodReestr.getDate());
        String org = organizationCombox.getItemAt(organizationCombox.getSelectedIndex()).toString();
        int numLine = org.indexOf("|");
        String numOrg=org.substring(0, numLine-1);

        String[] columnsReestr = new String[]{"Расчетный счет","Сумма"};
        final Class[] columnClassReestr =  new Class[]{String.class, Double.class};

        String sqlQueryReestr1 ="select distinct(oa.numberOperatingAccount), sum(rfcs.totalAmount)\n" +
                "from operating_account oa inner join bank_operating_account boa on oa.numberOperatingAccount = boa.numberOperatingAccount\n" +
                "inner join request_for_cash_settlement rfcs on rfcs.numberOperatingAccount = oa.numberOperatingAccount\n" +
                "inner join payoff p on rfcs.numberPayoff = p.numberPayoff\n" +
                "where p.datePayoff between '"+dateStart+"' and '"+dateEnd+"' and boa.bic='"+numOrg+"'" +
                "group by oa.numberOperatingAccount";
        String sqlQueryReestr2 ="select distinct(oa.numberOperatingAccount), sum(rfcs.totalAmount)\n" +
                "from operating_account oa inner join post_operating_account poa on oa.numberOperatingAccount = poa.numberOperatingAccount\n" +
                "inner join request_for_cash_settlement rfcs on rfcs.numberOperatingAccount = oa.numberOperatingAccount\n" +
                "inner join payoff p on rfcs.numberPayoff = p.numberPayoff\n" +
                "where p.datePayoff between '"+dateStart+"' and '"+dateEnd+"' and poa.postCode='"+numOrg+"'" +
                "group by oa.numberOperatingAccount";
        String sqlQueryReestr3 ="select distinct(oa.numberOperatingAccount), sum(rfcs.totalAmount)\n" +
                "from operating_account oa inner join cash_operating_account coa on oa.numberOperatingAccount = coa.numberOperatingAccount\n" +
                "inner join request_for_cash_settlement rfcs on rfcs.numberOperatingAccount = oa.numberOperatingAccount\n" +
                "inner join payoff p on rfcs.numberPayoff = p.numberPayoff\n" +
                "where p.datePayoff between '"+dateStart+"' and '"+dateEnd+"' and coa.tinCash='"+numOrg+"'" +
                "group by oa.numberOperatingAccount";

        mdtmSocialClient.columnsReestr = columnsReestr;
        mdtmSocialClient.columnClassReestr = columnClassReestr;
        dtmSaldoReport = mdtmSocialClient.MyTableModelReestr(sqlQueryReestr1, sqlQueryReestr2, sqlQueryReestr3);
        reestrTable.setModel(dtmSaldoReport);
        dtmSocialClient.fireTableDataChanged();
        rowTableSC = tableSocialClients.getRowCount();

        // для отправки данных в эксель
        rasAcc = mdtmSocialClient.rasAcc;
        summa = mdtmSocialClient.summa;

    }

    // Начисления (Вкладка 'Начисления и выплаты')
    String[] columnsChargeReq = new String[] {"Номер личного счета", "Фамилия", "Имя", "Отчество", "Код категории", "Категория гражданина", "Код меры","Мера социальной поддержки", "Сумма", "Статус меры", "Срок выплат"};
    final Class[] columnClassChargeReq = new Class[] {Integer.class, String.class, String.class, String.class, Integer.class, String.class, Integer.class, String.class, Double.class, String.class, Integer.class};
    String sqlQueryChargeReq="select sc.personalNumber, sc.surname, sc.name, sc.patronymic, scc.codeClientCategory, scc.nameClientCategory, moss.codeSocialMeasure, moss.nameSocialMeasure, moss.amountOfSupport, cm.statusMeasure, moss.termOfMeasure " +
            "from social_client sc inner join client_measure cm on sc.personalNumber=cm.personalNumber " +
            "inner join measure_of_social_support moss on (cm.codeSocialMeasure=moss.codeSocialMeasure and cm.codeClientCategory=moss.codeClientCategory) " +
            "inner join social_client_category scc on moss.codeClientCategory=scc.codeClientCategory " +
            "where scc.nameClientCategory=? and moss.nameSocialMeasure=? and cm.statusMeasure='Назначено' and '"+formatForSql.format(new Date())+"' between cm.dateStartMeasure and cm.dateEndMeasure";

    public void initModelClientCategoryMeasure() {
        mdtmSocialClient.columnsChargeReq = columnsChargeReq;
        mdtmSocialClient.columnClassChargeReq = columnClassChargeReq;
        mdtmSocialClient.cat = comboBoxSCCategory.getItemAt(comboBoxSCCategory.getSelectedIndex()).toString();
        mdtmSocialClient.ben = comboBoxMeasure.getItemAt(comboBoxMeasure.getSelectedIndex()).toString();
        mdtmSocialClient.sqlPreparedStatement=sqlQueryChargeReq;
        dtmClientCategoryMeasure = mdtmSocialClient.MyTableModelPayoff(1);
        tableChargeRequest.setModel(dtmClientCategoryMeasure);
        tableChargeRequest.removeColumn(tableChargeRequest.getColumnModel().getColumn(10));
        tableChargeRequest.removeColumn(tableChargeRequest.getColumnModel().getColumn(8));
        tableChargeRequest.removeColumn(tableChargeRequest.getColumnModel().getColumn(6));
        tableChargeRequest.removeColumn(tableChargeRequest.getColumnModel().getColumn(4));
        dtmClientCategoryMeasure.fireTableDataChanged();
    }

    String[] columnsFindRequest = new String[] {"Номер личного счета", "Фамилия", "Имя", "Отчество", "Номер заявки", "Дата начала", "Дата окончания", "Сумма", "Состояние"};
    final Class[] columnClassFindRequest = new Class[] {Integer.class, String.class, String.class, String.class, Integer.class, String.class, String.class, Double.class, String.class};
    String sqlQueryFindRequest="select sc.personalNumber, sc.surname, sc.name, sc.patronymic, rfcs.requestNumber, DATE_FORMAT(rfcs.periodFrom, '%d.%m.%Y'), DATE_FORMAT(rfcs.periodTo, '%d.%m.%Y'), rfcs.totalAmount, rfcs.stateRequest " +
            "from social_client sc inner join operating_account oa on sc.personalNumber=oa.personalNumber " +
            "inner join request_for_cash_settlement rfcs on oa.numberOperatingAccount=rfcs.numberOperatingAccount " +
            "where oa.statusOperaingAccount='Действителен' and rfcs.stateRequest='Назначено' and ? between rfcs.periodFrom and rfcs.periodTo";

    public void initModelFindRequest(){
        mdtmSocialClient.columnsFindRequest = columnsFindRequest;
        mdtmSocialClient.columnClassFindRequest = columnClassFindRequest;
        mdtmSocialClient.sqlPreparedStatement=sqlQueryFindRequest;
        dtmFindRequest = mdtmSocialClient.MyTableModelFindRequest(formatForSql.format(dcPeriodPayoff.getDate()));
        tableFindRequest.setModel(dtmFindRequest);
        dtmFindRequest.fireTableDataChanged();
    }

    public void setPayoff(){
        String sqlQueryPayoff1="select sc.personalNumber, sum(rfcs.totalAmount), rfcs.periodFrom, rfcs.periodTo " +
                "from social_client sc inner join operating_account oa on sc.personalNumber=oa.personalNumber " +
                "inner join request_for_cash_settlement rfcs on oa.numberOperatingAccount=rfcs.numberOperatingAccount " +
                "where oa.statusOperaingAccount='Действителен' and rfcs.stateRequest='Назначено' and '"+formatForSql.format(dcPeriodPayoff.getDate())+"' between rfcs.periodFrom and rfcs.periodTo " +
                "group by sc.personalNumber";

        String sqlQueryPayoff2="select distinct(p.numberPayoff), p.amountPayoff, p.numberPersonalAccount from payoff p inner join request_for_cash_settlement rfcs on p.numberPayoff=rfcs.numberPayoff " +
                "inner join operating_account oa on rfcs.numberOperatingAccount=oa.numberOperatingAccount " +
                "inner join social_client sc on oa.personalNumber=sc.personalNumber " +
                "where sc.personalNumber=? and p.datePayoff between ? and ?";
        String sqlQueryPayoff3="update personal_account set accrued=?, paid=? where numberPersonalAccount=?";

        String sqlQueryPayoff4="select max(numberPersonalAccount) as maxNumPA from personal_account";
        String sqlQueryPayoff5="insert into personal_account (numberPersonalAccount, periodPayoff, inputBalance, accrued, paid, outputBalance) " +
                "values (?, '"+formatForSql.format(dcPeriodPayoff.getDate())+"', 0, ?, ?, 0)";
        String sqlQueryPayoff6="insert into payoff (amountPayoff, datePayoff, numberPersonalAccount) " +
                "values (?, '"+formatForSql.format(dcPeriodPayoff.getDate())+"', ?)";

        String [] allSqlQuery={sqlQueryPayoff1, sqlQueryPayoff2, sqlQueryPayoff3, sqlQueryPayoff4, sqlQueryPayoff5, sqlQueryPayoff6};
        int rez=mdtmSocialClient.setClientPayoff(allSqlQuery);
        if(rez==1)
            JOptionPane.showMessageDialog(SocialProtectionForm.this, "Выплаты произведены", "Окно сообщения", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(SocialProtectionForm.this, "Ошибка при произведении выплаты", "Окно сообщения", JOptionPane.INFORMATION_MESSAGE);
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
    java.util.Date newDateBirth;

    String region;
    String district;
    String inhabitedLocality;
    String street;
    String house;
    String flat;
    String index;

    //Отображение даннных во вкладках Перс.данные, Адрес, Уд. личнсоти, Доп.документы
    public void getRowTableSC(){
        int selRow = tableSocialClients.getSelectedRow();
        if(selRow>=0) {
            selRowSC = tableSocialClients.getSelectedRow();
            persNum = tableSocialClients.getModel().getValueAt(selRowSC, 0).toString();
            textPersNum.setText(persNum);
            surname = tableSocialClients.getModel().getValueAt(selRowSC, 1).toString();
            textSurname.setText(surname);
            name = tableSocialClients.getModel().getValueAt(selRowSC, 2).toString();
            textName.setText(name);
            patronymic = tableSocialClients.getModel().getValueAt(selRowSC, 3).toString();
            textPatronymic.setText(patronymic);
            gender = tableSocialClients.getModel().getValueAt(selRowSC, 4).toString();
            textGender.setText(gender);
            setSelectedGender(gender);
            dateBirth = tableSocialClients.getModel().getValueAt(selRowSC, 5).toString();
//            textDateBirth.setText(dateBirth);
            //новая дата
            try {
                newDateBirth = new SimpleDateFormat("dd-MM-yyyy").parse(dateBirth.replace(".","-"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dcDateBirth.setDate(newDateBirth);

            snils = tableSocialClients.getModel().getValueAt(selRowSC, 6).toString();
            setMask(textSNILS,2);
            textSNILS.setText(snils);
            telephone = tableSocialClients.getModel().getValueAt(selRowSC, 7).toString();
            setMask(textTelephone,1);
            textTelephone.setText(telephone);
            email = tableSocialClients.getModel().getValueAt(selRowSC, 8).toString();
            textEmail.setText(email);

            initModelAddress(persNum);

            region = tableAddress.getModel().getValueAt(0, 1).toString();
            textRegion.setText(region);
            setSelectedValue(region, textRegionComboBox);
            district = tableAddress.getModel().getValueAt(0, 2).toString();
            textDistrict.setText(district);
            setSelectedValue(district, textDistrictComboBox);
            inhabitedLocality = tableAddress.getModel().getValueAt(0, 3).toString();
            textInhabitedLocality.setText(inhabitedLocality);
            setSelectedValue(inhabitedLocality, textInhabitedLocalityComboBox);
            street = tableAddress.getModel().getValueAt(0, 4).toString();
            textStreet.setText(street);
            setSelectedValue(street, textStreetComboBox);
            house = tableAddress.getModel().getValueAt(0, 5).toString();
            textHouse.setText(house);
            flat = tableAddress.getModel().getValueAt(0, 6).toString();
            textFlat.setText(flat);
            index = tableAddress.getModel().getValueAt(0, 0).toString();
            textIndex.setText(index);
            setSelectedValue(index, textIndexComboBox);

            initModelIdDocument(persNum);
            initModelAttDocument(persNum);
            initModelOperAcc(persNum);
            hideOperAcc();
            initModelRelatives(persNum);
            initModelCategoryMeasure(persNum);
            initModelRequest(persNum);
            initModelPayoffClient(persNum);
            initModelPersAcc(persNum);
            initModelIncome(persNum);

            addRequestButton.setEnabled(false);
            deleteRequestButton.setEnabled(false);
            addRelativeIdDoc.setEnabled(false);
            deleteRelativeIdDoc.setEnabled(false);
            deleteIdDocButton.setEnabled(false);
            deleteAttDocButton.setEnabled(false);
            deleteOperAccButton.setEnabled(false);
            deleteCategoryMeasureButton.setEnabled(false);
            deleteRequestButton.setEnabled(false);
            //соц выплаты другие кнопки
            deleteRelativeButton.setEnabled(false);
            addRelativeIdDoc.setVisible(false);
            deleteRelativeIdDoc.setVisible(false);

//        deleteCategoryMeasureButton.setVisible(false);
            selRowCatMeasure = -2;
            selRowRequest = -2;
        }
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
        cb.removeAllItems();
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
        final   String[]   nodes = new String[]  {"Пособия","Адреса","Личная карточка","Организации","Прочие"};
        final   String[][] leafs = new String[][]{{"Категории гражданина", "Меры социальной поддержки", "Законы", "Статьи", "Правила расчёта"},
                {"Индексы","Регионы","Районы","Населённые пункты","Улицы"},
                {"Родственные отношения","Виды доходов"},
                {"Банки","Почты","Кассы"},
                {"Документы удостоверения","Документы","Сотрудники","Отдел","Должность"}};

        // Создание древовидной структуры
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(ROOT);
        // Ветви первого уровня
        DefaultMutableTreeNode benefit = new DefaultMutableTreeNode(nodes[0]);
        DefaultMutableTreeNode address = new DefaultMutableTreeNode(nodes[1]);
        DefaultMutableTreeNode personalCard = new DefaultMutableTreeNode(nodes[2]);
        DefaultMutableTreeNode organization = new DefaultMutableTreeNode(nodes[3]);
        DefaultMutableTreeNode other = new DefaultMutableTreeNode(nodes[4]);
        // Добавление ветвей к корневой записи
        root.add(benefit);
        root.add(address);
        root.add(personalCard);
        root.add(organization);
        root.add(other);
        // Добавление листьев
        for ( int i = 0; i < leafs[0].length; i++)
            benefit.add(new DefaultMutableTreeNode(leafs[0][i], false));
        for ( int i = 0; i < leafs[1].length; i++)
            address.add(new DefaultMutableTreeNode(leafs[1][i], false));
        for ( int i = 0; i < leafs[2].length; i++)
            personalCard.add(new DefaultMutableTreeNode(leafs[2][i], false));
        for ( int i = 0; i < leafs[3].length; i++)
            organization.add(new DefaultMutableTreeNode(leafs[3][i], false));
        for ( int i = 0; i < leafs[4].length; i++)
            other.add(new DefaultMutableTreeNode(leafs[4][i], false));
        return root;
    }

    String[] columnsIndex = new String[] {"Наименование"};
    final Class[] columnClassIndex = new Class[] {String.class};
    String[] columnsHandbook = new String[] {"№", "Наименование"};
    String[] columnsClientCategory = new String[] {"Код категории", "Наименование"};
    final Class[] columnClassHandbook = new Class[] { Integer.class, String.class};
    String[] columnsMeasure = new String[] {"Код меры социальной поддержки", "Код категории гражданина", "Наименование", "Сумма", "Код статьи", "Код закона"};
    final Class[] columnClassMeasure = new Class[] { Integer.class, Integer.class, String.class, Double.class, Integer.class, Integer.class};
    String[] columnsLaw = new String[] {"Код закона", "№ закона", "Наименование", "Дата утверждения"};
    final Class[] columnClassLaw = new Class[] {Integer.class, Integer.class, String.class, String.class};
    String[] columnsArticle = new String[] {"Код статьи", "Код закона", "№ статьи", "Название статьи", "Дата принятия"};
    final Class[] columnClassArticle = new Class[] {Integer.class, Integer.class, Double.class, String.class, String.class};

    String[] columnsEmployee = new String[] {"Табельный номер", "Фамилия", "Имя", "Отчество", "Код должности", "Номер отдела"};
    final Class[] columnClassEmployee = new Class[] {Integer.class, String.class, String.class, String.class, Integer.class, Integer.class};

    String[] columnsBank = new String[] {"БИК", "Адрес", "Наименование", "ИНН"};
    final Class[] columnClassBank = new Class[] {String.class, String.class, String.class, String.class};
    String[] columnsPost = new String[] {"Индекс почты", "ИНН", "Адрес", "Наименование"};
    final Class[] columnClassPost = new Class[] {Integer.class, String.class, String.class, String.class};
    String[] columnsCash = new String[] {"ИНН кассы", "Адрес", "Наименование"};
    final Class[] columnClassCash = new Class[] {String.class, String.class, String.class};


    String sqlQuerySCCat="select * from social_client_category";
    String sqlQueryMeasure="select * from measure_of_social_support";
    String sqlQueryLaw="select codeOfLaw, numberLaw, nameLaw, DATE_FORMAT(certifiedDateLaw, '%d.%m.%Y') from law";
    String sqlQueryArt="select articleCode, codeOfLaw, articleNumber, nameArticle, DATE_FORMAT(certifiedDateArticle, '%d.%m.%Y') from article";

    String sqlQueryInd="select * from index_address";
    String sqlQueryReg="select * from region_address";
    String sqlQueryDist="select * from district_address";
    String sqlQueryLoc="select * from inhabited_locality_address";
    String sqlQueryStr="select * from street_address";

    String sqlQueryRel="select * from relation_degree";
    String sqlQueryInc="select * from type_income";
    String sqlQueryIdDoc="select * from type_identification_document";
    String sqlQueryDoc="select * from type_attached_document";

    String sqlQueryBank="select * from bank_organization";
    String sqlQueryPost="select * from post_organization";
    String sqlQueryCash="select * from cash_organization";

    String sqlQueryEmp="select * from employee";
    String sqlQueryDep="select * from department_employee";
    String sqlQueryJob="select * from job_position_employee";

    //запонение справочников (Вкладка 'НСИ')
    public void initModelHandbook(){
        mdtmSocialClient.columnsSCCategory = columnsClientCategory;
        mdtmSocialClient.columnClassSCCategory = columnClassHandbook;
        mdtmSocialClient.sqlQuery = sqlQuerySCCat;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(1);
        tableSCCategory.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsMeasure = columnsMeasure;
        mdtmSocialClient.columnClassMeasure = columnClassMeasure;
        mdtmSocialClient.sqlQuery = sqlQueryMeasure;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(2);
        tableMeasure.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsLaw = columnsLaw;
        mdtmSocialClient.columnClassLaw = columnClassLaw;
        mdtmSocialClient.sqlQuery = sqlQueryLaw;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(3);
        tableLaw.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsArticle = columnsArticle;
        mdtmSocialClient.columnClassArticle = columnClassArticle;
        mdtmSocialClient.sqlQuery = sqlQueryArt;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(4);
        tableArticle.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsIndex = columnsIndex;
        mdtmSocialClient.columnClassIndex = columnClassIndex;
            mdtmSocialClient.sqlQuery = sqlQueryInd;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(5);
            tableIndex.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsRegion = columnsHandbook;
            mdtmSocialClient.columnClassRegion = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryReg;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(6);
            tableRegion.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsDistrict = columnsHandbook;
            mdtmSocialClient.columnClassDistrict = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryDist;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(7);
            tableDistrict.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsLocality = columnsHandbook;
            mdtmSocialClient.columnClassLocality = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryLoc;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(8);
            tableLocality.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsStreet = columnsHandbook;
            mdtmSocialClient.columnClassStreet = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryStr;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(9);
            tableStreet.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsRelation = columnsHandbook;
            mdtmSocialClient.columnClassRelation = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryRel;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(10);
            tableRelation.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsTypeIncome = columnsHandbook;
            mdtmSocialClient.columnClassTypeIncome = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryInc;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(11);
            tableTypeIncome.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsTypeIndDoc = columnsHandbook;
            mdtmSocialClient.columnClassTypeIndDoc = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryIdDoc;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(12);
            tableIndDoc.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

            mdtmSocialClient.columnsTypeDoc = columnsHandbook;
            mdtmSocialClient.columnClassTypeDoc = columnClassHandbook;
            mdtmSocialClient.sqlQuery = sqlQueryDoc;
            dtmHandbook = mdtmSocialClient.MyTableModelHandbook(13);
            tableDoc.setModel(dtmHandbook);
            dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsEmployee = columnsEmployee;
        mdtmSocialClient.columnClassEmployee = columnClassEmployee;
        mdtmSocialClient.sqlQuery = sqlQueryEmp;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(14);
        tableEmployee.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsDepartment = columnsHandbook;
        mdtmSocialClient.columnClassDepartment = columnClassHandbook;
        mdtmSocialClient.sqlQuery = sqlQueryDep;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(15);
        tableDepartament.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsJob = columnsHandbook;
        mdtmSocialClient.columnClassJob = columnClassHandbook;
        mdtmSocialClient.sqlQuery = sqlQueryJob;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(16);
        tableJob.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsBank = columnsBank;
        mdtmSocialClient.columnClassBank = columnClassBank;
        mdtmSocialClient.sqlQuery = sqlQueryBank;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(17);
        tableBank.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsPost = columnsPost;
        mdtmSocialClient.columnClassPost = columnClassPost;
        mdtmSocialClient.sqlQuery = sqlQueryPost;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(18);
        tablePost.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

        mdtmSocialClient.columnsCash = columnsCash;
        mdtmSocialClient.columnClassCash = columnClassCash;
        mdtmSocialClient.sqlQuery = sqlQueryCash;
        dtmHandbook = mdtmSocialClient.MyTableModelHandbook(19);
        tableCash.setModel(dtmHandbook);
        dtmHandbook.fireTableDataChanged();

    }

    private void updateHandbook(){
        TreePath[] paths=glossaryJTree.getSelectionPaths();
        treeSelect=paths[0].getLastPathComponent().toString();

        if ("Категории гражданина".equals(treeSelect)) {
            tableHandbook.setModel(tableSCCategory.getModel());
            numHand=1;
        }else
        if ("Меры социальной поддержки".equals(treeSelect)) {
            tableHandbook.setModel(tableMeasure.getModel());
            tableHandbook.removeColumn(tableHandbook.getColumnModel().getColumn(5));
            tableHandbook.removeColumn(tableHandbook.getColumnModel().getColumn(4));
            tableHandbook.removeColumn(tableHandbook.getColumnModel().getColumn(1));
            numHand=2;
        }else
        if ("Законы".equals(treeSelect)) {
            tableHandbook.setModel(tableLaw.getModel());
            numHand=3;
        }else
        if ("Статьи".equals(treeSelect)) {
            tableHandbook.setModel(tableArticle.getModel());
            tableHandbook.removeColumn(tableHandbook.getColumnModel().getColumn(1));
            numHand=4;
        }else
        if ("Индексы".equals(treeSelect)) {
            tableHandbook.setModel(tableIndex.getModel());
            numHand=5;
        }else
        if ("Регионы".equals(treeSelect)) {
            tableHandbook.setModel(tableRegion.getModel());
            numHand=6;
        }else
        if ("Районы".equals(treeSelect)) {
            tableHandbook.setModel(tableDistrict.getModel());
            numHand=7;
        }else
        if ("Населённые пункты".equals(treeSelect)) {
            tableHandbook.setModel(tableLocality.getModel());
            numHand=8;
        }else
        if ("Улицы".equals(treeSelect)) {
            tableHandbook.setModel(tableStreet.getModel());
            numHand=9;
        }else
        if ("Родственные отношения".equals(treeSelect)) {
            tableHandbook.setModel(tableRelation.getModel());
            numHand=10;
        }else
        if ("Виды доходов".equals(treeSelect)) {
            tableHandbook.setModel(tableTypeIncome.getModel());
            numHand=11;
        }else
        if ("Документы удостоверения".equals(treeSelect)) {
            tableHandbook.setModel(tableIndDoc.getModel());
            numHand=12;
        }else
        if ("Документы".equals(treeSelect)) {
            tableHandbook.setModel(tableDoc.getModel());
            numHand=13;
        }else
        if ("Сотрудники".equals(treeSelect)) {
            tableHandbook.setModel(tableEmployee.getModel());
            numHand=14;
        }else
        if ("Отдел".equals(treeSelect)) {
            tableHandbook.setModel(tableDepartament.getModel());
            numHand=15;
        }else
        if ("Должность".equals(treeSelect)) {
            tableHandbook.setModel(tableJob.getModel());
            numHand=16;
        }else
        if ("Банки".equals(treeSelect)) {
            tableHandbook.setModel(tableBank.getModel());
            numHand=17;
        }else
        if ("Почты".equals(treeSelect)) {
            tableHandbook.setModel(tablePost.getModel());
            numHand=18;
        }else
        if ("Кассы".equals(treeSelect)) {
            tableHandbook.setModel(tableCash.getModel());
            numHand=19;
        }else {
            tableHandbook.setModel(new DefaultTableModel());
            numHand = 0;
        }
    }
    //выделение пункта дерева - новая таблица справочника
    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
        updateHandbook();
    }
    public String quotate(String content){
        return "'"+content+"'";
    }


    private String deleteItemInGlossary(){
        TreePath[] paths=glossaryJTree.getSelectionPaths();
        treeSelect=paths[0].getLastPathComponent().toString();

        TableModel tm = tableHandbook.getModel();
        String item = tm.getValueAt(selRowItemInHandbook,0).toString();
        String sqlItem = "";

        //Добавить для других случаев
        if ("Категории гражданина".equals(treeSelect)) {
            sqlItem = "delete from social_client_category where codeClientCategory = '"+item+"'";
        }else
        if ("Меры социальной поддержки".equals(treeSelect)) {
            String codCat=tm.getValueAt(selRowItemInHandbook,1).toString();
            sqlItem = "delete from measure_of_social_support where codeSocialMeasure = '"+item+"' and codeClientCategory = '"+codCat+"'";
        }else
        if ("Законы".equals(treeSelect)) {
            sqlItem = "delete from law where codeOfLaw = '"+item+"'";
        }else
        if ("Статьи".equals(treeSelect)) {
            String codL=tm.getValueAt(selRowItemInHandbook,1).toString();
            sqlItem = "delete from article where articleCode = '"+item+"' and codeOfLaw = '"+codL+"'";
        }else
        if ("Индексы".equals(treeSelect)) {
            sqlItem = "delete from index_address where numberIndex = '"+item+"'";
        }else
        if ("Регионы".equals(treeSelect)) {
            sqlItem = "delete from region_address where numberRegion = '"+item+"'";
        }else
        if ("Районы".equals(treeSelect)) {
            sqlItem = "delete from district_address where numberDistrict = '"+item+"'";
        }else
        if ("Населённые пункты".equals(treeSelect)) {
            sqlItem = "delete from inhabited_locality_address where numberInhabitedLocality = '"+item+"'";
        }else
        if ("Улицы".equals(treeSelect)) {
            sqlItem = "delete from street_address where numberStreet = '"+item+"'";
        }else
        if ("Родственные отношения".equals(treeSelect)) {
            sqlItem = "delete from relation_degree where numberRelationDegree = '"+item+"'";
        }else
        if ("Виды доходов".equals(treeSelect)) {
            sqlItem = "delete from type_income where numberTypeIncone = '"+item+"'";
        }else
        if ("Документы удостоверения".equals(treeSelect)) {
            sqlItem = "delete from type_identification_document where numberTypeIdDocument = '"+item+"'";
        }else
        if ("Документы".equals(treeSelect)) {
            sqlItem = "delete from type_attached_document where numberTypeAttachedDocument = '"+item+"'";
        }else
        if ("Сотрудники".equals(treeSelect)) {
            sqlItem = "delete from employee where personnelNumberEmployee = '"+item+"'";
        }else
        if ("Отдел".equals(treeSelect)) {
            sqlItem = "delete from department_employee where numberDepartament = '"+item+"'";
        }else
        if ("Должность".equals(treeSelect)) {
            sqlItem = "delete from job_position_employee where codeJob = '"+item+"'";
        }else
        if ("Банки".equals(treeSelect)) {
            sqlItem = "delete from bank_organization where bic = '"+item+"'";
        }else
        if ("Почты".equals(treeSelect)) {
            sqlItem = "delete from post_organization where postCode = '"+item+"'";
        }else
        if ("Кассы".equals(treeSelect)) {
            sqlItem = "delete from cash_organization where tinCash = '"+item+"'";
        }else
            tableHandbook.setModel(new DefaultTableModel());

        //String [] param = {sqlItem,item};
        return sqlItem;
    }

    private void getComboBoxOrganization(JComboBox cb, JTable b, JTable p, JTable c){
        cb.removeAllItems();
        String item;
        for (int i = 0;i<b.getRowCount();i++){
            item = b.getModel().getValueAt(i, 0).toString()+" | "+b.getModel().getValueAt(i,2).toString();
            cb.addItem(item);
        }
        for (int i = 0;i<p.getRowCount();i++){
            item = p.getModel().getValueAt(i, 0).toString()+" | "+p.getModel().getValueAt(i,3).toString();
            cb.addItem(item);
        }
        for (int i = 0;i<c.getRowCount();i++){
            item = c.getModel().getValueAt(i, 0).toString()+" | "+c.getModel().getValueAt(i,2).toString();
            cb.addItem(item);
        }
    }
}
