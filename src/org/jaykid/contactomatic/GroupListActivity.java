package org.jaykid.contactomatic;

import java.util.ArrayList;

import org.jaykid.adapters.GroupListViewAdapter;
import org.jaykid.classes.Group;
import org.jaykid.classes.GroupsManager;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class GroupListActivity extends ListActivity implements OnItemClickListener
{
	private GroupListViewAdapter groupAdapter;
	private ArrayList<Group> groups = new ArrayList<Group>();
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list);
        groupAdapter = new GroupListViewAdapter(this, R.layout.item_row, groups);
        setListAdapter(groupAdapter);
        
        ListView listView = getListView();
        
        listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
				
				Group clickedGroup = (Group)parent.getItemAtPosition(position);
				launchGroupDetailView(clickedGroup);
		    }

			private void launchGroupDetailView(Group clickedGroup) {
				Intent detailIntent = new Intent(getApplicationContext(),GroupDetailActivity.class);
	    		Bundle extras = new Bundle();
	    		extras.putParcelable("group", clickedGroup);
	    		detailIntent.putExtras(extras);
	    		startActivity(detailIntent);
			}
		});
		registerForContextMenu(getListView());
		
    }
	
	public void onResume()
	{
		super.onResume();
		fillData();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.about_menu, menu);
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
