package com.dreamcar.auction.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcar.auction.entities.Auction;

@RestController
@RequestMapping("/api")
public class AuctionController {

	@GetMapping("/auctions")
	public List<Auction> getAuctions() {
		
		List<Auction> auctions = new ArrayList<>();
		
		auctions.add(new Auction(0, "Wheels"));
		auctions.add(new Auction(1, "Chairs"));
		auctions.add(new Auction(2, "Windows"));
		
		return auctions;
	}
}
