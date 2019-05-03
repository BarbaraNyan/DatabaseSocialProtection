package db;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private String stringToDate(String param){
//        String date = new SimpleDateFormat("yyyy-MM-dd").format(param);
//        return date;
        String [] date = param.split("\\.");
        String newDate = date[2]+"-"+date[1]+"-"+date[0];
        return newDate;
    }

    private static boolean isDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

//    public String quotate(String content){
//        return "'"+content+"'";
//    }

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
                    if(isDate((String)param))
                        ps.setString(i,stringToDate((String) param));
                    else
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
