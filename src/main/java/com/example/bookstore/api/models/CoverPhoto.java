package com.example.bookstore.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoverPhoto {
    private Integer id;
    private Integer idBook;
    private String url;

    public CoverPhoto() {}

    public CoverPhoto(Integer id, Integer idBook, String url) {
        this.id = id;
        this.idBook = idBook;
        this.url = url;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdBook() { return idBook; }
    public void setIdBook(Integer idBook) { this.idBook = idBook; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
