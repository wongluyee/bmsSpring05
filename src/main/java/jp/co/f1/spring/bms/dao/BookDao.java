package jp.co.f1.spring.bms.dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import jp.co.f1.spring.bms.entity.Book;

@Repository
public class BookDao {
	// Entity manager
	private EntityManager entityManager;
	
	// Instance to create query
	private CriteriaBuilder builder;
	
	// Instance to execute query
	private CriteriaQuery<Book> query;
	
	// Root of searched entity
	private Root<Book> root;
	
	// Constructor (prepare to connect DB)
	public BookDao(EntityManager entityManager) {
		// Get EntityManager
		this.entityManager = entityManager;
		
		builder = entityManager.getCriteriaBuilder();
		
		query = builder.createQuery(Book.class);
		
		root = query.from(Book.class);
	}
	
	public ArrayList<Book> find(String isbn, String title, String price) {
		// setup SELECT
		query.select(root);
		
		// setup WHERE
		query.where(
				builder.like(root.<String>get("isbn"), "%" + isbn + "%"),
				builder.like(root.<String>get("title"), "%" + title + "%"),
				builder.like(root.<String>get("price"), "%" + price + "%")
		);
		
		return (ArrayList<Book>)entityManager.createQuery(query).getResultList();
		
	}
}
