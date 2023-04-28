package com.atm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="photos")
public class Photo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pid;
	private String photo;
	private Long uid;
	public Photo() {
	}
	public Photo(Long pid, String photo, Long uid) {
		super();
		this.pid = pid;
		this.photo = photo;
		this.uid = uid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Photo [pid=" + pid + ", photo=" + photo + ", uid=" + uid + "]";
	}
}
