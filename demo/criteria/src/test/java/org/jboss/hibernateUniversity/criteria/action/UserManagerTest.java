package org.jboss.hibernateUniversity.criteria.action;

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
		//User user = new User( "Emmanuel", "Bernard" );
		userManager.createNewUsers();
		
		weld.shutdown();
	}

}
