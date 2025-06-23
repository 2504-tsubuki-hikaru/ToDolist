package com.example.hikaru_tsubuki.controller;

import com.example.hikaru_tsubuki.controller.form.TaskForm;
import com.example.hikaru_tsubuki.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class FormController {
    @Autowired
    TaskService taskService;

    @GetMapping("/")
    public ModelAndView top() {

        ModelAndView mav = new ModelAndView();
        List<TaskForm> tasksData = taskService.findAllTask();
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = localDate.format(dateTimeFormatter);

        mav.addObject("now", now);
        mav.addObject("tasksData", tasksData);
        //getStatusOptionsを呼び出して戻り値を(map)バリュ―としている。
        mav.setViewName("top");

        return mav;

    }

    //タスク削除処理
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:/");
    }
}