package org.jboss.hibernateUniversity.criteria.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Resolution;

/**
 * @author Emmanuel Bernard
 */
@Entity @Indexed
public class Book {
	@Id @GeneratedValue
	public Long getId() { return id; }
	public void setId(Long id) {  this.id = id; }
	private Long id;

	@Fields( {
			@Field,
			@Field(name = "title_ngram",
					analyzer =  @Analyzer(definition = "ngram") )
	})
	public String getTitle() { return title; }
	public void setTitle(String title) {  this.title = title; }
	private String title;

	@Fields( {
			@Field,
			@Field(name = "description_ngram",
					analyzer =  @Analyzer(definition = "ngram") )
	})
	@Column(length = 10000)
	public String getDescription() { return description; }
	public void setDescription(String description) {  this.description = description; }
	private String description;

	@Temporal(TemporalType.DATE)
	@Field @DateBridge( resolution = Resolution.DAY)
	public Date getPublicationDate() { return publicationDate; }
	public void setPublicationDate(Date publicationDate) {  this.publicationDate = publicationDate; }
	private Date publicationDate;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@IndexedEmbedded
	public User getAuthor() { return author; }
	public void setAuthor(User author) {  this.author = author; }
	private User author;

	@Field @NumericField
	public int getNumberOfPages() { return numberOfPages; }
	public void setNumberOfPages(int numberOfPages) {  this.numberOfPages = numberOfPages; }
	private int numberOfPages;

	@Field @NumericField
	public int getStarred() { return starred; }
	public void setStarred(int starred) {  this.starred = starred; }
	private int starred;

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "Book" );
		sb.append( "{id=" ).append( id ).append( "\n" );
		sb.append( ", title='" ).append( title ).append( '\'' ).append( "\n" );
		sb.append( ", description='" ).append( description ).append( '\'' ).append( "\n" );
		sb.append( ", publicationDate=" ).append( publicationDate ).append( "\n" );
		sb.append( ", author=" ).append( author ).append( "\n" );
		sb.append( ", numberOfPages=" ).append( numberOfPages ).append( "\n" );
		sb.append( ", starred=" ).append( starred ).append( "\n" );
		sb.append( '}' ).append( "\n" );
		return sb.toString();
	}
}
