package com.polarbookshop.catalogservice.webtests;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.time.Instant;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Servlet Container listening on random port
@ActiveProfiles("integration")
public class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = Book.of("1231231231", "Title", "Author", 9.90, "Polarsophia");

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

   @Test
    void whenPutRequestThenBookUpdated(){
        var bookIsbn = "1231231232";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90, "Polarsophia");
        Book createdBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat( book).isNotNull())
                .returnResult().getResponseBody();

        var bookToUpdate = new Book(createdBook.id(), createdBook.isbn(), createdBook.title(), createdBook.author(),
                7.95,createdBook.publisher() , createdBook.createdDate(), createdBook.lastModifiedDate(), createdBook.version());

        webTestClient
                .put()
                .uri("/books/" + bookIsbn)
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.price()).isEqualTo(bookToUpdate.price());
                });
    }
}
