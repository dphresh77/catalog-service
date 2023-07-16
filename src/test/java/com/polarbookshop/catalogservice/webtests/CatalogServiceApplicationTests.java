package com.polarbookshop.catalogservice.webtests;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Servlet Container listening on random port
public class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    BookService bookService;

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = new Book(1L,"1231231231", "Title", "Author", 9.90, Instant.now(), Instant.now(), 1);

        webTestClient
                .post() //HTTP Request to send
                .uri("/books")//sends request to /books endpoint
                .bodyValue(expectedBook)//adds book in the request body
                .exchange() //Sends the request
                .expectStatus().isCreated() //verifies HTTP response has status 201 CREATED
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull(); //Verifies HTTP response has non-null body
                    assertThat(actualBook.isbn())  //Verifies that the created object is as expected
                            .isEqualTo(expectedBook.isbn());
                });
    }

/*    @Test
    void whenPutRequestThenIsOk(){
        var bookToBeUpdated = new Book("1234567890", "Title","Author", 10.00);
        var updatedBook = new Book("1234567890", "newTitle", "newAuthor", 1.00);

        webTestClient
                .put()
                .uri("/books/1234567890")
                .bodyValue(updatedBook)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                });

    }*/
}
