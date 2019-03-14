package main.dao;

import main.model.Note;
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
public class NoteDao {

    static final Logger logger = LoggerFactory.getLogger(RequestDao.class);

    public List<Note> listNote(int nid) {
        List<Note> notes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from note";
            if(nid!=-1){
                sql+=" where nid = " + nid;
            }else{
                sql+=" order by nid DESC";
            }
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Note note = new Note(rs.getInt(1), rs.getString(2),rs.getInt(3), rs.getInt(4),Double.parseDouble(rs.getString(5)),Double.parseDouble(rs.getString(6)),rs.getString(7),rs.getString(8));
                notes.add(note);
            }
            return notes;
        } catch (Exception e) {
            logger.info("List users error:" + e);
            return null;
        } finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

    public int addNote(Note note) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "insert into note values (null,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, note.getContent());
            pstmt.setInt(2, note.getUid());
            pstmt.setInt(3, note.getFid());
            pstmt.setString(4, String.valueOf(note.getLongitude()));
            pstmt.setString(5, String.valueOf(note.getLatitude()));
            pstmt.setString(6, note.getRange());
            pstmt.setString(7, note.getTime());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            logger.info("Add note error" + e);
            return 0;
        }  finally {
            JDBCUtil.release(conn, pstmt, null);
        }
    }

}
