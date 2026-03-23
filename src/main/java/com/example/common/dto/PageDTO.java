package com.example.common.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 通用分页请求参数
 */
@Data
public class PageDTO {

    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = 200, message = "每页条数最大为200")
    private Integer pageSize = 20;

    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
