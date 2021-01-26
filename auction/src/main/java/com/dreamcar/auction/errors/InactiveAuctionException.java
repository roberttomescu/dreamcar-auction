package com.dreamcar.auction.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="The Auction is inactive; no further bids can be made")
public class InactiveAuctionException extends RuntimeException {

}
