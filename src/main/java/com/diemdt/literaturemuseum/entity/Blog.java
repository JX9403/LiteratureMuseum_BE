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
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @Enumerated(EnumType.STRING)
    private Blog.Status status;

    private Integer view = 0;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public enum Status{
        PENDING,    // Chờ duyệt
        APPROVED,   // Đã duyệt
        REJECTED
    }
}
