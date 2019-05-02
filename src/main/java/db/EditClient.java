package db;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class EditClient {

    private DatabaseConnection mdbc;
    private Connection conn;
    private java.sql.Statement stmt;
    private ResultSet rs;
    private Object[][] rsm;
    private DefaultTableModel dtm;
    private PreparedStatement ps;

    public EditClient(){
            try {
                mdbc=new DatabaseConnection();
                conn=mdbc.getMyConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public String quotate(String content){
        return "'"+content+"'";
    }
//Перс.данные без даты и без пола
    public void update(String sql, String [] params){
        try {
            int i = 1;
            ps = conn.prepareStatement(sql);
            for(String param: params){
                ps.setString(i,param);
                i++;
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
