package com.sba.bookmarks.models;

import com.sba.bookmarks.entities.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class BookmarkDto {
    private Long id;
    private String title;
    private String url;
    private Instant createdAt;

}
