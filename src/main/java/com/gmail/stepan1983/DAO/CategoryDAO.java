package com.gmail.stepan1983.DAO;


import com.gmail.stepan1983.model.CategoryItem;
import com.gmail.stepan1983.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends JpaRepository<CategoryItem,Long> {

    @Query("SELECT c FROM CategoryItem c WHERE c.categoryName=:name")
    CategoryItem getByName(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CategoryItem c WHERE c.categoryName = :name")
    boolean existsByName(@Param("name") String name);


}
