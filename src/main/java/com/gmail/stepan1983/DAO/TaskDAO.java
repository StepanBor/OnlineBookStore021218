package com.gmail.stepan1983.DAO;

import com.gmail.stepan1983.model.CategoryItem;
import com.gmail.stepan1983.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDAO extends JpaRepository<Task,Long> {

    @Query("SELECT t FROM Task t WHERE t.status=:status")
    List<Task> getByStatus(@Param("status") String status);
}
