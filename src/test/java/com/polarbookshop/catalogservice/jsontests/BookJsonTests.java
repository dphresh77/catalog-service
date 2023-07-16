package com.polarbookshop.catalogservice.jsontests;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = new Book(1L, "1234567890","Jaws", "Mike", 9.90, Instant.now(), Instant.now(), 1);
        var jsonContent = json.write(book); //Verifying the parsing from Java to JSON using JsonPath format to naviaget JsonObject

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());

    }

    @Test
    void testDeserialize() throws Exception {
        //Defines a JSON Object using the Java text block feature
        var content = """  
            {
            "id": 2
            "isbn": "1234567890",
            "title": "Title",
            "author": "Author",
            "price": 9.90
            }
                """;
        assertThat(json.parse(content))//verifies the parsing from JSON to Java
                .usingRecursiveComparison()
                .isEqualTo(new Book(2L, "1234567891", "Title", "Author",9.90, Instant.now(), Instant.now(), 2));
    }

}
