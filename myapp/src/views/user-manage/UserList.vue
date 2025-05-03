<template>
    <div>
        <el-button type="primary" @click="handleAdd" style="margin-bottom: 20px;">添加用户</el-button>
        
        <!-- 搜索表单 -->
        <el-form :inline="true" :model="searchForm" class="search-form">
            <el-form-item label="用户名">
                <el-input v-model="searchForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="角色">
                <el-select v-model="searchForm.roleId" placeholder="请选择角色" clearable style="width: 130px"
                    filterable>
                    <el-option v-for="item in roleList" :key="item.id" :label="item.roleName" :value="item.id" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleSearch">搜索</el-button>
                <el-button @click="resetSearch">重置</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="tableData" style="width: 100%">
            <!-- <el-table-column prop="roleName" label="角色名称" width="180" /> -->
            <!-- <el-table-column prop="icon" label="图标" width="180" /> -->
            <el-table-column prop="username" label="用户名"/>
            <el-table-column label="角色名称">
                <template #default="scope">
                    <div>{{ scope.row.role.roleName }}</div>
                </template>
            </el-table-column>
            <el-table-column prop="stuCollege" label="学院"/>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button round type="warning" @click="handleUpdate(scope.row)"
                        :disabled="scope.row.is_default ? true : false">更新</el-button>
                    <el-popconfirm title="你确定要删除吗?" @confirm="handleDelete(scope.row)" confirm-button-text="确定"
                        cancel-button-text="取消">
                        <template #reference>
                            <el-button type="danger" round :disabled="scope.row.is_default ? true : false">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加对话 -->
        <el-dialog v-model="dialogVisible" title="添加用户">

            <el-form ref="addFormRef" :model="addForm" :rules="rules" label-width="80px" class="ruleForm" status-icon>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="addForm.username" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="addForm.password" type="password" />
                </el-form-item>
                <el-form-item label="角色" prop="roleId">
                    <el-select v-model="addForm.roleId" class="m-2" placeholder="请选择角色" size="large" style="width:100%">
                        <el-option v-for="item in roleList" :key="item.id" :label="item.roleName" :value="item.id" />
                    </el-select>

                </el-form-item>

                <!-- <el-form-item label="学院" prop="stuCollege">
                <el-input v-model="addForm.stuCollege" />
                </el-form-item> -->
                <el-form-item label="学院" prop="stuCollege">
                    <el-select v-model="addForm.stuCollege" class="m-2" placeholder="请选择学院" size="large"
                        style="width:100%">
                        <el-option v-for="item in CollegeType2" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>

                </el-form-item>
                <el-form-item label="学号">
                    <el-input v-model="addForm.stuId" />
                </el-form-item>
                <el-form-item label="班级">
                    <el-input v-model="addForm.stuClass" />
                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleAddConfirm()">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>

        <!--更新对话 -->
        <el-dialog v-model="dialogUpdateVisible" title="更新用户">

            <el-form ref="updateFormRef" :model="updateForm" :rules="rules" label-width="80px" class="ruleForm" status-icon>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="updateForm.username" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="updateForm.password" type="password" />
                </el-form-item>
                <el-form-item label="角色" prop="roleId">
                    <el-select v-model="updateForm.roleId" class="m-2" placeholder="请选择角色" size="large" style="width:100%">
                        <el-option v-for="item in roleList" :key="item.id" :label="item.roleName" :value="item.id" />
                    </el-select>

                <!-- </el-form-item>
                 <el-form-item label="学院" prop="stuCollege">
                <el-input v-model="updateForm.stuCollege" />
                </el-form-item> -->
                </el-form-item>
                <el-form-item label="学院" prop="stuCollege">
                    <el-select v-model="updateForm.stuCollege" class="m-2" placeholder="请选择学院" size="large"
                        style="width:100%">
                        <el-option v-for="item in CollegeType2" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>

                </el-form-item>
                <el-form-item label="学号">
                    <el-input v-model="updateForm.stuId" />
                </el-form-item>
                <el-form-item label="班级">
                    <el-input v-model="updateForm.stuClass" />
                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogUpdateVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleUpdateConfirm()">
                        更新
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted, reactive } from 'vue'
import { CollegeType2,  LabType} from '../../util/type'
const tableData = ref([])
const roleList = ref([])
onMounted(() => {
    getList()
    getRoleList()
})

const getList = async () => {
    var res = await axios.get("/adminapi/users", {
        params: searchForm
    })
    // console.log(res.data)
    tableData.value = res.data
}

const getRoleList = async () => {
    var res = await axios.get("/adminapi/roles")
    // console.log(res.data)
    roleList.value = res.data
}

//显示添加对话框
const handleAdd = () => {
    dialogVisible.value = true
}
const dialogVisible = ref(false)
const addFormRef = ref()
const addForm = reactive({
    username: "",
    password: "",
    roleId: "",
    stuCollege: "",
    stuId: "",
    stuClass: ""
})
const rules = reactive({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
    ],
    roleId: [
        { required: true, message: '请选择角色', trigger: 'blur' },
    ],
        stuCollege: [
        { required: true, message: '请输入学院', trigger: 'blur' },
    ],
})

const handleAddConfirm = () => {
    addFormRef.value.validate(async (valid) => {
        if (valid) {
            dialogVisible.value = false
            await axios.post(`/adminapi/users`, addForm)

            await getList()

            addForm.username = ""
            addForm.password = ""
            addForm.roleId = ""
            addForm.stuCollege = ""
            addForm.stuId = ""
            addForm.stuClass = ""
        } else {
            console.log(valid)
        }
    })
}

//更新
const dialogUpdateVisible = ref(false)
const updateFormRef = ref()
const currentItem = ref({})
const updateForm = reactive({
    username: "",
    password: "",
    roleId: "",
    stuCollege: "",
    stuId: "",
    stuClass: ""
})
const handleUpdate = (item) => {
    // console.log(item)
    //更新对话显示
    dialogUpdateVisible.value = true
    //updateForm 更新
    updateForm.username = item.username
    updateForm.password = item.password
    updateForm.roleId = item.roleId
    updateForm.stuCollege = item.stuCollege
    updateForm.stuId = item.stuId
    updateForm.stuClass = item.stuClass
    //currentItem id
    currentItem.value = item
}

const handleUpdateConfirm =async ()=>{
    // dialogUpdateVisible.value = false
    // await axios.put(`/adminapi/users/${currentItem.value.id}`,updateForm)

    // await getList()
        updateFormRef.value.validate(async (valid) => {
        if (valid) {
            dialogUpdateVisible.value = false
            await axios.put(`/adminapi/users/${currentItem.value.id}`, updateForm)
            await getList()
        } else {
            console.log(valid)
        }
    })
}

const handleDelete = async({id})=>{
    await axios.delete(`/adminapi/users/${id}`)
    await getList()
}

const searchForm = reactive({
    username: "",
    roleId: ""
})

const handleSearch = () => {
    getList()
}

const resetSearch = () => {
    searchForm.username = ""
    searchForm.roleId = ""
    getList()
}
</script>