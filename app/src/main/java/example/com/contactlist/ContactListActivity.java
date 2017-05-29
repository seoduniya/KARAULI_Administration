package example.com.contactlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ListView listV;

    ArrayAdapter<String> adapter;

    // Search EditText
    // EditText inputSearch;

    //Contact Detail Perameters
    final static String CONTACT_NAME = "CONTACT_NAME";
    final static String CONTACT_DESIGNATION = "CONTACT_DESIGNATION";
    final static String CONTACT_NUMBER = "CONTACT_NUMBER";
    final static String CONTACT_OFFICENUMBER = "CONTACT_OFFICENUMBER";
    final static String CONTACT_EMAILID = "CONTACT_EMAILID";
    final static String CONTACT_OFFICENAME= "CONTACT_OFFICENAME";


    String[] CONTACT_DETAIL_LIST;
    String[] CONTACT_NAME_LIST;

    //
    String[] SEARCH_CONTACT_DETAIL_LIST;
    String[] SEARCH_CONTACT_NAME_LIST;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Intent intent = getIntent();
        String contactDetailType= intent.getExtras().getString(MainActivity.EXTRA_CONTACT_DETAIL_TYPE);
        String contactNameType= intent.getExtras().getString(MainActivity.EXTRA_CONTACT_NAME_TYPE);


        listV = (ListView) findViewById(R.id.list_item);

        String jsonStr = MainActivity.readFromAssetOrSavedFile("contacts.txt",getApplicationContext());

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);

            CONTACT_DETAIL_LIST = jsonObj.getString(contactDetailType).split(",");
            CONTACT_NAME_LIST = jsonObj.getString(contactNameType).split(",");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SEARCH_CONTACT_NAME_LIST = CONTACT_NAME_LIST;
        SEARCH_CONTACT_DETAIL_LIST = CONTACT_DETAIL_LIST;

            initialiseAdaptor(CONTACT_NAME_LIST,CONTACT_DETAIL_LIST);

    }


    public void createIntentForContactView( int position ){
        Intent mIntent = new Intent(ContactListActivity.this, ContactViewActivity.class);
        mIntent.putExtra(CONTACT_NAME, SEARCH_CONTACT_DETAIL_LIST[7 * position + 1]);
        mIntent.putExtra(CONTACT_DESIGNATION, SEARCH_CONTACT_DETAIL_LIST[7 * position + 2]);
        mIntent.putExtra(CONTACT_NUMBER, SEARCH_CONTACT_DETAIL_LIST[7 * position + 3]);
        mIntent.putExtra(CONTACT_OFFICENUMBER, SEARCH_CONTACT_DETAIL_LIST[7 * position + 4]);
        mIntent.putExtra(CONTACT_EMAILID, SEARCH_CONTACT_DETAIL_LIST[7 * position + 5]);
        mIntent.putExtra(CONTACT_OFFICENAME, SEARCH_CONTACT_DETAIL_LIST[7 * position + 6]);



        startActivity(mIntent);
    }





    public void removeDefaultSearchText(View v)
    {
        EditText textSearch = (EditText) this.findViewById(R.id.searchEditText);
        String searchStr = textSearch.getText().toString();

        if(searchStr.equalsIgnoreCase("Search Text")){
            textSearch.setText("");
        }



    }

    public void searchTheList(View v)
    {
        EditText textSearch = (EditText) this.findViewById(R.id.searchEditText);

        String searchStr = textSearch.getText().toString();

        ArrayList<String> searchContactDetailArrayList = new ArrayList<String>();
        ArrayList<String> searchContactNameArrayList = new ArrayList<String>();
        for( int i = 0 ; i < CONTACT_NAME_LIST.length; i++)
        {
            //Log.i("ContactListActivity",(CONTACT_DETAIL_LIST[i*7+0]+CONTACT_DETAIL_LIST[i*7+1]+CONTACT_DETAIL_LIST[i*7+2]+CONTACT_DETAIL_LIST[i*7+3]+CONTACT_DETAIL_LIST[i*7+4]+CONTACT_DETAIL_LIST[i*7+5]+CONTACT_DETAIL_LIST[i*7+6]) +":::::"+searchStr);
            if((CONTACT_DETAIL_LIST[i*7+0]+CONTACT_DETAIL_LIST[i*7+1]+CONTACT_DETAIL_LIST[i*7+2]+CONTACT_DETAIL_LIST[i*7+3]+CONTACT_DETAIL_LIST[i*7+4]+CONTACT_DETAIL_LIST[i*7+5]+CONTACT_DETAIL_LIST[i*7+6]).toLowerCase().contains(searchStr.toLowerCase()))
            {
                //Log.i("ContactListActivity","---------------");
                searchContactDetailArrayList.add(CONTACT_DETAIL_LIST[i*7+0]);
                searchContactDetailArrayList.add(CONTACT_DETAIL_LIST[i*7+1]);
                searchContactDetailArrayList.add(CONTACT_DETAIL_LIST[i*7+2]);
                searchContactDetailArrayList.add(CONTACT_DETAIL_LIST[i*7+3]);
                searchContactDetailArrayList.add(CONTACT_DETAIL_LIST[i*7+4]);
                searchContactDetailArrayList.add(CONTACT_DETAIL_LIST[i*7+5]);
                searchContactDetailArrayList.add(CONTACT_DETAIL_LIST[i*7+6]);




                searchContactNameArrayList.add(CONTACT_NAME_LIST[i]);
            }
        }


        SEARCH_CONTACT_DETAIL_LIST = new String[searchContactDetailArrayList.size()];
        SEARCH_CONTACT_NAME_LIST = new String[searchContactNameArrayList.size()];

        SEARCH_CONTACT_DETAIL_LIST = searchContactDetailArrayList.toArray(SEARCH_CONTACT_DETAIL_LIST);
        SEARCH_CONTACT_NAME_LIST = searchContactNameArrayList.toArray(SEARCH_CONTACT_NAME_LIST);


        //Log.i("ContactListActivity","SEARCH ARRAY LENGTH--"+SEARCH_CONTACT_NAME_LIST.length);

        initialiseAdaptor(SEARCH_CONTACT_NAME_LIST,SEARCH_CONTACT_DETAIL_LIST);

    }




    public void resetTheSearch(View v) {
        EditText textSearch = (EditText) this.findViewById(R.id.searchEditText);

        textSearch.setText("");

        searchTheList(v);

        textSearch.setText("Search Text");
    }

    private void initialiseAdaptor(String[] contactNameList,String [] conatctDetailList)
    {
        adapter = new CustomContactListItem(this, contactNameList,conatctDetailList);

        listV.setAdapter(adapter);
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(ContactListActivity.this, "" + position, Toast.LENGTH_SHORT).show();

                createIntentForContactView(position);

            }
        });
    }

}
