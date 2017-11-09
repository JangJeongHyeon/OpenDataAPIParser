package dalab.dao.population;

import java.sql.PreparedStatement;
import dalab.config.DB;
import dalab.vo.PopulationVo;

import java.sql.*;
import java.util.ArrayList;
/**
 * Created by Jang Jeong Hyeon on 2017-08-09.
 */
public class PopulationDao {
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Connection conn = null;

    public PopulationDao() {
        DB dbConfig = new DB();

        try {
            Class.forName(dbConfig.getDatabasePath());
            conn = DriverManager.getConnection(dbConfig.getURL(), dbConfig.getUSER(), dbConfig.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getSgisCode() {
        final String query = "SELECT siggcode FROM sgiscode";
        ArrayList<String> sgiscode = new ArrayList<String>();
        try {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sgiscode.add(String.valueOf(rs.getInt("siggcode")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sgiscode;
    }

    public ArrayList<String> getAgeType() {
        final String query = "SELECT code FROM agecode";
        ArrayList<String> ageCode = new ArrayList<String>();
        try {
            rs = pstmt.executeQuery(query);
            while (rs.next()) {
                int i = rs.getInt("code");
                if(i <= 9){
                    ageCode.add("0"+i);
                }else{
                    ageCode.add(i+"");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ageCode;
    }

    public void save(PopulationVo data){
        final String query = "INSERT INTO population_all(year, gender, adm_cd, adm_nm, avg_age, age_type, population) VALUES(?,?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,data.getYear());
            pstmt.setInt(2,data.getGender());
            pstmt.setInt(3,data.getAdmCode());
            pstmt.setString(4,data.getAdmName());
            pstmt.setFloat(5,data.getAvgAge());
            pstmt.setInt(6,data.getAgeType());
            pstmt.setInt(7,data.getPopulation());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
