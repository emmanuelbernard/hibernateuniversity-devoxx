package org.jboss.hibernateUniversity.criteria.action;

import java.util.List;

import org.jboss.hibernateUniversity.criteria.domain.User;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

/**
 * @author Emmanuel Bernard
 */
public class UserManagerTest {


	@Test
	public void testUserCreation() {
		final Weld weld = new Weld();
		WeldContainer weldContainer = weld.initialize();

		final UserManager userManager = weldContainer.instance().select( UserManager.class ).get();
		userManager.createNewUsers();
		
		weld.shutdown();
	}

	@Test
	public void testQueries() {
		final Weld weld = new Weld();
		WeldContainer weldContainer = weld.initialize();

		final UserManager userManager = weldContainer.instance().select( UserManager.class ).get();

		final List<?> users = userManager.getUsersBetween30And40();

		for( Object o : users) {
			System.err.println( o.toString() );
		}

		weld.shutdown();
	}

	@Test
	public void testAvgQuery() {
		final Weld weld = new Weld();
		WeldContainer weldContainer = weld.initialize();

		final UserManager userManager = weldContainer.instance().select( UserManager.class ).get();

		userManager.displayAvgCreditByGender();

		weld.shutdown();
	}

}
