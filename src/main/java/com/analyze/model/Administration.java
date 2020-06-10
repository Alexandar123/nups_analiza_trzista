package com.analyze.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_data5_no_image")
public class Administration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ad")
	private Long id;
	private String name;
	private String url;
	private Long price;
	private int areas;
	private Date ad_published;
	private Date ad_removed;
	private String title;

	private String city;
	private String state;
	private String street;
	private float price_per_m;
	private String type_of_ad;
	private String type_of_property;
	private int active;
	
	public Administration() {}
	public Administration(Long id, String name, String url, Long price, int areas, Date ad_published, Date ad_removed,
			String title, String city, String state, String street, float price_per_m,
			String type_of_ad, String type_of_property, int active) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.price = price;
		this.areas = areas;
		this.ad_published = ad_published;
		this.ad_removed = ad_removed;
		this.title = title;
		this.city = city;
		this.state = state;
		this.street = street;
		this.price_per_m = price_per_m;
		this.type_of_ad = type_of_ad;
		this.type_of_property = type_of_property;
		this.active = active;
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


	public Date getAd_removed() {
		return ad_removed;
	}


	public void setAd_removed(Date ad_removed) {
		this.ad_removed = ad_removed;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public String getType_of_ad() {
		return type_of_ad;
	}


	public void setType_of_ad(String type_of_ad) {
		this.type_of_ad = type_of_ad;
	}

	public String getType_of_property() {
		return type_of_property;
	}


	public void setType_of_property(String type_of_property) {
		this.type_of_property = type_of_property;
	}

	public int getActive() {
		return active;
	}


	public void setActive(int active) {
		this.active = active;
	}

}
