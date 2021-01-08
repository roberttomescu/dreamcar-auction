package com.dreamcar.auction.dao;

import java.util.List;

import com.dreamcar.auction.entities.Auction;

public interface AuctionDAO {
	
	public List<Auction> getAuctions();

	public void saveOrUpdateAuction(Auction theAuction);

	public Auction findById(int id);

}
