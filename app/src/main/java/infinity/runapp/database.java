package infinity.runapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database extends ActionBarActivity {

    private TextView mConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getEmail(View view){

        mConnect = (TextView)findViewById(R.id.text2);
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "db.soic.indiana.edu/caps15_team36";
        String user = "caps15_team36";
        String pass = "my+sql=caps15_team36";

        try{
            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
            rs = st.executeQuery("SELECT userEmail FROM tblUsers WHERE userID = 1;");

            mConnect.setText(rs.toString());

        } catch (SQLException ex){

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

            }
        }
    }
}
