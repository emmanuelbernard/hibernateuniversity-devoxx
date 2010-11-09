package org.jboss.hibernateUniversity.criteria.action;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

	@Inject
	Provider<EntityManager> lazyEM;
	     
	@Transactional
	public void saveUser(User user) {
		final EntityManager entityManager = lazyEM.get();
		entityManager.persist( user );
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<User> query = cb.createQuery( User.class );
		final Root<User> u = query.from( User.class );
		query.where( cb.equal( u.get( User_.firstName ), "Emmanuel" ) );
		final List<User> users = entityManager.createQuery( query ).getResultList();

	}
}
