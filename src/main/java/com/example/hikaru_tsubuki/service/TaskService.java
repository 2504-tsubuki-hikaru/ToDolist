package com.example.hikaru_tsubuki.service;


import com.example.hikaru_tsubuki.controller.form.TaskForm;
import com.example.hikaru_tsubuki.repository.TaskRepository;
import com.example.hikaru_tsubuki.repository.entity.Task;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    //タスク全件取得処理
    public List<TaskForm> findAllTask(String start, String end, int status, String content) throws ParseException {

        String strStartDate;
        String strEndDate;

        if (!StringUtils.isBlank(start)) {
            strStartDate = start + " 00:00:00";
        } else {
            strStartDate = "2020-01-01 00:00:00";
        }

        if (!StringUtils.isBlank(end)) {
            strEndDate = end + " 23:59:59";
        } else {
            strEndDate = "2100-12-31 23:59:59";
        }

        //Date型に型変換をして引数でわたす。(string型でDB実行をするとエラーになる為）
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdFormat.parse(strStartDate);
        Date endDate = sdFormat.parse(strEndDate);

        List<Task> results = taskRepository.findAllByOrderByLimitDateAsc(startDate, endDate, status, content);
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //DBからとってきたlimit_dateを指定したフォーマットにしたい。
            //task.setLimitDate=date型が引数に必要。sdf.format(result.getLimitDate()=date型を文字列にするメソッド
            //型が一致していない。
            task.setLimitDate(sdf.format(result.getLimitDate()));

            tasks.add(task);
        }
        return tasks;
    }

    //タスク削除処理
    public void  deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
