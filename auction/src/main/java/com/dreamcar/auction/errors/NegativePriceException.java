package com.dreamcar.auction.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Please enter a positive price.")
public class NegativePriceException extends RuntimeException {

}
