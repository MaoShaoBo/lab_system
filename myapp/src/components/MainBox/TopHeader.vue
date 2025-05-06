<template>
    <el-header>
        <div>基于Springboot的高校实验室管理系统</div>
        <div>
            <span style="line-height:40px;">欢迎{{ user.username }}使用</span>
            <el-dropdown>
                <el-avatar :size="40" :src="circleUrl" />
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>{{ user.role.roleName }}</el-dropdown-item>
                        <el-dropdown-item v-if="user.username === 'admin'" @click="handleUpdate">更新学校平面图</el-dropdown-item>
                        <el-dropdown-item @click="handleChangePassword">修改密码</el-dropdown-item>
                        <el-dropdown-item @click="handleExit">退出</el-dropdown-item>

                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </el-header>

    <el-dialog v-model="dialogVisible" title="更新学校平面图" width="50%">
        <el-upload class="upload-demo" drag action="/adminapi/upload" :data="data" :on-success="handleSuccess">
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
                将文件拖到此处或 <em>点击上传</em>
            </div>
            <template #tip>
                <div class="el-upload__tip">
                    jpg/png files with a size less than 5MB
                </div>
            </template>
        </el-upload>

    </el-dialog>

    <el-dialog v-model="passwordDialogVisible" title="修改密码" width="30%">
    <el-form ref="passwordFormRef" :model="passwordForm" :rules="passwordRules" label-width="80px" class="ruleForm" status-icon>
        <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="passwordForm.newPassword" type="password" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="passwordForm.confirmPassword" type="password" />
        </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
            <el-button @click="passwordDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handlePasswordConfirm">
                确定
            </el-button>
        </span>
    </template>
</el-dialog>
</template>

<script setup>
import { useUserStore } from '../../store/useUserStore'
import { useRouterStore } from '../../store/useRouterStore'
import { useRouter } from 'vue-router'
import { ref, reactive } from 'vue'
import { UploadFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const { changeUser, user } = useUserStore()
const { changeRouter } = useRouterStore()
const router = useRouter()

//头像public
// const circleUrl = "/avatar.png"

//assets webpack require("../../assets/avatar.png")
// const circleUrl = "/avatar.png"

//assets vite
const circleUrl = new URL('../../assets/avatar.png', import.meta.url).href



const handleExit = () => {

    changeUser({})

    changeRouter(false)
    //跳转到登录
    router.push("/login")
}

//更新平面图
const dialogVisible = ref(false)
const handleUpdate = () => {
    dialogVisible.value = true
}

const data = {
    "token":localStorage.getItem("token")
}

const handleSuccess = ()=>{
    dialogVisible.value = false;
    window.location.reload();
}

// 修改密码相关
const passwordDialogVisible = ref(false)
const passwordFormRef = ref()
const passwordForm = reactive({
    newPassword: "",
    confirmPassword: ""
})

const validatePass = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else {
        if (passwordForm.confirmPassword !== '') {
            passwordFormRef.value.validateField('confirmPassword')
        }
        callback()
    }
}

const validatePass2 = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次输入密码'))
    } else if (value !== passwordForm.newPassword) {
        callback(new Error('两次输入密码不一致!'))
    } else {
        callback()
    }
}

const passwordRules = reactive({
    newPassword: [
        { required: true, validator: validatePass, trigger: 'blur' }
    ],
    confirmPassword: [
        { required: true, validator: validatePass2, trigger: 'blur' }
    ]
})

const handleChangePassword = () => {
    passwordDialogVisible.value = true
}

const handlePasswordConfirm = () => {
    passwordFormRef.value.validate(async (valid) => {
        if (valid) {
            try {
                await axios.put(`/adminapi/users/${user.id}`, {
                    password: passwordForm.newPassword
                })
                ElMessage({
                    message: '密码修改成功，请重新登录',
                    type: 'success',
                })
                passwordDialogVisible.value = false
                handleExit()
            } catch (error) {
                ElMessage.error('密码修改失败')
            }
        }
    })
}
</script>

<style lang="scss" scoped>
.el-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #00ace5;
    color: white;
}
</style>