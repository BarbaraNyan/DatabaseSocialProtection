package db;

import java.sql.*;

public class EditClient {

    private DatabaseConnection mdbc;
    private Connection conn;
    private PreparedStatement ps;

    public EditClient(){
            try {
                mdbc=new DatabaseConnection();
                conn=mdbc.getMyConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

//Редактирование данных
    public void update(String sql, Object [] params){
        try {
            int i = 1;
            ps = conn.prepareStatement(sql);
            for(Object param: params){
                if(param instanceof java.lang.Integer){
                    ps.setInt(i,(Integer)param);
                }
                else if (param instanceof java.lang.String){
                    ps.setString(i,(String)param);
                }
                i++;
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
