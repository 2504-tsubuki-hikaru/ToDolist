package com.example.hikaru_tsubuki.controller.form;

import java.io.Serializable;
import java.util.Date;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class TaskForm implements Serializable {

    private int id;

    //^(?!= 否定形(～で始まらない). ^[\\s　]+$)([\\s\S]*)$=空白または改行
    @Pattern(regexp = "^(?!^[\\s　]*$)([\\s\\S]*)$", message = "タスクを入力してください")
    @Length(max = 140, message="タスクは140文字以内で入力してください")
    private String content;

    private int status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")//文字列をDate型に変換して、フォーマットを指定できる。
    @NotNull(message = "期限を設定してください")//date型にNotBlankは使えない。
    @FutureOrPresent(message="無効な日付です")//「未来または現在の日付」をチェック。昨日より前はエラー表示。
    private Date limitDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

}
