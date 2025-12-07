<!--
 * @Author: DongHongzuo
 * @Date: 2025-12-08 20:38:49
-->
<!--左边下拉导航栏-->
<template>
  <div id="left" :style="{ backgroundColor: themeColors.sidebarBg, '--sidebar-hover': themeColors.sidebarHover, '--sidebar-bg': themeColors.sidebarBg }">
    <el-menu
      active-text-color="#e0f2fe"
      text-color="#f8fafc"
      :default-active="this.$route.path"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="flag"
      :background-color="themeColors.sidebarBg"
      menu-trigger="click" router>
      <el-submenu v-for="(item,index) in menu" :index='item.index' :key="index">
        <template slot="title">
          <div class="el-item-menu left-width">
            <i class="iconfont" :class="item.icon" style="font-size: 28px;"></i>
            <span slot="title" class="title"> {{item.title}}</span>
          </div>
        </template>
        <el-menu-item-group v-for="(list,index1) in item.content" :key="index1" >
          <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item1 != null" style="color:white;"><i :class="list.icon" style="font-size: 24px;color: white;"> </i> {{list.item1}}</el-menu-item>
          <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item2 != null" style="color:white;"><i :class="list.icon" style="font-size: 24px;color: white;"> </i> {{list.item2}}</el-menu-item>
          <el-menu-item @click="handleTitle(item.index)" :index="list.path" v-if="list.item3 != null" style="color:white;"><i :class="list.icon" style="font-size: 24px;color: white;"> </i> {{list.item3}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: "mainLeft",
  data() {
    return {
      role: 0,
    }
  },
  computed: {
    ...mapState(["flag","menu"]),
    themeColors() {
      return this.themeByRole();
    }
  },
  created() {
    this.role = Number(this.$cookies.get("role"));
    this.addData()
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    //点击标题传递参数给navigator组件
    handleTitle(index) {
      this.bus.$emit('sendIndex',index)
    },
    themeByRole() {
      const palette = {
        0: { sidebarBg: '#115e59', sidebarHover: '#134e4a' },
        1: { sidebarBg: '#5b21b6', sidebarHover: '#4c1d95' },
      }
      return palette[this.role] || { sidebarBg: '#0369a1', sidebarHover: '#075985' }
    },
    addData() {
      let role = this.$cookies.get("role")
      if(role == 0) {
        this.menu.push({
          index: '5',
          title: '教师管理',
          icon: 'icon-r-user3',
          content:[{item1:'教师管理',path:'/teacherManage',icon:"el-icon-a-041"},{item2: '添加教师',path: '/addTeacher',icon:"iconfont icon-r-add"}],
        })
      }
    }
  },
}
</script>

<style>
.el-menu-vertical-demo .el-submenu__title {
  overflow: hidden;
}
.left-width .iconfont {
  font-size: 18px;
  color: #fff;
}
.left-width {
  width: 213px;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  min-height: calc(100vh - 80px);
}
#left {
  min-height: calc(100vh - 80px);
  background-color: var(--sidebar-bg, #2384d6);
  z-index: 0;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
}
#left .el-menu-vertical-demo {
  background-color: inherit;
}
#left .el-menu-vertical-demo .title {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  margin-left: 14px;
}
#left .el-submenu__title {
  color: #f8fafc !important;
}
.el-submenu {
  border-bottom: 1px solid #eeeeee0f !important;
}
.el-submenu__title:hover {
  background-color: var(--sidebar-hover, #ffffff1c);
  color: #fff;
}
.el-submenu__title i {
    color: #fbfbfc !important;
}
</style>
