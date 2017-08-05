package dao;

import com.mysql.jdbc.PreparedStatement;
import config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by Jang Jeong Hyeon on 2017-08-05.
 */
public class DAO {
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Connection conn = null;

    public DAO() {
        Configuration dbConfig = new Configuration();

        try {
            Class.forName(dbConfig.getDatabasePath());
            conn = DriverManager.getConnection(dbConfig.getURL(), dbConfig.getUSER(), dbConfig.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
