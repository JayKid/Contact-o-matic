package org.jaykid.contactomatic;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class ContactomaticActivity extends TabActivity
{
	private TabHost tabHost;
	private Resources resources;
	private static String CONTACT_TABNAME = "Contacts";
	private static String PHONE_TABNAME = "Phone";
	private static String GROUPS_TABNAME = "Groups";

	private static int CONTACT_TAB_DISPLAYING_NAME = R.string.contacts_tab;
	private static int PHONE_TAB_DISPLAYING_NAME = R.string.phone_tab;
	private static int GROUPS_TAB_DISPLAYING_NAME = R.string.groups_tab;
	
	private static int CONTACT_TAB_ICON = R.drawable.ic_menu_sort_alphabetically;
	private static int PHONE_TAB_ICON = R.drawable.ic_menu_call;
	private static int GROUPS_TAB_ICON = R.drawable.ic_menu_group;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        resources = getResources();
        tabHost = getTabHost();
        addTabs();
    }

	private void addTabs() {
		addContactsTab();
        addPhoneTab();
        addGroupsTab();
	}
    
    private void addContactsTab()
    {
    	addElementTab(ContactListActivity.class,CONTACT_TABNAME,CONTACT_TAB_DISPLAYING_NAME,CONTACT_TAB_ICON);
    }
    
    private void addPhoneTab()
    {
    	addElementTab(PhoneDialerActivity.class,PHONE_TABNAME,PHONE_TAB_DISPLAYING_NAME,PHONE_TAB_ICON);
    }
    
    private void addGroupsTab()
    {
    	addElementTab(GroupListActivity.class,GROUPS_TABNAME,GROUPS_TAB_DISPLAYING_NAME,GROUPS_TAB_ICON);
    }
    
    private void addElementTab(Class<?> tabActivityClass, String tabName, int tabDisplayingTitle, int tabIcon)
    {
    	Intent tabIntent = new Intent(this, tabActivityClass);
    	TabSpec spec = tabHost.newTabSpec(tabName);
    	spec.setIndicator(getString(tabDisplayingTitle), resources.getDrawable(tabIcon));
    	spec.setContent(tabIntent);
    	tabHost.addTab(spec);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()){
	        case R.id.addContactMenu:
	        	Intent intent = new Intent(Intent.ACTION_INSERT);
	            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
				startActivity(intent);
	        break;
	        case R.id.aboutMenu:
	        	int text = R.string.about_the_author; 
	    		int duration = Toast.LENGTH_LONG;
	    		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	    		toast.show();
	        break;
    	}
    	return true;
    }
}