package com.analyze.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_data5_no_image")
public class AdvertiseForGraph {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ad")
	private int id_ad;
	private double price_per_m;
	private int price;
	private int areas;
	
	public AdvertiseForGraph() {}
	public AdvertiseForGraph(int id_ad, double price_per_m, int price, int areas) {
		super();
		this.id_ad = id_ad;
		this.price_per_m = price_per_m;
		this.price = price;
		this.areas = areas;
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
	@Override
	public String toString() {
		return "AdvertiseForGraph [id_ad=" + id_ad + ", price_per_m=" + price_per_m + ", price=" + price + ", areas="
				+ areas + "]";
	}

	
}
