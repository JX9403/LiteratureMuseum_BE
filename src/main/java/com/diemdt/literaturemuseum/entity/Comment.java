package com.diemdt.literaturemuseum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type targetType;

    private Long targetId;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum Type{
        WORK,
        ARTIFACT,
        BLOG,
        EXHIBIT,
        NEWS,
        STORY,
        AUTHOR,
        USER
    }

}
