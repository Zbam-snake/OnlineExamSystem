<!--
 * @Description:
 * @Author:
 * @Date: 2025-12-08 20:38:49
-->
<!-- 添加教师 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px" class="form-container">
      <el-form-item label="教师姓名">
        <el-input v-model="form.teacherName" placeholder="请输入教师姓名"></el-input>
      </el-form-item>

      <el-form-item label="学院">
        <el-input v-model="form.institute" placeholder="请输入学院"></el-input>
      </el-form-item>

      <el-form-item label="性别">
        <el-input v-model="form.sex" placeholder="请输入性别"></el-input>
      </el-form-item>

      <el-form-item label="电话号码">
        <el-input v-model="form.tel" placeholder="请输入电话号码" maxlength="11"></el-input>
      </el-form-item>

      <el-form-item label="密码">
        <el-input v-model="form.pwd" type="password" placeholder="请输入密码"></el-input>
      </el-form-item>

      <el-form-item label="身份证号">
        <el-input v-model="form.cardId" placeholder="请输入身份证号码" maxlength="18"></el-input>
      </el-form-item>

      <el-form-item label="职称">
        <el-input v-model="form.type" placeholder="请输入职称"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit" >立即创建</el-button>
        <el-button type="danger" @click="cancel" >取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  data() {
    return {
      form: {
        teacherName: "",
        institute: "",
        sex: "",
        tel: "",
        pwd: "",
        cardId: "",
        type: ""
      }
    };
  },
  methods: {
    onSubmit() {
      if (!this.form.teacherName) {
        this.$message.error('请输入教师姓名');
        return;
      }
      if (!this.form.institute) {
        this.$message.error('请输入学院');
        return;
      }
      if (!this.form.sex) {
        this.$message.error('请输入性别');
        return;
      }
      if (!this.form.tel || this.form.tel.length !== 11) {
        this.$message.error('请输入正确的电话号码');
        return;
      }
      if (!this.form.pwd) {
        this.$message.error('请输入密码');
        return;
      }
      if (!this.form.cardId || this.form.cardId.length !== 18) {
        this.$message.error('请输入正确的身份证号码');
        return;
      }
      if (!this.form.type) {
        this.$message.error('请输入职称');
        return;
      }

      this.$axios.post('/api/teacher', this.form)
        .then(res => {
          if (res.data.code === 200) {
            this.$message.success('数据添加成功');
            this.$router.push({ path: '/teacherManage' });
          }
        });
    },
    cancel() {
      this.form = {
        teacherName: "",
        institute: "",
        sex: "",
        tel: "",
        pwd: "",
        cardId: "",
        type: ""
      };
    }
  }
};
</script>

<style scoped>
.add {
  padding: 20px;
  max-width: 500px;
  margin: 0 auto;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
.form-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
}
.el-form-item {
  margin-bottom: 15px;
}
.el-button {
  margin-right: 10px;
}
</style>
