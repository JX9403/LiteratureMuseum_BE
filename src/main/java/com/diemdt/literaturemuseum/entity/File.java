package com.diemdt.literaturemuseum.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private File.Type targetType; // e.g. WORK, AUTHOR, NEWS
    private Long targetId;
    @Column(columnDefinition = "LONGTEXT")
    private String description;

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