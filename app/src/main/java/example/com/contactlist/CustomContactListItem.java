package example.com.contactlist;

import android.view.View;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomContactListItem extends ArrayAdapter<String> {
    private String[] names;
    private String[] contactDetail;
    private Integer[] imageidI;
    private Activity context;

    public CustomContactListItem(Activity context, String[] names, String[] contactDetail) {
        super(context, R.layout.activity_custom_contact_list_item, names);
        this.context = context;
        this.names = names;
        this.contactDetail = contactDetail;
        this.imageidI = imageidI;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_contact_list_item, null, true);

        TextView textViewcontactName = (TextView) listViewItem.findViewById(R.id.contactName);
        TextView textViewcontactDesignation = (TextView) listViewItem.findViewById(R.id.contactDesignation);
        TextView textViewcontactNumber = (TextView) listViewItem.findViewById(R.id.contactNumber);


        ImageView imageI = (ImageView) listViewItem.findViewById(R.id.imageViewI);
        ImageView imageL = (ImageView) listViewItem.findViewById(R.id.imageViewL);

        textViewcontactName.setText((position+1)+" "+names[position]);

        textViewcontactDesignation.setText(contactDetail[position*7 + 2]+"("+contactDetail[position*7 + 6]+")");


        textViewcontactNumber.setText (contactDetail[position*7 + 3]);



        imageI.setImageResource(R.drawable.icon);
        imageL.setImageResource(R.drawable.arrowicon); //image.setImageResource(imageid[position]);

        return  listViewItem;
    }
}