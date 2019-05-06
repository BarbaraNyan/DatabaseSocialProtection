package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteClient {
    private DatabaseConnection mdbc;
    private Connection conn;
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

    public void delete(){
        deleteOperAccOrg();
        deleteClient();
//        try {

//            String sqlDeleteBankOperAcc = "delete from bank_operating_account where numberOperatingAccount =\n" +
//                    "  (select oa.numberOperatingAccount from operating_account oa\n" +
//                    "  where oa.personalNumber=?)";
//            String sqlDeletePostOperAcc ="delete from post_operating_account where numberOperatingAccount=" +
//                    "(select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?)";
//            String sqlDeleteCashOperAcc ="delete from cash_operating_account where numberOperatingAccount =" +
//                    " (select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?)";
//            String checkBankOperAcc = "select exists(select numberOperatingAccount from bank_operating_account " +
//                    "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?))";
//            String checkPostOperAcc = "select exists(select numberOperatingAccount from post_operating_account " +
//                    "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?))";
//            String checkCashOperAcc = "select exists(select numberOperatingAccount  from cash_operating_account " +
//                    "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?))";

//            if(checkOperAcc(checkBankOperAcc) == 1) {
//                PreparedStatement psBank = conn.prepareStatement(sqlDeleteBankOperAcc);
//                psBank.setString(1, personalNumber);
//                psBank.executeUpdate();
//            }
//            if(checkOperAcc(checkPostOperAcc)==1) {
//                PreparedStatement psPost = conn.prepareStatement(sqlDeletePostOperAcc);
//                psPost.setString(1, personalNumber);
//                psPost.executeUpdate();
//            }
//            if(checkOperAcc(checkCashOperAcc)==1) {
//                PreparedStatement psCash = conn.prepareStatement(sqlDeleteCashOperAcc);
//                psCash.setString(1, personalNumber);
//                psCash.executeUpdate();
//            }
//            initializeSql();
//            String sqlDeleteOperAcc = "delete from operating_account where personalNumber=?";
//            String sqlDeleteIdDoc = "delete from identification_document where personalNumber=?";
//            String sqlDeleteAttDoc = "delete from attached_document where personalNumber=?";
//            String sqlDeleteIncome = "delete from income where personalNumber=?";
//            String sqlDeleteRelatives = "delete from relatives where personalNumber=?";
//            String sqlDeleteClientMeasure = "delete from client_measure where personalNumber=?";
//            String sqlDeleteClient = "delete from social_client where personalNumber=?";
//            String [] sqlDelete = {sqlDeleteOperAcc,sqlDeleteIdDoc,sqlDeleteAttDoc,sqlDeleteIncome,sqlDeleteRelatives,sqlDeleteClientMeasure,sqlDeleteClient};
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
    private void deleteOperAccOrg(){
        try {
            if (checkOperAcc(checkBankOperAcc) == 1) {
                PreparedStatement psBank = conn.prepareStatement(sqlDeleteBankOperAcc);
                psBank.setString(1, personalNumber);
                psBank.executeUpdate();
            }
            if (checkOperAcc(checkPostOperAcc) == 1) {
                PreparedStatement psPost = conn.prepareStatement(sqlDeletePostOperAcc);
                psPost.setString(1, personalNumber);
                psPost.executeUpdate();
            }
            if (checkOperAcc(checkCashOperAcc) == 1) {
                PreparedStatement psCash = conn.prepareStatement(sqlDeleteCashOperAcc);
                psCash.setString(1, personalNumber);
                psCash.executeUpdate();
            }
        }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int checkOperAcc(String sqlCheck){
        int check = 0;
        ResultSet rs;
        try {
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setString(1,personalNumber);
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
                "  where oa.personalNumber=?)";
        sqlDeletePostOperAcc ="delete from post_operating_account where numberOperatingAccount=" +
                "(select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?)";
        sqlDeleteCashOperAcc ="delete from cash_operating_account where numberOperatingAccount =" +
                " (select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?)";
        checkBankOperAcc = "select exists(select numberOperatingAccount from bank_operating_account " +
                "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?))";
        checkPostOperAcc = "select exists(select numberOperatingAccount from post_operating_account " +
                "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?))";
        checkCashOperAcc = "select exists(select numberOperatingAccount  from cash_operating_account " +
                "where numberOperatingAccount=(select oa.numberOperatingAccount from operating_account oa where oa.personalNumber=?))";

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
        deleteOperAccOrg();
        String sqlDelAttDoc = "delete from operating_account where numberOperatingAccount=?";
        params = new String [] {numberOperAcc};
        deleteOnce(1,sqlDelAttDoc,params);
    }
    public void deleteRelative(String relPersNum){
        String sqlDelRel = "delete from relatives where personalNumber=? and relativePersonalNumber=?";
        params = new String[]{personalNumber,relPersNum};
        deleteOnce(2,sqlDelRel,params);
    }
    public void deleteRelIdDoc(String relNum){
        String sqlDelRelIdDoc = "delete from identification_document where personalNumber=? and relativePersonalNumber=?";
        params = new String [] {personalNumber,relNum};
        deleteOnce(2,sqlDelRelIdDoc,params);
    }
}
