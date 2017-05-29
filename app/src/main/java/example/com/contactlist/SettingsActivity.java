package example.com.contactlist;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {


    static String updateURL = "https://drive.google.com/uc?export=download&id=0B7_boZFjYrFGejRLQWRWSWJoZ0U";

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }




    public void updateContactInformationFromWebLink(View v) {

        new GetContacts().execute();
    }



    public void showUpdateContactFileUrl(View v) {

        EditText txt = (EditText) findViewById(R.id.update_url);
        if (txt.getVisibility() == View.INVISIBLE){
            txt.setVisibility(View.VISIBLE);
            findViewById(R.id.save_url_btn).setVisibility(View.VISIBLE);
        }

        txt.getText().clear();
        txt.getText().append(updateURL);

        txt.setText(updateURL, TextView.BufferType.EDITABLE);



    }

    public void updateContactFileUrl(View v) {

        EditText txt = (EditText) findViewById(R.id.update_url);
        if (txt.getVisibility() == View.VISIBLE) {
            txt.setVisibility(View.INVISIBLE);
            findViewById(R.id.save_url_btn).setVisibility(View.INVISIBLE);
        }

        updateURL = txt.getText().toString();

    }



    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SettingsActivity.this,"Contact Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(updateURL);

            //


            /*
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(jsonStr);

                String[] gpNameList = jsonObj.getString(MainActivity.GRAM_PANCHAYAT_NAME_LIST).split(",");
                String[] departmentDetailTypeNameList = jsonObj.getString(MainActivity.CONTACT_TYPES).split(",");

                for (int i = 0 ; i< gpNameList.length ; i++)
                {
                    for (int j = 0; j < departmentDetailTypeNameList.length; j++ )
                    {
                        if(jsonObj.getString(departmentDetailTypeNameList[j]).toLowerCase().contains(gpNameList[i].toLowerCase()))
                        {

                        }
                    }

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }



               */
            //
            MainActivity.writeToFile("contacts.txt",jsonStr,getApplicationContext());

            Log.i(TAG, "Response from url: " + jsonStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.i(TAG,"dddddddddddddddd");

        }
    }




    ///

    public void sendSMSToUs(View v) {

        EditText txtSMS = (EditText) findViewById(R.id.write_sms);

        Log.i(TAG,"SMS:::"+txtSMS.getText());
        Uri sms_uri = Uri.parse("smsto:+91-"+MainActivity.DOITC_SMS_MOBILE_NUMBER);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", txtSMS.getText().toString());

        startActivity(sms_intent);

    }

}
