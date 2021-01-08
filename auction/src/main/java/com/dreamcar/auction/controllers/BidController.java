package com.dreamcar.auction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcar.auction.entities.Auction;
import com.dreamcar.auction.entities.Bid;
import com.dreamcar.auction.services.AuctionService;

@RestController
@RequestMapping("/api-bid")
public class BidController {
	
	private AuctionService auctionService;
	
	@Autowired
	public BidController(AuctionService auctionService) {
		this.auctionService = auctionService;
	}
	
	@PostMapping("/bid")
	public Bid addBid(@RequestBody Bid theBid) {
		
		// force save id to 0 to force create new bid
		theBid.setId(0);
		
		// save Bid
		auctionService.saveOrUpdateBid(theBid);
		
		return theBid;
	}
}
