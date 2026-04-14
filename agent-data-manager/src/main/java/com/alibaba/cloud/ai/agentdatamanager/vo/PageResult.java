
package com.alibaba.cloud.ai.agentdatamanager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

	/**
	 * 数据列表
	 */
	private List<T> data;

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

	/**
	 * 计算总页数
	 */
	public void calculateTotalPages() {
		if (this.total != null && this.pageSize != null && this.pageSize > 0) {
			this.totalPages = (int) Math.ceil((double) this.total / this.pageSize);
		}
		else {
			this.totalPages = 0;
		}
	}

}
