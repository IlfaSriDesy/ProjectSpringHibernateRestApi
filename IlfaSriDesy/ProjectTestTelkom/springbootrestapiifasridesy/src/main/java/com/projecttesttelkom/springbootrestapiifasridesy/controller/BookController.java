package com.projecttesttelkom.springbootrestapiifasridesy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projecttesttelkom.springbootrestapiifasridesy.dao.BookDAO;
import com.projecttesttelkom.springbootrestapiifasridesy.model.Book;

@RestController
public class BookController {
	@Autowired
	BookDAO bookDAO;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="books", method = RequestMethod.GET)
	public @ResponseBody Map getBook(@RequestParam Map<String,String> requestParams){
	    String query = requestParams.get("query");
	    String sort = requestParams.get("sort");
	    String filterStatus = requestParams.get("filter[status]");
	    String pageNumber = requestParams.get("page[number]");
	    String pageSize = requestParams.get("page[size]");
		
	    Sort sortObject = null;
	    if(sort.isEmpty()){
	    	sort="id";
	    	sortObject = new Sort(new Sort.Order(Direction.ASC, sort));
	    }else if(sort.equals("title") || sort.equals("price") || sort.equals("status")|| sort.equals("id") ||
	    		sort.equals("-title") || sort.equals("-price") || sort.equals("-status")|| sort.equals("-id")){
	    	if(sort.substring(0, 1).equals("-")){
		    	sort=sort.substring(1);
		    	sortObject = new Sort(new Sort.Order(Direction.DESC, sort));
		    }else{
		    	sortObject = new Sort(new Sort.Order(Direction.ASC, sort));
		    }
	    }else{
	    	sort="id";
	    	sortObject = new Sort(new Sort.Order(Direction.ASC, sort));
	    }
	    
	    if(pageNumber.isEmpty()){ pageNumber="0"; }
	    if(pageSize.isEmpty()){ pageSize="5"; }
	    
		Pageable pageable = new PageRequest(Integer.parseInt(pageNumber)-1, Integer.parseInt(pageSize), sortObject);
		List<Book> data = bookDAO.getDataApi(query, filterStatus, pageable);
		List<Book> totalData = bookDAO.readAll();
		
		Map meta = new HashMap();
		meta.put("page", pageNumber);
		meta.put("size", pageSize);
		meta.put("count", data.size());
		int bagi = totalData.size()/Integer.parseInt(pageSize);
		int modulus = totalData.size()%Integer.parseInt(pageSize);
		int totalPages=bagi;
		if(modulus>0){
			totalPages+=1;
		}
		meta.put("totalPages", totalPages);
		meta.put("totalData", totalData.size());
		
		Map result=new HashMap();
		try {
			result.put("status", ResponseEntity.ok().build());
		} catch (Exception e) {
			result.put("status", ResponseEntity.notFound().build());
		}
		result.put("data", data);
		result.put("meta", meta);
	    return result;
	}
	
	
}