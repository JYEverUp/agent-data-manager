
package com.alibaba.cloud.ai.agentdatamanager.vo;

import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 分页响应类
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageResponse<T> extends ApiResponse<T> {

	/**
	 * 总记录数
	 */
	private Long total;

	/**
	 * 当前页码
	 */
	private Integer pageNum;

	/**
	 * 每页大小
	 */
	private Integer pageSize;

	/**
	 * 总页数
	 */
	private Integer totalPages;

	public PageResponse(boolean success, String message) {
		super(success, message);
	}

	@SuppressWarnings("unchecked")
	public PageResponse(boolean success, String message, T data) {
		super(success, message, data);
	}

	@SuppressWarnings("unchecked")
	public PageResponse(boolean success, String message, T data, Long total, Integer pageNum, Integer pageSize,
			Integer totalPages) {
		super(success, message, data);
		this.total = total;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
	}

	public static <T> PageResponse<T> success(T data, Long total, Integer pageNum, Integer pageSize,
			Integer totalPages) {
		return new PageResponse<>(true, "查询成功", data, total, pageNum, pageSize, totalPages);
	}

	public static <T> PageResponse<T> success(String message, T data, Long total, Integer pageNum, Integer pageSize,
			Integer totalPages) {
		return new PageResponse<>(true, message, data, total, pageNum, pageSize, totalPages);
	}

	public static <T> PageResponse<T> pageError(String message) {
		return new PageResponse<>(false, message);
	}

	public static <T> PageResponse<T> pageError(String message, T data) {
		return new PageResponse<>(false, message, data);
	}

}
