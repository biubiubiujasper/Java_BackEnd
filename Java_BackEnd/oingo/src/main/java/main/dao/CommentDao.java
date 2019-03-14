package main.dao;

import main.model.Comment;
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
public class CommentDao {

    static final Logger logger = LoggerFactory.getLogger(CommentDao.class);

    public List<Comment> listComment(int nid) {
        List<Comment> comments = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from comment where nid = "+nid;
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(rs.getInt(1),  rs.getInt(2), rs.getString(3),rs.getString(4));
                comments.add(comment);
            }
            return comments;
        } catch (Exception e) {
            logger.info("List comment error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int addComment(Comment comment) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "insert into comment values (null,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, comment.getNid());
            pstmt.setString(2, comment.getTime());
            pstmt.setString(3, comment.getContent());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Add comment error" + e);
            return 0;
        }  finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int deleteComment(int cid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from comment where cid=" + cid;
        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Delete comment error:" + e);
            return 0;
        }finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }
}
