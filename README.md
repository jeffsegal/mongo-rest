mongo-rest
==========

A simple library meant to reduce boilerplate code required to set up a RESTful interface in front of a MongoDB database.

See mongo-rest-example for usage examples of how to create POJOs, Repositories, Controllers and classes necessary for testing basic functionality.

POJOs
==========

Here's how we might represent an author (constructors, getters and setters omitted):

```java
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
	
	...
```

We did the following for this object:

1. Define a @DocumentType, which is used in conjuction wtih ApplicationRegistry to define a central registry of the various services, repositories, etc. for each object type.
2. Extend BaseDocument, which is used throughout the library and contains a few fields and methods common to all Document objects.
3. Define JSR 303 Bean Validation annotations.

Repositories
==========

We extend PagingAndSortingRepository exactly as prescribed in spring-data-mongodb documentation:

```java
public interface AuthorRepository extends PagingAndSortingRepository<Author, String> {}
```



