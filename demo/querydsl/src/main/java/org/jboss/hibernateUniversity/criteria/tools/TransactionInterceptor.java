package org.jboss.hibernateUniversity.criteria.tools;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Emmanuel Bernard
 */
@Transactional @Interceptor
public class TransactionInterceptor {
	@Inject 
	TransactionManager manager;

	@AroundInvoke
	public Object manageTransaction(InvocationContext ctx) throws Exception {
		manager.begin();
		try {
			final Object result = ctx.proceed();
			manager.commit();
			return result;
		}
		catch ( Exception e ) {
			manager.rollback();
			throw e;
		}
	}
}
