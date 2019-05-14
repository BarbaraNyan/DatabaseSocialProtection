package db;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FilterClient extends JFrame{
    private JPanel rootPanel;
    private JCheckBox surnameCheckBox;
    private JTextField textSurname;
    private JComboBox comboBoxIndex;
    private JComboBox comboBoxRegion;
    private JComboBox comboBoxDistrict;
    private JComboBox comboBoxLocality;
    private JComboBox comboBoxStreet;
    private JComboBox comboBoxCategory;
    private JComboBox comboBoxMeasure;
    private JButton buttonCansel;
    private JButton buttonFind;
    private JTextField textName;
    private JCheckBox nameCheckBox;
    private JTextField textPatronymic;
    private JCheckBox patronymicCheckBox;
    private JCheckBox dateBirthChecBox;
    private JPanel pnDateBirth;
    private JCheckBox indexCheckBox;
    private JCheckBox regionCheckBox;
    private JCheckBox districtCheckBox;
    private JCheckBox localityCheckBox;
    private JCheckBox streetCheckBox;
    private JCheckBox categoryClientCheckBox;
    private JCheckBox measureCheckBox;
    private JCheckBox personalCheckBox;
    private JCheckBox documentCheckBox;
    private JCheckBox seriesCheckBox;
    private JCheckBox numberCheckBox;
    private JCheckBox addressCheckBox;
    private JFormattedTextField textSeries;
    private JFormattedTextField textNumber;
    private ButtonGroup radioButtonGender;
    private JCheckBox genderCheckBox;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JCheckBox categoryCheckBox;
    private com.toedter.calendar.JDateChooser dcDateBirth = new com.toedter.calendar.JDateChooser();
    SocialProtectionForm sf;
    private DatabaseConnection mdbc;
    private Statement stmt;

    FilterClient(final TableModel[] tm, SocialProtectionForm spf){
        sf=spf;
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1200,700));
        pnDateBirth.add(dcDateBirth);

        JCheckBox []b1={surnameCheckBox, nameCheckBox, patronymicCheckBox, dateBirthChecBox, genderCheckBox};
        JCheckBox []b2={seriesCheckBox, numberCheckBox};
        JCheckBox []b3={indexCheckBox, regionCheckBox, districtCheckBox, localityCheckBox, streetCheckBox};
        JCheckBox []b4={categoryClientCheckBox, measureCheckBox};
        checkRoot(personalCheckBox, b1);
        checkRoot(documentCheckBox, b2);
        checkRoot(addressCheckBox, b3);
        checkRoot(categoryCheckBox, b4);

        check(surnameCheckBox, textSurname, personalCheckBox, b1);
        check(nameCheckBox, textName, personalCheckBox, b1);
        check(patronymicCheckBox, textPatronymic, personalCheckBox, b1);
        check(dateBirthChecBox, dcDateBirth, personalCheckBox, b1);
        check(genderCheckBox, maleRadioButton, personalCheckBox, b1);
        check(genderCheckBox, femaleRadioButton, personalCheckBox, b1);

        check(seriesCheckBox, textSeries, documentCheckBox, b2);
        check(numberCheckBox, textNumber, documentCheckBox, b2);

        check(indexCheckBox, comboBoxIndex, addressCheckBox, b3);
        check(regionCheckBox, comboBoxRegion, addressCheckBox, b3);
        check(districtCheckBox, comboBoxDistrict, addressCheckBox, b3);
        check(localityCheckBox, comboBoxLocality, addressCheckBox, b3);
        check(streetCheckBox, comboBoxStreet, addressCheckBox, b3);

        check(categoryClientCheckBox, comboBoxCategory, categoryCheckBox, b4);
        check(measureCheckBox, comboBoxMeasure, categoryCheckBox, b4);
        setMasks();
        getComboBox(comboBoxIndex, tm[0], 0);
        getComboBox(comboBoxRegion, tm[1], 1);
        getComboBox(comboBoxDistrict, tm[2], 1);
        getComboBox(comboBoxLocality, tm[3], 1);
        getComboBox(comboBoxStreet, tm[4], 1);
        getComboBox(comboBoxCategory,tm[5], 1);
        getComboBox(comboBoxMeasure,tm[6], 2);

        comboBoxCategory.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                comboBoxMeasure.removeAllItems();
                String item;
                for (int i = 0;i<tm[6].getRowCount();i++){
                    if(Integer.valueOf(tm[6].getValueAt(i, 1).toString())==comboBoxCategory.getSelectedIndex()+1) {
                        item = tm[6].getValueAt(i, 2).toString();
                        comboBoxMeasure.addItem(item);
                    }
                }
            }
        });

        buttonCansel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rezult = JOptionPane.showOptionDialog(FilterClient.this, "Вы уверены, что хотите отменить поиск клиентов?", "Отмена поиска клиентов", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Да", "Нет"},
                        "Да");
                if(rezult==JOptionPane.YES_OPTION)
                    setVisible(false);
            }
        });

        buttonFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertClientMeasure(tm);
                setVisible(false);
            }
        });
        setButtonGroup();
    }

    private void setButtonGroup(){
        radioButtonGender = new ButtonGroup();
        radioButtonGender.add(maleRadioButton);
        radioButtonGender.add(femaleRadioButton);
    }
    private String getGender(){
        if(femaleRadioButton.isSelected())
            return "1";
        else
            return "2";
    }

    private void check(final JCheckBox cb, final JComponent o, final JCheckBox root, final JCheckBox [] box){
        cb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if(cb.isSelected()==true) {
                    o.setEnabled(true);
                    root.setSelected(true);
                }
                else {
                    o.setEnabled(false);
                    for(int j=0; j<box.length; j++)
                        if(box[j].isSelected())
                             return;
                    root.setSelected(false);
                }
            }
        });
    }

    private void checkRoot(final JCheckBox root, final JCheckBox [] box){
        root.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(root.isSelected())
                    for(int i=0; i<box.length; i++)
                        box[i].setSelected(true);
                else
                    for(int i=0; i<box.length; i++)
                        box[i].setSelected(false);
            }
        });
    }

    public void getComboBox(JComboBox cb, TableModel tl, int k){
        String item;
        for (int i = 0;i<tl.getRowCount();i++){
            item = tl.getValueAt(i, k).toString();
            cb.addItem(item);
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

    private void insertClientMeasure(final TableModel[] handbook){
        String surname="%";
        String name="%";
        String patronymic="%";
        String dateBirth="%";
        String genderCode="%";
        String series="%";
        String number="%";
        String indexCode="%";
        String regionCode="%";
        String districtCode="%";
        String localCode="%";
        String streetCode="%";
        String categoryCode="%";
        String measureCode="%";
        SimpleDateFormat formatForSql= new SimpleDateFormat("yyyy-MM-dd");

        if(personalCheckBox.isSelected()){
            if(surnameCheckBox.isSelected())
                surname=textSurname.getText();
            if(nameCheckBox.isSelected())
                name=textName.getText();
            if(dateBirthChecBox.isSelected())
                dateBirth=formatForSql.format(dcDateBirth.getDate());
            if(genderCheckBox.isSelected())
                genderCode=getGender();
        }
        if(documentCheckBox.isSelected()) {
            if (seriesCheckBox.isSelected())
                series = textSeries.getText();
            if (numberCheckBox.isSelected())
                number=textNumber.getText();
        }
        if(addressCheckBox.isSelected()){
            if(indexCheckBox.isSelected())
                indexCode=comboBoxIndex.getSelectedItem().toString();
            if(regionCheckBox.isSelected())
                regionCode=handbook[1].getValueAt(comboBoxRegion.getSelectedIndex(), 0).toString();
            if(districtCheckBox.isSelected())
                districtCode=handbook[2].getValueAt(comboBoxDistrict.getSelectedIndex(), 0).toString();
            if(localityCheckBox.isSelected())
                localCode=handbook[3].getValueAt(comboBoxLocality.getSelectedIndex(), 0).toString();
            if(streetCheckBox.isSelected())
                streetCode=handbook[4].getValueAt(comboBoxStreet.getSelectedIndex(), 0).toString();
        }
        if(categoryCheckBox.isSelected()){
            if(categoryClientCheckBox.isSelected())
                categoryCode=handbook[5].getValueAt(comboBoxCategory.getSelectedIndex(), 0).toString();
            if(measureCheckBox.isSelected())
                for (int i = 0;i<handbook[6].getRowCount();i++){
                    if(handbook[6].getValueAt(i, 2).toString().equals(comboBoxMeasure.getSelectedItem().toString())) {
                        measureCode = handbook[6].getValueAt(i, 0).toString();
                        break;
                    }
                }
        }


        String sqlQuery = "select distinct(sc.personalNumber), sc.surname, sc.name, sc.patronymic, g.nameGender, DATE_FORMAT(sc.dateBirth,'%d.%m.%Y'), " +
                "sc.snils, sc.telephone, sc.email from social_client sc inner join gender g on g.numberGender=sc.numberGender " +
                "inner join identification_document id on sc.personalNumber=id.personalNumber " +
                "inner join address a on sc.idAddress=a.idAddress " +
                "inner join client_measure cm on sc.personalNumber=cm.personalNumber " +
                "where sc.surname like '"+surname+"' and sc.name like '"+name+"' and sc.patronymic like '"+patronymic+"' and " +
                "sc.dateBirth like '"+dateBirth+"' and sc.numberGender like '"+genderCode+"' and a.numberIndex like '"+indexCode+"' and " +
                "a.numberRegion like '"+regionCode+"' and a.numberDistrict like '"+districtCode+"' and a.numberInhabitedLocality like '"+localCode+"' and " +
                "a.numberStreet like '"+streetCode+"' and id.docSeries like '"+series+"' and id.docNumber like '"+number+"' and " +
                "cm.codeSocialMeasure like '"+measureCode+"' and cm.codeClientCategory like '"+categoryCode+"'";

        sf.initModelSocialClient(sqlQuery);

    }

}
