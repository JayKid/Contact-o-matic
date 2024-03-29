package org.jaykid.adapters;

import java.util.ArrayList;

import org.jaykid.classes.Contact;
import org.jaykid.contactomatic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactoMaticListViewAdapter extends ArrayAdapter<Contact>
{
	
	private LayoutInflater inflater;
	private ArrayList<Contact> contacts;

	public ContactoMaticListViewAdapter(Context context,int textViewResourceId, ArrayList<Contact> contacts)
	{
		super(context, textViewResourceId, contacts);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.contacts = contacts;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		Contact item = contacts.get(position);
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_row, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.contactName);
			viewHolder.photo = (ImageView) convertView.findViewById(R.id.contactPhoto);
			viewHolder.telephone = (TextView) convertView.findViewById(R.id.contactTelephone);
			
			convertView.setTag(viewHolder);
			
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Contact contact = (Contact) item;
		viewHolder.name.setText(contact.getName());
		viewHolder.photo.setImageBitmap(contact.getPhoto());
		viewHolder.telephone.setText(String.valueOf(contact.getPhone()));
		return convertView;
	}
	
	private class ViewHolder
	{
		public TextView name = null;
		public ImageView photo = null;
		public TextView telephone = null;
	}
}
