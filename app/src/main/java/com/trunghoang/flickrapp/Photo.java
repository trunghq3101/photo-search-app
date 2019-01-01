package com.trunghoang.flickrapp;

import java.io.Serializable;

class Photo implements Serializable {
    private String title;
    private String author;
    private String authorId;
    private String tags;
    private String link;
    private String image;

    Photo(String title, String author, String authorId, String tags, String link, String image) {
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.tags = tags;
        this.link = link;
        this.image = image;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getAuthor() {
        return author;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    String getAuthorId() {
        return authorId;
    }

    void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    String getTags() {
        return tags;
    }

    void setTags(String tags) {
        this.tags = tags;
    }

    String getLink() {
        return link;
    }

    void setLink(String link) {
        this.link = link;
    }

    String getImage() {
        return image;
    }

    void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", authorId='" + authorId + '\'' +
                ", tags='" + tags + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
