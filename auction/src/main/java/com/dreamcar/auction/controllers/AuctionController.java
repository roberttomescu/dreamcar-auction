package com.dreamcar.auction.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcar.auction.entities.Auction;
import com.dreamcar.auction.services.AuctionService;

@RestController
@RequestMapping("/api")
public class AuctionController {
	
	private AuctionService auctionService;
	
	@Autowired
	public AuctionController(AuctionService auctionService) {
		this.auctionService = auctionService;
	}

	@GetMapping("/auctions")
	public List<Auction> getAuctions() {
		return auctionService.getAuctions();
	}
	
	@PostMapping("/auction")
	public Auction addAuction(@RequestBody Auction theAuction) {
		
		// force save id to 0 to force create new auction
		theAuction.setId(0);
		
		// force new auction to be active when added
		theAuction.setActive(true);
		
		//auctionService.saveOrUpdateAuction(theAuction);
		
		return theAuction;
	}
}
