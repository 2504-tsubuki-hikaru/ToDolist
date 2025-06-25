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

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    //タスク全件取得処理
    public List<TaskForm> findAllTask(String start, String end, Integer status, String content) throws ParseException {

        String strStartDate;
        String strEndDate;

        if (!isBlank(start)) {
            strStartDate = start + " 00:00:00";
        } else {
            strStartDate = "2020-01-01 00:00:00";
        }

        if (!isBlank(end)) {
            strEndDate = end + " 23:59:59";
        } else {
            strEndDate = "2100-12-31 23:59:59";
        }

        //Date型に型変換をして引数でわたす。(string型でDB実行をするとエラーになる為）
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdFormat.parse(strStartDate);
        Date endDate = sdFormat.parse(strEndDate);

        //絞込みの引数の条件によってSQLを変えるので条件分のSQLを用意。
        if (StringUtils.isBlank(content) && status == null) {
            List<Task> results = taskRepository.findByLimitDateBetweenOrderByLimitDateAsc(startDate, endDate);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else if (!StringUtils.isBlank(content) && status != null) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndContentAndStatusOrderByLimitDateAsc(startDate, endDate, content, status);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else if (StringUtils.isBlank(content) && status != null) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndStatusOrderByLimitDateAsc(startDate, endDate, status);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else if (!StringUtils.isBlank(content) && status == null) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndContentOrderByLimitDateAsc(startDate, endDate, content);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        }
        return List.of();
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

    //ステータス変更処理
    public void updateStatus(Integer id, Integer status) {
        //setReportEntityメソッドでFormからEntityに詰め替えている。
        taskRepository.updateStatus(id,status);
    }

    /*
     * タスク追加情報登録処理
     */
    public void saveTask(TaskForm reqTask) throws ParseException {
        Task saveTask = setTaskEntity(reqTask);
        taskRepository.save(saveTask);
    }

    /*
     * 引数の情報をEntityに設定
     */
    private Task setTaskEntity(TaskForm reqTask) throws ParseException {
        Task task = new Task();
        task.setContent(reqTask.getContent());
        task.setStatus(reqTask.getStatus());
        //String型からDate型に型変換。
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date FormatDate = sdFormat.parse(reqTask.getLimitDate());
        task.setLimitDate(FormatDate);
        task.setUpdatedDate(new Date());
        task.setCreatedDate(new Date());

        //task.setLimitDate(reqTask.getLimitDate());
        return task;
    }
}
