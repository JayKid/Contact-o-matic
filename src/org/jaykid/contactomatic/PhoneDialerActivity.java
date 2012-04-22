package org.jaykid.contactomatic;

import org.jaykid.classes.DialerHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhoneDialerActivity extends Activity
{
	private DialerHelper dialerHelper;
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialpad);
        dialerHelper = new DialerHelper(this);
    }
	
	public void addButtonTextToDialer(View button)
	{
		Button uiButton = (Button)button;
	    String buttonText = uiButton.getText().toString();
		addCharacterToDialer(buttonText);
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
	
	public void callDialedNumber(View button) 
    {
		String telephoneNumberToBeCalled = getDialedPhoneNumberFromUI();
		
		dialerHelper.callNumber(telephoneNumberToBeCalled);
    }
	
	private String getDialedPhoneNumberFromUI() {
		return ((TextView) findViewById(R.id.dialedNumber)).getText().toString();
	}
	
	private void setDialedPhoneNumber(String telephoneNumber) 
	{
		((TextView) findViewById(R.id.dialedNumber)).setText(telephoneNumber);
	}
	
	private void addCharacterToDialer(String character)
	{
		String dialedPhoneNumberFromUI, dialedPhoneNumberFromUIWithAddedCharacter;
		dialedPhoneNumberFromUI = getDialedPhoneNumberFromUI();
		dialedPhoneNumberFromUIWithAddedCharacter = dialedPhoneNumberFromUI.concat(character);
		setDialedPhoneNumber(dialedPhoneNumberFromUIWithAddedCharacter);
	}

	private String substractLastNumber(String dialedTelephonedNumberFromUI) 
	{
		String dialedTelephonedNumberFromUIWithoutLastNumber;
		dialedTelephonedNumberFromUIWithoutLastNumber = 
				(String) dialedTelephonedNumberFromUI.subSequence(
						0, dialedTelephonedNumberFromUI.length()-1); 
		return dialedTelephonedNumberFromUIWithoutLastNumber;
	}
	
}