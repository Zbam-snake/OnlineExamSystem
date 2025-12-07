<!--
 * @Author: DongHongzuo
 * @Date: 2025-12-08 20:38:49
-->
<!--
 * @Description:
 * @Author:
 * @Date: 2024-03-08 20:38:49
-->
<template>
    <div class="index">
        <div  style="font-size: 35px;">
            <span style="color: #000000">{{ greetingText }}</span>
        </div>
        <div style="margin-top: 20px; font-size: 24px;" >
            <img
                :src="require('@/assets/bg11.jpg')"
                style="margin-top: 10px; width: 60vw"
            />
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            user: {
                //用户信息
                userName: null,
                userId: null,
            },
            role: null,
        };
    },
    created() {
        this.getUserInfo();
    },
    methods: {
        getUserInfo() {
            //获取用户信息
            let userName = this.$cookies.get("cname");
            let userId = this.$cookies.get("cid");
            this.role = Number(this.$cookies.get("role"));
            this.user.userName = userName;
            this.user.userId = userId;
        },
    },
    computed: {
        greetingText() {
            if (this.role === 0) {
                return `教导主任 ${this.user.userName}，欢迎回到管理中心。`;
            }
            if (this.role === 1) {
                return `欢迎教师 ${this.user.userName}，祝您考试管理顺利！`;
            }
            return `您好 : 很高兴遇见你, ${this.user.userName}, 欢迎您登录在线考试系统`;
        },
    },
};
</script>


<style lang="less" scoped>




.index {
    margin-left: 70px;
    .hello {
        font-size: 28px;
        color: #000000;
    }
    .msg {
        .title {
            font-size: 16px;
            color: #000;
            margin-top: 20px;
            margin-left: 10px;
        }
        ul {
            display: flex;
            flex-direction: column;
            width: 200px;
            overflow: hidden;
        }
        li {
            margin-top: 10px;
            font-size: 14px;
            color: lightcoral;
            cursor: pointer;
            display: inline-block;
        }
    }
}
</style>

