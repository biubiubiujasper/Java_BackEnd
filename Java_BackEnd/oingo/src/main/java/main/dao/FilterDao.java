package main.dao;

import main.model.Filter;
import main.util.jdbc.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class FilterDao {

    static final Logger logger = LoggerFactory.getLogger(RequestDao.class);

    public Filter listFilter(int fid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from filter";
            if(fid!=-1){
                sql+=" where fid = " + fid;
            }else{
                sql+=" order by fid DESC";
            }
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Filter filter = new Filter(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getInt(4),rs.getString(5),rs.getInt(6),rs.getString(7),rs.getInt(8),rs.getInt(9),rs.getInt(10));
                return filter;
            }
            return null;
        } catch (Exception e) {
            logger.info("List users error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int addFilter(Filter filter) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "insert into filter values (null,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, filter.getBeginTime());
            pstmt.setString(2, filter.getEndTime());
            pstmt.setInt(3, filter.getIfSpecial());
            pstmt.setString(4, filter.getSpecialDate());
            pstmt.setInt(5, filter.getIfRepeat());
            pstmt.setString(6, filter.getRepeatDate());
            pstmt.setInt(7, filter.getIfFestival());
            pstmt.setInt(8, filter.getFesId());
            pstmt.setInt(9, filter.getRadius());

            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Add filter error:" + e);
            return 0;
        }  finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

}
