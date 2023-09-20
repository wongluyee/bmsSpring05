package jp.co.f1.spring.bms.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "bookinfo")
public class Book {

	// ISBN
	@Id
	@Column(length = 20)
	@NotEmpty(message = "ISBNを入力してください")
	private String isbn;

	// Title
	@Column(length = 100, nullable = true)
	@NotEmpty(message = "タイトルを入力してください")
	private String title;

	// Price
	@Column(length = 11, nullable = true)
	@NotEmpty(message = "価格を入力してください")
	private String price;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
