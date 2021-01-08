package com.dreamcar.auction.services;

import java.util.List;

import com.dreamcar.auction.entities.Auction;

public interface AuctionService {
	
	public List<Auction> getAuctions();
	
	public void setAuctions();

}
