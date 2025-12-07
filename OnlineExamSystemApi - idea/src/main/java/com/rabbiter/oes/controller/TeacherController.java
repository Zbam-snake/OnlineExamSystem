package com.rabbiter.oes.controller;

import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.Teacher;
import com.rabbiter.oes.serviceimpl.TeacherServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController {

    private TeacherServiceImpl teacherService;

    @Autowired
    public TeacherController(TeacherServiceImpl teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/teacher")
    public ApiResult add(@RequestBody Teacher teacher) {
        if (teacherService.add(teacher)) {
            return ApiResultHandler.success("教师信息添加成功");
        } else {
            return ApiResultHandler.error("教师信息添加失败");
        }
    }

    @GetMapping("/teachers/{page}/{size}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size) {
        // 分页查询所有教师
        return ApiResultHandler.buildApiResult(200, "查询所有教师", teacherService.findAll(page, size));
    }

    @GetMapping("/teacher/{teacherId}")
    public ApiResult findById(@PathVariable("teacherId") Integer teacherId) {
        return ApiResultHandler.success(teacherService.findById(teacherId));
    }

    @PutMapping("/teacher")
    public ApiResult update(@RequestBody Teacher teacher) {
        return ApiResultHandler.success(teacherService.update(teacher));
    }

    @DeleteMapping("/teacher/{teacherId}")
    public ApiResult deleteById(@PathVariable("teacherId") Integer teacherId) {
        return ApiResultHandler.success(teacherService.deleteById(teacherId));
    }
}
