package org.jboss.hibernateUniversity.criteria.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.hibernateUniversity.criteria.domain.Address;
import org.jboss.hibernateUniversity.criteria.domain.User;
//import org.jboss.hibernateUniversity.criteria.domain.User_;
import org.jboss.hibernateUniversity.criteria.domain.User_;
import org.jboss.hibernateUniversity.criteria.tools.Transactional;

/**
 * @author Emmanuel Bernard
 */
@Transactional
public class UserManager {

	@Inject
	EntityManagerFactory factory;
	private Random random = new Random( );

	@Inject
	Provider<EntityManager> lazyEM;
	     
	@Transactional
	public void createNewUsers() {
		final EntityManager entityManager = lazyEM.get();
		for (int i = 0 ; i < 1000 ; i++) {
			entityManager.persist( createNewRandomUser() );
		}


		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<User> query = cb.createQuery( User.class );
		final Root<User> u = query.from( User.class );
		query.where( cb.equal( u.get( User_.firstName ), "Emmanuel" ) );
		final List<User> users = entityManager.createQuery( query ).getResultList();

	}

	private User createNewRandomUser() {
		User user = new User( "Daniel" + random.nextInt(), "Marie" + random.nextInt() );
		new Address(
				random.nextInt() + " rue de la Porte Numero " + random.nextInt(),
				city.get( random.nextInt( city.size() ) ) ,
				new Integer(random.nextInt() % 9999).toString(),
				user );
		new Address(
				random.nextInt() + " avenue des Champs " + random.nextInt(),
				city.get( random.nextInt( city.size() ) ) ,
				new Integer(random.nextInt() % 9999).toString(),
				user );
		new Address(
				random.nextInt() + " downing street " + random.nextInt(),
				city.get( random.nextInt( city.size() ) ) ,
				new Integer(random.nextInt() % 9999).toString(),
				user );
		return user;

	}

	private List<String> city = new ArrayList<String>(10);

	public UserManager() {
		city.add( "Paris" );
		city.add( "New York" );
		city.add( "Sidney" );
		city.add( "Anwerpen" );
		city.add( "Atlanta" );
	}
}
