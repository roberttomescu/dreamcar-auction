package com.dreamcar.auction.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcar.auction.dao.AuctionDAO;
import com.dreamcar.auction.dao.BidDAO;
import com.dreamcar.auction.entities.Auction;
import com.dreamcar.auction.entities.Bid;
import com.dreamcar.auction.errors.AuctionNotFoundException;
import com.dreamcar.auction.errors.InactiveAuctionException;
import com.dreamcar.auction.errors.PriceHigherThanMinPriceException;

@Service
public class AuctionServiceImpl implements AuctionService {
	
	@Autowired
	private AuctionDAO auctionDAO;
	@Autowired
	private BidDAO bidDAO;
	
	private EmailService emailService;
	
	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

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
	public List<Bid> findBidsByUsername(String name) {
		return bidDAO.findByUsername(name);
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
		if (auction.getActive() == false || auction.getTimeLimit().before(new Date())) {
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
	
    
    @Scheduled(fixedRate = 15000)
    @Transactional
    public void scheduledCheckAuctionTimeoutTask() {
    	
    	// get active auctions
    	List<Auction> activeAuctions = auctionDAO.getActiveAuctions();
    	
    	// set inactive if after time limit
    	activeAuctions.forEach(auction -> {
            if (auction.getTimeLimit().before(new Date())) {
            	auction.setActive(false);
            	auctionDAO.saveOrUpdateAuction(auction);
            	System.out.println("<<<debug>>> scheduledCheckAuctionTimeoutTask: Time limit reached for auction with id: " + auction.getId());
            }
    	});

    }
    
    
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void scheduledAnnouncementTask() {
    	
    	// get inactive auctions without email_sent
    	List<Auction> activeAuctions = auctionDAO.getInactiveAuctionsWithoutEmailSent();
    	 	
    	// send email and set email_sent to true
    	activeAuctions.forEach(auction -> {
    		
    		try {
	    		//get top bid for auction
	    		Bid topBid = auction.findTopBid();
	    		
	    		//get email for topBid
	    		String email = topBid.getUsername();
	    		
	    		//fake email for debugging
	    		sendEmail(auction, email);
	    		
	            emailService.sendBidWinnerMail(auction, "mingeam@gmail.com");
	            auction.setEmailSent(true);
    		}
    		catch (NoSuchElementException e) {
    			emailService.sendAuctionNoBidsMail(auction);
    			auction.setEmailSent(true);
    		}
	            
            auctionDAO.saveOrUpdateAuction(auction);
    	});

    }
    
    // old email method used for testing
    private void sendEmail(Auction auction, String email) {
    	System.out.println("Sent email for auction with id: " + auction.getId() + " to email: " + email);
    }

}
