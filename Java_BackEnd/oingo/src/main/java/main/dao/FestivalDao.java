package main.dao;

import main.model.Festival;
import main.model.Filter;
import main.util.jdbc.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FestivalDao {

    static final Logger logger = LoggerFactory.getLogger(RequestDao.class);

    public List<Festival> listFestival(int fesid) {
        List<Festival> festivals = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from festival";
            if(fesid>-1){
                sql+=" where fes_id = " + fesid;
            }
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Festival festival = new Festival(rs.getInt(1), rs.getString(2),rs.getString(3));
                festivals.add(festival);
            }
            return festivals;
        } catch (Exception e) {
            logger.info("List festival error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }
}
