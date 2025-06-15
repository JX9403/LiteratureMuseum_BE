package com.diemdt.literaturemuseum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exhibit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Integer view;

    public enum Type {
        THUONGXUYEN,
        CHUYENDE
    }
}
