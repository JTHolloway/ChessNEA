package LibaryFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExecuteSQL {

    /**
     * Executes SQL queries and communicates with the database
     *
     * @param con   The connection to the database
     * @param query The SQL query to be executed
     * @return a Result set of all results returned by the database
     */
    public static ResultSet executeQuery(Connection con, String query) {

        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            stmt.close();

            return rs;
        } catch (Exception e) {
            System.out.println("Error in the ExecuteSQL class:" + e);
            return null;
        }
    }

    /**
     * Exectues an SQL update query which updates data which is already contained in the database. Therefore nothing is returned.
     *
     * @param con   The connection to the database
     * @param query The SQL query to be executed
     */
    public static void executeUpdateQuery(Connection con, String query) {
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query);

        } catch (Exception e) {
            System.out.println("Error in the ExecuteSQL class:" + e);
        }
    }
}
