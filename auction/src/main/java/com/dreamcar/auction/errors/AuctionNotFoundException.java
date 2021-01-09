package com.dreamcar.auction.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No Auction with given auctionId could be found")
public class AuctionNotFoundException extends RuntimeException {
	
}
