package com.dreamcar.auction.services;

import java.util.List;

import com.dreamcar.auction.entities.Auction;
import com.dreamcar.auction.entities.Bid;

public interface AuctionService {
	
	public List<Auction> getAuctions();

	public void saveOrUpdateAuction(Auction theAuction);

	public Auction findAuctionById(int id);
	
	public void saveOrUpdateBid(Bid theBid);

}
