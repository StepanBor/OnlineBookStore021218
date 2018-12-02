package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DAO.TaskDAO;
import com.gmail.stepan1983.model.Publisher;
import com.gmail.stepan1983.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class TaskServiceImpl {

    @Autowired
    TaskDAO taskDAO;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Task addTask(Task task) {
//        return taskDAO.save(task);
        return entityManager.merge(task);
    }

    public Task getById(Long taskId) {
        return taskDAO.getOne(taskId);
    }

    @Transactional
    public void updateTask(Task task) {
        taskDAO.save(task);

    }

    @Transactional
    public void deleteTask(Task task) {
        Task temp = entityManager.merge(task);
        entityManager.remove(temp);
    }

    public List<Task> findAll() {
        return taskDAO.findAll();
    }

    public List<Task> findByStatus(String status) {
        return taskDAO.getByStatus(status);
    }

    public long count() {
        return taskDAO.count();
    }
}
