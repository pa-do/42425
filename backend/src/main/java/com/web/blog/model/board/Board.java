package com.web.blog.model.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;

    @JoinColumn(name = "user_uid")
    private int uid;

    private String title;
    private String content;

    @Column(insertable = false, updatable = true, nullable = true)
    private String nickname;

    @Column(insertable = false, updatable = true)
    private int likes_count;

    @Column(insertable = false, updatable = false)
    private LocalDateTime writeDate;

    private String boardImg;
}
