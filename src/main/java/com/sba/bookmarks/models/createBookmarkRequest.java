package com.sba.bookmarks.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
@Getter
@Setter
public class createBookmarkRequest {

    @NotEmpty(message="Title should not be null.")
    private String title;

    @NotEmpty(message="Url should not be null.")
    private String url;


}
