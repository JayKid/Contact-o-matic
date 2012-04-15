package org.jaykid.contactomatic;



import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ContactomaticActivity extends TabActivity
{
	private TabHost tabHost;
	private Resources resources;
	private static String CONTACT_TAB_NAME = "Contacts";
	private static String PHONE_TAB_NAME = "Phone";
	private static String GROUPS_TAB_NAME = "Groups";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        resources = getResources();
        tabHost = getTabHost();
        addContactsTab();
        addPhoneTab();
        addGroupsTab();
    }
    
//    Cursor managedCursor = getContentResolver()
//    	    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//    	     new String[] {Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER}, null, null,  Phone.DISPLAY_NAME + " ASC");
    
    
    private void addContactsTab()
    {
        Intent intent = new Intent(this, ContactListActivity.class);
        TabSpec spec = tabHost.newTabSpec(CONTACT_TAB_NAME);
        spec.setIndicator(getString(R.string.contacts_tab), resources.getDrawable(R.drawable.ic_menu_sort_alphabetically));
        spec.setContent(intent);
        tabHost.addTab(spec);
    }
    
    private void addPhoneTab()
    {
        Intent intent = new Intent(this, PhoneDialerActivity.class);
        TabSpec spec = tabHost.newTabSpec(PHONE_TAB_NAME);
        spec.setIndicator(getString(R.string.phone_tab), resources.getDrawable(R.drawable.ic_menu_call));
        spec.setContent(intent);
        tabHost.addTab(spec);
    }
    
    private void addGroupsTab()
    {
        Intent intent = new Intent(this, PhoneDialerActivity.class);
        TabSpec spec = tabHost.newTabSpec(GROUPS_TAB_NAME);
        spec.setIndicator(getString(R.string.groups_tab), resources.getDrawable(R.drawable.ic_menu_group));
        spec.setContent(intent);
        tabHost.addTab(spec);
    }
    
    
}