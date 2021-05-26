package com.jatis.training.bookstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatis.training.bookstore.entity.BookEntity;
import com.jatis.training.bookstore.entity.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookRepository repo;
	
	@PostMapping
	@Transactional
	@Secured("ROLE_ADMIN")
	public BookEntity save(@RequestBody @Valid BookEntity book){
		return repo.save(book);
	}
	
	@GetMapping("/{id}")
	public BookEntity get(@PathVariable String id) {
		return repo.findById(id).orElse(null);
	}
	
	@GetMapping("/all")
	public Iterable<BookEntity> getAll() {
		return repo.findAll(Sort.by(Direction.ASC, "title"));
	}
}
