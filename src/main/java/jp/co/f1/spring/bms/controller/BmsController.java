package jp.co.f1.spring.bms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.f1.spring.bms.repository.BookRepository;
import jp.co.f1.spring.bms.entity.Book;

@Controller
public class BmsController {
	
	// Repositoryインターフェースを自動インスタンス化
	@Autowired
	private BookRepository bookinfo;
	
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
	public ModelAndView insert(ModelAndView mav) {
		mav.setViewName("insert");
		
		return mav;
	}
	
	// [/insert]へPOST送信された場合
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	// POSTデータをBookインスタンスとして受け取る
	public ModelAndView insertPost(@ModelAttribute Book book, ModelAndView mav) {
		// 入力されたデータをDBに保存
		bookinfo.saveAndFlush(book);
		
		// リダイレクト先を設定
		mav = new ModelAndView("redirect:/list");
		
		// ModelとView情報を返す
		return mav;
	}
}
