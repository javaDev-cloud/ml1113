package com.sba.bookmarks.models;

import com.sba.bookmarks.entities.Bookmark;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {
    public BookmarkDto toDto(Bookmark bookmark) {

        return new BookmarkDto(
            bookmark.getId(),
            bookmark.getTitle(),
            bookmark.getUrl(),
            bookmark.getCreatedAt());

    }
}
