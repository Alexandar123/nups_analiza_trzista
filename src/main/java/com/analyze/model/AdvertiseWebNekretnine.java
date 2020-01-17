package com.analyze.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_data3")
public class AdvertiseWebNekretnine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ad")
	private Long id;
	private String name;
	private String url;
	private Long price;
	private int areas;
	private Date ad_published;
	private String title;
	private String description;
	private String address;
	private String full_address;

	private String floor;
	private float num_of_rooms;
	private String city;
	private String state;
	private String street;
	private float price_per_m;
	private byte[] image1;
	private byte[] image2;
	private String type_of_ad;
	private Date date_of_inserting;
	private String type_of_property;
	private int building_year;

	public AdvertiseWebNekretnine() {
	}

	public AdvertiseWebNekretnine(String name, String url, Long price, int areas, Date ad_published, String title,
			String description, String address, String full_address, String floor, float num_of_rooms, String city,
			String state, String street, float price_per_m, byte[] image1, byte[] image2, String type_of_ad, int building_yer) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.price = price;
		this.areas = areas;
		this.ad_published = ad_published;
		this.title = title;
		this.description = description;
		this.address = address;
		this.full_address = full_address;
		this.floor = floor;
		this.num_of_rooms = num_of_rooms;
		this.city = city;
		this.state = state;
		this.street = street;
		this.price_per_m = price_per_m;
		this.image1 = image1;
		this.image2 = image2;
		this.type_of_ad = type_of_ad;
		this.building_year = building_yer;
		
	}
	
	public AdvertiseWebNekretnine(String name, String url, Long price, int areas, Date ad_published, String title,
			String description, String address, String full_address, String floor, float num_of_rooms, String city,
			String state, String street, float price_per_m, byte[] image1, byte[] image2, String type_of_ad, String type_of_property, int building_yer) {
		super();
		this.name = name;
		this.url = url;
		this.price = price;
		this.areas = areas;
		this.ad_published = ad_published;
		this.title = title;
		this.description = description;
		this.address = address;
		this.full_address = full_address;
		this.floor = floor;
		this.num_of_rooms = num_of_rooms;
		this.city = city;
		this.state = state;
		this.street = street;
		this.price_per_m = price_per_m;
		this.image1 = image1;
		this.image2 = image2;
		this.type_of_ad = type_of_ad;
		this.type_of_property = type_of_property;
		this.building_year = building_yer;
	}

	public AdvertiseWebNekretnine(Long id, String name, String url, Long price, int areas, Date ad_published,
			String title, String description, String address, String full_address, String floor, float num_of_rooms,
			String city, String state, String street, float price_per_m, byte[] image1, byte[] image2,
			String type_of_ad, int building_yer) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.price = price;
		this.areas = areas;
		this.ad_published = ad_published;
		this.title = title;
		this.description = description;
		this.address = address;
		this.full_address = full_address;
		this.floor = floor;
		this.num_of_rooms = num_of_rooms;
		this.city = city;
		this.state = state;
		this.street = street;
		this.price_per_m = price_per_m;
		this.image1 = image1;
		this.image2 = image2;
		this.type_of_ad = type_of_ad;
		this.building_year = building_yer;
	}

	public String getType_of_ad() {
		return type_of_ad;
	}

	public void setType_of_ad(String type_of_ad) {
		this.type_of_ad = type_of_ad;
	}

	public String getFull_address() {
		return full_address;
	}

	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public int getAreas() {
		return areas;
	}

	public void setAreas(int areas) {
		this.areas = areas;
	}

	public Date getAd_published() {
		return ad_published;
	}

	public void setAd_published(Date ad_published) {
		this.ad_published = ad_published;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public float getNum_of_rooms() {
		return num_of_rooms;
	}

	public void setNum_of_rooms(float num_of_rooms) {
		this.num_of_rooms = num_of_rooms;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public float getPrice_per_m() {
		return price_per_m;
	}

	public void setPrice_per_m(float price_per_m) {
		this.price_per_m = price_per_m;
	}

	public byte[] getImage1() {
		return image1;
	}

	public void setImage1(byte[] image1) {
		this.image1 = image1;
	}

	public byte[] getImage2() {
		return image2;
	}

	public void setImage2(byte[] image2) {
		this.image2 = image2;
	}

	public Date getDate_of_inserting() {
		return date_of_inserting;
	}

	public void setDate_of_inserting(Date date_of_inserting) {
		this.date_of_inserting = date_of_inserting;
	}

	public String getType_of_property() {
		return type_of_property;
	}

	public void setType_of_property(String type_of_property) {
		this.type_of_property = type_of_property;
	}

	public int getBuilding_yer() {
		return building_year;
	}

	public void setBuilding_yer(int building_yer) {
		this.building_year = building_yer;
	}

	@Override
	public String toString() {
		return "AdvertiseWebNekretnine [id=" + id + ", name=" + name + ", url=" + url + ", price=" + price + ", areas="
				+ areas + ", ad_published=" + ad_published + ", title=" + title + ", description=" + description
				+ ", address=" + address + ", floor=" + floor + ", num_of_rooms=" + num_of_rooms + ", city=" + city
				+ ", state=" + state + ", street=" + street + ", price_per_m=" + price_per_m;
	}

}
