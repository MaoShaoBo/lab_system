<template>
  <div class="login-page">
    <Particles id="tsparticles" :particlesInit="particlesInit" :particlesLoaded="particlesLoaded" :options="config" />

    <div class="formContainer">
        <h3>基于Springboot的高校实验室管理系统</h3>
        <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="120px" class="ruleForm" status-icon>
            <el-form-item label="用户名" prop="username" style="--el-form-label-font-size: 16px; --el-text-color-regular: #f0e7e5;">
                <el-input v-model="ruleForm.username" style="--el-input-text-color: black"/>
            </el-form-item>
            <el-form-item label="密码" prop="password" style="--el-form-label-font-size: 16px; --el-text-color-regular: #f0e7e5;">
                <el-input v-model="ruleForm.password" type="password" style="--el-input-text-color: black"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm(ruleFormRef)">
                    登录
                </el-button>

            </el-form-item>
        </el-form>
    </div>
    </div>
</template>

<script setup>
import { useUserStore } from '../store/useUserStore'
import { useRouter } from 'vue-router'
import { ref, reactive } from 'vue'
import { loadFull } from "tsparticles";
// import {config} from '../util/config'
import axios from 'axios'
import { ElMessage } from 'element-plus'

//粒子配置
// const particlesInit = async engine => {
//     await loadFull(engine);
// };

// const particlesLoaded = async container => {
// };




//ref获取表单对象
const ruleFormRef = ref()
const ruleForm = reactive({
    username: "",
    password: ""
})

const rules = reactive({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
    ],
})

const { changeUser } = useUserStore()

const router = useRouter()

//登录回调
const submitForm = async (formEl) => {
    if (!formEl) return
    await formEl.validate(async (valid, fields) => {
        if (valid) {
            const res = await axios.post(`/adminapi/users/login`,ruleForm)
            let {code,data} = res.data
            if(code===0){
                changeUser(data)
                router.push("/")
            }else{
                ElMessage.error('用户名密码不匹配')

            }
        } else {
            console.log('error submit!', fields)
        }
    })

}

</script>


<style lang="scss">
//  style lang="scss" scoped
.formContainer {
    width: 500px;
    height: 300px;
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    color: white;
    text-shadow: 2px 2px 5px black;
    text-align: center;
    z-index: 100;

    // background: rgba(0, 0, 0, 0.5);
    padding: 20px;
    border-radius: 10px;
    // backdrop-filter: blur(5px);
    .ruleForm {
        margin-top: 50px;
    }

    h3 {
        font-size: 40px;

        white-space: nowrap; /* 禁止换行 */
        width: 100%;        /* 确保宽度适应 */
    }

    :deep(.el-form-item__label) {
        color: white;
        font-size: 16px;
        font-weight: 700;
    }

}

.login-page{
  margin: 0;
  background: url('/login.jpg') no-repeat center center fixed;
  background-size: cover;
  height: 100vh;
  overflow: hidden;
}
</style>