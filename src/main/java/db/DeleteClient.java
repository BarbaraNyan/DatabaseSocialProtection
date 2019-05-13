package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteClient {
    private DatabaseConnection mdbc;
    private Connection conn;
    private java.sql.Statement stmt;
    private String personalNumber;
    String sqlDeleteOperAcc;
    String sqlDeleteIdDoc;
    String sqlDeleteAttDoc;
    String sqlDeleteIncome;
    String sqlDeleteRelatives;
    String sqlDeleteClientMeasure;
    String sqlDeleteClient;
    String [] sqlDelete;
    String sqlDeleteBankOperAcc;
    String sqlDeletePostOperAcc;
    String sqlDeleteCashOperAcc;
    String checkBankOperAcc;
    String checkPostOperAcc;
    String checkCashOperAcc;
    String [] params;

    public DeleteClient(){
        try {
            mdbc=new DatabaseConnection();
            conn=mdbc.getMyConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DeleteClient(String persNum){
        try {
            mdbc=new DatabaseConnection();
            conn=mdbc.getMyConnection();
            personalNumber = persNum;
            initializeSql();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String numberOperAcc){
        deleteOperAccOrg(numberOperAcc);
        deleteClient();
    }
    private void deleteOperAccOrg(String numOperAcc){
        try {
            if (checkOperAcc(checkBankOperAcc,numOperAcc) == 1) {
                PreparedStatement psBank = conn.prepareStatement(sqlDeleteBankOperAcc);
                psBank.setString(1, numOperAcc);
                psBank.executeUpdate();
            }
            if (checkOperAcc(checkPostOperAcc,numOperAcc) == 1) {
                PreparedStatement psPost = conn.prepareStatement(sqlDeletePostOperAcc);
                psPost.setString(1, numOperAcc);
                psPost.executeUpdate();
            }
            if (checkOperAcc(checkCashOperAcc,numOperAcc) == 1) {
                PreparedStatement psCash = conn.prepareStatement(sqlDeleteCashOperAcc);
                psCash.setString(1, numOperAcc);
                psCash.executeUpdate();
            }
        }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int checkOperAcc(String sqlCheck,String numOperAcc){
        int check = 0;
        ResultSet rs;
        try {
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setString(1,numOperAcc);
            rs = psCheck.executeQuery();
            if(rs.next())
                check = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    private void deleteClient(){
        try {
            PreparedStatement psClient;
            for(String sqlDel:sqlDelete){
                psClient = conn.prepareStatement(sqlDel);
                psClient.setString(1,personalNumber);
                psClient.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeSql(){
        sqlDeleteBankOperAcc = "delete from bank_operating_account where numberOperatingAccount =\n" +
                "  (select oa.numberOperatingAccount from operating_account oa\n" +
                "  where oa.numberOperatingAccount=?)";
        sqlDeletePostOperAcc ="delete from post_operating_account where numberOperatingAccount=" +
                "(select oa.numberOperatingAccount from operating_account oa where oa.numberOperatingAccount=?)";
        sqlDeleteCashOperAcc ="delete from cash_operating_account where numberOperatingAccount =" +
                " (select oa.numberOperatingAccount from operating_account oa where oa.numberOperatingAccount=?)";
        checkBankOperAcc = "select exists(select numberOperatingAccount from bank_operating_account " +
                "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.numberOperatingAccount=?))";
        checkPostOperAcc = "select exists(select numberOperatingAccount from post_operating_account " +
                "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.numberOperatingAccount=?))";
        checkCashOperAcc = "select exists(select numberOperatingAccount  from cash_operating_account " +
                "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.numberOperatingAccount=?))";

        sqlDeleteOperAcc = "delete from operating_account where personalNumber=?";
        sqlDeleteIdDoc = "delete from identification_document where personalNumber=?";
        sqlDeleteAttDoc = "delete from attached_document where personalNumber=?";
        sqlDeleteIncome = "delete from income where personalNumber=?";
        sqlDeleteRelatives = "delete from relatives where personalNumber=?";
        sqlDeleteClientMeasure = "delete from client_measure where personalNumber=?";
        sqlDeleteClient = "delete from social_client where personalNumber=?";
        sqlDelete = new String[]{sqlDeleteOperAcc,sqlDeleteIdDoc,sqlDeleteAttDoc,sqlDeleteIncome,sqlDeleteRelatives,sqlDeleteClientMeasure,sqlDeleteClient};
    }

    private void deleteOnce(int type,String sqlDeleteQuery,String [] params){
        try {
        PreparedStatement ps = conn.prepareStatement(sqlDeleteQuery);
        switch(type){
            case 1:
                ps.setString(1,params[0]);
                break;
            case 2:
                ps.setString(1,params[0]);
                ps.setString(2,params[1]);
                break;
        }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteIdDoc(String idDocSeries, String idDocNumber){
        String sqlDelIdDoc = "delete from identification_document where docSeries=? and docNumber=?";
        params = new String [] {idDocSeries,idDocNumber};
        deleteOnce(2,sqlDelIdDoc,params);
    }
    public void deleteAttDoc(String numAttDoc){
        String sqlDelAttDoc = "delete from attached_document where numberAttachedDocument=?";
        params = new String [] {numAttDoc};
        deleteOnce(1,sqlDelAttDoc,params);
    }
    public void deleteOperAcc(String numberOperAcc){
        initializeSql();
        deleteOperAccOrg(numberOperAcc);
        String sqlDelOperAcc = "delete from operating_account where numberOperatingAccount=?";
        params = new String [] {numberOperAcc};
        deleteOnce(1,sqlDelOperAcc,params);
    }
    public void deleteRelative(String relPersNum){
        String sqlDelRel = "delete from relatives where personalNumber=? and relativePersonalNumber=?";
        params = new String[]{personalNumber,relPersNum};
        deleteOnce(2,sqlDelRel,params);
    }
    public void deleteItemInGlossary(String sqlItem,String item){
        try {
            PreparedStatement ps = conn.prepareStatement(sqlItem);
            ps.setString(1,item);
            ps.executeUpdate();
//
//            stmt= conn.createStatement();
//            stmt.executeUpdate(sqlItem);
//            mdbc.close(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
