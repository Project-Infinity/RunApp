package infinity.runapp;

import junit.runner.Version;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ADC on 2/16/2015.
 */
public class DatabaseConnection
{
    public static void main(String[] args)
    {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql//db.soic.indiana.edu/caps15_team36";

        String user = "caps15_team36";

        String pass = "my+sql=caps15_team36";

        try{
            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException ex){
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (st != null){
                    st.close();
                }
                if (con != null){
                    con.close();
                }
            } catch (SQLException ex){
                Logger lgr = Logger.getLogger(Version.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
