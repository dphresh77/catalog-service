package com.polarbookshop.catalogservice.webtests;

import com.polarbookshop.catalogservice.controllers.BookController;
import com.polarbookshop.catalogservice.domain.BookService;
import com.polarbookshop.catalogservice.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //Adds mockj of book service to Spring application Context
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn ="73737313940";
        given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class); //Defines expected behavior for BookService MockBean

        mockMvc
                .perform(get("/books" + isbn)) //MockMvc used to perform GET and verify the result
                .andExpect(status().isNotFound()); //Expects the status to have a 404 NOT FOUND
    }

}
