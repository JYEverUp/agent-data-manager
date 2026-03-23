package com.alibaba.cloud.ai.agentdatamanager.controller;

import com.alibaba.cloud.ai.agentdatamanager.dto.ModelConfigDTO;
import com.alibaba.cloud.ai.agentdatamanager.service.ModelConfigOpsService;
import com.alibaba.cloud.ai.agentdatamanager.service.ModelConfigService;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import com.alibaba.cloud.ai.agentdatamanager.vo.ModelCheckVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/model-config")
public class ModelConfigController {

    private final ModelConfigService modelConfigService;

    private final ModelConfigOpsService modelConfigOpsService;

    @GetMapping("/list")
    public ApiResponse<List<ModelConfigDTO>> list() {
        return ApiResponse.success("获取模型配置列表成功", modelConfigService.list());
    }

    @PostMapping("/add")
    public ApiResponse<String> add(@Valid @RequestBody ModelConfigDTO config) {
        modelConfigService.add(config);
        return ApiResponse.success("配置已保存");
    }

    @PutMapping("/update")
    public ApiResponse<String> update(@Valid @RequestBody ModelConfigDTO config) {
        modelConfigOpsService.updateAndRefresh(config);
        return ApiResponse.success("配置已更新");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Integer id) {
        modelConfigService.delete(id);
        return ApiResponse.success("配置已删除");
    }

    @PostMapping("/activate/{id}")
    public ApiResponse<String> activate(@PathVariable Integer id) {
        modelConfigOpsService.activateConfig(id);
        return ApiResponse.success("模型切换成功！");
    }

    @PostMapping("/test")
    public ApiResponse<String> test(@Valid @RequestBody ModelConfigDTO config) {
        modelConfigOpsService.testConnection(config);
        return ApiResponse.success("连接测试成功！模型可用。");
    }

    @GetMapping("/check-ready")
    public ApiResponse<ModelCheckVo> checkReady() {
        return ApiResponse.success("模型配置检查完成", modelConfigService.checkReady());
    }

}
