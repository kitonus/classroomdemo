package com.jatis.training.bookstore.entity;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<BookEntity, String> {

}
