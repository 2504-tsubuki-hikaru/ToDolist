package com.example.hikaru_tsubuki.repository;


import com.example.hikaru_tsubuki.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    public List<Task> findAllByOrderByLimitDateAsc();
}
