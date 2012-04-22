package org.jaykid.contactomatic;

import java.util.ArrayList;

import org.jaykid.adapters.ContactoMaticListViewAdapter;
import org.jaykid.classes.Contact;
import org.jaykid.classes.ContactsManager;
import org.jaykid.classes.Item;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ContactListActivity extends ListActivity implements OnItemClickListener
{
	private ContactoMaticListViewAdapter contactAdapter;
	private ArrayList<Item> items = new ArrayList<Item>();
	private static final CharSequence[] menuOptions = { "Editar categoria", "Borrar categoria", "Llistar tasques assignades a la categoria" };
	private static final int DIALOG_TITLE = R.string.contact_click_dialog_title;
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        contactAdapter = new ContactoMaticListViewAdapter(this, R.layout.item_row, items);
        setListAdapter(contactAdapter);
        fillData();
        
        ListView listView = getListView();
        
        listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
				
				Contact clickedContact = (Contact)parent.getItemAtPosition(position);
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+clickedContact.getPhone()));
				startActivity(callIntent);
		    }
		});
		registerForContextMenu(getListView());
        
        listView.setOnItemLongClickListener( new OnItemLongClickListener()
        {
            @Override 
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
            { 
            	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());

				dialogBuilder.setTitle(DIALOG_TITLE);
				dialogBuilder.setItems(menuOptions, new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int item) {
                    	
                    	Context context = getApplicationContext();
                    	CharSequence text = "";
                    	int duration = Toast.LENGTH_SHORT;

                        switch(item){
                            case 0:
                            	text = "Hello 1!";
                              break;
                            case 1:
                            	text = "Hello 2!";

                            	break;
                            case 2:
                            	text = "Hello 3!";
                                break;
                        }
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                });
				AlertDialog ad = dialogBuilder.create();
				ad.show();
				return false;
            }
        }); 
    }
	
	// Create the menu based on the XML defintion
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_list_menu, menu);
		return true;
	}
	
	public void fillData()
	{
		ContactsManager contactsManager = new ContactsManager(this);
		ArrayList<Item> contacts = contactsManager.getAllContacts();
		contactAdapter.clear();
		for(int i = 0; i < contacts.size(); ++i)
		{
			contactAdapter.add(contacts.get(i));
		}
		contactAdapter.notifyDataSetChanged();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//		Dialog dialog = new Dialog(this);
//		dialog.setTitle(String.valueOf(id));
//		dialog.setContentView(R.layout.help);
	}
	
}
