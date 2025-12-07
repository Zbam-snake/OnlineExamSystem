<!--
 * @Author: DongHongzuo
 * @Date: 2025-12-08 20:38:49
-->
<template>
  <!-- 页面根容器，负责渲染当前路由对应的视图 -->
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    // 初始化时验证 Cookie 中的登录信息，缺失时重定向到首页
    var token = this.$cookies.get("rb_token");
    var role = this.$cookies.get("rb_role");
    if(token == null || token == "" || role == null || role == null) {
      this.$router.push({path: '/' }) // 跳转到首页
    }
  }
}
</script>

<style>
/* 隐藏冗余图标的通用样式 */
.icon-r-yes {
  display: none;
}

/* 全局无序列表样式重置 */
ul {
  list-style: none;
}
a {
  text-decoration: none;
}
* {
  margin: 0;
  padding: 0;
}
#app {
  font-family: "Microsoft YaHei", "Helvetica", "Tahoma", "Geneva", "Arial", sans-serif;
  background-color: #eee;
}
</style>
