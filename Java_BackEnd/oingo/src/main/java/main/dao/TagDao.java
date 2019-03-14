package main.dao;

import main.model.Tag;
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
public class TagDao {

    static final Logger logger = LoggerFactory.getLogger(TagDao.class);

    public List<Tag> listTag(int nid) {
        List<Tag> tags = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from tag";
            sql+=" where nid = " + nid;
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag(rs.getInt(1), rs.getInt(2),rs.getString(3));
                tags.add(tag);
            }
            return tags;
        } catch (Exception e) {
            logger.info("List tags error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int addTag(Tag tag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "insert into tag values (null,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, tag.getNid());
            pstmt.setString(2, tag.getName());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Add tag error" + e);
            return 0;
        }  finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

}
