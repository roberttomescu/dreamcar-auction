package com.dreamcar.auction.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcar.auction.dao.AuctionDAO;
import com.dreamcar.auction.dao.BidDAO;
import com.dreamcar.auction.entities.Auction;
import com.dreamcar.auction.entities.Bid;

@Service
public class AuctionServiceImpl implements AuctionService {
	
	@Autowired
	private AuctionDAO auctionDAO;
	@Autowired
	private BidDAO bidDAO;

	@Override
	@Transactional
	public List<Auction> getAuctions() {
		return auctionDAO.getAuctions();
	}

	@Override
	@Transactional
	public void saveOrUpdateAuction(Auction theAuction) {

		auctionDAO.saveOrUpdateAuction(theAuction);
	}

	@Override
	@Transactional
	public Auction findAuctionById(int id) {
		return auctionDAO.findById(id);
	}
	
	@Override
	@Transactional
	public void saveOrUpdateBid(Bid theBid) {
		// get auction, add Bid, save Auction
		Auction auction = auctionDAO.findById(theBid.getAuctionId());
		auction.add(theBid);
		auctionDAO.saveOrUpdateAuction(auction);
		
		//save bid
		bidDAO.saveOrUpdateBid(theBid);
	}


}
