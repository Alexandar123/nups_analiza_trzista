package com.analyze.model;

import java.sql.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Advertise {
	private Long id;
	private String image;
	private String imageUrl;
	private int price;
	private int square;
	private int num_of_rooms;
	private float price_per_m;
	private String cite;
	private String address;
	private String heating;
	private int floor;
	private Date ad_published;
	
	public String images;

	public Advertise() {

	}

	public Advertise(String image, String imageUrl, int price, int square, int num_of_rooms, float price_per_m,
			String cite, String address, String heating, int floor, Date ad_published) {
		super();
		this.image = image;
		this.imageUrl = imageUrl;
		this.price = price;
		this.square = square;
		this.num_of_rooms = num_of_rooms;
		this.cite = cite;
		this.price_per_m = price_per_m;
		this.address = address;
		this.heating = heating;
		this.floor = floor;
		this.ad_published = ad_published;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCite() {
		return cite;
	}

	public void setCite(String cite) {
		this.cite = cite;
	}

	public int getSquare() {
		return square;
	}

	public void setSquare(int square) {
		this.square = square;
	}

	public int getNum_of_rooms() {
		return num_of_rooms;
	}

	public void setNum_of_rooms(int num_of_rooms) {
		this.num_of_rooms = num_of_rooms;
	}

	public float getPrice_per_m() {
		return price_per_m;
	}

	public void setPrice_per_m(float price_per_m) {
		this.price_per_m = price_per_m;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeating() {
		return heating;
	}

	public void setHeating(String heating) {
		this.heating = heating;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public Date getAd_published() {
		return ad_published;
	}

	public void setAd_published(Date ad_published) {
		this.ad_published = ad_published;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("***** Advertise Details *****\n");
		sb.append("Image: " + image + "\n");
		sb.append("Image url: " + imageUrl + "\n");
		sb.append("Price: " + price + "\n");
		sb.append("Square: " + square + "\n");
		sb.append("Number of rooms: " + num_of_rooms + "\n");
		sb.append("Price per meter: " + price_per_m + "\n");
		sb.append("Cite: " + cite + "\n");
		sb.append("Address: " + address + "\n");
		sb.append("Heating: " + heating + "\n");
		sb.append("Floor: " + floor + "\n");
		sb.append("Ad published: " + ad_published + "\n");
		sb.append("*****************************");

		return sb.toString();
	}

}
