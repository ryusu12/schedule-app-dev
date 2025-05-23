package com.example.scheduleappdev.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @Setter
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Schedule schedule;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public void updateComment(String content) {
        this.content = content;
    }

}