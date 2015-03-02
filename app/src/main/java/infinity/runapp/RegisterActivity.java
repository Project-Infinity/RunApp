package infinity.runapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADC on 3/2/2015.
 */
public class RegisterActivity extends ActionBarActivity
{
    EditText regEmail, regPass1, regPass2, regLName, regFName;
    TextView error, login;

    Button register;

    ProgressDialog pDiaglog;

    JSONParser jsonParser = new JSONParser();

    private static final String Url = "http://cgi.soic.indiana.edu/~team36/testdb/register.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button)findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regEmail = (EditText)findViewById(R.id.regEmail);
                regPass1 = (EditText)findViewById(R.id.regPassword);
                regPass2 = (EditText)findViewById(R.id.regPassword);
                regFName = (EditText)findViewById(R.id.regFName);
                regLName = (EditText)findViewById(R.id.regLName);

                new JSONParse().execute();
            }
        });
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Registering..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            JSONObject json = jParser.getJSONFromUrl(Url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json){
            pDialog.dismiss();
            String email = regEmail.getText().toString().toUpperCase();
            String pass1 = regPass1.getText().toString();
            String pass2 = regPass2.getText().toString();
            String fname = regFName.getText().toString();
            String lname = regLName.getText().toString();
            if (pass1.equals(pass2)) {
                try {
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("userEmail", email));
                    params.add(new BasicNameValuePair("pswd", pass1));
                    params.add(new BasicNameValuePair("fName", fname));
                    params.add(new BasicNameValuePair("lName", lname));

                    json = jsonParser.getJSONFromUrl(Url);
                } catch (JSONException e){}
            }
            else
            {
                error.setText("Passwords do not match.");
            }
        }
    }
}
