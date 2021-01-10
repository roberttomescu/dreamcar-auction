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

	@Override
	public void saveOrUpdateAuction(Auction theAuction) {
		
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save customer
		currentSession.saveOrUpdate(theAuction);
	}

	@Override
	public Auction findById(int id) {
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		return currentSession.get(Auction.class, id);
	}

	@Override
	public List<Auction> getActiveAuctions() {
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create query
		Query<Auction> theQuery =
				currentSession.createQuery("from Auction where active = 1", Auction.class);
		
		// execute query and get result list
		List<Auction> auctions = theQuery.getResultList();
		
		// return results
		return auctions;
	}

	@Override
	public List<Auction> getInactiveAuctionsWithoutEmailSent() {
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create query
		Query<Auction> theQuery =
				currentSession.createQuery("from Auction where active = 0 and email_sent = 0", Auction.class);
		
		// execute query and get result list
		List<Auction> auctions = theQuery.getResultList();
		
		// return results
		return auctions;
	}

}
