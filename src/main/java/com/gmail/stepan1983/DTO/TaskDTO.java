package com.gmail.stepan1983.DTO;

import com.gmail.stepan1983.model.Task;

import javax.persistence.*;
import java.util.Date;

public class TaskDTO {

    private long id;

    private String description;

    private String status;

    private Date date;

    public TaskDTO(long id, String description, String status, Date date) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public TaskDTO() {
    }

    public Task toTask(){
        Task task=new Task(description,status);
        task.setId(id);
        task.setDate(date);
        return task;
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
}
