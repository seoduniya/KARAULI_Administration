package example.com.contactlist;

import android.graphics.Color;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class GramPanchayatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gram_panchayat);



        String jsonStr = MainActivity.readFromAssetOrSavedFile("contacts.txt",getApplicationContext());
        Log.i("sss--------------",jsonStr);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);

            String [] gpList = jsonObj.getString(MainActivity.GRAM_PANCHAYAT_NAME_LIST).split(",");

            //Creating the instance of ArrayAdapter containing list of language names
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,gpList);

            // /Getting the instance of AutoCompleteTextView
            AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
            actv.setThreshold(1);//will start working from first character
            actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
            actv.setTextColor(Color.RED);


        } catch (JSONException e) {
            e.printStackTrace();
        }














    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    public  void showGPData(View v)
    {


        String jsonStr = MainActivity.readFromAssetOrSavedFile("contacts.txt",getApplicationContext());
        Log.i("sss--------------",jsonStr);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);

            AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

            Log.i("sss--------------",actv.getText().toString());
            String [] gpOfficerList = jsonObj.getString(actv.getText().toString()).split(",");


            GridLayout gridLayout = (GridLayout) findViewById(R.id.gp_view_grid_id);

            for(int i = 0; i < (gpOfficerList.length/7); i++) {

                TextView tv = new TextView(this);

                tv.setText("\n\n"+gpOfficerList[i*7+0]+":"+gpOfficerList[i*7+1]+":"+gpOfficerList[i*7+2]+":"+gpOfficerList[i*7+3]+":"+gpOfficerList[i*7+4]+":"+gpOfficerList[i*7+5]+":"+gpOfficerList[i*7+6]);


                GridLayout.Spec row = GridLayout.spec(i+1, 1);
                GridLayout.Spec colspan = GridLayout.spec(0, 1);
                gridLayout.addView(tv, new GridLayout.LayoutParams(row, colspan));
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
