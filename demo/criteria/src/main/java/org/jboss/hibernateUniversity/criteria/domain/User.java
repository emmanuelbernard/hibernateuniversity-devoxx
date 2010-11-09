package org.jboss.hibernateUniversity.criteria.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class User {

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Id @GeneratedValue
	public Long getId() { return id; }
	public void setId(Long id) {  this.id = id; }
	private Long id;

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) {  this.firstName = firstName; }
	private String firstName;

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) {  this.lastName = lastName; }
	private String lastName;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "owner")
	public Set<Address> getAddresses() { return addresses; }
	public void setAddresses(Set<Address> addresses) {  this.addresses = addresses; }
	private Set<Address> addresses = new HashSet<Address>();

}
