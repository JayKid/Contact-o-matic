package org.jaykid.classes;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;

public class ContactsManager {
	
	private ContentResolver contentResolver;
	private ArrayList<Contact> contacts;
	
	public ContactsManager(Context context)
	{
		contentResolver = context.getContentResolver();
		contacts = new ArrayList<Contact>();
	}
	
	public ArrayList<Item> getAllContacts()
	{
		getContactsId();
		getContactsNames();
		ArrayList<Item> contactsItems = new ArrayList<Item>();
		contactsItems.addAll(contacts);
		return contactsItems;
	}
	
	private void getContactsId()
	{
		String[] projection = new String[] { RawContacts.CONTACT_ID, RawContacts.DELETED };
		Cursor cursorContacts = contentResolver.query(RawContacts.CONTENT_URI, projection, null, null, RawContacts.CONTACT_ID + " ASC");
		int contactIdColumn = cursorContacts.getColumnIndex(RawContacts.CONTACT_ID);
		int deletedColumn = cursorContacts.getColumnIndex(RawContacts.DELETED);
		for(cursorContacts.moveToFirst(); !cursorContacts.isAfterLast(); cursorContacts.moveToNext())
		{
			int contactId = cursorContacts.getInt(contactIdColumn);
			boolean hasNotBeenDeleted = (cursorContacts.getInt(deletedColumn) == 0);
			if(hasNotBeenDeleted)
			{
				Contact contact = new Contact();
				contact.setId(contactId);
				contacts.add(contact);
			}
		}
		cursorContacts.close();
	}
	
	private void getContactsNames()
	{
		String[] projection = new String[] {Contacts._ID, Contacts.DISPLAY_NAME};
		Cursor cursorContacts = getContactNamesCursor(projection);
		int idColumnIndex = cursorContacts.getColumnIndex(Contacts._ID);
		int nameColumnIndex = cursorContacts.getColumnIndex(Contacts.DISPLAY_NAME);
		
		int contactArrayIndex = 0;
		
		for(cursorContacts.moveToFirst(); !cursorContacts.isAfterLast(); cursorContacts.moveToNext())
		{

			int actualContactId = cursorContacts.getInt(idColumnIndex);
			String actualContactName = cursorContacts.getString(nameColumnIndex);
			
			while (contactArrayIndex < contacts.size() && 
					contacts.get(contactArrayIndex).getId() < actualContactId)
			{
				++contactArrayIndex;
			}
			
			if (contacts.get(contactArrayIndex).getId() == actualContactId)
			{
				Contact contactToUpdate = contacts.get(contactArrayIndex);
				contactToUpdate.setName(actualContactName);
				contacts.set(contactArrayIndex, contactToUpdate);
			}
			else
			{
				contacts.remove(contactArrayIndex);
			}
		}
		cursorContacts.close();
	}

	private Cursor getContactNamesCursor(String[] projection) {
		return contentResolver.query(Contacts.CONTENT_URI, projection, null, null, Contacts._ID + " ASC");
	}
}
