package com.dreamcar.auction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcar.auction.entities.Bid;
import com.dreamcar.auction.errors.NegativePriceException;
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
	public Bid addBid(@RequestBody Bid theBid, Authentication authentication) {
		
		// force save id to 0 to force create new bid
		theBid.setId(0);
		
		// force set user to current logged in user
		theBid.setUsername(authentication.getName());
		
		// check if the price has a positive value
		if (theBid.getPrice() <= 0) {
			throw new NegativePriceException();
		}
		
		// save Bid
		auctionService.saveOrUpdateBid(theBid);
		
		return theBid;
	}
	
}
