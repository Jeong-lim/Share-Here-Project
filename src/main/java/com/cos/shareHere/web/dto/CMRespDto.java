package com.cos.shareHere.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
    private Integer code; // 1. 성공, -1 실패
    private String message;
    private T data;
}
