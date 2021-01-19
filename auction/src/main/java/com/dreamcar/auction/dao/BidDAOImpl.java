package com.dreamcar.auction.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dreamcar.auction.entities.Bid;

@Repository
public class BidDAOImpl implements BidDAO {

	private EntityManager entityManager;
	
	@Autowired
	public BidDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public void saveOrUpdateBid(Bid theBid) {
		
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save customer
		currentSession.saveOrUpdate(theBid);

	}

}
