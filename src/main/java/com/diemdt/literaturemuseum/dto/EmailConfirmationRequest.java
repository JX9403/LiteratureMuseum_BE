package com.diemdt.literaturemuseum.dto;

import lombok.Data;

@Data
public class EmailConfirmationRequest {
    private String email;
    private String confirmationCode;
}
