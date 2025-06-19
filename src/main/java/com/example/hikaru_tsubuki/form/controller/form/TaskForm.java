package com.example.hikaru_tsubuki.form.controller.form;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskForm implements Serializable {
    private int id;
    private String content;
    private int status;
    private Date limitDate;
}
