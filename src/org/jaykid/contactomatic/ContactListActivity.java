package org.jaykid.contactomatic;

import java.util.ArrayList;

import org.jaykid.adapters.ContactoMaticListViewAdapter;
import org.jaykid.classes.Contact;
import org.jaykid.classes.ContactsManager;
import org.jaykid.classes.DialerHelper;
import org.jaykid.classes.Item;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ContactListActivity extends ListActivity implements OnItemClickListener
{
	private ContactoMaticListViewAdapter contactAdapter;
	private ArrayList<Item> items = new ArrayList<Item>();
	private DialerHelper dialerHelper;
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        dialerHelper = new DialerHelper(this);
        contactAdapter = new ContactoMaticListViewAdapter(this, R.layout.item_row, items);
        setListAdapter(contactAdapter);
//        fillData();
        
        ListView listView = getListView();
        
        listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
				
				Contact clickedContact = (Contact)parent.getItemAtPosition(position);
				String clickedContactPhoneNumber = clickedContact.getPhone();
				dialerHelper.callNumber(clickedContactPhoneNumber);
		    }
		});
		registerForContextMenu(getListView());
		
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	    Contact contact = (Contact) getListView().getAdapter().getItem(info.position);
        menu.setHeaderTitle(contact.getName());
        menu.setHeaderIcon(R.drawable.ic_launcher);
        inflater.inflate(R.menu.contact_list_longclick_menu, menu);
//	    if (!contact.getHasPhoneNumber()) menu.setGroupVisible(R.id.CallGroup, false);
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Contact clickedContact = (Contact) getListView().getAdapter().getItem(info.position);
		String clickedContactPhoneNumber = clickedContact.getPhone();
	    switch (item.getItemId())
	    {
	    	case R.id.viewContactLongClick:
	    		Intent detailIntent = new Intent(this, ContactDetailActivity.class);
	    		Bundle extras = new Bundle();
	    		extras.putParcelable("contact", clickedContact);
	    		detailIntent.putExtras(extras);
	    		startActivity(detailIntent);
	    		return true;
	        case R.id.editContactLongClick:
	        	Intent callIntent = new Intent(Intent.ACTION_EDIT);
	            callIntent.setData(Uri.parse(ContactsContract.Contacts.CONTENT_LOOKUP_URI + "/" + clickedContact.getId()));
	            startActivity(callIntent);
	            return true;
	        case R.id.sendSMSLongClick:
	        	dialerHelper.sendSMSToNumber(clickedContactPhoneNumber);
	            return true;
	        case R.id.callLongClick:
				dialerHelper.callNumber(clickedContactPhoneNumber);
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	public void onResume()
	{
		super.onResume();
		fillData();
	}
	
	// Create the menu based on the XML defintion
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
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
