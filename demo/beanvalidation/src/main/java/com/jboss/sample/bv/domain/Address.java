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

import com.jboss.sample.bv.domain.constraints.ZipCode;

/**
 * @author Emmanuel Bernard
 */
@ZipCode
public class Address {
	public String getStreet1() { return street1; }
	public void setStreet1(String street1) {  this.street1 = street1; }
	private String street1;

	public String getCity() { return city; }
	public void setCity(String city) {  this.city = city; }
	private String city;

	public String getZipCode() { return zipCode; }
	public void setZipCode(String zipCode) {  this.zipCode = zipCode; }
	private String zipCode;

	@Valid
	public Country getCountry() { return country; }
	public void setCountry(Country country) {  this.country = country; }
	private Country country;
}
