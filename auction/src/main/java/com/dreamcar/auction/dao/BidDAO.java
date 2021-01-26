package com.dreamcar.auction.dao;

import java.util.List;

import com.dreamcar.auction.entities.Bid;

public interface BidDAO {

	public void saveOrUpdateBid(Bid theBid);

	public List<Bid> findByUsername(String name);

}
