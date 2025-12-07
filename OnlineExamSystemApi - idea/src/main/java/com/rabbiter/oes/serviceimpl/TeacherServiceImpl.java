package com.rabbiter.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.Teacher;
import com.rabbiter.oes.mapper.TeacherMapper;
import com.rabbiter.oes.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public IPage<Teacher> findAll(Page<Teacher> page) {
        // 使用分页查询教师信息
        return teacherMapper.findAll(page);
    }

    @Override
    public List<Teacher> findAll() {
        // 查询所有教师信息，限制返回数量
        Page<Teacher> teacherPage = new Page<>(1, 9999);
        return teacherMapper.findAll(teacherPage).getRecords();
    }

    @Override
    public Teacher findById(Integer teacherId) {
        // 根据教师ID查询教师信息
        return teacherMapper.findById(teacherId);
    }

    @Override
    public int deleteById(Integer teacherId) {
        // 删除指定ID的教师信息
        return teacherMapper.deleteById(teacherId);
    }

    @Override
    public int update(Teacher teacher) {
        // 更新教师信息
        return teacherMapper.update(teacher);
    }

    @Override
    public int add(Teacher teacher) {
        // 添加新的教师信息，默认角色为"1"
        teacher.setRole("1");
        return teacherMapper.add(teacher);
    }
}
