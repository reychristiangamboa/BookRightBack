package com.example.reychristian.bookrightback;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Rey Christian on 10/27/2017.
 */

public class Book {

    public static final String TABLE_NAME = "book";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_PUBLISHER = "publisher";
    public static final String COLUMN_DATEPUBLISHED = "datePublished";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_STATUS = "status";

    private int id;
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private Date publishedDate;
    private byte[] image;
    private Date dateBorrowed;
    private Date dateDue;
    private Date dateReturned;
    private String status;


    public Book() {

    }

    public Book(String title, String author, String genre, String publisher, Date publishedDate, byte[] image, Date dateBorrowed, Date dateDue, Date dateReturned) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.image = image;
        this.dateBorrowed = dateBorrowed;
        this.dateDue = dateDue;
        this.dateReturned = dateReturned;
    }

    public Book(int id, String title, String author, String genre, String publisher, Date publishedDate, byte[] image, Date dateBorrowed, Date dateDue, Date dateReturned) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.image = image;
        this.dateBorrowed = dateBorrowed;
        this.dateDue = dateDue;
        this.dateReturned = dateReturned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
