package org.jboss.hibernateUniversity.criteria.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;

import org.jboss.hibernateUniversity.criteria.domain.Address;
import org.jboss.hibernateUniversity.criteria.domain.Address_;
import org.jboss.hibernateUniversity.criteria.domain.Gender;
import org.jboss.hibernateUniversity.criteria.domain.Login_;
import org.jboss.hibernateUniversity.criteria.domain.User;
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


	//get all users whose firstname is emmanuel
	@Transactional
	public List<User> getUsersNamedEmmanuel() {
		EntityManager em = lazyEM.get();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		final CriteriaQuery<User> query = cb.createQuery( User.class );
		final Root<User> u = query.from( User.class );
		u.fetch( User_.addresses );

		query.select( u ).distinct( true ).where( cb.equal( u.get( User_.firstName ), "Emmanuel" ) );

		final TypedQuery<User> typedQuery = em.createQuery( query );
		typedQuery.setFirstResult( 0 ).setMaxResults( 20 );
		final List<User> resultList = typedQuery.getResultList();
		return  resultList;
	}

	//get all users living in Paris
	@Transactional
	public List<User> getUsersFromParis() {
		EntityManager em = lazyEM.get();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		final CriteriaQuery<User> query = cb.createQuery( User.class );
		final Root<User> u = query.from( User.class );
		final SetJoin<User,Address> a = u.join( User_.addresses );

		query.select( u ).distinct( true ).where( cb.equal( a.get( Address_.city ), "Paris" ) );

		final TypedQuery<User> typedQuery = em.createQuery( query );
		final List<User> resultList = typedQuery.getResultList();
		return  resultList;
	}

	//get all users between 30 and 40 years old
	@Transactional
	public List<User> getUsersBetween30And40() {
		EntityManager em = lazyEM.get();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		final CriteriaQuery<User> query = cb.createQuery( User.class );
		final Root<User> u = query.from( User.class );

		final Calendar end = Calendar.getInstance();
		end.setTimeInMillis( System.currentTimeMillis() );
		end.add( Calendar.YEAR, - 30 );
		final Calendar begin = Calendar.getInstance();
		end.setTimeInMillis( System.currentTimeMillis() );
		end.add( Calendar.YEAR, - 40 );
		query
				.select( u )
				.where(
						cb.between(
								u.get( User_.birthDate ),
								new Date( end.getTimeInMillis() ),
								new Date( begin.getTimeInMillis() ) ) );

		final TypedQuery<User> typedQuery = em.createQuery( query );
		final List<User> resultList = typedQuery.getResultList();
		return  resultList;
	}

	//get average user credit by gender limited to male and females and number on each level
	@Transactional
	public List<?> getAvgCreditByGender() {
		EntityManager em = lazyEM.get();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		final CriteriaQuery<Tuple> query = cb.createTupleQuery();
		final Root<User> u = query.from( User.class );

		final Path<Gender> gender = u.get( User_.gender );
		gender.alias( "gender" );
		final Selection<Double> credits = cb.avg( u.get( User_.credits ) ).alias( "credits" );
		query.multiselect(
				credits,
				gender,
				cb.count( u ) )
			.where(
					cb.between( u.get(User_.login).get( Login_.username ), "a", "v" ),
					cb.isNotNull( u.get(User_.login).get( Login_.password ) )
			)

			.groupBy( gender )
				.having(
						cb.in( gender)
								.value( Gender.FEMALE )
								.value( Gender.MALE ) )
			.orderBy( cb.desc( gender ) );

		final TypedQuery<Tuple> typedQuery = em.createQuery( query );
		final List<Tuple> list = typedQuery.getResultList();
		final Tuple tuple = list.get( 0 );
		final Double average = tuple.get( credits );
		System.out.println("Avg credit " + tuple.get( credits ) + " " + tuple.get( "gender" ) );
		return list;
	}

	//get all users who live in cities that have users above 55 years old

	     
	@Transactional
	public void createNewUsers() {
		final EntityManager entityManager = lazyEM.get();
		for (int i = 0 ; i < 1000 ; i++) {
			entityManager.persist( createNewRandomUser() );
		}
	}

	private User createNewRandomUser() {
		final Calendar cal = Calendar.getInstance();
		cal.set( 19 * 100 + 50 + random.nextInt( 60 ), random.nextInt(12 ), random.nextInt( 28 ) );
		final int userRand = random.nextInt();
		User user = new User(
				userRand % 20 == 0 ? "Emmanuel" : "Daniel" + userRand,
				"Marie" + random.nextInt(),
				new Date( cal.getTimeInMillis() ),
				getGender(),
				random.nextInt( 30 ),
				"user" + random.nextInt(),
				"" + random.nextInt()
		);
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

	private Gender getGender() {
		int i = random.nextInt(10);
		if ( i < 4 ) {
			return Gender.MALE;
		}
		else if ( i < 8 ) {
			return Gender.FEMALE;
		}
		else {
			return Gender.NOT_SURE;
		}
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
