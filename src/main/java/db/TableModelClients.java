package db;

import java.sql.*;
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

        public String sqlQuery;
        public String sqlPreparedStatement;
        public int persNum;

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

        public DefaultTableModel MyTableModelReports(int type) {
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sqlQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            switch(type) {
                case 1: createTable(rs, columnsSalRep, columnClassSalRep);
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
