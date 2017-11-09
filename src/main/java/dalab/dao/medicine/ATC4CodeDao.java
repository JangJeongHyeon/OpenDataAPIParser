package dalab.dao.medicine;

import dalab.common.Pair;
import dalab.config.DB;
import dalab.vo.ATC4CodeVo;
import dalab.vo.ATC4StepAPIVo;
import dalab.vo.SGISCode;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Jang Jeong Hyeon on 2017-09-14.
 */
public class ATC4CodeDao {
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Connection conn = null;

    /**
     *  Default Constructor
     *  It wil be find information of database configuration from properties file
     */
    public ATC4CodeDao() {
        DB dbConfig = new DB();

        try {
            Class.forName(dbConfig.getDatabasePath());
            conn = DriverManager.getConnection(dbConfig.getURL(), dbConfig.getUSER(), dbConfig.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Save data that parsing from medicine api to database
     * @param vo - Value object of ATCStep4Api
     */
    public void save(ATC4StepAPIVo vo){
        final String query = "INSERT INTO atc_api(atcStep4Cd, atcStepCdNm, diagYm, insupTpCd, msupUseAmt, recuClCd, sgguCd, sgguCdNm, sidoCdNm, totUseQty) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(query);

            // Generate insert query of ATCStep4API parsing data
            pstmt.setString(1,vo.getAtcCode());
            pstmt.setString(2,vo.getAtcCodeName());
            pstmt.setString(3,vo.getDate());
            pstmt.setInt(4,vo.getInsuranceType());
            pstmt.setInt(5,vo.getMoneyOfUseAmount());
            pstmt.setInt(6,vo.getRecuClcd());
            pstmt.setInt(7,vo.getSgguCd());
            pstmt.setString(8,vo.getSgguCdNm());
            pstmt.setString(9,vo.getSidoCdNm());
            pstmt.setFloat(10,vo.getTotalUseQuantity());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Get SidoCode and Sigungu Code from database
     * @return The list of SGISCode object
     */
    public ArrayList<SGISCode> getSgisCode(){
        final String query = "SELECT sidoCd, sgguCd FROM medicineaddress";
        ArrayList<SGISCode> list = new ArrayList<SGISCode>();
        try{
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(new SGISCode(String.valueOf(rs.getInt("sidoCd")),String.valueOf(rs.getInt("sgguCd"))));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get all of ATC code and ATC code name;
     * @return list of ATC information
     * R03 - 천식
     */
    public ArrayList<Pair<String,String>> getATCInfo(){
        final String query = "SELECT DISTINCT atcCode, atcCodeName FROM atc_code WHERE length(atcCode) = 5 AND atcCode LIKE 'R05%'";
        ArrayList<Pair<String, String>> list = new ArrayList<Pair<String, String>>();
        try{
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(new Pair<String, String>(rs.getString("atcCode"),rs.getString("atcCodeName")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
