package org.jaykid.contactomatic;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class ContactomaticActivity extends TabActivity implements OnTabChangeListener
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
	private static int GROUPS_TAB_ICON = R.drawable.ic_menu_group_tab;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        resources = getResources();
        tabHost = getTabHost();
        addTabs();
        styleTabs();
    }

	private void styleTabs() {
		tabHost.setOnTabChangedListener(this);
        tabHost.setBackgroundColor(Color.parseColor("#2184a0"));
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(
                    Color.parseColor("#2184a0"));
        }
        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#2ec5f1"));
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
    
    public void onTabChanged(String tabId) {
        
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(
                    Color.parseColor("#2184a0"));
        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
                .setBackgroundColor(Color.parseColor("#2ec5f1"));
     
    }
}