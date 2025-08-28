package com.sba.bookmarks.services;

import com.sba.bookmarks.entities.Bookmark;
import com.sba.bookmarks.models.BookmarkDto;
import com.sba.bookmarks.models.BookmarkMapper;
import com.sba.bookmarks.models.BookmarksDto;
import com.sba.bookmarks.models.createBookmarkRequest;
import com.sba.bookmarks.repositories.BookmarkRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
//@RequiredArgsConstructor
public class BookmarksService {

 private final BookmarkRepository bookmarkRepository;
 private final BookmarkMapper bookmarkMapper;

    public BookmarksService(BookmarkRepository bookmarkRepository, BookmarkMapper bookmarkMapper) {
        this.bookmarkRepository = bookmarkRepository;
        this.bookmarkMapper = bookmarkMapper;
    }

    @Transactional(readOnly = true)
    public BookmarksDto getBookmarks(Integer page){

        int pageNo = page < 1 ? 0: page -1 ;
        Pageable pageable = PageRequest.of(pageNo,10, Sort.Direction.DESC, "createdAt");

        Page<BookmarkDto> bookmarkPage = bookmarkRepository.findBookmarks(pageable);

        return new BookmarksDto(bookmarkPage);
   }

   @Transactional(readOnly = true)
    public BookmarksDto searchBookmarks(Integer page, String query) {
        int pageNo = page < 1 ? 0 : page-1 ;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC,"createdAt");

      // Page<BookmarkDto> bookmarkPage= bookmarkRepository.searchBookmarks(query, pageable);
        Page<BookmarkDto> bookmarkPage= bookmarkRepository.findByTitleContainsIgnoreCase(query, pageable);

        return new BookmarksDto(bookmarkPage);
    }

    public BookmarkDto createBookmark(createBookmarkRequest request) {
        Bookmark bookmark = new Bookmark(null,request.getTitle(),
                                        request.getUrl(), Instant.now());
        Bookmark savedBookmark= bookmarkRepository.save(bookmark);

        return bookmarkMapper.toDto(savedBookmark);
    }
}
