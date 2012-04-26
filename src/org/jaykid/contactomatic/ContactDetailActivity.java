package org.jaykid.contactomatic;

import org.jaykid.classes.Contact;
import org.jaykid.classes.DialerHelper;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetailActivity extends Activity implements SensorEventListener
{
	private SensorManager sensorMgr;
    private long lastUpdate = -1;
    private float x, y, z;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 800;
    private Contact contact;
    private DialerHelper dialerHelper = new DialerHelper(this);
    
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_detail);
		retrieveContact();
		setLayoutContactValues();
		// start motion detection
		inicialiseSensorManager();
	}
	private void retrieveContact()
	{
		Bundle extras = getIntent().getExtras();
        if (extras != null) contact = (Contact) extras.getParcelable("contact");
	}
	
	
	private void inicialiseSensorManager() 
	{
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE); 
		sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	} 
	
	private void setLayoutContactValues()
	{
		TextView contactNameLabel = (TextView)findViewById(R.id.contactName);
		contactNameLabel.setText(contact.getName());
		TextView contactPhoneLabel = (TextView)findViewById(R.id.contactPhone);
		contactPhoneLabel.setText(contact.getPhone());
		TextView contactEmailLabel = (TextView)findViewById(R.id.contactEmail);
		contactEmailLabel.setText(contact.getEmail());
	}
	
	public void onAccuracyChanged(Sensor s, int arg1) 
	{ 
		
	} 
	
	@Override 
	public void onSensorChanged(SensorEvent event) 
	{ 
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){ 
			long currentTime = getCurrentTimeInMillis(); 
			// only allow one update every 100ms.
			if ((currentTime - lastUpdate) > 100) { 
				long diffTime = (currentTime - lastUpdate);
				lastUpdate = currentTime; 
				getCurrentPositions(event);
				float speed = calculateSpeedFromDifferenceOfPositionsDividedByDifferenceOfTime(diffTime);
				if (speed > SHAKE_THRESHOLD) { 
					dialerHelper.callNumber(contact.getPhone());
				}
				updateLastPositionsWithCurrentOnes();
			} 
		} 
	}
	
	private long getCurrentTimeInMillis() 
	{
		return System.currentTimeMillis();
	}
	
	private float calculateSpeedFromDifferenceOfPositionsDividedByDifferenceOfTime(
			long diffTime) {
		return Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;
	}
	
	private void updateLastPositionsWithCurrentOnes()
	{
		last_x = x; 
		last_y = y; 
		last_z = z;
	}
	
	private void getCurrentPositions(SensorEvent event) 
	{
		x = event.values[0];
		y = event.values[1]; 
		z = event.values[2];
	} 
	
	@Override protected void onPause() 
	{ 
		if (sensorMgr != null) 
		{ 
			sensorMgr.unregisterListener(this);
			sensorMgr = null;
		} 
		super.onPause();
	}
				
}
