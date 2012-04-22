package org.jaykid.classes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DialerHelper {

	private Context myContext;
	private static final String PHONE_PREFIX = "tel:";
	private static final Uri EMPTY_URI = Uri.parse(PHONE_PREFIX);
	
	public DialerHelper(Context context)
	{
		myContext = context;
	}
	
	public void callNumber(String telephoneNumberToBeCalledFromUI)
	{
		String dialedTelephonedNumberFromUIWithPrefix;
		dialedTelephonedNumberFromUIWithPrefix = addPhonePrefix(telephoneNumberToBeCalledFromUI);
		Uri telephoneNumberToBeCalled = Uri.parse(dialedTelephonedNumberFromUIWithPrefix);
		
		if (isANotAnEmptyUri(telephoneNumberToBeCalled)) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(telephoneNumberToBeCalled);
			myContext.startActivity(callIntent);
		}
	}
	
	private String addPhonePrefix(String dialedTelephonedNumberFromUI) {
		return PHONE_PREFIX.concat(dialedTelephonedNumberFromUI);
	}
	
	private boolean isANotAnEmptyUri(Uri telephoneNumberToBeCalled)
	{
		return !telephoneNumberToBeCalled.equals(EMPTY_URI);
	}
	
}
