package db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AddHandbook extends JFrame{
    private JPanel root;
    private JComboBox codeCatComboBox;
    private JTextField textNum;
    private JTextField textSurname;
    private JTextField textName;
    private JTextField textPatronymic;
    private JComboBox periodComboBox;
    private JComboBox depComboBox;
    private JComboBox jobComboBox;
    private JButton canselButton;
    private JButton addButton;
    private JLabel lbNum;
    private JLabel lbName;
    private JLabel lbDepOrCod;
    private JLabel lbJobOrCod;
    private JPanel panelDate;
    private JLabel lbSurname;
    private JLabel lbPatronymic;
    private JLabel lbPeriod;
    private JLabel lbDate;
    private JLabel lbCode;
    private com.toedter.calendar.JDateChooser dcDate = new com.toedter.calendar.JDateChooser();

    private DatabaseConnection mdbc;
    private Statement stmt;

    public AddHandbook(final int var, final JTable [] tb){
        setContentPane(root);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width/2-this.getSize().width/2,dimension.height/2-this.getSize().height/2);
        dcDate.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        panelDate.add(dcDate);
        hideComp();

        switch(var){
            case 1:
                lbNum.setVisible(true);
                lbNum.setText("Категория гражданина");
                textNum.setVisible(true);
                break;
            case 2:
                lbCode.setVisible(true);
                lbCode.setText("Категория гражданина");
                codeCatComboBox.setVisible(true);
                getComboBox(codeCatComboBox, tb[0], 1);
                lbNum.setVisible(true);
                lbNum.setText("Название меры");
                textNum.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Сумма меры");
                textName.setVisible(true);
                lbPeriod.setVisible(true);
                periodComboBox.setVisible(true);
                lbDepOrCod.setVisible(true);
                lbDepOrCod.setText("Номер закона");
                depComboBox.setVisible(true);
                getComboBox(depComboBox, tb[2], 1);
                lbJobOrCod.setVisible(true);
                lbJobOrCod.setText("Номер статьи");
                jobComboBox.setVisible(true);
                setArt(tb[1], tb[2]);
                depComboBox.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        setArt(tb[1],tb[2]);
                    }
                });
                break;
            case 3:
                lbNum.setVisible(true);
                lbNum.setText("Номер закона");
                textNum.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Имя закона");
                textName.setVisible(true);
                lbDate.setVisible(true);
                lbDate.setText("Дата утверждения");
                panelDate.setVisible(true);
                dcDate.setVisible(true);
                break;
            case 4:
                lbCode.setVisible(true);
                lbCode.setText("Номер закона");
                codeCatComboBox.setVisible(true);
                getComboBox(codeCatComboBox, tb[2], 1);
                lbNum.setVisible(true);
                lbNum.setText("Номер статьи");
                textNum.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Имя статьи");
                textName.setVisible(true);
                lbDate.setVisible(true);
                lbDate.setText("Дата принятия");
                panelDate.setVisible(true);
                dcDate.setVisible(true);
                break;
            case 5:
                lbNum.setVisible(true);
                lbNum.setText("Индекс");
                textNum.setVisible(true);
                break;
            case 6:
                lbNum.setVisible(true);
                lbNum.setText("Регион");
                textNum.setVisible(true);
                break;
            case 7:
                lbNum.setVisible(true);
                lbNum.setText("Район");
                textNum.setVisible(true);
                break;
            case 8:
                lbNum.setVisible(true);
                lbNum.setText("Населенный пункт");
                textNum.setVisible(true);
                break;
            case 9:
                lbNum.setVisible(true);
                lbNum.setText("Улица");
                textNum.setVisible(true);
                break;
            case 10:
                lbNum.setVisible(true);
                lbNum.setText("Родственные отношения");
                textNum.setVisible(true);
                break;
            case 11:
                lbNum.setVisible(true);
                lbNum.setText("Вид дохода");
                textNum.setVisible(true);
                break;
            case 12:
                lbNum.setVisible(true);
                lbNum.setText("Вид документа, удостоверяющего личность");
                textNum.setVisible(true);
                break;
            case 13:
                lbNum.setVisible(true);
                lbNum.setText("Вид дополнительного документа");
                textNum.setVisible(true);
                break;
            case 14:
                lbNum.setVisible(true);
                lbNum.setText("Табельный номер");
                textNum.setVisible(true);
                lbSurname.setVisible(true);
                lbSurname.setText("Фамилия сотрудника");
                textSurname.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Имя сотрудника");
                textName.setVisible(true);
                lbPatronymic.setVisible(true);
                lbPatronymic.setText("Отчество сотрудника");
                textPatronymic.setVisible(true);
                lbDepOrCod.setVisible(true);
                lbDepOrCod.setText("Отдел");
                depComboBox.setVisible(true);
                getComboBox(depComboBox, tb[3], 1);
                lbJobOrCod.setVisible(true);
                lbJobOrCod.setText("Должность");
                jobComboBox.setVisible(true);
                getComboBox(jobComboBox, tb[4], 1);
                break;
            case 15:
                lbNum.setVisible(true);
                lbNum.setText("Код отдела");
                textNum.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Наименование отдела");
                textName.setVisible(true);
                break;
            case 16:
                lbNum.setVisible(true);
                lbNum.setText("Код должности");
                textNum.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Наименование должности");
                textName.setVisible(true);
                break;
            case 17:
                lbNum.setVisible(true);
                lbNum.setText("БИК");
                textNum.setVisible(true);
                lbSurname.setVisible(true);
                lbSurname.setText("Адрес филиала");
                textSurname.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Наименование банка");
                textName.setVisible(true);
                lbPatronymic.setVisible(true);
                lbPatronymic.setText("ИНН банка");
                textPatronymic.setVisible(true);
                break;
            case 18:
                lbNum.setVisible(true);
                lbNum.setText("Индекс почты");
                textNum.setVisible(true);
                lbSurname.setVisible(true);
                lbSurname.setText("Адрес почты");
                textSurname.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Наименование почты");
                textName.setVisible(true);
                lbPatronymic.setVisible(true);
                lbPatronymic.setText("ИНН почты");
                textPatronymic.setVisible(true);
                break;
            case 19:
                lbNum.setVisible(true);
                lbNum.setText("ИНН кассы");
                textNum.setVisible(true);
                lbSurname.setVisible(true);
                lbSurname.setText("Адрес кассы");
                textSurname.setVisible(true);
                lbName.setVisible(true);
                lbName.setText("Наименование кассы");
                textName.setVisible(true);
                break;
            default:
                AddHandbook.this.setVisible(false);
                AddHandbook.this.dispose();
                break;
        }

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNullBorder();
                if(textNum.getText().equals("")){
                    textNum.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
                    showMess();
                }else if((var==14 || var==17 || var==18) && textSurname.getText().equals("")){
                    textSurname.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
                    showMess();
                }else if ((var==2 || var==3 || var==4 || var==14 || var==15 || var==16 || var==17 || var==18 || var==19)&&textName.getText().equals("")){
                    textName.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
                    showMess();
                }else if ((var==14 || var==17 || var==18 || var==19) && textPatronymic.getText().equals("")){
                    textPatronymic.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
                    showMess();
                }else if ((var==3 || var==4) && dcDate.getDate()==null){
                    dcDate.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
                    showMess();
                } else {
                    int rez = addItemInGlossary(var, tb);
                    if (rez == 1)
                        JOptionPane.showMessageDialog(AddHandbook.this, "Успешно добавлено!", "Добавление", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(AddHandbook.this, "Ошибка при добавлении", "Добавление", JOptionPane.WARNING_MESSAGE);
                    setVisible(false);
                    dispose();
                }
            }
        });
        canselButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int rezult = JOptionPane.showOptionDialog(AddHandbook.this, "Вы уверены, что хотите отменить добавление элемента справочника?", "Отмена добавления элемента справочника", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });
    }

    private void showMess(){
        JOptionPane.showMessageDialog(AddHandbook.this, "Должны быть введены все поля", "Добавление", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setNullBorder(){
        textNum.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        textSurname.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        textName.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        textPatronymic.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        dcDate.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
    }

    private void hideComp(){
        codeCatComboBox.setVisible(false);
        textNum.setVisible(false);
        textSurname.setVisible(false);
        textName.setVisible(false);
        textPatronymic.setVisible(false);
        periodComboBox.setVisible(false);
        depComboBox.setVisible(false);
        jobComboBox.setVisible(false);
        lbNum.setVisible(false);
        lbName.setVisible(false);
        lbDepOrCod.setVisible(false);
        lbJobOrCod.setVisible(false);
        panelDate.setVisible(false);
        dcDate.setVisible(false);
        lbSurname.setVisible(false);
        lbPatronymic.setVisible(false);
        lbPeriod.setVisible(false);
        lbCode.setVisible(false);
        lbDate.setVisible(false);
    }

    public void getComboBox(JComboBox cb, JTable tl, int k){
        String item;
        for (int i = 0;i<tl.getRowCount();i++){
            item = tl.getModel().getValueAt(i, k).toString();
            cb.addItem(item);
        }
    }

    private void setArt(JTable art, JTable l){
        jobComboBox.removeAllItems();
        String item;
        int k=depComboBox.getSelectedIndex();
        for (int i = 0;i<art.getRowCount();i++){
            if(Integer.valueOf(art.getModel().getValueAt(i, 1).toString())==l.getModel().getValueAt(k,0)) {
                item = art.getValueAt(i, 2).toString();
                jobComboBox.addItem(item);
            }
        }
    }

    private int addItemInGlossary(int var, JTable [] tb){
        String item;
        String sql="";
        String name;
        String getMaxNum="";
        SimpleDateFormat formatForSql= new SimpleDateFormat("yyyy-MM-dd");

        switch (var){
            case 1:
                item=textNum.getText();
                sql="insert into social_client_category(nameClientCategory) values ('"+item+"')";
                break;
            case 2:
                String code=tb[0].getModel().getValueAt(codeCatComboBox.getSelectedIndex(),0).toString();
                item=textNum.getText();
                name=textName.getText();
                int period=periodComboBox.getSelectedIndex()+1;
                String numL=tb[2].getModel().getValueAt(depComboBox.getSelectedIndex(),0).toString();
                String numAr=tb[1].getModel().getValueAt(jobComboBox.getSelectedIndex(),0).toString();
                getMaxNum="select max(codeSocialMeasure) as maxNum from measure_of_social_support";
                sql="insert into measure_of_social_support(codeSocialMeasure, codeClientCategory, nameSocialMeasure, " +
                        "amountOfSupport, termOfMeasure, articleCode, codeOfLaw) " +
                        "values (?, '"+code+"', '"+item+"', '"+name+"', '"+period+"', '"+numAr+"', '"+numL+"')";
                break;
            case 3:
                item=textNum.getText();
                name=textName.getText();
                String dat=formatForSql.format(dcDate.getDate());
                getMaxNum="select max(codeOfLaw) as maxNum from law";
                sql="insert into law(codeOfLaw, numberLaw, nameLaw, certifiedDateLaw) " +
                        "values (?, '"+item+"', '"+name+"', '"+dat+"')";
                break;
            case 4:
                String codL=tb[2].getModel().getValueAt(codeCatComboBox.getSelectedIndex(),0).toString();
                item=textNum.getText();
                name=textName.getText();
                String date=formatForSql.format(dcDate.getDate());
                getMaxNum="select max(articleCode) as maxNum from article";
                sql="insert into article(articleCode, codeOfLaw, articleNumber, nameArticle, certifiedDateArticle) " +
                        "values (?, '"+codL+"', '"+item+"', '"+name+"', '"+date+"')";
                break;
            case 5:
                item=textNum.getText();
                sql="insert into index_address(numberIndex) values ('"+item+"')";
                break;
            case 6:
                item=textNum.getText();
                sql="insert into region_address(nameRegion) values ('"+item+"')";
                break;
            case 7:
                item=textNum.getText();
                sql="insert into district_address(nameDistrict) values ('"+item+"')";
                break;
            case 8:
                item=textNum.getText();
                sql="insert into inhabited_locality_address(nameInhabitedLocality) values ('"+item+"')";
                break;
            case 9:
                item=textNum.getText();
                sql="insert into street_address(nameStreet) values ('"+item+"')";
                break;
            case 10:
                item=textNum.getText();
                sql="insert into relation_degree(nameRelationDegree) values ('"+item+"')";
                break;
            case 11:
                item=textNum.getText();
                sql= "insert into type_income(nameTypeIncone) values ('"+item+"')";
                break;
            case 12:
                item=textNum.getText();
                sql= "insert into type_identification_document(nameTypeIdDocument) values ('"+item+"')";
                break;
            case 13:
                item=textNum.getText();
                sql= "insert into type_attached_document(nameTypeAttachedDocument) values ('"+item+"')";
                break;
            case 14:
                item=textNum.getText();
                String surnm=textSurname.getText();
                name=textName.getText();
                String patr=textPatronymic.getText();
                String codDep=tb[3].getModel().getValueAt(depComboBox.getSelectedIndex(), 0).toString();
                String codJob=tb[4].getModel().getValueAt(jobComboBox.getSelectedIndex(), 0).toString();
                sql="insert into employee(personnelNumberEmployee, surnameEmployee, nameEmployee, patronymicEmployee, codeJob, numberDepartament) " +
                        "values ('"+item+"', '"+surnm+"', '"+name+"', '"+patr+"', '"+codJob+"', '"+codDep+"')";
                break;
            case 15:
                item=textNum.getText();
                name=textName.getText();
                sql="insert into department_employee(numberDepartament, nameDepartament) values ('"+item+"', '"+name+"')";
                break;
            case 16:
                item=textNum.getText();
                name=textName.getText();
                sql="insert into job_position_employee(codeJob, nameJob) values ('"+item+"', '"+name+"')";
                break;
            case 17:
                item=textNum.getText();
                String addB=textSurname.getText();
                name=textName.getText();
                String tinB=textPatronymic.getText();
                sql="insert into bank_organization(bic, addressBank, nameBank, tinBank) " +
                        "values ('"+item+"', '"+addB+"', '"+name+"', '"+tinB+"')";
                break;
            case 18:
                item=textNum.getText();
                String addP=textSurname.getText();
                name=textName.getText();
                String tinP=textPatronymic.getText();
                sql="insert into post_organization(postCode, tinPost, addressPost, namePost) " +
                        "values ('"+item+"', '"+tinP+"', '"+addP+"', '"+name+"')";
                break;
            case 19:
                item=textNum.getText();
                String addC=textSurname.getText();
                name=textName.getText();
                sql="insert into cash_organization(tinCash, addressCash, addressCash) " +
                        "values ('"+item+"', '"+addC+"', '"+name+"')";
                break;
        }
        try {
            mdbc = new DatabaseConnection();
            Connection conn = mdbc.getMyConnection();
            stmt= conn.createStatement();
            if(var==2 || var==3 || var==4){
                ResultSet rs=stmt.executeQuery(getMaxNum);
                if(rs.next()){
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, rs.getInt("maxNum")+1);
                    ps.executeUpdate();
                }
            }else
                stmt.executeUpdate(sql);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
}
