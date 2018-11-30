package com.example.recycle.final_assessment;


public class Book {
    public String bookTitle;
    public String bookAuthor;
    public String bookCondition;
    public String bookBorrowed;



    public Book() {
    }

    public Book(String bookAuthor, String bookBorrowed, String bookCondition, String bookTitle) {
        this.bookAuthor = bookAuthor;
        this.bookBorrowed = bookBorrowed;
        this.bookCondition = bookCondition;
        this.bookTitle = bookTitle;
    }


    private void writenewBook(String bookAuthor, String bookBorrowed, String bookCondition, String bookTitle) {
        Book Book = new Book(bookAuthor, bookBorrowed, bookCondition, bookTitle);
        return;
    }
}
