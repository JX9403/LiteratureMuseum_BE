package com.diemdt.literaturemuseum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Artifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content ;

    private String model ;

    private String usdz;

    private String url;

    private Integer view ;

}
