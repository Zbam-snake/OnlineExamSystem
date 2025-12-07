<!--
 * @Author: DongHongzuo
 * @Date: 2025-12-08 20:38:49
-->
<!--
 * @Description:
 * @Author:
 * @Date: 2025-12-08 20:38:49
-->
// 展示组件页面
<template>
  <div id="index">
    <header1 class="topbar"></header1>
    <section class="container">
      <div class="left_side">
        <mainLeft></mainLeft>
      </div>
      <div class="main_wrapper">
        <navigator class="nav"></navigator>
        <router-view></router-view>
      </div>
    </section>
  </div>
</template>

<script>
import header from '@/components/common/header'
import mainLeft from '@/components/common/mainLeft'
import navigator from '@/components/common/navigator'
export default {
  components:{
    header1: header,
    mainLeft: mainLeft,
    navigator:navigator
  },
  data() {
    return {
      username: '许如梦'
    }
  },
  methods: {

  }
}
</script>

<style lang="less" scoped>
#index {
  min-height: 100vh;
  background: #f4f6fb;
}

#index .nav {
  box-shadow: 1px 0 5px rgba(0, 0, 0, 0.08);
  margin-bottom: 24px;
  border-radius: 10px;
  overflow: hidden;
}

.container {
  display: flex;
  gap: 18px;
  padding: 18px 18px 28px;
  min-height: calc(100vh - 80px);
}

.left_side {
  display: flex;
  flex-direction: column;
}

.main_wrapper {
  overflow: hidden;
  flex: 1;
  background-color: #fff;
  border-radius: 12px;
  padding: 0 20px 24px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.06);
}
</style>

