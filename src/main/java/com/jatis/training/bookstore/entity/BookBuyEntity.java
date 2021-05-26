package com.jatis.training.bookstore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

@Entity
public class BookBuyEntity {

	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	private BookEntity book;
	
	@Min(0)
	private int quantity;
	
	@Min(0)
	private BigDecimal amount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date trxDate;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public BookEntity getBook() {
		return book;
	}
	public void setBook(BookEntity book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}
	
	@PrePersist
	private void initTrxDate() {
		if (this.trxDate == null) {
			this.trxDate = new Date();
		}
	}

}
