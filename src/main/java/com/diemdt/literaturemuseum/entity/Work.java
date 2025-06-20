package com.diemdt.literaturemuseum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Integer view = 0;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Author author;
}