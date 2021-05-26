package com.jatis.training.bookstore.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface BookBuyRepository extends PagingAndSortingRepository<BookBuyEntity, UUID> {

	@Query("from BookBuyEntity where trxDate >= :d and trxDate < :d1 order by trxDate")
	List<BookBuyEntity> findByTrxDate(@Param("d") Date today, @Param("d1") Date nextDay);
}
