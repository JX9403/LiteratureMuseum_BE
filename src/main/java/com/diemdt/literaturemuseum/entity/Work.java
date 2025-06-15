package com.diemdt.literaturemuseum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}