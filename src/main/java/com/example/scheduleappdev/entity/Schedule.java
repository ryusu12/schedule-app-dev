package com.example.scheduleappdev.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String todoTitle;

    @Column(columnDefinition = "longtext")
    private String todoContents;

    public Schedule() {}

    public Schedule(String authorName, String todoTitle, String todoContents) {
        this.authorName = authorName;
        this.todoTitle = todoTitle;
        this.todoContents = todoContents;
    }

}