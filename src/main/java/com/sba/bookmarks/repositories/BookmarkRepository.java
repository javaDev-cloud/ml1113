package com.sba.bookmarks.repositories;

import com.sba.bookmarks.entities.Bookmark;
import com.sba.bookmarks.models.BookmarkDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {

    @Query("select new com.sba.bookmarks.models.BookmarkDto(b.id, b.title, b.url, b.createdAt) from Bookmark b")
   Page<BookmarkDto> findBookmarks(Pageable pageable);

    @Query("""
            select new com.sba.bookmarks.models.BookmarkDto(b.id, b.title, b.url, b.createdAt) from Bookmark b
            where lower(b.title) like lower(concat('%',:query,'%'))
            """)
    Page<BookmarkDto> searchBookmarks(String query, Pageable pageable);

    Page<BookmarkDto> findByTitleContainsIgnoreCase(String query, Pageable pageable);


}
