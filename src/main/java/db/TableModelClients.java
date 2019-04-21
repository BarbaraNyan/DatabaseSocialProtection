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
            catch (SQLException e){}
        }
    }
