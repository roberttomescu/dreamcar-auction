package com.dreamcar.auction.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="The entered price is higher than the current min price; please enter a bid with a price lower than the current leader's.")
public class PriceHigherThanMinPriceException extends RuntimeException {

}
