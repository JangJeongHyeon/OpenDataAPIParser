package dalab.dao.medicine;

import dalab.config.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by Jang Jeong Hyeon on 2017-08-05.
 */
public class MedicineAreaDao {
    private java.sql.PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Connection conn = null;

    public MedicineAreaDao() {
        DB dbConfig = new DB();

        try {
            Class.forName(dbConfig.getDatabasePath());
            conn = DriverManager.getConnection(dbConfig.getURL(), dbConfig.getUSER(), dbConfig.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveAddressInfo(String sidoCd, String sidoCdNm, String sgguCd, String sgguCdNm){
        String query = "INSERT INTO medicineaddress(sidoCd, sidoCdNm, sgguCd, sgguCdNm) VALUES(?,?,?,?)";
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(sidoCd));
            pstmt.setString(2,sidoCdNm);
            pstmt.setInt(3,Integer.parseInt(sgguCd));
            pstmt.setString(4,sgguCdNm);
            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
