package com.diemdt.literaturemuseum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer birthYear ;

    private Integer deathYear;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content ;

    @Enumerated(EnumType.STRING)
    private Author.Type type;

    @ManyToOne
    @JoinColumn(name = "award_id") // FK trong bảng author
    private Award award;



    public enum Type{
        POET, WRITER
    }


}
