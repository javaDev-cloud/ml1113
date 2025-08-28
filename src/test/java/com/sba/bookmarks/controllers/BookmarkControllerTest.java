package com.sba.bookmarks.controllers;

import com.sba.bookmarks.entities.Bookmark;
import com.sba.bookmarks.repositories.BookmarkRepository;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.CoreMatchers;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties={
        "spring.datasource= jdbc:tc:postgresql:14-alpine:///demo"
})
@AutoConfigureMockMvc
class BookmarkControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    BookmarkRepository repo;

    private List<Bookmark> bookmarks;

    @BeforeEach
    void setup() {
        repo.deleteAll();

        bookmarks = new ArrayList<>();

        bookmarks.add(new Bookmark(null, "SivaLabs", "https://sivalabs.in", Instant.now()));
        bookmarks.add(new Bookmark(null, "SpringBlog", "https://spring.io/blog", Instant.now()));
        bookmarks.add(new Bookmark(null, "Quarkus", "https://quarkus.io/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Micronaut", "https://micronauts.io", Instant.now()));
        bookmarks.add(new Bookmark(null, "JOOQ", "https://jooq.org/", Instant.now()));
        bookmarks.add(new Bookmark(null, "VladMihalcea", "https://vladmihalcea.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "How To Remove Docker Containers, Images, Volumes, and Networks", "https://linuxize.com/post/how-to-remove-docker-images-containers-volumes-and-networks", Instant.now()));
        bookmarks.add(new Bookmark(null, "All You Need To Know About Unit Testing with Spring Boot", "https://reflectoring.io/unit-testing-spring-boot", Instant.now()));
        bookmarks.add(new Bookmark(null, "Spring Boot Security Jwt Authentication", "https://www.devglan.com/spring-security/spring-boot-jwt-auth", Instant.now()));
        bookmarks.add(new Bookmark(null, "A categorized list of all Java and JVM features since JDK 8", "https://advancedweb.hu/2019/02/19/post_java_8", Instant.now()));
        bookmarks.add(new Bookmark(null, "Flyway and jOOQ for Unbeatable SQL Development Productivity", "https://blog.jooq.org/2014/06/25/flyway-and-jooq-for-unbeatable-sql-development-productivity", Instant.now()));
        bookmarks.add(new Bookmark(null, "Java Microservices: A Practical Guide", "https://www.marcobehler.com/guides/java-microservices-a-practical-guide", Instant.now()));
        bookmarks.add(new Bookmark(null, "Deserialize Immutable Objects with Jackson", "https://www.baeldung.com/jackson-deserialize-immutable-objects", Instant.now()));

        repo.saveAll(bookmarks);

    }

    @ParameterizedTest
    @CsvSource({
            "1,13,2,1,true,false,true,false",
            "2,13,2,2,false,true,false,true"
    })
    void shouldGetBookmarks(int pageNo, int totalElements, int totalPages, int currentPage,
                            boolean isFirst, boolean isLast, boolean hasNext, boolean hasPrevious) throws Exception {
        mvc.perform(get("/bookmarks?page=" + pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(isLast)))
                .andExpect(jsonPath("$.hasNext",CoreMatchers.equalTo(hasNext)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)));
    }

    @Test
    void shouldCreateBookmarkSuccessfully() throws Exception {
        this.mvc.perform(
                post("/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title":"sivaLabs Blog",
                                    "url":"https://sivalabs.in"
                                 }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title",is("sivaLabs Blog")))
                .andExpect(jsonPath("$.url",is("https://sivalabs.in")));
    }


// Error handling library need to be add and configured
//    @Test
//    void shouldFailToCreateBookmarkWhenUrlIsNotProvided() throws Exception {
//        this.mvc.perform(post("/bookmarks")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                        {
//                            "title": "Sivalabs Blog"
//                        }
//                        """)
//        )
//                .andExpect(status().isBadRequest())
//                .andExpect(header().string("Content-type", is("application/problem+json")))
//                .andExpect(jsonPath("$.type",is("https://zalando.github.io/problem/constraint-violation")))
//                .andExpect(jsonPath("$.title",is("Constraint Violation")))
//                .andExpect(jsonPath("$.status", is(400)))
//                .andReturn();
//    }
}