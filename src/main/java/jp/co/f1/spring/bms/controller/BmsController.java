package jp.co.f1.spring.bms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import jp.co.f1.spring.bms.repository.BookRepository;
import jp.co.f1.spring.bms.dao.BookDao;
import jp.co.f1.spring.bms.entity.Book;

@Controller
public class BmsController {
	
	// Repositoryインターフェースを自動インスタンス化
	@Autowired
	private BookRepository bookinfo;
	
	// EntityManager自動インスタンス化
	@PersistenceContext
	private EntityManager entityManager;
	
	// DAO自動インスタンス化
	@Autowired
	private BookDao bookDao;
	
	@PostConstruct
	public void init() {
		bookDao = new BookDao(entityManager);
	}
	
	// [/list]へアクセスがあった場合
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mav) {
		// bookinfoテーブルから全件取得
		Iterable<Book> book_list = bookinfo.findAll();
		
		// Viewに渡す変数をModelに格納
		mav.addObject("book_list", book_list);
		
		mav.setViewName("list");
		
		return mav;
	}
	
	// [/insert]へアクセスがあった場合
	@RequestMapping("/insert")
	public ModelAndView insert(@ModelAttribute Book book, ModelAndView mav) {
		mav.addObject("book", book);
		
		mav.setViewName("insert");
		
		return mav;
	}
	
	// [/insert]へPOST送信された場合
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	// POSTデータをBookインスタンスとして受け取る
	public ModelAndView insertPost(@ModelAttribute @Validated Book book, BindingResult result, ModelAndView mav) {
		// 入力エラーがある場合
		if (result.hasErrors()) {
			// エラーメッセージ
			mav.addObject("message", "入力内容に誤りがあります");
			
			mav.setViewName("insert");
			
			return mav;
		}
		
		// 入力されたデータをDBに保存
		bookinfo.saveAndFlush(book);
		
		// リダイレクト先を設定
		mav = new ModelAndView("redirect:/list");
		
		// ModelとView情報を返す
		return mav;
	}
	
	// [/search]へアクセスがあった場合
	@RequestMapping("/search")
	public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
		// bookinfoテーブルから検索
		Iterable<Book> book_list = bookDao.find(
				request.getParameter("isbn"),
				request.getParameter("title"),
				request.getParameter("price")
		);
		
		mav.addObject("book_list", book_list);
		
		mav.setViewName("list");
		
		return mav;
	}
}
