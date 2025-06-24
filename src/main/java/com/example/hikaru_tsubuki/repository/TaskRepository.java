package com.example.hikaru_tsubuki.repository;


import com.example.hikaru_tsubuki.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    public List<Task> findByLimitDateBetweenOrderByLimitDateAsc(Date startDate, Date endDate);

    public List<Task> findByLimitDateBetweenAndContentAndStatusOrderByLimitDateAsc(Date startDate, Date endDate, String content, Integer status);

    public List<Task> findByLimitDateBetweenAndStatusOrderByLimitDateAsc(Date startDate, Date endDate, Integer status);

    public List<Task> findByLimitDateBetweenAndContentOrderByLimitDateAsc(Date startDate, Date endDate, String content);
}
