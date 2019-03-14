package main.dao;

import main.model.Post;
import main.model.User;
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
public class PostDao {

    static final Logger logger = LoggerFactory.getLogger(PostDao.class);

    public List<Post> listPost(int uid) {
        List<Post> posts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from post";
            sql+= " where uid = "+uid;
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getInt(1),  rs.getInt(2), rs.getInt(3));
                posts.add(post);
            }
            return posts;
        } catch (Exception e) {
            logger.info("List posts error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int addPost(Post post) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "insert into post values (?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, post.getPid());
            pstmt.setInt(2, post.getNid());
            pstmt.setInt(3, post.getUid());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Add post error" + e);
            return 0;
        }  finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }
}
