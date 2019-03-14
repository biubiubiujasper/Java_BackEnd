package main.dao;

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
public class UserDao {

    static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public List<User> listUser(String name) {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from user";
            if(name!=null){
                sql+= " where name = '"+name+"'";
            }
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(Integer.parseInt(rs.getString(1)),  rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            logger.info("List users error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public User listUser(int uid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from user";
            sql+= " where uid = "+uid;
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(Integer.parseInt(rs.getString(1)),  rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5));
                return user;
            }
            return null;
        } catch (Exception e) {
            logger.info("List users error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int addUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "insert into user values (null,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getFriends());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Add user error" + e);
            return 0;
        }  finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int updateUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "update user set ";
            if (user.getName() != null) {
                sql = sql + "name = '" + user.getName();
            }
            if (user.getEmail() != null) {
                sql = sql + "', email = '" + user.getEmail();
            }
            if (user.getPassword() != null) {
                sql = sql + "' , pwd = '" + user.getPassword();
            }
            if (user.getFriends() != null) {
                sql = sql + "', friends = '" + user.getFriends();
            }

            sql = sql + "' where uid = " + user.getId();
            pstmt = conn.prepareStatement(sql);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Update user error" + e);
            return 0;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int deleteUser(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from user where uid=" + id;
        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Delete user error:" + e);
            return 0;
        }finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

}
