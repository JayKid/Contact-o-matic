package org.jaykid.classes;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.Groups;

public class GroupsManager {
	
	private ContentResolver contentResolver;
	private ArrayList<Group> groups;
	private Resources resources;
	private Bitmap DEFAULT_PHOTO;
	private static String GROUP_ID_COLUMN = Groups._ID;
	private static String GROUP_TITLE_COLUMN = Groups.TITLE;
	private static String GROUP_DELETED_COLUMN = Groups.DELETED;
	
	public GroupsManager(Context context)
	{
		contentResolver = context.getContentResolver();
		groups = new ArrayList<Group>();
		resources = context.getResources();
		DEFAULT_PHOTO = getDefaultGroupPhoto();
	}
	
	public ArrayList<Group> getAllGroups()
	{
		getGroupsWithData();
		return groups;
	}

	private void getGroupsWithData() {
		getGroupsIdNameAndPhoto();
	}
	
	private void getGroupsIdNameAndPhoto()
	{
		int groupIdColumn, groupNameColumn, deletedColumn;
		String[] projection = getGroupIdNamePhotoProjection();
		Cursor groupsCursor = getGroupIdNamePhotoCursor(projection);
		
		groupIdColumn = groupsCursor.getColumnIndex(GROUP_ID_COLUMN);
		groupNameColumn = groupsCursor.getColumnIndex(GROUP_TITLE_COLUMN);
		deletedColumn = groupsCursor.getColumnIndex(GROUP_DELETED_COLUMN);
		
		for(groupsCursor.moveToFirst(); !groupsCursor.isAfterLast(); groupsCursor.moveToNext())
		{
			int groupId = groupsCursor.getInt(groupIdColumn);
			String groupName = groupsCursor.getString(groupNameColumn);
			
			boolean hasNotBeenDeleted = (groupsCursor.getInt(deletedColumn) == 0);
			if(hasNotBeenDeleted)
			{
				groups.add(new Group(groupId, groupName, DEFAULT_PHOTO));
			}
		}
		groupsCursor.close();
	}

	private String[] getGroupIdNamePhotoProjection() {
		return new String[] { Groups._ID, Groups.TITLE, Groups.DELETED };
	}
	
	private Cursor getGroupIdNamePhotoCursor(String[] projection) {
		return contentResolver.query(Groups.CONTENT_URI, projection, null, null, Groups.TITLE + " ASC");
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
	
	private Bitmap getDefaultGroupPhoto() {
		return BitmapFactory.decodeResource(resources, resources.getIdentifier("default_photo", "drawable", "org.jaykid.contactomatic"));
	}

}
