package org.jaykid.contactomatic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhoneDialerActivity extends Activity
{
	private static final String PHONE_PREFIX = "tel:";
	private static final Uri EMPTY_URI = Uri.parse(PHONE_PREFIX);
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialpad);
    }
	
	public void addButtonTextToDialer(View button)
	{
		Button uiButton = (Button)button;
	    String buttonText = uiButton.getText().toString();
		addCharacterToDialer(buttonText);
	}
	
	private void addCharacterToDialer(String character)
	{
		String dialedPhoneNumberFromUI, dialedPhoneNumberFromUIWithAddedCharacter;
		dialedPhoneNumberFromUI = getDialedPhoneNumberFromUI();
		dialedPhoneNumberFromUIWithAddedCharacter = dialedPhoneNumberFromUI.concat(character);
		setDialedPhoneNumber(dialedPhoneNumberFromUIWithAddedCharacter);
	}
	
	public void eraseCharacterFromDialer(View button) 
    {
		String dialedTelephonedNumberFromUI;
		dialedTelephonedNumberFromUI = getDialedPhoneNumberFromUI();
		if (!dialedTelephonedNumberFromUI.isEmpty())
		{
			String dialedTelephonedNumberFromUIWithoutLastNumber = 
					substractLastNumber(dialedTelephonedNumberFromUI);
			setDialedPhoneNumber(dialedTelephonedNumberFromUIWithoutLastNumber);
		}
    }
	
	private void setDialedPhoneNumber(String telephoneNumber) 
	{
		((TextView) findViewById(R.id.dialedNumber)).setText(telephoneNumber);
	}

	private String substractLastNumber(String dialedTelephonedNumberFromUI) 
	{
		String dialedTelephonedNumberFromUIWithoutLastNumber;
		dialedTelephonedNumberFromUIWithoutLastNumber = 
				(String) dialedTelephonedNumberFromUI.subSequence(
						0, dialedTelephonedNumberFromUI.length()-1); 
		return dialedTelephonedNumberFromUIWithoutLastNumber;
	}

	public void callDialedNumber(View button) 
    {
		Uri telephoneNumberToBeCalled = getDialedTelephoneNumber();
		if (isAValidUri(telephoneNumberToBeCalled)) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(telephoneNumberToBeCalled);
			startActivity(callIntent);
		}
    }

	private Uri getDialedTelephoneNumber() 
	{
		Uri dialedTelephonedNumber;
		String dialedTelephonedNumberFromUI, dialedTelephonedNumberFromUIWithPrefix;
		dialedTelephonedNumberFromUI = getDialedPhoneNumberFromUI();
		dialedTelephonedNumberFromUIWithPrefix = addPhonePrefix(dialedTelephonedNumberFromUI);
		dialedTelephonedNumber = Uri.parse(dialedTelephonedNumberFromUIWithPrefix);
		return dialedTelephonedNumber;
	}

	private String getDialedPhoneNumberFromUI() {
		return ((TextView) findViewById(R.id.dialedNumber)).getText().toString();
	}

	private String addPhonePrefix(String dialedTelephonedNumberFromUI) {
		return PHONE_PREFIX.concat(dialedTelephonedNumberFromUI);
	}
	
	private boolean isAValidUri(Uri telephoneNumberToBeCalled)
	{
		return !telephoneNumberToBeCalled.equals(EMPTY_URI);
	}
}