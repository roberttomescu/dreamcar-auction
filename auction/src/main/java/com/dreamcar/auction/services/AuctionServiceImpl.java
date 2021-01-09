package com.dreamcar.auction.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcar.auction.dao.AuctionDAO;
import com.dreamcar.auction.dao.BidDAO;
import com.dreamcar.auction.entities.Auction;
import com.dreamcar.auction.entities.Bid;
import com.dreamcar.auction.errors.AuctionNotFoundException;
import com.dreamcar.auction.errors.InactiveAuctionException;
import com.dreamcar.auction.errors.NegativePriceException;
import com.dreamcar.auction.errors.PriceHigherThanMinPriceException;

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
		
		// get auction
		Auction auction = auctionDAO.findById(theBid.getAuctionId());
		
		// validate the auction is not null
		if (auction == null) {
			throw new AuctionNotFoundException();
		}

		// validate the auction is active
		if (auction.getActive() == false) {
			throw new InactiveAuctionException();
		}
		
		// validate the new Bid price is lower than the current minimum price
		if (auction.findMinPrice() <= theBid.getPrice()) {
			throw new PriceHigherThanMinPriceException();
		}
		
		// set Auction inactive if new Bid price is lower than or equal to limit price
		if (theBid.getPrice() <= auction.getPriceLimit()) {
			auction.setActive(false);
		}
		
		auction.add(theBid);
		auctionDAO.saveOrUpdateAuction(auction);
		
		//save bid
		bidDAO.saveOrUpdateBid(theBid);
	}

}
