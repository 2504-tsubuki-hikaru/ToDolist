package com.example.hikaru_tsubuki.controller;

import com.example.hikaru_tsubuki.controller.form.TaskForm;
import com.example.hikaru_tsubuki.repository.entity.Task;
import com.example.hikaru_tsubuki.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class FormController {
    @Autowired
    TaskService taskService;

    @GetMapping("/")
    public ModelAndView top(@RequestParam(name="start",required = false)String start,
                            @RequestParam(name="end",required = false)String end,
                            @RequestParam(name="status",required = false) Integer status,
                            @RequestParam(name="content",required = false) String content) throws ParseException {

        ModelAndView mav = new ModelAndView();
        List<TaskForm> tasksData = taskService.findAllTask(start, end, status, content);
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = localDate.format(dateTimeFormatter);

        mav.addObject("now", now);
        mav.addObject("tasksData", tasksData);
        //getStatusOptionsを呼び出して戻り値を(map)バリュ―としている。
        mav.setViewName("top");

        return mav;

    }

    /*
     * タスク削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:/");
    }

    /*
     * ステータス変更処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateStatus(@PathVariable Integer id,
                                      @ModelAttribute("status") Integer status) {
        taskService.updateStatus(id, status);
        return new ModelAndView("redirect:/");
    }

    /*
     * タスク追加画面遷移処理
     */
    @GetMapping("/new")
    public ModelAndView nweTask() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        TaskForm taskForm = new TaskForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        mav.addObject("formModel", taskForm);
        return mav;
    }

    /*
     * タスク追加処理
     */
    @PostMapping("/add")
    public ModelAndView addTask(@Validated @ModelAttribute("formModel") TaskForm taskForm, BindingResult result) throws ParseException {
        if(result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            //エラーの時は新規投稿画面でエラーを出したいから遷移先を指定
            mav.setViewName("/new");
            //引数をそのまま返す。
            mav.addObject("formModel", taskForm);
            return mav;
    }
        //DBにタスク追加情報を登録
        taskService.saveTask(taskForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}