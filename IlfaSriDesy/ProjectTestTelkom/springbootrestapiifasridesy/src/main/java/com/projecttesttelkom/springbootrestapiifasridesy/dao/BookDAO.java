package com.projecttesttelkom.springbootrestapiifasridesy.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projecttesttelkom.springbootrestapiifasridesy.model.Book;
import com.projecttesttelkom.springbootrestapiifasridesy.repository.BookRepository;

@Service
public class BookDAO {
	@Autowired
	BookRepository bookRepository;
	
	/*simpan*/
	public Book save(Book book){
		return bookRepository.save(book);
	}
	
	/*read all*/
	public List<Book> readAll(){
		return bookRepository.findAll();
	}
	
	/*pencarian by id*/
	public Book findById(Long bookId){
		return bookRepository.findOne(bookId);
	}
	
	/*hapus by object*/
	public void delete(Book book){
		bookRepository.delete(book);
	}
	
	/*getAPI*/
	public List<Book> getDataApi(String query, String filterStatus, Pageable pageable){
		return bookRepository.getDataApi(query, filterStatus, pageable);
	}
}
