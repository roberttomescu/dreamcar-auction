package com.dreamcar.auction.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcar.auction.entities.Auction;

@Repository
public class AuctionDAOImpl implements AuctionDAO {

	private EntityManager entityManager;
	
	@Autowired
	public AuctionDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	@Transactional
	public List<Auction> getAuctions() {
		
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create query
		Query<Auction> theQuery =
				currentSession.createQuery("from Auction", Auction.class);
		
		// execute query and get result list
		List<Auction> auctions = theQuery.getResultList();
		
		// return results
		return auctions;
	}

}
