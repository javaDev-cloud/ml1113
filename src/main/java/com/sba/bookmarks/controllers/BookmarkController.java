package com.sba.bookmarks.controllers;

import com.sba.bookmarks.entities.Bookmark;
import com.sba.bookmarks.models.BookmarkDto;
import com.sba.bookmarks.models.BookmarksDto;
import com.sba.bookmarks.models.createBookmarkRequest;
import com.sba.bookmarks.services.BookmarksService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
//@RestControllerAdvice
public class BookmarkController {
    private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);
     private final BookmarksService bookmarkService;

//    public BookmarkController(BookmarksService bookmarkService) {
//
//        this.bookmarkService = bookmarkService;
//    }

    @GetMapping
    public BookmarksDto getBookmarks(@RequestParam(name="page", defaultValue = "1") Integer page,
                                     @RequestParam(name="query", defaultValue = "") String query) {
        logger.info("Logger page {}", page);
        if(query.trim().length() == 0 || query == null) {
            return bookmarkService.getBookmarks(page);
        }
        return bookmarkService.searchBookmarks(page, query);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookmarkDto createBookmark(@RequestBody @Valid createBookmarkRequest request) {

        return bookmarkService.createBookmark(request);

    }


}
