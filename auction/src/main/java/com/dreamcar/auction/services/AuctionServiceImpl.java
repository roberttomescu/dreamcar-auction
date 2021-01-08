package com.dreamcar.auction.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcar.auction.dao.AuctionDAO;
import com.dreamcar.auction.entities.Auction;

@Service
public class AuctionServiceImpl implements AuctionService {
	
	@Autowired
	private AuctionDAO auctionDAO;

	@Override
	@Transactional
	public List<Auction> getAuctions() {
		return auctionDAO.getAuctions();
	}

	@Override
	public void setAuctions() {
		// TODO Auto-generated method stub

	}

}
