package com.analyze.mapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_data5_no_image")
public class AdsData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ad")
	private int id_ad;
	@Column(name = "price_per_m")
	private double price_per_m;
	@Column(name = "areas")
	private int areas;
	@Column(name = "type_of_ad")
	private String type_of_ad;
	@Column(name = "type_of_property")
	private String type_of_property;
	private double lat;
	@Column(name = "lon")
	private double lon;
	
	public AdsData() {}
	public AdsData(int id_ad, double price_per_m, int areas, String type_of_ad, String type_of_property, double lat,
			double lon) {
		super();
		this.id_ad = id_ad;
		this.price_per_m = price_per_m;
		this.areas = areas;
		this.type_of_ad = type_of_ad;
		this.type_of_property = type_of_property;
		this.lat = lat;
		this.lon = lon;
	}
	public int getId_ad() {
		return id_ad;
	}
	public void setId_ad(int id_ad) {
		this.id_ad = id_ad;
	}
	public double getPrice_per_m() {
		return price_per_m;
	}
	public void setPrice_per_m(double price_per_m) {
		this.price_per_m = price_per_m;
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
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	

}
