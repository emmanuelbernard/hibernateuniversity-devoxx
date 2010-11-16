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

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.jboss.sample.bv.domain.constraints.Luhn;
import com.jboss.sample.bv.domain.constraints.OrderNumber;

/**
 * @author Emmanuel Bernard
 */
public class Order {
	@OrderNumber( size = 10 )
	public String getOrderNumber() { return orderNumber; }
	public void setOrderNumber(String orderNumber) {  this.orderNumber = orderNumber; }
	private String orderNumber;

	@Valid
	public Customer getCustomer() { return customer; }
	public void setCustomer(Customer customer) {  this.customer = customer; }
	private Customer customer;

	@Valid
	public CreditCard getCreditCard() { return creditCard; }
	public void setCreditCard(CreditCard creditCard) {  this.creditCard = creditCard; }
	private CreditCard creditCard;

	@Valid
	public Address getShippingAddress() { return shippingAddress; }
	public void setShippingAddress(Address shippingAddress) {  this.shippingAddress = shippingAddress; }
	private Address shippingAddress;

	public List<Item> getItems() { return items; }
	public void setItems(List<Item> items) {  this.items = items; }
	private List<Item> items = new ArrayList<Item>();
}
