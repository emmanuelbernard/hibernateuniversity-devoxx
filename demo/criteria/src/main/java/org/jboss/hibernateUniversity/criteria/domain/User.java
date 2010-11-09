package org.jboss.hibernateUniversity.criteria.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class User {
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

}
