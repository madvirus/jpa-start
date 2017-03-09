package jpastart.reserve.model;

import jpastart.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserDomain {

    private static int countsByEmail(String email) {
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

    public static void assertUserExist(String email) {
        int count = countsByEmail(email);
        assertThat("user "+ email +" exists", count, equalTo(1));
    }


    public static void assertUserNotExist(String email) {
        int count = countsByEmail(email);
        assertThat("user "+ email +" not exists", count, equalTo(0));
    }

    public static void assertName(String email, String expectedName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement("select * from user where email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            assertTrue(rs.next());
            String realName = rs.getString("name");
            assertThat(expectedName, equalTo(realName));
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }
}
