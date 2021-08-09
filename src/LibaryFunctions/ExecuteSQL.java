package LibaryFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExecuteSQL {

    public static ResultSet executeQuery(Connection con, String query) {

        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);

            return rs;
        } catch (Exception e) {
            System.out.println("Error in the ExecuteSQL class:" + e);
            return null;
        }
    }

    public static void executeUpdateQuery(Connection con, String query) {
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query);

        } catch (Exception e) {
            System.out.println("Error in the ExecuteSQL class:" + e);
        }
    }
}
