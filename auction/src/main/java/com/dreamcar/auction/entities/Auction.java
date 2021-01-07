package com.dreamcar.auction.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="auctions")
public class Auction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column(name = "time_limit", nullable = false, updatable=false)
	@Temporal(TemporalType.DATE)
	private Date timeLimit;
	
	@Column(name = "price_limit")
	private float priceLimit;
	
	@Column
	private boolean active;
	
	@OneToMany()
	@JoinColumn(name = "auction_id")
	private List<Bid> bids;
	
	public Auction() {
		
	}
	
	public Auction(String name, String description, Date timeLimit, int priceLimit, boolean active) {
		this.name = name;
		this.description = description;
		this.timeLimit = timeLimit;
		this.priceLimit = priceLimit;
		this.active = active;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Date timeLimit) {
		this.timeLimit = timeLimit;
	}

	public float getPriceLimit() {
		return priceLimit;
	}

	public void setPriceLimit(float priceLimit) {
		this.priceLimit = priceLimit;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	@Override
	public String toString() {
		return "Auction [id=" + id + ", name=" + name + ", description=" + description + ", timeLimit=" + timeLimit
				+ ", priceLimit=" + priceLimit + ", active=" + active + "]";
	}	
	
	public void add(Bid tempBid) {
		if (bids == null) {
			bids = new ArrayList<>();
		}
		
		bids.add(tempBid);
	}
	
}
