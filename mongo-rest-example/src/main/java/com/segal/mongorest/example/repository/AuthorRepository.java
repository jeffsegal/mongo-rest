package com.segal.mongorest.example.repository;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.example.pojo.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuthorRepository extends PagingAndSortingRepository<Author, String> {
}
