package org.jboss.hibernateUniversity.criteria.domain;

import javax.persistence.Embeddable;

/**
 * @author Emmanuel Bernard
 */
@Embeddable
public class Login {

	public Login() {
	}

	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() { return username; }
	public void setUsername(String username) {  this.username = username; }
	private String username;

	public String getPassword() { return password; }
	public void setPassword(String password) {  this.password = password; }
	private String password;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "Login" );
		sb.append( "{username='" ).append( username ).append( '\'' );
		sb.append( ", password='" ).append( password ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}
}
