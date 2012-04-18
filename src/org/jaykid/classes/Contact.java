package org.jaykid.classes;

import android.graphics.Bitmap;

public class Contact implements Item
{
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private Integer phone;
	private Bitmap photo;
	
	public Contact()
	{
		
	}

	public Contact(String name, Integer phone, Bitmap photo) {
		this.name = name;
		this.phone = phone;
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	
}
