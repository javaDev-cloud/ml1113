package com.sba.bookmarks.controllers;

import com.sba.bookmarks.entities.Bookmark;
import com.sba.bookmarks.models.BookmarkDto;
import com.sba.bookmarks.models.BookmarksDto;
import com.sba.bookmarks.services.BookmarksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RestController
public class BookmarkController {
    private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);
     private final BookmarksService bookmarkService;

    public BookmarkController(BookmarksService bookmarkService) {

        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/bookmarks")
    public BookmarksDto getBookmarks(@RequestParam(name="page", defaultValue = "1") Integer page,
                                     @RequestParam(name="query", defaultValue = "") String query) {
        logger.info("Logger page {}", page);
        if(query.trim().length() == 0 || query == null) {
            return bookmarkService.getBookmarks(page);
        }
        return bookmarkService.searchBookmarks(page, query);
    }


}
