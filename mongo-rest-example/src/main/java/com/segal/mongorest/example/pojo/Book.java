package com.segal.mongorest.example.pojo;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.pojo.BaseDocument;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Document
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Book book = (Book) o;

		if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
		if (publicationDate != null ? !publicationDate.equals(book.publicationDate) : book.publicationDate != null)
			return false;
		if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
		if (title != null ? !title.equals(book.title) : book.title != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = title != null ? title.hashCode() : 0;
		result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
		result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
		result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
