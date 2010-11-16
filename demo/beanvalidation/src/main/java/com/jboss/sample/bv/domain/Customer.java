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
package com.jboss.sample.bv.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jboss.sample.bv.domain.constraints.CheckBlackListStatus;
import com.jboss.sample.bv.domain.constraints.NoDelay;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Emmanuel Bernard
 */
public class Customer {
	@NotEmpty @Size(max = 50)
	public String getFirstname() { return firstname; }
	public void setFirstname(String firstname) {  this.firstname = firstname; }
	private String firstname;

	@NotEmpty @Size(max = 50)
	public String getLastname() { return lastname; }
	public void setLastname(String lastname) {  this.lastname = lastname; }
	private String lastname;

	@Valid
	@NotNull(groups = ShippableAutomatically.class, message = "cannot bypass shipping info screen")
	public Address getDefaultAddress() { return defaultAddress; }
	public void setDefaultAddress(Address defaultAddress) {  this.defaultAddress = defaultAddress; }
	private Address defaultAddress;

	@Valid @NotNull(groups = BillableAutomatically.class, message = "cannot bypass billing info screen")
	public CreditCard getDefaultCreditCard() { return defaultCreditCard; }
	public void setDefaultCreditCard(CreditCard defaultCreditCard) {  this.defaultCreditCard = defaultCreditCard; }
	private CreditCard defaultCreditCard;

	//TODO Status should be OK for no delays
	@CheckBlackListStatus(
			accepted = BlackListStatus.OK,
			message = "{com.jboss.sample.bv.domain.constraints.CheckBlackListStatus.customer}",
			groups = NoDelay.class
	)
	// use {com.jboss.sample.bv.domain.constraints.CheckBlackListStatus.customer} 
	public BlackListStatus getStatus() { return status; }
	public void setStatus(BlackListStatus status) {  this.status = status; }
	private BlackListStatus status;
}
