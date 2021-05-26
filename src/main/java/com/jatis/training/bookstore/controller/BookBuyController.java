package com.jatis.training.bookstore.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatis.training.bookstore.entity.BookBuyEntity;
import com.jatis.training.bookstore.entity.BookBuyRepository;

@RestController
@RequestMapping("/buy")
public class BookBuyController {

	@Autowired
	private BookBuyRepository repo;
	
	@PostMapping
	@Transactional
	@Secured("ROLE_CASHIER")
	public BookBuyEntity buy(@RequestBody @Valid BookBuyEntity buy) {
		return repo.save(buy);
	}
	
	@GetMapping("/{yyMMdd}")
	public List<BookBuyEntity> list(@PathVariable String yyMMdd) throws ParseException{
		Date today = DateUtils.truncate(new SimpleDateFormat("yyMMdd").parse(yyMMdd), Calendar.DATE);
		return repo.findByTrxDate(
				today,
				DateUtils.addDays(today, 1));
	}

	@GetMapping("/today")
	public List<BookBuyEntity> list() throws ParseException{
		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		return repo.findByTrxDate(
				today,
				DateUtils.addDays(today, 1));
	}
}
