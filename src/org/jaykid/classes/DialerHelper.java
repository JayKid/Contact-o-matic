package org.jaykid.classes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DialerHelper {

	private Context myContext;
	private static final String PHONE_PREFIX = "tel:";
	
	public DialerHelper(Context context)
	{
		myContext = context;
	}
	
	public void callNumber(String telephoneNumberToBeCalledFromUI)
	{
		if (isNotAnEmptyTelephone(telephoneNumberToBeCalledFromUI))
		{
			String dialedTelephonedNumberFromUIWithPrefix;
			dialedTelephonedNumberFromUIWithPrefix = addPhonePrefix(telephoneNumberToBeCalledFromUI);
			Uri telephoneNumberToBeCalled = Uri.parse(dialedTelephonedNumberFromUIWithPrefix);
			
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(telephoneNumberToBeCalled);
			myContext.startActivity(callIntent);
		}
	}
	
	private String addPhonePrefix(String dialedTelephonedNumberFromUI) 
	{
		return PHONE_PREFIX.concat(dialedTelephonedNumberFromUI);
	}
	
	private boolean isNotAnEmptyTelephone(String telephoneNumberToBeCalled)
	{
		if (telephoneNumberToBeCalled == null) return false;
		if (telephoneNumberToBeCalled.equals("")) return false;
		return true;
	}
	
}
