package com.dreamcar.auction.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcar.auction.dao.AuctionDAO;
import com.dreamcar.auction.entities.Auction;

@RestController
@RequestMapping("/api")
public class AuctionController {
	
	private AuctionDAO auctionDAO;
	
	@Autowired
	public AuctionController(AuctionDAO theAuctionDAO) {
		auctionDAO = theAuctionDAO;
	}

	@GetMapping("/auctions")
	public List<Auction> getAuctions() {
		return auctionDAO.getAuctions();
	}
}
