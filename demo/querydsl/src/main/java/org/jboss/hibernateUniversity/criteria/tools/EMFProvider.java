package org.jboss.hibernateUniversity.criteria.tools;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Emmanuel Bernard
 */
public class EMFProvider {
	@Produces @Singleton
	public EntityManagerFactory createEMF() {
		return Persistence.createEntityManagerFactory( "users" );
	}

	public void close(@Disposes EntityManagerFactory factory) {
		factory.close();
	}
}
