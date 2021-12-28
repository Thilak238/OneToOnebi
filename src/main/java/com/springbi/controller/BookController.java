package com.springbi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbi.entity.Book;
import com.springbi.repository.BookRepository;
import com.springbi.repository.StoryRepository;
import com.springbi.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private StoryRepository storyRepository;
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody Book book){
		Optional<Book> optionalBook = bookRepository.findById(book.getBookId()); 
		if(optionalBook.isPresent()) {
			return ResponseEntity.unprocessableEntity().body("Book Already Exist");
		}
		else {
			Book insertingBook = new Book();
			insertingBook.setBookId(book.getBookId());
			insertingBook.setBookName(book.getBookName());
			insertingBook.setStory(book.getStory());
			bookRepository.save(insertingBook);
			return ResponseEntity.ok().body("Book Inserted Successfully");
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Book book){
		Optional<Book> optionalBook = bookRepository.findById(id);
		if(!optionalBook.isPresent()) {
			return ResponseEntity.unprocessableEntity().body("Book Id Does Not Exist");
		}
		else {
			Book existingBook = optionalBook.get();
			existingBook.setBookId(book.getBookId());
			existingBook.setBookName(book.getBookName());
			
			storyRepository.deleteById(book.getStory().getStoryId());
			existingBook.setStory(book.getStory());
			
			bookRepository.save(existingBook);
			return ResponseEntity.ok().body("Book Updated Successfully");
		}
	}
	
	@GetMapping
	public ResponseEntity<?> get(@RequestParam Optional<Long> id){
		if(id.isPresent()) {
			Optional<Book> optionalBook = bookRepository.findById(id.get());
			if(optionalBook.isPresent()) {
				return ResponseEntity.ok().body(bookRepository.findById(id.get()));
			}
			else {
				return ResponseEntity.unprocessableEntity().body("Given Id does not exist");
			}
		}
		else {
			return ResponseEntity.ok().body(bookRepository.findAll());
		}
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Book> optionalBook = bookRepository.findById(id);
		if(optionalBook.isPresent()) {
			bookRepository.deleteById(id);
			bookRepository.save(optionalBook.get());
			return ResponseEntity.ok().body("Book Record Deleted Successfully");
		}
		else {
			return ResponseEntity.unprocessableEntity().body("Id does not exist");
		}
	}
 }
