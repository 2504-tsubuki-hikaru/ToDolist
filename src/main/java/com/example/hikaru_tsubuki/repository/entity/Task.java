package com.example.hikaru_tsubuki.repository.entity;

import jakarta.persistence.*;
        import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String content;

    @Column
    private int status;

    @Column
    private Date limitDate;

    @Column
    //@LastModifiedDate //更新時に自動で現在日時を登録してくれる。
    private Date updatedDate;

    @Column
    //@CreatedDate //初回登録時に自動で現在日時を登録してくれる。
    private Date createdDate;

}
