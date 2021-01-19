package com.dreamcar.auction.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bids")
public class Bid {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	private String username;
	
	@Column
	private float price;
	
	@Column(name = "auction_id")
	private int auctionId;

	public Bid(String username, float price, int auctionId) {
		this.username = username;
		this.price = price;
		this.auctionId = auctionId;
	}
	
	public Bid() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(int auctionId) {
		this.auctionId = auctionId;
	}

	@Override
	public String toString() {
		return "Bid [id=" + id + ", username=" + username + ", price=" + price + ", auctionId=" + auctionId + "]";
	}
	
	

}
