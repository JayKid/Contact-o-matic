package org.jaykid.classes;

import org.jaykid.contactomatic.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

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
		else
		{
        	showEmptyPhoneNumberToast();
		}
	}
	
	public void sendSMSToNumber(String telephoneNumberToBeCalledFromUI)
	{
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
    	smsIntent.setType("vnd.android-dir/mms-sms");
    	smsIntent.putExtra("address", telephoneNumberToBeCalledFromUI);
    	smsIntent.putExtra("sms_body","Body of Message");
    	myContext.startActivity(smsIntent);
	}

	private void showEmptyPhoneNumberToast() {
		int text = R.string.empty_phone_number; 
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(myContext, text, duration);
		toast.show();
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
