package org.jaykid.contactomatic;

import java.util.ArrayList;

import org.jaykid.adapters.GroupListViewAdapter;
import org.jaykid.classes.Group;
import org.jaykid.classes.GroupsManager;

import android.app.ListActivity;
import android.os.Bundle;
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
import android.widget.Toast;

public class GroupListActivity extends ListActivity implements OnItemClickListener
{
	private GroupListViewAdapter groupAdapter;
	private ArrayList<Group> groups = new ArrayList<Group>();
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        groupAdapter = new GroupListViewAdapter(this, R.layout.item_row, groups);
        setListAdapter(groupAdapter);
        
        ListView listView = getListView();
        
        listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
				
				Group clickedGroup = (Group)parent.getItemAtPosition(position);
				String clickedGroupName = clickedGroup.getName();
				
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getApplicationContext(), clickedGroupName, duration);
				toast.show();
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
	    Group group = (Group) getListView().getAdapter().getItem(info.position);
        menu.setHeaderTitle(group.getName());
        menu.setHeaderIcon(R.drawable.ic_launcher);
        inflater.inflate(R.menu.contact_list_longclick_menu, menu);
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Group clickedGroup = (Group) getListView().getAdapter().getItem(info.position);
		
	    switch (item.getItemId())
	    {
	        case R.id.editContactLongClick:
//	        	Intent callIntent = new Intent(Intent.ACTION_EDIT);
//	            callIntent.setData(Uri.parse(ContactsContract.Contacts.CONTENT_LOOKUP_URI + "/" + clickedContact.getId()));
//	            startActivity(callIntent);
	            return true;
	        case R.id.sendSMSLongClick:
//	        	dialerHelper.sendSMSToNumber(clickedContactPhoneNumber);
	            return true;
	        case R.id.callLongClick:
//				dialerHelper.callNumber(clickedContactPhoneNumber);
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
		GroupsManager groupsManager = new GroupsManager(this);
		ArrayList<Group> groups = groupsManager.getAllGroups();
		groupAdapter.clear();
		for(int i = 0; i < groups.size(); ++i)
		{
			groupAdapter.add(groups.get(i));
		}
		groupAdapter.notifyDataSetChanged();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//		Dialog dialog = new Dialog(this);
//		dialog.setTitle(String.valueOf(id));
//		dialog.setContentView(R.layout.help);
	}
	
}
