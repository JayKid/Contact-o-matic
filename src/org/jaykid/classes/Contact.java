package org.jaykid.classes;

import android.graphics.Bitmap;

public class Contact implements Item
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
	
}
