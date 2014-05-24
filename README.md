mongo-rest
==========

A simple library meant to reduce boilerplate code required to set up a RESTful interface in front of a MongoDB database.

See mongo-rest-example for usage examples of how to create POJOs, Repositories, Controllers and classes necessary for testing basic functionality.

###POJOs
Here's how we might represent an author (constructors, getters and setters omitted):

```java
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
	
	...
```

We did the following for this object:

1. Define a @DocumentType, which is used in conjuction wtih ApplicationRegistry to define a central registry of the various services, repositories, etc. for each object type.
2. Extend BaseDocument, which is used throughout the library and contains a few fields and methods common to all Document objects.
3. Define JSR 303 Bean Validation annotations.

###Repositories
Extend PagingAndSortingRepository exactly as prescribed in spring-data-mongodb documentation:

```java
public interface AuthorRepository extends PagingAndSortingRepository<Author, String> {}
```

###Controllers
Extend DocumentController and provide standard Spring MVC annotations @RestController (or @Controller) and @RequestMapping, then autowire the strongly typed CrudService.

```java
@RestController
@RequestMapping("/author")
public class AuthorController extends DocumentController<Author> {

	@Override
	@Autowired
	@Qualifier("authorService")
	public void setService(CrudService<Author> service) {
		super.setService(service);
	}

}
```

##REST Interface

Deploying these classes into your Spring MVC web app will yield the following services:

| URI | HTTP Method | Description |
| ------------- | ------------- | ------------- |
| /author/test | GET | Returns literal string "TEST" for basic smoke testing of the service  |
| /author/ | GET | Returns all Authors in the repository |
| /author/{id} | GET | Returns the Author with ID {id} |
| /author/ | POST | Creates new Author if POST content is a valid Author represented in JSON |
| /author/{id} | PUT | Updates an existing Author if PUT content is a valid Author represented in JSON |
| /author/{id} | DELETE | Deletes an existing Author with ID {id} |

==========

#Tests

###DocumentProviders
First, provide at least one implementation of DocumentProvider<? extends BaseDocument>. In our example, we provide one for testing the persistence layer and another for the web Controller. Here is a a look at the former:
```java
@Component
@DocumentType("author")
public class AuthorDocumentProvider implements DocumentProvider<Author> {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Collection<DocumentTestResult<Author>> createDocuments() {
		Author validAuthor = new Author("first", "middle", "last", new Date(
				new Date().getTime() - 1000L * 60 * 60 * 24 * 365 * 50), null);
		DocumentTestResult<Author> validResult = new ValidDocumentTestResult<>(validAuthor, DocumentTestResult.Operation.create);

		Author invalidAuthor = new Author(null, "middle", null, null, new Date());
		InvalidDocumentTestResult<Author> invalidResult = new InvalidDocumentTestResult<>(invalidAuthor,
				DocumentTestResult.Operation.create, ConstraintViolationException.class);

		Author clonedAuthor = null;
		try {
			clonedAuthor = (Author) validAuthor.clone();
			clonedAuthor.setId("1234");
		} catch (CloneNotSupportedException e) {
			log.warn("Error while cloning author.", e);
		}
		ValidDocumentTestResult<Author> validFind = new ValidDocumentTestResult<>(clonedAuthor,
				DocumentTestResult.Operation.find);

		InvalidDocumentTestResult<Author> invalidFind = new InvalidDocumentTestResult<>(new Author(),
				DocumentTestResult.Operation.find, IllegalArgumentException.class);

		return Arrays.asList(validResult, invalidResult, validFind, invalidFind);
	}

}
```

###Spring Configuration
Next, create a Spring configuration to load the required classes for your tests. In our example, we used annotation-based configuration with separate classes for persistence and web tests. Note that we use Mock objects for the repositories but "real" objects for the CrudServices since we wish to verify proper Bean validation which is implemented there.

```java
@Configuration
@Import(ExampleMockMongoConfig.class)
@EnableWebMvc
public class ExampleMockRestConfig {}
```

```java
@Configuration
@ComponentScan(
		basePackages = {"com.segal"},
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@Import(MockRepositoryConfig.class)
@PropertySource("classpath:mongorest.properties")
public class ExampleMockMongoConfig extends MongoConfig {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	BookRepository bookRepository;

	@Bean
	@Qualifier("authorService")
	@DocumentType("author")
	public DefaultMongoCrudService<Author> authorService() {
		return getMongoService(authorRepository);
	}

	@Bean
	@Qualifier("bookService")
	@DocumentType("book")
	public DefaultMongoCrudService<Book> bookService() {
		return getMongoService(bookRepository);
	}

	private DefaultMongoCrudService getMongoService(CrudRepository crudService) {
		return new DefaultMongoCrudService(crudService, createNiceMock(TimeProvider.class));
	}

	@Bean
	@Override
	public MongoOptions mongoOptions() {
		return getDefaultMongoOptions();
	}

}

```

###Test Class Implementations
Finally, extend the base test classes for persistence and web (DocumentValidationTest<? extends BaseDocument> and DocumentControllerTest<? extends BaseDocument> respectively).  Simply autowire dependencies for each. Note that we don't define a baseUrl in AuthorControllerTest; instead we rely on the convention that the Controller will be hosted at /author.
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExampleMockMongoConfig.class)
@DirtiesContext
public class AuthorValidationTest extends DocumentValidationTest<Author> {

	@Override
	@Autowired
	@Qualifier("authorRepository")
	public void setRepository(CrudRepository repository) {
		super.setRepository(repository);
	}

	@Override
	@Autowired
	@Qualifier("authorService")
	public void setService(CrudService<Author> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("authorDocumentProvider")
	public void setDocumentProvider(DocumentProvider<Author> documentProvider) {
		super.setDocumentProvider(documentProvider);
	}

}
```

```java
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ExampleMockRestConfig.class)
public class AuthorControllerTest extends DocumentControllerTest<Author> {

	@Override
	@Autowired
	@Qualifier("authorControllerDocumentProvider")
	public void setDocumentProvider(DocumentProvider<Author> documentProvider) {
		super.setDocumentProvider(documentProvider);
	}

	@Override
	@Autowired
	@Qualifier("authorRepository")
	public void setCrudRepository(CrudRepository<Author, String> crudService) {
		super.setCrudRepository(crudService);
	}

}

```
