package com.segal.mongorest.example.pojo;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.pojo.BaseDocument;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
@DocumentType("book")
public class Book extends BaseDocument {

	private static final long serialVersionUID = 5843901029942180009L;

	@NotBlank
	String title;

	@NotBlank
	String isbn;

	String publisher;

	Date publicationDate;

	public Book() {
	}

	public Book(String title, String isbn, String publisher, Date publicationDate) {
		this.title = title;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
}
