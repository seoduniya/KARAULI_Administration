package example.com.contactlist;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


import android.app.DownloadManager.Request;


import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {



    public final static String DOITC_SMS_MOBILE_NUMBER = "9910880165";

    //
    private ListView listV;
    ArrayAdapter<String> adapter;




    //
    final static String EXTRA_CONTACT_DETAIL_TYPE = "EXTRA_CONTACT_DETAIL_TYPE";
    final static String EXTRA_CONTACT_NAME_TYPE = "EXTRA_CONTACT_NAME_TYPE";




    private long enqueue;
    private DownloadManager dm;


    ///////////

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

    //

    static String[] departmentDetailTypeNameList = null;
    static String[] departmentNameTypeList = null;
    static String[] departmentDisplayNameList = null;

    final static String CONTACT_TYPES = "CONTACT_TYPES";
    final static String CONTACT_NAME_TYPES = "CONTACT_NAME_TYPES";
    final static String CONTACT_DISPLAY_NAME_TYPES = "CONTACT_DISPLAY_NAME_TYPES";

    final static String GRAM_PANCHAYAT_NAME_LIST = "GRAM_PANCHAYAT_NAME_LIST";






    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) this.findViewById(R.id.dept);
        tv.setSelected(true);

        //
        blink();


        //
        listV = (ListView) findViewById(R.id.dep_list_item);

        String jsonStr = MainActivity.readFromAssetOrSavedFile("contacts.txt",getApplicationContext());
        Log.i("sss--------------",jsonStr);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);

            departmentNameTypeList = jsonObj.getString(CONTACT_NAME_TYPES).split(",");
            departmentDetailTypeNameList = jsonObj.getString(CONTACT_TYPES).split(",");
            departmentDisplayNameList = jsonObj.getString(CONTACT_DISPLAY_NAME_TYPES).split(",");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        initialiseAdaptor(departmentDisplayNameList);

        /////////////////////////////////////////////////////////////









    }




    ///////////////////////////////////////////////////////////////////////////////////////////////////////////




    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) findViewById(R.id.dept);
                        if (txt.getVisibility() == View.VISIBLE) {
                            txt.setVisibility(View.INVISIBLE);
                        } else {
                            txt.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }


    /////////////////////////

    public void showSettings(View v) {

        Intent mIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(mIntent);
    }

    public void showGPView(View v) {

        Intent mIntent = new Intent(MainActivity.this, GramPanchayatActivity.class);
        startActivity(mIntent);
    }



    ////
    private void showContactListView(String contactDetailType,String contactNameType) {


        Intent mIntent = new Intent(MainActivity.this, ContactListActivity.class);
        mIntent.putExtra(EXTRA_CONTACT_DETAIL_TYPE, contactDetailType);
        mIntent.putExtra(EXTRA_CONTACT_NAME_TYPE, contactNameType);

        startActivity(mIntent);
    }


    /////
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /////////////////////

    public static void writeToFile(String fileName, String data, Context context) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }


    }


    public static String readFromAssetOrSavedFile(String fileName, Context context) {

        String ret = "";

        InputStream inputStream = null;

        try {
            Log.i("FFFF","1111");
            inputStream = context.openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try
        {
            Log.i("FFFF","2222"+inputStream);
            if (inputStream == null)
                inputStream = context.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (Exception e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }




    //////////////

    private void initialiseAdaptor(String[] contactDisplayNameList)
    {
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,contactDisplayNameList){

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getView(position, convertView, parent);
            textView.setTextColor(Color.rgb(255,128,0));
            return textView;
        }
    };

        listV.setAdapter(adapter);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();

                showContactListView(departmentDetailTypeNameList[position], departmentNameTypeList[position]);
                //sendGroupSMS(departmentDetailTypeNameList[position], departmentNameTypeList[position]);

            }

        });

    }

    //////


    public void sendGroupSMS(String contactDetailType,String contactNameType) {




        String jsonStr = MainActivity.readFromAssetOrSavedFile("contacts.txt",getApplicationContext());

        JSONObject jsonObj = null;

        String [] CONTACT_DETAIL_LIST = null ;
        String [] CONTACT_NAME_LIST = null;

        try {
            jsonObj = new JSONObject(jsonStr);

            CONTACT_DETAIL_LIST = jsonObj.getString(contactDetailType).split(",");
            CONTACT_NAME_LIST = jsonObj.getString(contactNameType).split(",");

        } catch (JSONException e) {
            e.printStackTrace();
        }



        String contactNumber =  MainActivity.DOITC_SMS_MOBILE_NUMBER;

        String separator = "; ";
        if(android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")){
            separator = ", ";
        }

        for (int i = 0 ; i < CONTACT_NAME_LIST.length; i++)
        {
            contactNumber = contactNumber + separator + CONTACT_DETAIL_LIST[i*7 + 3];
        }





        String SMSText = "श्रीमानजी, \n  \n\n " +


                " \n-- \n \n धन्यवाद ";

        Log.i(ContactListActivity.class.getSimpleName(),"SMS:::"+contactNumber+SMSText);
        Uri sms_uri = Uri.parse("smsto:+91-"+ contactNumber);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", SMSText);

        startActivity(sms_intent);

    }
}


