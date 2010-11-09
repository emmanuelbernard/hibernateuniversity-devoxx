package org.jboss.hibernateUniversity.criteria.tools;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * @author Emmanuel Bernard
 */
@InterceptorBinding
@Target({ METHOD, TYPE})
@Retention(RUNTIME)
public @interface Transactional {
}
