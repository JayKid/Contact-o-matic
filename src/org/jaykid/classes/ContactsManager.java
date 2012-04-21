package org.jaykid.classes;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

public class ContactsManager {
	
	private ContentResolver contentResolver;
	private ArrayList<Contact> contacts;
	private Resources resources;
	private Bitmap DEFAULT_PHOTO;
	
	public ContactsManager(Context context)
	{
		contentResolver = context.getContentResolver();
		contacts = new ArrayList<Contact>();
		resources = context.getResources();
		DEFAULT_PHOTO = BitmapFactory.decodeResource(resources, resources.getIdentifier("default_photo", "drawable", "org.jaykid.contactomatic"));
		
	}
	
	public ArrayList<Item> getAllContacts()
	{
		getContactsId();
		getContactsNames();
		getContactsPhotos();
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
	
	private void getContactsPhotos()
	{
		ArrayList<Integer> idsWithPhotoAlready = new ArrayList<Integer>();

		String[] projection = new String[] { Photo.CONTACT_ID, Photo.PHOTO };
        Cursor cursorForContactsPhotos = getCursorForContactsPhotos(projection);
        int idColumnIndex = cursorForContactsPhotos.getColumnIndex(Photo.CONTACT_ID);
		int photoColumnIndex = cursorForContactsPhotos.getColumnIndex(Photo.PHOTO);
        
		int contactArrayIndex = 0;
		
		for(cursorForContactsPhotos.moveToFirst(); !cursorForContactsPhotos.isAfterLast(); cursorForContactsPhotos.moveToNext())
		{

			int actualContactId = cursorForContactsPhotos.getInt(idColumnIndex);
			
			byte[] actualContactPhotoWithoutDecoding = cursorForContactsPhotos.getBlob(photoColumnIndex);
			Bitmap actualContactPhoto = getPhotoFromNonEncodedData(actualContactPhotoWithoutDecoding);
			
			while (contactArrayIndex < contacts.size() && 
					contacts.get(contactArrayIndex).getId() < actualContactId)
			{
				++contactArrayIndex;
			}
			
			if ( !idsWithPhotoAlready.contains(new Integer(actualContactId) ) )
			{
				if ( contacts.get(contactArrayIndex).getId() == actualContactId )
				{
					idsWithPhotoAlready.add(new Integer(actualContactId));
					Contact contactToUpdate = contacts.get(contactArrayIndex);
					contactToUpdate.setPhoto(actualContactPhoto);
					contacts.set(contactArrayIndex, contactToUpdate);
				}
				else
				{
					contacts.remove(contactArrayIndex);
				}
			}
			
		}
		cursorForContactsPhotos.close();
        
	}
	
	private Bitmap getPhotoFromNonEncodedData(byte[] actualContactPhotoWithoutDecoding)
	{
		Bitmap photo;
		if (actualContactPhotoWithoutDecoding == null)
		{
			photo = DEFAULT_PHOTO;
		}
		else
		{
			photo = decodePhotoToBitmap(actualContactPhotoWithoutDecoding);
		}
		return photo;
	}

	private Bitmap decodePhotoToBitmap(byte[] actualContactPhotoWithoutDecoding)
	{
		return BitmapFactory.decodeByteArray(actualContactPhotoWithoutDecoding, 0, actualContactPhotoWithoutDecoding.length);
	}

	private Cursor getCursorForContactsPhotos(String[] projection) 
	{
		return contentResolver.query(Data.CONTENT_URI, projection, null, null, Photo.CONTACT_ID + " ASC");
	}
	
	
	private void getContactsNames()
	{
		String[] projection = new String[] {Contacts._ID, Contacts.DISPLAY_NAME};
		Cursor cursorForContactsNames = getContactNamesCursor(projection);
		int idColumnIndex = cursorForContactsNames.getColumnIndex(Contacts._ID);
		int nameColumnIndex = cursorForContactsNames.getColumnIndex(Contacts.DISPLAY_NAME);
		
		int contactArrayIndex = 0;
		
		for(cursorForContactsNames.moveToFirst(); !cursorForContactsNames.isAfterLast(); cursorForContactsNames.moveToNext())
		{

			int actualContactId = cursorForContactsNames.getInt(idColumnIndex);
			String actualContactName = cursorForContactsNames.getString(nameColumnIndex);
			
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
		cursorForContactsNames.close();
	}

	private Cursor getContactNamesCursor(String[] projection) {
		return contentResolver.query(Contacts.CONTENT_URI, projection, null, null, Contacts._ID + " ASC");
	}
}
