package com.medveckis.util;

import com.medveckis.model.Adrese;
import com.medveckis.model.SkolasBiedrs;
import com.medveckis.model.Skolnieks;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DatabaseUtils {

    private static final String connectionName = "jdbc:oracle:thin:@85.254.218.229:1521:DITF11";
    private static final String username = "DB_171RDB030";
    private static final String password = "DB_171RDB030";

    private static final String GET_ALL_SKOLNIEKS = "SELECT * FROM skolnieki";
    private static final String INSERT_INTO_SKOLNIEKS = "INSERT INTO skolnieki VALUES(?, skolasbiedrs(?, ?, ?, ?, ?, null), ?)";
    private static final String INSERT_INTO_ADRESES = "INSERT INTO adreses VALUES(adrese(?, ?, ?, ?, ?, ?))";
    private static final String INSRT_ADRESE_INTO_SKOLNIEKS = "declare\n" +
            "ats REF adrese;\n" +
            "BEGIN\n" +
            "SELECT REF(A) INTO ats FROM adreses A WHERE A.adrese_id = ?;\n" +
            "UPDATE skolnieki S SET S.skolnieks.dzives_vieta = ats WHERE S.skolnieks_id = ?;\n" +
            "END;";
    private static final String DELETE_SKOLNIEKS = "DELETE FROM skolnieki WHERE skolnieks_id=?";


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionName, username, password);
    }

    public static List<Skolnieks> getAllRowsFromSKOLNIEKI() {
        List<Skolnieks> skolnieksList = new ArrayList<>();
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(GET_ALL_SKOLNIEKS);
            while (resultSet.next()) {
                Skolnieks skolnieks = new Skolnieks();
                skolnieks.setId(Integer.parseInt(resultSet.getObject(1).toString()));
                skolnieks.setSkolnieksInfo(getSkolasBiedrsFromStruct((Struct) resultSet.getObject(2)));
                skolnieks.getSkolnieksInfo().setDzivesVieta(getAdreseFromStruct((Struct) ((Ref)
                        ((Struct) resultSet.getObject(2)).getAttributes()[5]).getObject()));
                skolnieks.setKlaseId(Integer.parseInt(resultSet.getObject(3).toString()));
                skolnieksList.add(skolnieks);
            }

            return skolnieksList;
        } catch (SQLException e) {
            System.err.format("SQL Status: %s\n%s", e.getSQLState(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public static boolean insertIntoSkolniekiTable(Skolnieks skolnieks) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            PreparedStatement stmtSkolnieks = conn.prepareStatement(INSERT_INTO_SKOLNIEKS);

            stmtSkolnieks.setBigDecimal(1, new BigDecimal(skolnieks.getId()));
            stmtSkolnieks.setBigDecimal(2, new BigDecimal(skolnieks.getSkolnieksInfo().getId()));
            stmtSkolnieks.setString(3, skolnieks.getSkolnieksInfo().getVards());
            stmtSkolnieks.setString(4, skolnieks.getSkolnieksInfo().getUzvards());
            stmtSkolnieks.setString(5, skolnieks.getSkolnieksInfo().getePast());
            stmtSkolnieks.setString(6, skolnieks.getSkolnieksInfo().getTelefonaNumurs());
            stmtSkolnieks.setBigDecimal(7, new BigDecimal(skolnieks.getKlaseId()));

            stmtSkolnieks.executeUpdate();

            if (Objects.nonNull(skolnieks.getSkolnieksInfo().getDzivesVieta())) {
                PreparedStatement stmtAdrese = conn.prepareStatement(INSERT_INTO_ADRESES);
                CallableStatement cstmtAdrese = conn.prepareCall(INSRT_ADRESE_INTO_SKOLNIEKS);

                stmtAdrese.setBigDecimal(1, new BigDecimal(skolnieks.getSkolnieksInfo().getDzivesVieta().getId()));
                stmtAdrese.setBigDecimal(2, new BigDecimal(skolnieks.getSkolnieksInfo().getDzivesVieta().getDzivoklaNumurs()));
                stmtAdrese.setString(3, skolnieks.getSkolnieksInfo().getDzivesVieta().getIela());
                stmtAdrese.setString(4, skolnieks.getSkolnieksInfo().getDzivesVieta().getPilseta());
                stmtAdrese.setString(5, skolnieks.getSkolnieksInfo().getDzivesVieta().getValst());
                stmtAdrese.setString(6, skolnieks.getSkolnieksInfo().getDzivesVieta().getPastaIndeks());

                stmtAdrese.executeUpdate();

                cstmtAdrese.setBigDecimal(1, new BigDecimal(skolnieks.getSkolnieksInfo().getDzivesVieta().getId()));
                cstmtAdrese.setBigDecimal(2, new BigDecimal(skolnieks.getId()));

                cstmtAdrese.execute();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.err.format("SQL Status: %s\n%s", e.getSQLState(), e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                Objects.requireNonNull(conn).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteSkolnieks(int id) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(DELETE_SKOLNIEKS);
            stmt.setBigDecimal(1, new BigDecimal(id));
            int res = stmt.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static SkolasBiedrs getSkolasBiedrsFromStruct(Struct struct) throws SQLException {
        SkolasBiedrs skolasBiedrs = new SkolasBiedrs();

        skolasBiedrs.setId(Integer.parseInt(struct.getAttributes()[0].toString()));
        skolasBiedrs.setVards(struct.getAttributes()[1].toString());
        skolasBiedrs.setUzvards(struct.getAttributes()[2].toString());
        skolasBiedrs.setePast(struct.getAttributes()[3].toString());
        skolasBiedrs.setTelefonaNumurs(struct.getAttributes()[4].toString());

        return skolasBiedrs;
    }

    private static Adrese getAdreseFromStruct(Struct struct) throws SQLException {
        Adrese adrese = new Adrese();

        adrese.setId(Integer.parseInt(struct.getAttributes()[0].toString()));
        adrese.setDzivoklaNumurs(Integer.parseInt(struct.getAttributes()[1].toString()));
        adrese.setIela(struct.getAttributes()[2].toString());
        adrese.setPilseta(struct.getAttributes()[3].toString());
        adrese.setValst(struct.getAttributes()[4].toString());
        adrese.setPastaIndeks(struct.getAttributes()[5].toString());

        return adrese;
    }
}
