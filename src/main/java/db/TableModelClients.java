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


        //для таблицы charge
        public String[] columnsChargeReq;
        public Class[] columnClassChargeReq;
        String ben;
        String cat;

        //для таблицы(справочнка) категория льготника
        public String[] columnsSCCategory;
        public Class[] columnClassSCCategory;

        //для таблицы(справочнка) категория льготы
        public String[] columnsBenCategory;
        public Class[] columnClassBenCategory;

        //для таблицы(справочнка) тип категории льготы
        public String[] columnsTypeBenCategory;
        public Class[] columnClassTypeBenCategory;

        //для таблицы(справочнка) закон
        public String[] columnsLaw;
        public Class[] columnClassLaw;

        //для таблицы(справочнка) версия статьи
        public String[] columnsVersionArt;
        public Class[] columnClassVersionArt;

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
                    case 2: // категория льготы
                        createTable(rs,columnsBenCategory,columnClassBenCategory);
                        break;
                    case 3: // тип категории льготы
                        createTable(rs,columnsTypeBenCategory,columnClassTypeBenCategory);
                        break;
                    case 4: // закон
                        createTable(rs,columnsLaw,columnClassLaw);
                        break;
                    case 5: // версия статьи
                        createTable(rs,columnsVersionArt,columnClassVersionArt);
                        break;
                    case 6: // статья
                        createTable(rs,columnsArticle,columnClassArticle);
                        break;
                    case 7: // Индекс
                        createTable(rs,columnsIndex,columnClassIndex);
                        break;
                    case 8: // Регион
                        createTable(rs,columnsRegion,columnClassRegion);
                        break;
                    case 9: // Район
                        createTable(rs,columnsDistrict,columnClassDistrict);
                        break;
                    case 10: // Пункт
                        createTable(rs,columnsLocality,columnClassLocality);
                        break;
                    case 11: // Улица
                        createTable(rs,columnsStreet,columnClassStreet);
                        break;
                    case 12: // Родственники
                        createTable(rs,columnsRelation,columnClassRelation);
                        break;
                    case 13: // Доход
                        createTable(rs,columnsTypeIncome,columnClassTypeIncome);
                        break;
                    case 14: // Документ идентификатор
                        createTable(rs,columnsTypeIndDoc,columnClassTypeIndDoc);
                        break;
                    case 15: // Документ
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
                }

                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
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
