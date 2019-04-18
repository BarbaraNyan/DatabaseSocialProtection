package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

    public class TableModelClients extends DefaultTableModel {

        private DatabaseConnection mdbc;
        private java.sql.Statement stmt;
        private ResultSet rs;
        private Object[][] rsm;
        private DefaultTableModel dtm;

        public String[] columns;
        public Class[] columnClass;
        public String sqlQuery;

        public TableModelClients(){};

        public DefaultTableModel MyTableModel() {
            try {
                mdbc=new DatabaseConnection();
                Connection conn=mdbc.getMyConnection();
                stmt= conn.createStatement();
                rs = stmt.executeQuery(sqlQuery);
                ResultSetMetaData meta = rs.getMetaData();
                int numberOfColumns = meta.getColumnCount();
                dtm = new DefaultTableModel(rsm, columns) {
                    @Override
                    public boolean isCellEditable
                            (int row, int column)
                    {  return false;   }
                    @Override
                    public Class<?> getColumnClass
                            (int columnIndex)
                    {  return columnClass[columnIndex]; }
                };
                while (rs.next())
                {
                    Object [] rowData = new
                            Object[numberOfColumns];
                    for (int i = 0; i < rowData.length; ++i)
                    { rowData[i] = rs.getObject(i+1); }
                    dtm.addRow(rowData);
                }
                mdbc.close(stmt);
                return dtm;
            }
            catch(SQLException e){
                return null;
            }
        }
    }
