package org.jaykid.classes;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable
{
	private int id;
	private String name;
	private String phone;
	private Bitmap photo;
	private String email;

	public Contact()
	{
		
	}
	
	public Contact(int id, String name, String phone, Bitmap photo, String email) {
		this.name = name;
		this.phone = phone;
		this.photo = photo;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean nameStartsWith(String needle)
	{
		if (name == null) return false;
		else 
		{
			return name.toLowerCase().startsWith(needle);
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public Contact(Parcel in)
	{
		readFromParcel(in);
	}
	
	public void readFromParcel(Parcel parcel)
	{
		id = parcel.readInt();
		name = parcel.readString();
		photo = parcel.readParcelable(Bitmap.class.getClassLoader());
		phone = parcel.readString();
		email = parcel.readString();
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(id);
		parcel.writeString(name);
		parcel.writeParcelable(photo, flags);
		parcel.writeString(phone);
		parcel.writeString(email);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
	{
        public Contact createFromParcel(Parcel in)
        {
            return new Contact(in);
        }
 
        public Contact[] newArray(int size)
        {
            return new Contact[size];
        }
    };
	
}
