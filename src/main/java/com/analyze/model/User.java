package com.analyze.model;

import java.sql.Date;
import java.util.List;

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
	private String name;
	private String lastname;
	private String email;
	private String mobile;
	private int points;
	private String country;
	private String city;
	private String password;
	private Date date_of_creating_account;
	private Date last_login;
	

	public User(String name, String lastname, String email, String mobile, int points, String country, String city,
			String password, Date date_of_creating_account, Date last_login) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.mobile = mobile;
		this.points = points;
		this.country = country;
		this.city = city;
		this.password = password;
		this.date_of_creating_account = date_of_creating_account;
		this.last_login = last_login;
	}
	
	public User() {}
	public User(String name, String lastname, String email, String mobile, int points, String country, String city,
			String password) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.mobile = mobile;
		this.points = points;
		this.country = country;
		this.city = city;
		this.password = password;
		long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
		this.date_of_creating_account = date;
		this.last_login = date;
	}

	public User(Long id, String name, String lastname, String email, String mobile, int points, String country,
			String city, String password, Date date_of_creating_account, Date last_login) {
		this(name, lastname, email, mobile, points, country, city, password, date_of_creating_account, last_login);
		this.id = id;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate_of_creating_account() {
		return date_of_creating_account;
	}

	public void setDate_of_creating_account(Date date_of_creating_account) {
		this.date_of_creating_account = date_of_creating_account;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", mobile="
				+ mobile + ", points=" + points + ", country=" + country + ", city=" + city + ", password=" + password
				+ ", date_of_creating_account=" + date_of_creating_account + ", last_login=" + last_login + "]";
	}

}
