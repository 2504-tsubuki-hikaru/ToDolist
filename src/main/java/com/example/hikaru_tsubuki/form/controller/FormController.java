package com.example.hikaru_tsubuki.form.controller;

import com.example.hikaru_tsubuki.form.controller.form.TaskForm;
import com.example.hikaru_tsubuki.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FormController {
    @Autowired
    TaskService taskService;

    @GetMapping("/")
    public ModelAndView top() {

        ModelAndView mav = new ModelAndView();
        List<TaskForm> workData = taskService.findAllReport();

        mav.setViewName("/top");

        mav.addObject("contents", workData);

        return mav;
    }
}











