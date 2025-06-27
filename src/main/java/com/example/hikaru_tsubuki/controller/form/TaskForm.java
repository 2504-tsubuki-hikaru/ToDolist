package com.example.hikaru_tsubuki.controller.form;

import java.io.Serializable;
import java.time.LocalDate;
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

    //@Pattern(regexp = "[\\u3000]+$", message = "タスクは空白文字のみではいけません。")
    //@NotBlank(message="タスクを入力してください")

    //^(?!= 否定形(～で始まらない）(|　)= 全角スペースを通す。
    //"[^(?!|　)]"= 全角スペースで始まらない物を通す。（全角スペースはエラー表示）
    @Pattern(regexp = "^(?!^[\\s　]+$)([\\s\\S]*)$", message = "空白または改行のみの入力は許可されていません。")
    @Length(max = 141, message="タスクは140文字以内で入力してください")
    private String content;

    private int status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")//文字列をDate型に変換して、フォーマットを指定できる。
    @NotNull(message = "期限を設定してください")//date型にNotBlankは使えない。
    @FutureOrPresent(message="無効な日付です")//「未来または現在の日付」を許容。
    private Date limitDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
}
