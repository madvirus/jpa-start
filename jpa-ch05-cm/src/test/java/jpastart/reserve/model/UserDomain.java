package jpastart.reserve.model;

import jpastart.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDomain {
    private static UserDomain instance = new UserDomain();

    public static UserDomain instance() {
        return instance;
    }

    public UserDomain givenUser(String email, String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtDelete = null;

        try {
            conn = DBUtil.getConnection();
            pstmtDelete = conn.prepareStatement("delete from user where email = ?");
            pstmtDelete.setString(1, email);
            pstmtDelete.executeUpdate();

            pstmt = conn.prepareStatement("insert into user values (?, ?, ?)");
            pstmt.setString(1, email);
            pstmt.setString(2, name);
            pstmt.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            return this;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(pstmtDelete);
            DBUtil.close(conn);
        }
    }

    public int countsByEmail(String email) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("select count(*) from user where email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            rs.next();
            count = rs.getInt(1);
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return count;
    }

}
