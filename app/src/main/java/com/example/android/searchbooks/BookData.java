package com.example.android.searchbooks;

/**
 * Created by jiwanpokharel89 on 10/24/2017.
 */

public class BookData {
    private String title;
    private String author;
    private String publishedDate;
    private String printType;

    public BookData() { //Deault Constructor to facilitate instiation
    }

    public BookData(String title, String author, String publishedDate, String printType) {
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.printType = printType;
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

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    @Override
    public String toString() {
        return "BookData{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                ", printType='" + printType + '\'' +
                '}';
    }
}
