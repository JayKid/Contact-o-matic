package org.jaykid.contactomatic;

import java.util.ArrayList;

import org.jaykid.adapters.ContactoMaticListViewAdapter;
import org.jaykid.classes.Contact;
import org.jaykid.classes.Group;
import org.jaykid.classes.GroupsManager;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class GroupDetailActivity extends ListActivity
{
	private ContactoMaticListViewAdapter contactAdapter;
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	private Group group;
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail);
        contactAdapter = new ContactoMaticListViewAdapter(this, R.layout.item_row, contacts);
        setListAdapter(contactAdapter);
        retrieveGroup();
        setGroupTitle();
    }
	
	public void onResume()
	{
		super.onResume();
		fillData();
	}
	
	private void retrieveGroup()
	{
		Bundle extras = getIntent().getExtras();
        if (extras != null) group = (Group) extras.getParcelable("group");
	}
	
	private void setGroupTitle() 
	{
		TextView groupName = (TextView)findViewById(R.id.groupDetailName);
		groupName.setText(group.getName());		
	}
	
	public void fillData()
	{
		GroupsManager groupsManager = new GroupsManager(this);
		ArrayList<Contact> groups = groupsManager.getContactsBelongingToAGroup(group.getId());
		contactAdapter.clear();
		for(int i = 0; i < groups.size(); ++i)
		{
			contactAdapter.add(groups.get(i));
		}
		contactAdapter.notifyDataSetChanged();
	}
}
