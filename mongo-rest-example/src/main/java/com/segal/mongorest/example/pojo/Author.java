package com.segal.mongorest.example.pojo;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.pojo.BaseDocument;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Document
@DocumentType("author")
public class Author extends BaseDocument {

	private static final long serialVersionUID = -6355585932646821288L;

	@NotBlank
	String firstName;

	String middleName;

	@NotBlank
	String lastName;

	@NotNull
	Date birthDate;

	Date deathDate;

	public Author() {
	}

	public Author(String firstName, String middleName, String lastName, Date birthDate, Date deathDate) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.deathDate = deathDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Author author = (Author) o;

		if (birthDate != null ? !birthDate.equals(author.birthDate) : author.birthDate != null) return false;
		if (deathDate != null ? !deathDate.equals(author.deathDate) : author.deathDate != null) return false;
		if (firstName != null ? !firstName.equals(author.firstName) : author.firstName != null) return false;
		if (lastName != null ? !lastName.equals(author.lastName) : author.lastName != null) return false;
		if (middleName != null ? !middleName.equals(author.middleName) : author.middleName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = firstName != null ? firstName.hashCode() : 0;
		result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
		result = 31 * result + (deathDate != null ? deathDate.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("firstName", firstName)
				.append("middleName", middleName)
				.append("lastName", lastName)
				.append("birthDate", birthDate)
				.append("deathDate", deathDate)
				.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
