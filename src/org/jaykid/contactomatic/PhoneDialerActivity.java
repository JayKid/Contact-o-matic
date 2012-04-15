package org.jaykid.contactomatic;

import android.app.Activity;
import android.os.Bundle;

public class PhoneDialerActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialpad);
        
//        try {
//        	Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:+436641234567"));
//            startActivity(callIntent);
//    	} catch (Exception e) {
//    	   Log.e("Contact-o-matic", "Failed to invoke call", e);
//    	}
        
        

        
    }
	
}