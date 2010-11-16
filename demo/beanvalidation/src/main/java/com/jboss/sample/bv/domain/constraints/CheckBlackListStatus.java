/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat, Inc. and/or its affiliates or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat, Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package com.jboss.sample.bv.domain.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.jboss.sample.bv.domain.BlackListStatus;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Emmanuel Bernard
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CheckBlackListStatus.Validator.class)
public @interface CheckBlackListStatus {
	String message() default "{com.jboss.sample.bv.domain.constraints.CheckBlackListStatus}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};

	BlackListStatus[] accepted();

	public class Validator implements ConstraintValidator<CheckBlackListStatus, BlackListStatus> {
		private List<BlackListStatus> accepted;

		public void initialize(CheckBlackListStatus constraintAnnotation) {
			final BlackListStatus[] statuses = constraintAnnotation.accepted();
			accepted = new ArrayList<BlackListStatus>( statuses.length );
			for (BlackListStatus current : statuses) {
				accepted.add( current );
			}
		}

		public boolean isValid(BlackListStatus value, ConstraintValidatorContext context) {
			return accepted.contains( value );
		}
	}
}
