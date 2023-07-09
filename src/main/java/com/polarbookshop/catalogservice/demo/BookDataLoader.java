package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")
public class BookDataLoader {

    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class) //test data generation is triggered when an Application Ready event is sent
    public void loadBookTestData(){             //That is when the application startup phase is completed
        var book1 =  Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90);
        var book2 =  Book.of("1234567892", "Polar Journey","Iorek Polarsaon", 12.90);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

}