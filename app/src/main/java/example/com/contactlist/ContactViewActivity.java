package example.com.contactlist;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class ContactViewActivity extends AppCompatActivity {





    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);


        Intent intent = getIntent();

        TextView editText = (TextView) findViewById(R.id.cont_name);
        editText.setText((CharSequence) getIntent().getExtras().get(ContactListActivity.CONTACT_NAME).toString());

        editText = (TextView) findViewById(R.id.desig);
        editText.setText((CharSequence) getIntent().getExtras().get(ContactListActivity.CONTACT_DESIGNATION).toString());

        editText = (TextView) findViewById(R.id.cont_num);
        editText.setText((CharSequence) getIntent().getExtras().get(ContactListActivity.CONTACT_NUMBER).toString());


        editText = (TextView) findViewById(R.id.off_num);
        editText.setText((CharSequence) getIntent().getExtras().get(ContactListActivity.CONTACT_OFFICENUMBER).toString());


        editText = (TextView) findViewById(R.id.email);
        editText.setText((CharSequence) getIntent().getExtras().get(ContactListActivity.CONTACT_EMAILID).toString());

        editText = (TextView) findViewById(R.id.cont_off_nm);
        editText.setText((CharSequence) getIntent().getExtras().get(ContactListActivity.CONTACT_OFFICENAME).toString());

        //editText.setText((CharSequence) getIntent().getExtras().get(ContactListActivity.CONTACT_NUMBER).toString());


        //Button callButton = (Button) findViewById(R.id.buttonNumberCall);
        //callButton.setText((CharSequence) getIntent().getExtras().get(ContactListActivity.CONTACT_NUMBER).toString());

    }




    public void makeNumberCall(View view) {


        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+91" + ((TextView) findViewById(R.id.cont_num)).getText()));

        startActivity(intent);
    }


        /*int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+91" + ((TextView) findViewById(R.id.cont_num)).getText() ));


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }
        }*/

  public void makeSMS(View view) {

        Uri sms_uri = Uri.parse("smsto:+91" + ((TextView) findViewById(R.id.cont_num)).getText());
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", "Namaskar Shrimaanji");

        startActivity(sms_intent);
    }


    /*public void makeSMS(View view) {
        String strnum = "7790890085;9462901316;8003602424;9414306752";
        Uri smsToUri = Uri.parse("smsto:" + strnum);
        Intent intent = new Intent(
                android.content.Intent.ACTION_SENDTO, smsToUri);
        String message = "Namaskar Shrimaanji";
        // message = message.replace("%s", StoresMessage.m_storeName);
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }*/



    public void sendSMSToUsForEdit(View v) {




        String contactName =  getIntent().getExtras().get(ContactListActivity.CONTACT_NAME).toString();
        String contactDesignation =  getIntent().getExtras().get(ContactListActivity.CONTACT_DESIGNATION).toString();
        String contactNumber =  getIntent().getExtras().get(ContactListActivity.CONTACT_NUMBER).toString();
        String contactOfficeNumber =  getIntent().getExtras().get(ContactListActivity.CONTACT_OFFICENUMBER).toString();
        String contactEmailID =  getIntent().getExtras().get(ContactListActivity.CONTACT_EMAILID).toString();
        String contactOfficeName =  getIntent().getExtras().get(ContactListActivity.CONTACT_OFFICENAME).toString();

        String SMSText = "श्रीमानजी, \n वर्तमान सूचना -- \n\n " +
                "नाम "+contactName+" के स्थान पर XXXX;\n\n"+
                "पद  "+contactDesignation+" के स्थान पर XXXX;\n\n"+
                "मोबाइल  "+contactNumber+" के स्थान पर XXXX;\n\n"+
                "आफिस नंबर  "+contactOfficeNumber+" के स्थान पर XXXX;\n\n"+
                "ईमेल  "+contactEmailID+" के स्थान पर XXXX;\n\n"+
                "आफिस पते  "+contactOfficeName+" के स्थान पर XXXX;\n\n"+

                " \n--  बदलाव करना उचित होगा --\n"+

                " \n-- \n \n धन्यवाद ";

        Log.i(ContactListActivity.class.getSimpleName(),"SMS:::"+SMSText);
        Uri sms_uri = Uri.parse("smsto:+91-"+MainActivity.DOITC_SMS_MOBILE_NUMBER);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", SMSText);

        startActivity(sms_intent);

    }



    public void addNewContact(View v) {




        String contactName =  getIntent().getExtras().get(ContactListActivity.CONTACT_NAME).toString();
        String contactDesignation =  getIntent().getExtras().get(ContactListActivity.CONTACT_DESIGNATION).toString();
        String contactNumber =  getIntent().getExtras().get(ContactListActivity.CONTACT_NUMBER).toString();
        String contactOfficeNumber =  getIntent().getExtras().get(ContactListActivity.CONTACT_OFFICENUMBER).toString();
        String contactEmailID =  getIntent().getExtras().get(ContactListActivity.CONTACT_EMAILID).toString();
        String contactOfficeName =  getIntent().getExtras().get(ContactListActivity.CONTACT_OFFICENAME).toString();


        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, contactName + contactDesignation);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactNumber);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, contactEmailID);

        startActivity(intent);

    }





        /*int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {


            Uri sms_uri = Uri.parse("smsto:+91"+((TextView) findViewById(R.id.cont_num)).getText() );
            Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
            sms_intent.putExtra("sms_body", "Namaskar Shrimaanji");
            startActivity(sms_intent);

        }*/

}