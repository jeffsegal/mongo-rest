package com.segal.mongorest.example.repository;

import com.segal.mongorest.example.pojo.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BookRepository extends PagingAndSortingRepository<Book, String> {
}
