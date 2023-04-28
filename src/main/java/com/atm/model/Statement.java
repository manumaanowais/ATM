package com.atm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "statements")
public class Statement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sid;
	private Long uid;
	private int withdrawn;
	private int deposited;
	private int credited;
	private int debited;
	private String date;
	private int balance;
	public Statement() {
	}
	public Statement(Long sid, Long uid, int withdrawn, int deposited, int credited, int debited, String date, int balance) {
		super();
		this.sid = sid;
		this.uid = uid;
		this.withdrawn = withdrawn;
		this.deposited = deposited;
		this.credited = credited;
		this.debited = debited;
		this.date = date;
		this.balance = balance;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public int getWithdrawn() {
		return withdrawn;
	}
	public void setWithdrawn(int withdrawn) {
		this.withdrawn = withdrawn;
	}
	public int getDeposited() {
		return deposited;
	}
	public void setDeposited(int deposited) {
		this.deposited = deposited;
	}
	public int getCredited() {
		return credited;
	}
	public void setCredited(int credited) {
		this.credited = credited;
	}
	public int getDebited() {
		return debited;
	}
	public void setDebited(int debited) {
		this.debited = debited;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Statement [sid=" + sid + ", uid=" + uid + ", withdrawn=" + withdrawn + ", deposited=" + deposited
				+ ", credited=" + credited + ", debited=" + debited + ", date=" + date + ", balance=" + balance + "]";
	}
	
}
