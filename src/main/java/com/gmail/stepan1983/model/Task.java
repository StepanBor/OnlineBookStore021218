package com.gmail.stepan1983.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Task1")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String description;

    String status;
    @Temporal(value = TemporalType.DATE)
    Date date;

    public Task(String description, String status) {
        this.description = description;
        this.status = status;
        this.date=new Date();
    }

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", date=" + date +
                '}';
    }
}
