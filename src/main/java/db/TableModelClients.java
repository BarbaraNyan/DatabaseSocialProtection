package db;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

    public class TableModelClients extends DefaultTableModel {

        private DatabaseConnection mdbc;
        private Connection conn;
        private java.sql.Statement stmt;
        private ResultSet rs;
        private Object[][] rsm;
        private DefaultTableModel dtm;
        private PreparedStatement ps;

        //для таблицы social_client
        public String[] columnsSC;
        public Class[] columnClassSC;

        //для таблицы identification_document
        public String[] columnsIdDoc;
        public Class[] columnClassIdDoc;

        //для таблицы attached_document
        public String[] columnsAttDoc;
        public Class[] columnClassAttDoc;

        //для таблицы address
        public String[] columnsAddress;
        public Class[] columnClassAddress;

        //для таблицы operation_account
        public String[] columnsOpAcc;
        public Class[] columnClassOpAcc;

        //для таблицы category measure
        public String[] columnsCategoryMeasure;
        public Class[] columnClassCategoryMeasure;

        //для таблицы request
        public String[] columnsRequest;
        public Class[] columnClassRequest;

        //для таблицы payoff
        public String[] columnsPayoff;
        public Class[] columnClassPayoff;

        //для таблицы personal_account
        public String[] columnsPersonalAccount;
        public Class[] columnClassPersonalAccount;

        //для таблицы relatives
        public String[] columnsRelatives;
        public Class[] columnClassRelatives;

        //для таблицы identification_document relatives
        public String[] columnsIdDocRelatives;
        public Class[] columnClassIdDocRelatives;

        //для таблицы relatives
        public String[] columnsIncome;
        public Class[] columnClassIncome;

        //для таблицы charge
        public String[] columnsChargeReq;
        public Class[] columnClassChargeReq;
        String ben;
        String cat;

        //для таблицы(справочнка) категория льготника
        public String[] columnsSCCategory;
        public Class[] columnClassSCCategory;

        //для таблицы(справочнка) мера социальной поддержки
        public String[] columnsMeasure;
        public Class[] columnClassMeasure;

        //для таблицы(справочнка) закон
        public String[] columnsLaw;
        public Class[] columnClassLaw;

        //для таблицы(справочнка) статья
        public String[] columnsArticle;
        public Class[] columnClassArticle;

        //для таблицы(справочнка) индекс
        public String[] columnsIndex;
        public Class[] columnClassIndex;

        //для таблицы(справочнка) регион
        public String[] columnsRegion;
        public Class[] columnClassRegion;

        //для таблицы(справочнка) район
        public String[] columnsDistrict;
        public Class[] columnClassDistrict;

        //для таблицы(справочнка) населенный пункт
        public String[] columnsLocality;
        public Class[] columnClassLocality;

        //для таблицы(справочнка) улица
        public String[] columnsStreet;
        public Class[] columnClassStreet;

        //для таблицы(справочнка) родств.отношения
        public String[] columnsRelation;
        public Class[] columnClassRelation;

        //для таблицы(справочнка) вид дохода
        public String[] columnsTypeIncome;
        public Class[] columnClassTypeIncome;

        //для таблицы(справочнка) вид документа удостоверения
        public String[] columnsTypeIndDoc;
        public Class[] columnClassTypeIndDoc;

        //для таблицы(справочнка) вид документа
        public String[] columnsTypeDoc;
        public Class[] columnClassTypeDoc;

        // для таблицы saldoReportTable
        public String[] columnsSalRep;
        public Class[] columnClassSalRep;

        //для таблицы льгот в Добавлении нового клиента
        public String[] columnsCatMeasure;
        public Class[] columnClassCatMeasure;

        //для таблицы(справочнка) сотрудники
        public String[] columnsEmployee;
        public Class[] columnClassEmployee;

        // для таблицы отдел
        public String[] columnsDepartment;
        public Class[] columnClassDepartment;

        //для таблицы должность
        public String[] columnsJob;
        public Class[] columnClassJob;

        //для таблицы поиска выплат
        public String[] columnsFindRequest;
        public Class[] columnClassFindRequest;

        public String sqlQuery;
        public String sqlPreparedStatement;
        public int persNum;


        // для оборотной ведомости
        private ArrayList<Integer> index = new ArrayList<Integer>();
        private  ArrayList sqlArray;

        ArrayList<Integer> persAcc = new ArrayList<Integer>();
        ArrayList<String> surname = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> patronimyc = new ArrayList<String>();
        ArrayList<Integer> inSaldo = new ArrayList<Integer>();
        ArrayList<Integer> accrued = new ArrayList<Integer>();

        public TableModelClients(){
            try {
                mdbc=new DatabaseConnection();
                conn=mdbc.getMyConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public DefaultTableModel MyTableModelClients(int type) {
            try {
                stmt= conn.createStatement();
                rs = stmt.executeQuery(sqlQuery);

                switch (type){
                    case 1: // SocialClient
                        createTable(rs,columnsSC,columnClassSC);
                        break;
                }

                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
                return null;
            }
        }

        public DefaultTableModel deleteRow() {
            try {
                stmt= conn.createStatement();
                stmt.executeUpdate(sqlQuery);
                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
                return null;
            }
        }

        public DefaultTableModel MyTableModelMeasure(){
            try {
                stmt= conn.createStatement();
                rs = stmt.executeQuery(sqlQuery);
                createTable(rs,columnsCatMeasure,columnClassCatMeasure);
                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
                return null;
            }
        }

        // получаем массив личных номеров клиентов
        public ArrayList createIndexArray(int catCode) throws SQLException {
            PreparedStatement ps =conn.prepareStatement(sqlQuery);
            ps.setInt(1,catCode);
            rs = ps.executeQuery();
            while(rs.next()) {
                index.add(rs.getInt("personalNumber"));
            }
            return index;
        }

        // преобразуем sql запрос согласно количеству элементов в массиве
        public String cheatCode(int num) {
            String sqlNew="";
            sqlNew = sqlPreparedStatement;
            String str = "";
            for(int i=0; i<num-1;i++) {
                str +=",?";
            }
            str = str + ")";
            sqlNew=sqlNew.concat(str);
            return sqlNew;
        }

        public DefaultTableModel MyTableModelReports(int catCode) {
            persAcc.clear();
            surname.clear();
            name.clear();
            patronimyc.clear();
            inSaldo.clear();
            accrued.clear();
            try {
                /*sqlArray = createIndexArray(catCode);
                String cheatSql = cheatCode(index.size());
                ps = conn.prepareStatement(cheatSql);
                for(int i=0; i<index.size();i++ ){
                    ps.setInt(i+1, index.get(i));
                }*/
                ps = conn.prepareStatement(sqlPreparedStatement);
                ps.setInt(1, catCode);
                rs = ps.executeQuery();
                createTable(rs, columnsSalRep, columnClassSalRep);

                rs = ps.executeQuery();
                ResultSetMetaData meta = ps.getMetaData();
                int numOfCol = meta.getColumnCount();
                while(rs.next()) {
                    persAcc.add(rs.getInt(1));
                    surname.add(rs.getString(2));
                    name.add(rs.getString(3));
                    patronimyc.add(rs.getString(4));
                    inSaldo.add(rs.getInt(5));
                    accrued.add(rs.getInt(6));
                }

                //sqlArray.clear();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            mdbc.close(stmt);
            return dtm;
        }

        public DefaultTableModel MyTableModelDocument(int type){
            try{
                ps = conn.prepareStatement(sqlPreparedStatement);
                ps.setInt(1,persNum);
                rs = ps.executeQuery();

                switch (type){
                    case 1: // IdDocument
                        createTable(rs,columnsIdDoc,columnClassIdDoc);
                        break;
                    case 2: // AttachedDocument
                        createTable(rs,columnsAttDoc,columnClassAttDoc);
                        break;
                    case 3:  // Address
                        createTable(rs,columnsAddress,columnClassAddress);
                        break;
                    case 4: //OperationAccount
                        createTable(rs, columnsOpAcc, columnClassOpAcc);
                        break;
                    case 5: //Relatives
                        createTable(rs, columnsRelatives, columnClassRelatives);
                        break;
                    case 6: //Income
                        createTable(rs, columnsIncome, columnClassIncome);
                        break;
                    case 7: //category measure
                        createTable(rs, columnsCategoryMeasure, columnClassCategoryMeasure);
                        break;
                    case 8: //request
                        createTable(rs, columnsRequest, columnClassRequest);
                        break;
                    case 9: //payoff
                        createTable(rs, columnsPayoff, columnClassPayoff);
                        break;
                    case 10: //pers account
                        createTable(rs, columnsPersonalAccount, columnClassPersonalAccount);
                        break;
                }

                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
                return null;
            }
        }

        public DefaultTableModel MyTableModelFindRequest(String date){
            try{
                ps = conn.prepareStatement(sqlPreparedStatement);
                ps.setString(1,date);
                rs = ps.executeQuery();

                createTable(rs,columnsFindRequest,columnClassFindRequest);

                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
                return null;
            }
        }

        public int setClientPayoff(String [] queryPayoff){
            try{
                String[] columnsPayoff = new String[] {"Номер личного счета", "Сумма", "Дата начала", "Дата окончания"};
                Class[] columnClassPayoff = new Class[] {Integer.class, Double.class, String.class, String.class};
                stmt= conn.createStatement();
                rs = stmt.executeQuery(queryPayoff[0]);
                createTable(rs, columnsPayoff, columnClassPayoff);
                String numP="";
                int numPA=-1;

                for(int i=0; i<dtm.getRowCount(); i++){
                    String amountPayoff=dtm.getValueAt(i, 1).toString();
                    ps=conn.prepareStatement(queryPayoff[1]);
                    ps.setString(1, dtm.getValueAt(i, 0).toString());
                    ps.setString(2, dtm.getValueAt(i, 2).toString());
                    ps.setString(3, dtm.getValueAt(i, 3).toString());
                    rs = ps.executeQuery();
                    if(rs.next()){
                        double amount=Double.valueOf(rs.getString("amountPayoff"))+Double.valueOf(amountPayoff);
                        numPA=rs.getInt("numberPersonalAccount");

                        ps=conn.prepareStatement(queryPayoff[2]);
                        ps.setDouble(1, amount);
                        ps.setDouble(2, amount);
                        ps.setInt(3, numPA);
                        ps.executeUpdate();
                    }else{
                        rs=stmt.executeQuery(queryPayoff[3]);
                        if(rs.next()){
                            numPA=rs.getInt("maxNumPA")+1;
                            ps=conn.prepareStatement(queryPayoff[4]);
                            ps.setInt(1, numPA);
                            ps.setString(2, amountPayoff);
                            ps.setString(3, amountPayoff);
                            ps.executeUpdate();
                        }
                    }
                    ps=conn.prepareStatement(queryPayoff[5]);
                    ps.setString(1, amountPayoff);
                    ps.setInt(2, numPA);
                    ps.executeUpdate();

                    rs=stmt.executeQuery("select max(numberPayoff) as maxNumP from payoff");
                    if(rs.next())
                        numP=rs.getString("maxNumP");

                    String queryNumRequest = "select rfcs.requestNumber from request_for_cash_settlement rfcs " +
                            "inner join operating_account oa on rfcs.numberOperatingAccount=oa.numberOperatingAccount " +
                            "inner join social_client sc on oa.personalNumber=sc.personalNumber " +
                            "where sc.personalNumber='"+dtm.getValueAt(i, 0).toString()+"' and rfcs.stateRequest='Назначено'";
                    Statement stmt1= conn.createStatement();
                    ResultSet rs1=stmt1.executeQuery(queryNumRequest);
                    while(rs1.next()){
                        String reqNum=rs1.getString(1);
                        Statement stmt2= conn.createStatement();
                        stmt2.executeUpdate("update request_for_cash_settlement set stateRequest='Выплачено', numberPayoff='"+numP+"' where requestNumber='"+reqNum+"'");
                    }
                }

                mdbc.close(stmt);
                return 1;
            }
            catch(SQLException e){
                return 0;
            }
        }

        public DefaultTableModel MyTableModelHandbook(int type){
            try{
                stmt= conn.createStatement();
                rs = stmt.executeQuery(sqlQuery);

                switch (type){
                    case 1: // категория льготника
                        createTable(rs,columnsSCCategory,columnClassSCCategory);
                        break;
                    case 2: // категория меры
                        createTable(rs,columnsMeasure,columnClassMeasure);
                        break;
                    case 3: // закон
                        createTable(rs,columnsLaw,columnClassLaw);
                        break;
                    case 4: // статья
                        createTable(rs,columnsArticle,columnClassArticle);
                        break;
                    case 5: // Индекс
                        createTable(rs,columnsIndex,columnClassIndex);
                        break;
                    case 6: // Регион
                        createTable(rs,columnsRegion,columnClassRegion);
                        break;
                    case 7: // Район
                        createTable(rs,columnsDistrict,columnClassDistrict);
                        break;
                    case 8: // Пункт
                        createTable(rs,columnsLocality,columnClassLocality);
                        break;
                    case 9: // Улица
                        createTable(rs,columnsStreet,columnClassStreet);
                        break;
                    case 10: // Родственники
                        createTable(rs,columnsRelation,columnClassRelation);
                        break;
                    case 11: // Доход
                        createTable(rs,columnsTypeIncome,columnClassTypeIncome);
                        break;
                    case 12: // Документ идентификатор
                        createTable(rs,columnsTypeIndDoc,columnClassTypeIndDoc);
                        break;
                    case 13: // Документ
                        createTable(rs,columnsTypeDoc,columnClassTypeDoc);
                        break;
                    case 14: // Сотрудники
                        createTable(rs,columnsEmployee,columnClassEmployee);
                        break;
                    case 15: // Отдел
                        createTable(rs,columnsDepartment,columnClassDepartment);
                        break;
                    case 16: // Должность
                        createTable(rs,columnsJob,columnClassJob);
                        break;
                }

                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
                return null;
            }
        }

        public DefaultTableModel MyTableModelPayoff(int type){
            try{
                ps = conn.prepareStatement(sqlPreparedStatement);
                ps.setString(1,cat);
                ps.setString(2,ben);
                rs = ps.executeQuery();

                switch (type){
                    case 1: // категория льготника
                        createTable(rs,columnsChargeReq,columnClassChargeReq);
                        break;
                    case 2: // категория льготы
                        //createTable(rs,columnsBenCategory,columnClassBenCategory);
                        break;
                    case 3:
                        createTable(rs, columnsIdDocRelatives, columnClassIdDocRelatives);
                        break;
                }

                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
                return null;
            }
        }

        public String [] setComponentOrganization(String [] par){
            try {
                stmt= conn.createStatement();
                rs = stmt.executeQuery(par[1]);
                if(rs.next()){
                    par[0]="1";
                    par[4]=rs.getString(1);
                    par[5]=rs.getString(2);
                    par[6]=rs.getString(3);
                    par[7]=rs.getString(4);
                } else {
                    rs = stmt.executeQuery(par[2]);
                    if (rs.next()) {
                        par[0]="2";
                        par[4] = rs.getString(1);
                        par[5] = rs.getString(2);
                        par[6] = rs.getString(3);
                    } else {
                        rs = stmt.executeQuery(par[3]);
                        if (rs.next()) {
                            par[0]="3";
                            par[4] = rs.getString(1);
                            par[5] = rs.getString(2);
                            par[6] = rs.getString(3);
                            par[7] = rs.getString(4);
                        }
                    }
                }
                return par;
            } catch (SQLException e) {
                return null;
            }
        }

        public int getMonthIncome(String sqlQ){
            try{
                ps = conn.prepareStatement(sqlQ);
                ps.setInt(1,persNum);
                rs = ps.executeQuery();
                int kol=0;
                if(rs.next())
                    kol=Integer.parseInt(rs.getString(1));
                mdbc.close(stmt);
                return kol;
            }
            catch(SQLException e){
                return -1;
            }
        }

        public void addItemInHandbook(String sqlItem){
            try {
                stmt= conn.createStatement();
                stmt.executeUpdate(sqlItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void updateStatusMeasure(String dat){
            String findStatus="update client_measure set statusMeasure='Закончено' where dateEndMeasure<'"+dat+"' and statusMeasure='Назначено'";
            try {
                stmt= conn.createStatement();
                stmt.executeUpdate(findStatus);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void createTable(ResultSet rs, String [] columns, final Class [] columnClass){
            try {
                ResultSetMetaData meta = rs.getMetaData();
                int numberOfColumns = meta.getColumnCount();

                dtm = new DefaultTableModel(rsm, columns) {
                    @Override
                    public boolean isCellEditable
                            (int row, int column) {
                        return false;
                    }

                    @Override
                    public Class<?> getColumnClass
                            (int columnIndex) {
                        return columnClass[columnIndex];
                    }
                };
                while (rs.next()) {
                    Object[] rowData = new
                            Object[numberOfColumns];
                    for (int i = 0; i < rowData.length; ++i) {
                        rowData[i] = rs.getObject(i + 1);
                    }
                    dtm.addRow(rowData);
                }
            }
            catch (SQLException e){
                System.out.println("Error createTable");
            }
        }
    }
