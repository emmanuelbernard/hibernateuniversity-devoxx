package org.jboss.hibernateUniversity.criteria.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class Address {

	public Address() {
	}

	public Address(String street, String city, String zipCode, User owner) {
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.owner = owner;
		owner.getAddresses().add( this );
	}

	@Id @GeneratedValue
	public Long getId() { return id; }
	public void setId(Long id) {  this.id = id; }
	private Long id;

	public String getStreet() { return street; }
	public void setStreet(String street) {  this.street = street; }
	private String street;

	public String getCity() { return city; }
	public void setCity(String city) {  this.city = city; }
	private String city;

	public String getZipCode() { return zipCode; }
	public void setZipCode(String zipCode) {  this.zipCode = zipCode; }
	private String zipCode;

	@ManyToOne
	public User getOwner() { return owner; }
	public void setOwner(User owner) {  this.owner = owner; }
	private User owner;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "Address" );
		sb.append( "{id=" ).append( id );
		sb.append( ", street='" ).append( street ).append( '\'' );
		sb.append( ", city='" ).append( city ).append( '\'' );
		sb.append( ", zipCode='" ).append( zipCode ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}
}
