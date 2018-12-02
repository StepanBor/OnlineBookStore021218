package com.gmail.stepan1983.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Category1")
public class CategoryItem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="categoryId")
    private long id;

    private String categoryName;

    private String description;

//    @OneToMany(mappedBy = "category",cascade = CascadeType.PERSIST)
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<BookItem> books;

    public CategoryItem(String categoryName, String description, List<BookItem> books) {
        this.categoryName = categoryName;
        this.description = description;
        this.books=books;
    }

    public CategoryItem() {
        this.books=new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
        if (!(o instanceof CategoryItem)) return false;
        CategoryItem that = (CategoryItem) o;
        return Objects.equals(getCategoryName(), that.getCategoryName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCategoryName());
    }

    @Override
    public String toString() {
        return "CategoryItem{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
