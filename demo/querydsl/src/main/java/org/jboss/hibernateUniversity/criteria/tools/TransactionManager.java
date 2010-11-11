package org.jboss.hibernateUniversity.criteria.tools;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Emmanuel Bernard
 */
@Singleton
public class TransactionManager {
	@Inject
	private EntityManagerFactory factory;
	private ThreadLocal<EntityManager> emHolder = new ThreadLocal<EntityManager>();

	public void begin() {
		EntityManager entityManager = this.emHolder.get();
		if ( entityManager == null ) {
			entityManager = factory.createEntityManager();
			entityManager.getTransaction().begin();
			emHolder.set( entityManager );
		}
		else {
			throw new RuntimeException( "start a tx on an already started EM");
		}
	}

	@Produces @Dependent
	public EntityManager getEntityManager() {
		EntityManager entityManager = this.emHolder.get();
		if ( entityManager == null ) {
			throw new RuntimeException( "EM not started");
		}
		else {
			return entityManager;
		}
	}

	public void commit() {
		EntityManager entityManager = this.emHolder.get();
		if ( entityManager == null ) {
			throw new RuntimeException( "commit a tx on a null EM");
		}
		else {
			entityManager.getTransaction().commit();
			entityManager.close();
			emHolder.set( null );
		}
	}

	public void rollback() {
		EntityManager entityManager = this.emHolder.get();
		if ( entityManager == null ) {
			throw new RuntimeException( "rollback a tx on a null EM");
		}
		else {
			entityManager.getTransaction().rollback();
			entityManager.close();
			emHolder.set( null );
		}
	}
}
