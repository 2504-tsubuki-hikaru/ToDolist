package com.example.hikaru_tsubuki.controller;

import com.example.hikaru_tsubuki.controller.form.TaskForm;
import com.example.hikaru_tsubuki.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FormController {
    @Autowired
    TaskService taskService;

    @GetMapping("/")
    public ModelAndView top() {

        ModelAndView mav = new ModelAndView();
        List<TaskForm> tasksData = taskService.findAllTask();
        LocalDate now = LocalDate.now();

        mav.addObject("now", now);
        mav.addObject("tasksData", tasksData);
        //getStatusOptionsを呼び出して戻り値を(map)バリュ―としている。
        mav.addObject("statusOptions", getStatusOptions());
        mav.setViewName("top");

        return mav;
    }

    private Map<Integer, String> getStatusOptions() {
        //キーとバリュ―。キーをDBにバリュ―が画面表示される。
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "未着手");
        map.put(2, "実行中");
        map.put(3, "ステイ中");
        map.put(4, "完了");
        return map;
    }

}