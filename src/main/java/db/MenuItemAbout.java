package db;

import javax.swing.*;
import java.awt.*;

public class MenuItemAbout extends JFrame{
    private JPanel rootPanel;
    private JTabbedPane jTabbed;
    private JLabel labelPersonnelFileImg;
    private JTextArea textPersonnelFile;
    private JLabel labelPayments;
    private JTextArea textPayments;
    private JLabel labelHandbook;
    private JTextArea textHandbook;
    private JLabel labelReport;
    private JTextArea textReport;
    private JLabel labelPayments2;

    public MenuItemAbout(){

        super("Справка");

        ImageIcon iconPersFile = new ImageIcon("src\\PersonnelFile.PNG");
        Image imagePersFile = iconPersFile.getImage();
        Image newimgPersFile = imagePersFile.getScaledInstance(1400,600,Image.SCALE_SMOOTH);
        iconPersFile = new ImageIcon(newimgPersFile);
        labelPersonnelFileImg.setIcon(iconPersFile);
        textPersonnelFile.setText("\nВо вкладке 'Личное дело' показывается список всех клиентов.\n" +
                "С помощью кнопок, находящихся под таблицей, можно добавлять," +
                " редактировать и удалять клиентов.\n" +
                "Над таблицей находятся кнопки 'Обновить' и 'Поиск'," +
                " с помощью которых можно произвести поиск по нужному критерию,\n" +
                " а так же обновить таблицу, чтобы вернуть её в исходное состояние.\n" +
                "Кнопки '+/-' во вкладках, такие как 'Персональные данные','Уд.личности' и т.д., " +
                "позволяют дополнять и удалять информацию о клиенте.");

        ImageIcon iconPayments = new ImageIcon("src\\Payments.png");
        Image imagePayments = iconPayments.getImage();
        Image newimgPayments = imagePayments.getScaledInstance(1700,300,Image.SCALE_SMOOTH);
        iconPayments = new ImageIcon(newimgPayments);
        labelPayments.setIcon(iconPayments);
        ImageIcon iconPayments2 = new ImageIcon("src\\Payments2.png");
        Image imagePayments2 = iconPayments2.getImage();
        Image newimgPayments2 = imagePayments2.getScaledInstance(1700,200,Image.SCALE_SMOOTH);
        iconPayments2 = new ImageIcon(newimgPayments2);
        labelPayments2.setIcon(iconPayments2);
        textPayments.setText("\nВо вкладке 'Начисления' нужно выбрать категорию льготника и категорию льготы,\n" +
                "после чего нажать на кнопку 'Найти льготников' и назначить выплату, нажав на соответствующую кнопку.\n" +
                "Во вкладке 'Выплаты' нужны выбрать период выплат, после чего нажать на кнопки 'Найти выплаты' и 'Выплатить'.");

        ImageIcon iconHandbook = new ImageIcon("src\\Handbook.PNG");
        Image imageHandbook = iconHandbook.getImage();
        Image newimgHandbook = imageHandbook.getScaledInstance(1400,500,Image.SCALE_SMOOTH);
        iconHandbook = new ImageIcon(newimgHandbook);
        labelHandbook.setIcon(iconHandbook);
        textHandbook.setText("\nВкладка 'Справочники' позволяет посмотреть информацию, лежащую в каком-либо справочнике,\n" +
                "а так же осуществить добавление новой информации в справочник.");

        ImageIcon iconReports = new ImageIcon("src\\Reports.png");
        Image imageReports = iconReports.getImage();
        Image newimgReports = imageReports.getScaledInstance(1700,300,Image.SCALE_SMOOTH);
        iconReports = new ImageIcon(newimgReports);
        labelReport.setIcon(iconReports);
        textReport.setText("\nВкладка 'Отчёты' позволяет сформировать оборотно-сальдовую ведомость,\n" +
                "задав дату составления отчёта(период),категорию льготника и сотрудника, который составляет отчёт.\n" +
                "После чего нужно нажать на кнопку 'Составить отчёт' и 'Сохранить для печати',\n" +
                "чтобы соответсвующий отчёт сохранился в формате pdf.");

        setContentPane(rootPanel);
        pack();
        setVisible(true);
    }
}
