package main.dao;

import main.model.Request;
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
public class RequestDao {

    static final Logger logger = LoggerFactory.getLogger(RequestDao.class);

    public List<Request> listRequest(int askId,int answerId) {
        List<Request> requests = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from request";
            if(askId != -1){
                sql+=" where ask_uid = " + askId;
            }
            if(answerId != -1){
                sql+=" where answer_uid = " + answerId;
            }
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Request request = new Request(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getInt(4));
                requests.add(request);
            }
            return requests;
        } catch (Exception e) {
            logger.info("List users error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int addRequest(Request request) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "insert into request values (?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, request.getAskId());
            pstmt.setInt(2, request.getAnswerId());
            pstmt.setString(3, request.getRequestTime());
            pstmt.setInt(4, request.getStatus());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Add request error" + e);
            return 0;
        }  finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int updateRequest(Request request) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "update request set ";
            sql = sql + "status = " + request.getStatus();
            sql = sql + " where ask_uid = " + request.getAskId() +
            " and request_time = '" + request.getRequestTime()+"'";
            pstmt = conn.prepareStatement(sql);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Update request error" + e);
            return 0;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

}
