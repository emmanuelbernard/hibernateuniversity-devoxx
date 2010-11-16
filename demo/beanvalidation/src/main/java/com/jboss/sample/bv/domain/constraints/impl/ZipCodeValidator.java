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
package com.jboss.sample.bv.domain.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jboss.sample.bv.domain.Address;
import com.jboss.sample.bv.domain.constraints.ZipCode;

/**
 * @author Emmanuel Bernard
 */
public class ZipCodeValidator implements ConstraintValidator<ZipCode, Address> {

	public void initialize(ZipCode constraintAnnotation) {
	}

	public boolean isValid(Address value, ConstraintValidatorContext context) {
		if ( value == null ) return true;
		//TODO disable default error report
		context.disableDefaultConstraintViolation();
		String countryName = value.getCountry() == null ? null : value.getCountry().getName();
		boolean result = true;
		if (countryName == null) {
			//TODO build error report "country should not be null"
			context.buildConstraintViolationWithTemplate( "country should not be null" )
					.addNode( "country" )
					.addConstraintViolation();
			result = false;
		}
		String zipCode = value.getZipCode();
		if ( zipCode == null ) {
			//TODO build error report "zipCode should not be null"
			context.buildConstraintViolationWithTemplate( "zipcode should not be null" )
					.addNode( "zipCode" )
					.addConstraintViolation();
			result = false;
		}
		if (!result) return false;

		if ( "France".equals( countryName ) ) {
			return validateFrenchZipCode( value.getZipCode(), context );
		}
		else if ( "USA".equals( countryName ) ) {
			return validateUSAZipCode( zipCode, context );
		}
		return true;
	}

	private boolean validateFrenchZipCode(String zipCode, ConstraintValidatorContext context) {
		//check that they are of 5 digits
		if ( zipCode.length() != 5 ) {
			context.buildConstraintViolationWithTemplate( "zipCode should be made of 5 digits" )
					.addNode( "zipCode" )
					.addConstraintViolation();
			return false;
		}
		//TODO check that the first two are the department
		return true;
	}

	private boolean validateUSAZipCode(String zipCode, ConstraintValidatorContext context) {
		//check that they are of 5 digits
		if ( zipCode.length() != 5 ) {
			context.buildConstraintViolationWithTemplate( "zipCode should be made of 5 digits" )
					.addNode( "zipCode" )
					.addConstraintViolation();
			return false;

		}
		if ( ! isInUSPSDatabase(zipCode) ) {
			context.buildConstraintViolationWithTemplate( "zipCode is unknown to USPS" )
					.addNode( "zipCode" )
					.addConstraintViolation();
			return false;
		}
		return true;
	}

	private boolean isInUSPSDatabase(String zipCode) {
		return true;
	}
}
