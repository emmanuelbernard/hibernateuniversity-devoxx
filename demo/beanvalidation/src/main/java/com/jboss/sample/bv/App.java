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
package com.jboss.sample.bv;

import java.math.BigDecimal;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.jboss.sample.bv.domain.Address;
import com.jboss.sample.bv.domain.BlackListStatus;
import com.jboss.sample.bv.domain.Country;
import com.jboss.sample.bv.domain.CreditCard;
import com.jboss.sample.bv.domain.Customer;
import com.jboss.sample.bv.domain.Item;
import com.jboss.sample.bv.domain.Order;
import com.jboss.sample.bv.domain.StraightToOrderValidation;
import com.jboss.sample.bv.domain.constraints.NoDelay;

/**
 * Main application demonstrating the various Bean Validation usages
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		final App app = new App();

		//TODO uncomment one by one each operation to move to the next step of the demo
		app.testGroups();
		//app.testGroupsInheritance();
		//app.testComposition();
		//app.testComplexConstraint();
    }

	private void testGroups() {
		System.out.println("Testing group validation: state validation");
		Order order = buildValidOrder();
		Validator v = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Order>> violations =  v.validate( order );
		displayViolations( "default validation", violations );

		//Typical pattern when testing if an object graph is in a given state
		violations = v.validate( order, NoDelay.class );
		if (violations.size() == 0) {
			//place order wo delay
		}
		else {
			//route for manual inspection
		}
		displayViolations( "First no delay", violations );

		//making the object failing the state => we should see errors
		order.getCustomer().setStatus( BlackListStatus.GREY );
		Country country = new Country();
		country.setName( "LalaLand" );
		country.setStatus( BlackListStatus.BLACK );
		order.getShippingAddress().setCountry( country );
		violations = v.validate( order, NoDelay.class );
		displayViolations( "Second no delay", violations );
	}

	private void testGroupsInheritance() {
		System.out.println("\n\nTesting group inheritance");
		Customer customer = buildValidCustomer();
		Validator v = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Customer>> violations =  v.validate( customer );
		displayViolations( "default validation", violations );

		//Typical pattern when testing if an object graph is in a given state
		violations = v.validate( customer, StraightToOrderValidation.class );
		if (violations.size() == 0) {
			//To straight to validation screen
		}
		else {
			//ask for shipping and credit card
		}
		displayViolations( "Can go to validation screen", violations );

		//The object graph will now be both invalid and not shippable autonatically
		customer.setDefaultAddress( null );
		customer.getDefaultCreditCard().setNumber( "4408041234567890" );
		violations = v.validate( customer, StraightToOrderValidation.class );
		if (violations.size() == 0) {
			//place order wo delay
		}
		else {
			//route for manual inspection
		}
		displayViolations( "Can go to validation screen", violations );

	}

	private void testComplexConstraint() {
		System.out.println("\n\nTesting complex constraint writing");
		Validator v = Validation.buildDefaultValidatorFactory().getValidator();
		Address address = buildvalidAddress();
		//the zipcode is now invalid
		address.setZipCode( "123456" );
		Set<ConstraintViolation<Address>> violations =  v.validate( address );
		displayViolations( "default validation", violations );
	}

	private void testComposition() {
		System.out.println("\n\nTesting composition");
		Order order = buildValidOrder();
		Validator v = Validation.buildDefaultValidatorFactory().getValidator();
		//the order number is both too short and not passing the Luhn algorithm
		order.setOrderNumber( "123456789" );
		Set<ConstraintViolation<Order>> violations =  v.validate( order );
		displayViolations( "default validation", violations );
	}


	private <T> void displayViolations(String message, Set<ConstraintViolation<T>> violations) {
		StringBuilder errorReport = new StringBuilder("\n");
		errorReport.append( message ).append( "\n" );
		if (violations.size() == 0) {
			errorReport.append( "No error, the object is valid" );
		}
		else {
			errorReport.append( "Found " ).append( violations.size() ).append( " error" );
			if ( violations.size() > 1) {
				errorReport.append( "s" );
			}
			for (ConstraintViolation violation : violations) {
				errorReport.append( "\n\t\tin " ).append( violation.getRootBeanClass().getName() ).append( "." ).append( violation.getPropertyPath() );
				errorReport.append( "\n\t\tMessage: \'").append( violation.getMessage() ).append( "\'" );
			}
		}
		System.out.println(errorReport.toString());
	}

	private Order buildValidOrder() {
		Address address = buildvalidAddress();
		CreditCard creditCard = buildValidCreditCard();
		Item item = new Item();
		item.setDescription( "Canon 5D Mark II" );
		item.setPrice( new BigDecimal( "1499.9" ) );
		item.setStock( 3 );
		Customer customer = new Customer();
//		customer.setDefaultAddress( address );
//		customer.setDefaultCreditCard( creditCard );
		customer.setFirstname( "Emmanuel" );
		customer.setLastname( "Bernard" );
		customer.setStatus( BlackListStatus.OK );
		Order order = new Order();
		order.setCreditCard( creditCard );
		order.setCustomer( customer );
		order.setShippingAddress( address );
		order.getItems().add( item );
		order.setOrderNumber( "1234567897" );
		return order;
	}

	private Address buildvalidAddress() {
		Country country = new Country();
		country.setName( "USA" );
		country.setStatus( BlackListStatus.OK );
		Address address = new Address();
		address.setStreet1( "100 Peachtree Circle" );
		address.setCity( "Atlanta, GA" );
		address.setCountry( country );
		address.setZipCode( "30300" );
		return address;
	}

	private Customer buildValidCustomer() {
		Address address = buildvalidAddress();
		CreditCard creditCard = buildValidCreditCard();
		Customer customer = new Customer();
		customer.setDefaultAddress( address );
		customer.setDefaultCreditCard( creditCard );
		customer.setFirstname( "Emmanuel" );
		customer.setLastname( "Bernard" );
		customer.setStatus( BlackListStatus.OK );
		return customer;
	}

	private CreditCard buildValidCreditCard() {
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber( "4408041234567893" );
		creditCard.setOwner( "Emmanuel Bernard" );
		return creditCard;
	}
}
