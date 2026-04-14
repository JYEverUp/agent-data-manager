
package com.alibaba.cloud.ai.agentdatamanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 2025/9/16
 */
@RestController
@RequestMapping("/echo")
public class EchoController {

	/**
	 * 心跳检测
	 */
	@GetMapping("ok")
	public String ok() {
		return "ok";
	}

}
