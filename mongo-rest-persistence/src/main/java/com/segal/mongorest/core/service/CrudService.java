package com.segal.mongorest.core.service;

import com.segal.mongorest.core.pojo.BaseDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/15/14
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CrudService<T extends BaseDocument> {

	public T create(T pojo);

	public T update(T pojo);

	public Collection<T> create(Collection<T> pojos);

	public Collection<T> update(Collection<T> pojos);

	public void delete(String id);

	public void deleteAll();

	public T findOne(String id);

	public Iterable<T> findAll();

	public void setCrudRepository(CrudRepository<T, String> crudRepository);
	
}
