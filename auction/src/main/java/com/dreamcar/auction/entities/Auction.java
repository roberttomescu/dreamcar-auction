package com.dreamcar.auction.entities;

public class Auction {

	private int id;
	private String name;
	//private String description;
	//private float priceLimit; //only for ROLE_ADMIN
	//private List<Offer> offers; //only for ROLE_ADMIN
	//private LocalDate timeLimit;
	
	public Auction() {
		
	}
	
	public Auction(int id, String name) {
		this.id = id;
		this.name = name;
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
	
}
