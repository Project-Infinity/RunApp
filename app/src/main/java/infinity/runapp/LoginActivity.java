package infinity.runapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity {
    EditText email;
    EditText password;
    TextView error;
    Button loginBtn;


    //URL to get JSON Array
    private static String url = "http://cgi.soic.indiana.edu/~team36/dbtest/getUsers.php";
    //JSON Node Names
    private static final String TAG_USERS = "users";
    private static final String TAG_ID = "userID";
    private static final String TAG_FIRST_NAME = "fName";
    private static final String TAG_LAST_NAME = "lName";
    private static final String TAG_EMAIL = "userEmail";
    private static final String TAG_PASSWORD = "pswd";

    JSONArray user = null;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                email = (EditText)findViewById(R.id.email);
                password = (EditText)findViewById(R.id.password);
                error = (TextView)findViewById(R.id.error);

                new JSONParse().execute();
            }
        });
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>{
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();


            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Logging In..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args){
            JSONParser jParser = new JSONParser();

            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json){
            pDialog.dismiss();
            try{
                // Getting JSON Array
                user = json.getJSONArray(TAG_USERS);
                for (int i = 0; i < user.length(); i++)
                {
                    JSONObject c = user.getJSONObject(i);
                    if(c.getString(TAG_EMAIL).equals(email.getText().toString().toUpperCase()))
                    {
                        ID = i;
                    }
                }
                JSONObject c = user.getJSONObject(ID);
                if(c.getString(TAG_PASSWORD).equals(password.getText().toString()))
                {
                    goApp(ID);
                }
                else
                {
                    error.setText("Invalid login information");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void goApp(int id){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SESSION_ID", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
