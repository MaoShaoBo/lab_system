<template>
    <div class="not-found-container">
        <div class="not-found-content">
            <h1>404</h1>
            <p>抱歉，你访问的页面不存在。</p>
            <el-button type="primary" @click="goHome">返回主页</el-button>
        </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goHome = () => {
    router.push('/');
};
</script>

<style scoped lang="scss">
.not-found-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f4f4f4;
}

.not-found-content {
    text-align: center;
    background-color: white;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);

    h1 {
        font-size: 100px;
        color: #333;
        margin-bottom: 20px;
    }

    p {
        font-size: 18px;
        color: #666;
        margin-bottom: 30px;
    }
}
</style>