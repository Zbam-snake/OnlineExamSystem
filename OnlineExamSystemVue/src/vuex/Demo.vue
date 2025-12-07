<!--
 * @Author: DongHongzuo
 * @Date: 2025-12-08 20:38:49
-->
<template>

</template>

<script>
import {mapState,mapMutations} from 'vuex'
export default {
    data() {
        return {

        }
    },
    // computed: {
    //     count() {
    //         return this.$store.state.count
    //     }
    // },
    // computed:mapState({
    //     count: state => state.count
    // }),
    computed: mapState(["count","msg","flag"]),
    methods: mapMutations(["add","reduce"]),
}
</script>
