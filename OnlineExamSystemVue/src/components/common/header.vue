<!--
 * @Author: DongHongzuo
 * @Date: 2025-12-08 20:38:49
-->
<!-- 顶部信息栏，包含实时时间、用户操作与主题色切换 -->
<template>
    <header id="topbar" :style="headerStyle">
        <!-- 修改密码对话框 -->
        <el-dialog
            :append-to-body="true"
            style="z-index: 2028px"
            title="修改密码"
            :visible.sync="dialogVisible"
            width="30%"
        >
            <el-form
                status-icon
                ref="ruleForm2"
                label-width="100px"
                class="demo-ruleForm"
            >
                <el-form-item label="旧密码" prop="pass">
                    <el-input
                        type="password"
                        v-model="oldPsw"
                        autocomplete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="pass">
                    <el-input
                        type="password"
                        v-model="newPsw"
                        autocomplete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" prop="checkPass">
                    <el-input
                        type="password"
                        v-model="confirmNewPsw"
                        autocomplete="off"
                    ></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false" style="font-size: 20px;"> 取 消</el-button>
                <el-button type="primary" @click="resetPsw"> 确 定</el-button>
            </span>
        </el-dialog>

        <el-row>
            <!-- 左侧系统标题与实时时间 -->
            <el-col :span="4" class="topbar-left" :style="{ color: themeColors.headerText }">
              <img src="../../assets/img/考试.png" alt="" width="50" height="50" style="margin-top: 15px; margin-right: 10px">
                <span class="system-title">在线考试系统 {{ currentTime }}</span>
            </el-col>
            <!-- 右侧折叠按钮与用户头像、退出入口 -->
            <el-col :span="20" class="topbar-right">
                <i @click="toggle()"></i>
                <div class="user">
                    <span>{{ user.userName }}</span>
                    <img
                        src="@/assets/img/R-C.jpg"
                        class="user-img"
                        ref="img"
                        @click="showSetting()"
                    />
                    <transition name="fade">
                        <div class="out" ref="out" v-show="login_flag">
                            <ul>
                                <li>
                                    <a
                                        @click="dialogVisible = true"
                                        href="javascript:;"
                                        >修改密码</a
                                    >
                                </li>
                                <li class="exit" @click="exit()">
                                    <a href="javascript:;">退出登录</a>
                                </li>
                            </ul>
                        </div>
                    </transition>
                </div>
            </el-col>
        </el-row>
    </header>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
    // 顶部栏状态数据与生命周期钩子
    data() {
        return {
            // 控制退出菜单与对话框的展示
            login_flag: false,
            // 当前登录用户基础信息
            user: {
                // 用户信息
                userName: null,
                userId: null,
            },
            // 修改密码表单的弹窗与输入值
            dialogVisible: false,
            oldPsw: "",
            newPsw: "",
            confirmNewPsw: "",
            // 角色用于区分配色
            role: 0,
            // 页面展示的实时时间文本
            currentTime: "",
            // 计时器句柄，组件销毁时清理
            timer: null,
        };
    },
    created() {
        // 初始化用户信息与角色配色，并开启时钟刷新
        this.getUserInfo();

        this.role = this.$cookies.get("role");
        this.startClock();
    },
    beforeDestroy() {
        // 离开页面时清除定时器，避免内存泄露
        if (this.timer) {
            clearInterval(this.timer);
        }
    },
    computed: {
        ...mapState(["flag", "menu"]),
        themeColors() {
            // 根据角色返回当前主题色板
            return this.themeByRole();
        },
        headerStyle() {
            // 封装头部样式，便于主题切换
            return {
                backgroundColor: this.themeColors.headerBg,
                boxShadow: `5px 0px 12px ${this.themeColors.shadow}`,
                color: this.themeColors.headerText,
            };
        },
    },
    methods: {
        startClock() {
            // 初始化与定时更新当前时间
            this.updateTime();
            this.timer = setInterval(this.updateTime, 1000);
        },
        updateTime() {
            // 生成本地化日期时间字符串
            const now = new Date();
            const pad = (val) => (val < 10 ? `0${val}` : val);
            const dateStr = `${now.getFullYear()}年${pad(now.getMonth() + 1)}月${pad(now.getDate())}日`;
            const timeStr = `${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`;
            this.currentTime = `${dateStr} ${timeStr}`;
        },
        themeByRole() {
            // 不同角色使用不同主题配色
            const palette = {
                0: { headerBg: "#0f766e", headerText: "#e0f2f1", shadow: "rgba(15, 118, 110, 0.45)" },
                1: { headerBg: "#7c3aed", headerText: "#f5f3ff", shadow: "rgba(124, 58, 237, 0.45)" },
            };
            const roleKey = Number(this.role);
            return palette[roleKey] || { headerBg: "#0ea5e9", headerText: "#e0f2fe", shadow: "rgba(14, 165, 233, 0.45)" };
        },
        // 管理员重置密码
        resetPsw() {
            if (this.oldPsw == "") {
                this.$message("请输入旧密码");
                return;
            }
            if (this.newPsw == "") {
                this.$message("请输入新密码");
                return;
            }
            if (this.confirmNewPsw != this.newPsw) {
                this.$message("两次新密码不一致");
                return;
            }
            this.$axios(
                `/api/admin/resetPsw/${this.user.userId}/${this.oldPsw}/${this.newPsw}`
            ).then((res) => {
                let status = res.data.code;
                if (status == 200) {
                    if (res.data.data != true) {
                        this.$message(res.data.data);
                    } else {
                        // 修改成功
                        this.$message("修改成功");
                        this.dialogVisible = false;
                        this.oldPsw = "";
                        this.newPsw = "";
                        this.confirmNewPsw = "";
                    }
                }
            });
        },
        //显示、隐藏退出按钮
        showSetting() {
            this.login_flag = !this.login_flag;
        },
        //左侧栏放大缩小
        ...mapMutations(["toggle"]),
        getUserInfo() {
            //获取用户信息
            let userName = this.$cookies.get("cname");
            let userId = this.$cookies.get("cid");
            this.user.userName = userName;
            this.user.userId = userId;
        },
        index() {
            this.$router.push({ path: "/index" });
        },
        exit() {
            let role = this.$cookies.get("role");
            this.$router.push({ path: "/" }); //跳转到登录页面
            this.$cookies.remove("cname"); //清除cookie
            this.$cookies.remove("cid");
            this.$cookies.remove("role");
            this.$cookies.remove("rb_token"); //清除cookie
            this.$cookies.remove("rb_role");
            if (role == 0) {
                this.menu.pop();
            }
        },
    },
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
}
#topbar {
    position: relative;
    z-index: 10;
    background-color: #2384d9;
    height: 80px;
    line-height: 80px;
    color: #fff;
    box-shadow: 5px 0px 10px rgba(0, 0, 0, 0.5);
}
#topbar .topbar-left {
    height: 80px;
    display: flex;
    justify-content: center;
    overflow: hidden;
}
.system-title {
    font-size: 22px;
    font-weight: 600;
    color: inherit;
}
.topbar-left .icon-kaoshi {
    font-size: 60px;
}
.topbar-left .title {
    font-size: 20px;
    cursor: pointer;
}
.topbar-right {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.topbar-right .user-img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}
.topbar-right .el-icon-menu {
    font-size: 30px;
    margin-left: 20px;
}
.topbar-right .user {
    position: relative;
    margin-right: 40px;
    display: flex;
}
.topbar-right .user .user-img {
    margin-top: 15px;
    margin-left: 10px;
    cursor: pointer;
}
.user .out {
    font-size: 14px;
    position: absolute;
    top: 80px;
    right: 0px;
    background-color: #fff;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    padding: 12px;
}
.user .out ul {
    list-style: none;
}
.user .out ul > li {
    height: 26px;
    line-height: 26px;
}
.out a {
    text-decoration: none;
    color: #000;
}
.out .exit {
    margin-top: 4px;
    padding-top: 4px;
    border-top: 1px solid #ccc;
}
</style>
