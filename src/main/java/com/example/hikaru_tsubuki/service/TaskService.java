package com.example.hikaru_tsubuki.service;


import com.example.hikaru_tsubuki.form.controller.form.TaskForm;
import com.example.hikaru_tsubuki.repository.TaskRepository;
import com.example.hikaru_tsubuki.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    //タスク全件取得処理
    public List<TaskForm> findAllReport() {
        List<Task> results = taskRepository.findAllByOrderByLimitDateAsc();
        List<TaskForm> tasks = setTaskForm(results);
        return tasks;
    }

    //DBから取得したデータをFormに入れ替え。
    private List<TaskForm> setTaskForm(List<Task> results) {
        List<TaskForm> tasks = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            TaskForm task = new TaskForm();
            Task result = results.get(i);
            task.setId(result.getId());
            task.setContent(result.getContent());
            task.setStatus(result.getStatus());
            task.setLimitDate(result.getLimitDate());
            tasks.add(task);
        }
        return tasks;
    }

    }
