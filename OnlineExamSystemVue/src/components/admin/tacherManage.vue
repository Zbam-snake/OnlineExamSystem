// 教师管理页面
<template>
  <div class="teacher-management">
    <!-- 表格区域 -->
    <div class="table-section">
      <el-card class="card">
        <div slot="header" class="card-header">
          <span class="header-title">教师管理</span>
        </div>

        <!-- 教师信息表格 -->
        <el-table :data="pagination.records" border stripe class="teacher-table">
          <el-table-column prop="teacherId" label="ID" width="120"></el-table-column>
          <el-table-column prop="teacherName" label="姓名" width="180"></el-table-column>
          <el-table-column prop="institute" label="学院" width="200"></el-table-column>
          <el-table-column prop="sex" label="性别" width="120"></el-table-column>
          <el-table-column prop="tel" label="联系方式" width="160"></el-table-column>
          <el-table-column prop="email" label="邮箱" width="240"></el-table-column>
          <el-table-column prop="cardId" label="身份证号" width="240"></el-table-column>
          <el-table-column prop="type" label="职称" width="120"></el-table-column>

          <!-- 操作列，放置编辑和删除按钮 -->
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <div class="button-group">
                <el-button
                  @click="checkGrade(scope.row.teacherId)"
                  type="primary"
                  size="small"
                  icon="el-icon-edit"
                  class="action-btn">
                  编辑
                </el-button>
                <el-button
                  @click="deleteById(scope.row.teacherId)"
                  type="danger"
                  size="small"
                  icon="el-icon-delete"
                  class="action-btn">
                  删除
                </el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页控件 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.current"
          :page-sizes="[6, 10]"
          :page-size="pagination.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          class="pagination">
        </el-pagination>
      </el-card>
    </div>

    <!-- 编辑对话框 -->
    <el-dialog
      title="编辑教师信息"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose">
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="姓名" :rules="[{ required: true, message: '请输入教师姓名', trigger: 'blur' }]">
          <el-input v-model="form.teacherName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="学院" :rules="[{ required: true, message: '请输入学院', trigger: 'blur' }]">
          <el-input v-model="form.institute" placeholder="请输入学院"></el-input>
        </el-form-item>
        <el-form-item label="性别" :rules="[{ required: true, message: '请选择性别', trigger: 'blur' }]">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话号码" :rules="[{ required: true, message: '请输入电话号码', trigger: 'blur' }]">
          <el-input v-model="form.tel" placeholder="请输入电话号码"></el-input>
        </el-form-item>
        <el-form-item label="密码" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <el-input v-model="form.pwd" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" :rules="[{ required: true, message: '请输入身份证号码', trigger: 'blur' }]">
          <el-input v-model="form.cardId" placeholder="请输入身份证号"></el-input>
        </el-form-item>
        <el-form-item label="职称" :rules="[{ required: true, message: '请输入职称', trigger: 'blur' }]">
          <el-input v-model="form.type" placeholder="请输入职称"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        current: 1,
        total: 0,
        size: 6,
      },
      dialogVisible: false,
      form: {},
    };
  },
  created() {
    this.getTeacherInfo();
  },
  methods: {
    // 获取教师信息
    getTeacherInfo() {
      this.$axios(`/api/teachers/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data;
      }).catch(error => {});
    },
    // 改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getTeacherInfo();
    },
    // 改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getTeacherInfo();
    },
    // 编辑教师信息
    checkGrade(teacherId) {
      this.dialogVisible = true;
      this.$axios(`/api/teacher/${teacherId}`).then(res => {
        this.form = res.data.data;
      });
    },
    // 删除教师信息
    deleteById(teacherId) {
      this.$confirm("确定删除当前教师吗？删除后无法恢复", "Warning", {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        this.$axios({ url: `/api/teacher/${teacherId}`, method: 'delete' }).then(() => {
          this.getTeacherInfo();
        });
      });
    },
    // 提交修改
    submit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.dialogVisible = false;
          this.$axios({
            url: '/api/teacher',
            method: 'put',
            data: this.form,
          }).then(res => {
            if (res.data.code === 200) {
              this.$message({ message: '更新成功', type: 'success' });
              this.getTeacherInfo();
            }
          });
        } else {
          this.$message({ message: '请填写完整信息', type: 'error' });
        }
      });
    },
    handleClose(done) {
      this.$confirm('确认关闭？').then(() => done()).catch(() => {});
    },
  }
};
</script>

<style scoped lang="less">
.teacher-management {
  padding: 30px;
}

/* 卡片样式 */
.card {
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.card-header {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 20px;
}

.header-title {
  color: #409EFF;
}

/* 表格样式 */
.teacher-table {
  margin-bottom: 20px;
}

/* 分页样式 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 按钮样式 */
.el-button {
  border-radius: 4px;
  font-size: 14px;
  padding: 8px 16px;
  transition: all 0.3s ease;
}

.el-button:hover {
  transform: scale(1.05);
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

/* 操作按钮 */
.action-btn {
  margin-right: 10px; /* 增加按钮间隔 */
}

/* 编辑按钮样式 */
.el-button--primary {
  background-color: #409EFF;
  border-color: #409EFF;
}

.el-button--primary:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

/* 删除按钮样式 */
.el-button--danger {
  background-color: #f56c6c;
  border-color: #f56c6c;
}

.el-button--danger:hover {
  background-color: #f79c9c;
  border-color: #f79c9c;
}

/* 表单项间距 */
.el-form-item {
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
