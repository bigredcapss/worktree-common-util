package com.example.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前页码 */
    private Integer pageNum;
    /** 每页条数 */
    private Integer pageSize;
    /** 总记录数 */
    private Long total;
    /** 总页数 */
    private Integer pages;
    /** 数据列表 */
    private List<T> list;

    public static <T> PageResult<T> of(List<T> list, long total, int pageNum, int pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setList(list);
        result.setTotal(total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setPages(pageSize == 0 ? 0 : (int) Math.ceil((double) total / pageSize));
        return result;
    }

    public static <T> Result<PageResult<T>> successPage(List<T> list, long total, int pageNum, int pageSize) {
        return Result.success(of(list, total, pageNum, pageSize));
    }
}
