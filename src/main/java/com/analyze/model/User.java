package com.analyze.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "lastname")
	private String lastname;
	@Column(name = "email")
	private String email;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "points")
	private int points;
	@Column(name = "country")
	private String country;
	@Column(name = "city")
	private String city;
	
	@Column(name="auth_id")
	private String auth_id;

	@Column(name = "date_of_creating_account")
	private Date date_of_creating_account;

	@Column(name = "coins_expiration")
	private Date coins_expiration;

	public User(Long id, String name, String lastname, String email, String mobile, int points, String country,
			String city, Date date_of_creating_account, Timestamp last_login, Date coins_expiration, String auth_id ) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.mobile = mobile;
		this.points = points;
		this.country = country;
		this.city = city;
		this.date_of_creating_account = date_of_creating_account;
		this.coins_expiration = coins_expiration;
		this.auth_id = auth_id;
	}
	public User() {}
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getDate_of_creating_account() {
		return date_of_creating_account;
	}

	public void setDate_of_creating_account(Date date_of_creating_account) {
		this.date_of_creating_account = date_of_creating_account;
	}

	public Date getCoins_expiration() {
		return coins_expiration;
	}

	public void setCoins_expiration(Date coins_expiration) {
		this.coins_expiration = coins_expiration;
	}

	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", mobile="
				+ mobile + ", points=" + points + ", country=" + country + ", city=" + city
				+ ", date_of_creating_account=" + date_of_creating_account + ", coins_expiration=" + coins_expiration + "]";
	}

}
