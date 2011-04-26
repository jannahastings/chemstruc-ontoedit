package uk.ac.ebi.chebi.ontology.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection
                ("jdbc:oracle:thin:@localhost:1521:orcl", "chebi", "chebi");
        return conn;
    }
}
