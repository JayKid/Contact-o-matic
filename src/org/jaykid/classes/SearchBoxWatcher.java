package org.jaykid.classes;

import java.util.ArrayList;

import org.jaykid.adapters.ContactoMaticListViewAdapter;

import android.text.Editable;
import android.text.TextWatcher;

public class SearchBoxWatcher implements TextWatcher
{
	private ContactoMaticListViewAdapter contactAdapter;
	private ArrayList<Item> contacts;
	
	public SearchBoxWatcher(ContactoMaticListViewAdapter contactAdapter, ArrayList<Item> contacts)
	{
		this.contactAdapter = contactAdapter;
		this.contacts = contacts;
	}
	
	@Override
	public void onTextChanged(CharSequence inputSequence, int start, int before, int count) {
		
		String needle = inputSequence.toString().toLowerCase();
		fillFilteredData(needle);
		
	}
	
	public void fillFilteredData(String needle)
	{
		ArrayList<Item> filteredContacts = new ArrayList<Item>();
		contactAdapter.clear();
		if (needle.length() > 0)
		{
			for(int i = 0; i < contacts.size(); ++i)
			{
				Contact contact = (Contact) contacts.get(i);
				if ( contact.nameStartsWith(needle) )
				{
					filteredContacts.add(contact);
				}
			}
		}
		
		else {
			filteredContacts = contacts;
		}
		
		for (int i = 0; i < filteredContacts.size(); ++i)
		{
			contactAdapter.add(filteredContacts.get(i));
		}
		contactAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		
	}
	
}

