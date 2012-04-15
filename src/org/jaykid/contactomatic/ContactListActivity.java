package org.jaykid.contactomatic;

import java.util.ArrayList;

import org.jaykid.adapters.ContactoMaticListViewAdapter;
import org.jaykid.classes.Contact;
import org.jaykid.classes.Item;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class ContactListActivity extends ListActivity
{
	private ContactoMaticListViewAdapter contactAdapter;
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        contactAdapter = new ContactoMaticListViewAdapter(this, R.layout.item_row, items);
        setListAdapter(contactAdapter);
        fillData();
    }
	
	public void fillData()
	{
		Bitmap photo = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("ic_launcher", "drawable", "org.jaykid.contactomatic" ));
		contactAdapter.clear();
		contactAdapter.add(new Contact("Dummy",121231,photo));
		contactAdapter.add(new Contact("Lammy",121231,photo));
		contactAdapter.add(new Contact("Trilly",121231,photo));
		contactAdapter.notifyDataSetChanged();
	}
}
