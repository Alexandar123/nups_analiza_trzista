package com.analyze.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_data5_no_image")
public class TableAds {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ad")
	private int id_ad;
	@Column(name = "url")
	private String url;
	@Column(name = "full_address")
	private String full_address;
	@Column(name = "price_per_m")
	private double price_per_m;
	@Column(name = "ad_published")
	private Date ad_published;
	@Column(name = "price")
	private int price;
	@Column(name = "areas")
	private int areas;
	@Column(name = "type_of_ad")
	private String type_of_ad;
	@Column(name = "type_of_property")
	private String type_of_property;
	@Column(name = "lat")
	private double lat;
	@Column(name = "lon")
	private double lon;
	@Column(name = "active")
	private int active;
	public TableAds() {}
	public TableAds(int id_ad, String url, String full_address, double price_per_m, Date ad_published, int price,
			int areas, String type_of_ad, String type_of_property, double lat,double lon, int active) {
		super();
		this.id_ad = id_ad;
		this.url = url;
		this.full_address = full_address;
		this.price_per_m = price_per_m;
		this.ad_published = ad_published;
		this.price = price;
		this.areas = areas;
		this.type_of_ad = type_of_ad;
		this.type_of_property = type_of_property;
		this.lon = lon;
		this.lat = lat;
		this.active = active;
	}
	public int getId_ad() {
		return id_ad;
	}
	public void setId_ad(int id_ad) {
		this.id_ad = id_ad;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFull_address() {
		return full_address;
	}
	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}
	public double getPrice_per_m() {
		return price_per_m;
	}
	public void setPrice_per_m(double price_per_m) {
		this.price_per_m = price_per_m;
	}
	public Date getAd_published() {
		return ad_published;
	}
	public void setAd_published(Date ad_published) {
		this.ad_published = ad_published;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAreas() {
		return areas;
	}
	public void setAreas(int areas) {
		this.areas = areas;
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
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "TableAds [id_ad=" + id_ad + ", url=" + url + ", full_address=" + full_address + ", price_per_m="
				+ price_per_m + ", ad_published=" + ad_published + ", price=" + price + ", areas=" + areas
				+ ", type_of_ad=" + type_of_ad + ", type_of_property=" + type_of_property + ", lon=" + lon + ", lat="
				+ lat + ", active=" + active + "]";
	}
}
