package com.gmail.stepan1983.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Publisher1")
public class Publisher {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="publisherId")
    private long id;

    private String publisherName;

    private String publisherAdress;

    private String description;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.PERSIST)
//    @OneToMany
    private List<BookItem> books;

    public Publisher(String publisherName,
                     String publisherAdress, String description, List<BookItem> books) {
        this.publisherName = publisherName;
        this.publisherAdress = publisherAdress;
        this.description = description;
        this.books = books;
    }

    public Publisher() {
        this.books=new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherAdress() {
        return publisherAdress;
    }

    public void setPublisherAdress(String publisherAdress) {
        this.publisherAdress = publisherAdress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BookItem> getBooks() {
        return books;
    }

    public void setBooks(List<BookItem> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publisher)) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(getPublisherName(), publisher.getPublisherName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPublisherName());
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", publisherName='" + publisherName + '\'' +
                ", publisherAdress='" + publisherAdress + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
