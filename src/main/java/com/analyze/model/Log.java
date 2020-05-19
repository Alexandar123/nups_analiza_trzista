package com.analyze.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logs")
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_log;
	private Date time_of_entry;
	private Date exit_time;
	private String ip;

	int user_id;
	
	public Log() {
	}

	public Log(int id_log, Date time_of_entry, Date exit_time, String ip, int user_id) {
		super();
		this.id_log = id_log;
		this.time_of_entry = time_of_entry;
		this.exit_time = exit_time;
		this.ip = ip;
		this.user_id = user_id;
	}

	public Log(Date time_of_entry, Date exit_time, String ip, int user_id) {
		super();
		this.time_of_entry = time_of_entry;
		this.exit_time = exit_time;
		this.ip = ip;
		this.user_id = user_id;
	}

	public int getId_log() {
		return id_log;
	}

	public void setId_log(int id_log) {
		this.id_log = id_log;
	}

	public Date getTime_of_entry() {
		return time_of_entry;
	}

	public void setTime_of_entry(Date time_of_entry) {
		this.time_of_entry = time_of_entry;
	}

	public Date getExit_time() {
		return exit_time;
	}

	public void setExit_time(Date exit_time) {
		this.exit_time = exit_time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
