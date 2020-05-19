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
public class AdvForMainGraphs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ad")
	private Long id_ad;
	private float price_per_m;
	private Long price;
	private Date ad_published;
	
	public AdvForMainGraphs() {}
	public AdvForMainGraphs(Long id_ad, float price_per_m, Long price, Date ad_published) {
		super();
		this.id_ad = id_ad;
		this.price_per_m = price_per_m;
		this.price = price;
		this.ad_published = ad_published;
	}
	public Long getId_ad() {
		return id_ad;
	}
	public void setId_ad(Long id_ad) {
		this.id_ad = id_ad;
	}
	public float getPrice_per_m() {
		return price_per_m;
	}
	public void setPrice_per_m(float price_per_m) {
		this.price_per_m = price_per_m;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Date getAd_published() {
		return ad_published;
	}
	public void setAd_published(Date ad_published) {
		this.ad_published = ad_published;
	}
	

}
