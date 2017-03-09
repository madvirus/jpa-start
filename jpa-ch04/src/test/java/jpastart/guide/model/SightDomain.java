package jpastart.guide.model;

import jpastart.util.DBUtil;

import java.sql.*;

public class SightDomain {
    private static final SightDomain domainInstance = new SightDomain();

    public static SightDomain instance() {
        return domainInstance;
    }

    public SightDomain truncate() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("truncate table sight");
            stmt.executeUpdate("truncate table sight_detail");
            return this;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(stmt);
            DBUtil.close(conn);
        }
    }

    public SightDomain initHorimSightWithDetail() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtDetail = null;
        ResultSet rsKey = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into sight (name, zipcode, address1, address2, eng_zipcode, eng_addr1, eng_addr2) " +
                    "values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, "호림박물관");
            pstmt.setString(2, "08772");
            pstmt.setString(3, "서울 관악구");
            pstmt.setString(4, "남부순환로152길 53");
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.VARCHAR);
            pstmt.setNull(7, Types.VARCHAR);
            pstmt.executeUpdate();
            rsKey = pstmt.getGeneratedKeys();
            rsKey.next();
            long key = rsKey.getLong(1);

            pstmtDetail = conn.prepareStatement("insert into sight_detail values (?, ?, ?, ?) ");
            pstmtDetail.setLong(1, key);
            pstmtDetail.setString(2, "오전10시-오후5시");
            pstmtDetail.setString(3, "매주 토요일, 일요일, 월요일");
            pstmtDetail.setString(4, "주차 요금 무료");
            pstmtDetail.executeUpdate();

            conn.commit();
            return this;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(rsKey);
            DBUtil.close(pstmt);
            DBUtil.close(pstmtDetail);
            DBUtil.close(conn);
        }
    }

    public SightDomain initGwanakMtSightWithoutDetail() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into sight (name, zipcode, address1, address2, eng_zipcode, eng_addr1, eng_addr2) " +
                    "values (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, "관악산 연주대");
            pstmt.setNull(2, Types.VARCHAR);
            pstmt.setString(3, "경기 과천시");
            pstmt.setString(4, "중앙동");
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.VARCHAR);
            pstmt.setNull(7, Types.VARCHAR);
            pstmt.executeUpdate();

            conn.commit();
            return this;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }

    }
}
