package com.diemdt.literaturemuseum.dto;

import com.diemdt.literaturemuseum.entity.File;
import lombok.Data;

@Data
public class AssignFileRequest {
    private File.Type type;
    private Long targetId;
}
