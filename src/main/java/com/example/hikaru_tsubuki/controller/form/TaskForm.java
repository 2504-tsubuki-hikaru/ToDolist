package com.example.hikaru_tsubuki.controller.form;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class TaskForm implements Serializable {
    private int id;
    @NotBlank(message="タスクを入力してください")
    @Length(max = 140, message="タスクは140文字以内で入力してください")
    private String content;
    private int status;
    @NotBlank(message="期限をを入力してください")
    //@Past(message="無効な日付です")
    private String limitDate;
}
