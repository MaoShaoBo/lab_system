<template>
    <div>
        <el-button type="primary" @click="handleAdd" style="margin-bottom: 20px;">添加设备</el-button>

        <el-table :data="filterTableData" style="width: 100%">
            <el-table-column label="设备名称">
                <template #header>
                    <div style="display: flex; align-items: center; gap: 10px;">
                        <span>设备名称</span>
                        <el-input v-model="titleSearch" size="small" placeholder="请搜索" style="width: 100px;" />
                    </div>
                </template>
                <template #default="scope">
                    <div>{{ scope.row.title }}</div>
                </template>
            </el-table-column>

            <el-table-column label="设备编号">
                <template #header>
                    <div style="display: flex; align-items: center; gap: 10px;">
                        <span>设备编号</span>
                        <el-input v-model="numSearch" size="small" placeholder="请搜索" style="width: 100px;" />
                    </div>
                </template>
                <template #default="scope">
                    <div>{{ scope.row.num }}</div>
                </template>
            </el-table-column>

            <el-table-column label="所属实验室">
                <template #default="scope">
                    <div>{{ getLabName(scope.row.lab) }}</div>
                </template>
            </el-table-column>
            <el-table-column prop="facturer" label="生产厂商"/>

            <el-table-column label="操作">
                <template #default="scope">
                    <el-button round type="warning" @click="handleUpdate(scope.row)">更新</el-button>
                    <el-popconfirm title="你确定要删除吗?" @confirm="handleDelete(scope.row)" confirm-button-text="确定"
                        cancel-button-text="取消">
                        <template #reference>
                            <el-button type="danger" round>删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加对话框 -->
        <el-dialog v-model="dialogVisible" title="添加设备">
            <el-form ref="addFormRef" :model="addForm" :rules="rules" label-width="100px" class="ruleForm" status-icon>
                <el-form-item label="设备名称" prop="title">
                    <el-input v-model="addForm.title" />
                </el-form-item>
                <el-form-item label="设备编号" prop="num">
                    <el-input v-model="addForm.num" />
                </el-form-item>
                <el-form-item label="所属实验室" prop="lab">
                    <el-select v-model="addForm.lab" placeholder="请选择实验室" style="width: 100%">
                        <el-option
                            v-for="item in labList"
                            :key="item.id"
                            :label="item.title"
                            :value="item.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="生产厂商" prop="facturer">
                    <el-input v-model="addForm.facturer" />
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

        <!-- 更新对话框 -->
        <el-dialog v-model="dialogUpdateVisible" title="更新设备">
            <el-form ref="updateFormRef" :model="updateForm" :rules="rules" label-width="100px" class="ruleForm" status-icon>
                <el-form-item label="设备名称" prop="title">
                    <el-input v-model="updateForm.title" />
                </el-form-item>
                <el-form-item label="设备编号" prop="num">
                    <el-input v-model="updateForm.num" />
                </el-form-item>
                <el-form-item label="所属实验室" prop="lab">
                    <el-select v-model="updateForm.lab" placeholder="请选择实验室" style="width: 100%">
                        <el-option
                            v-for="item in labList"
                            :key="item.id"
                            :label="item.title"
                            :value="item.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="生产厂商" prop="facturer">
                    <el-input v-model="updateForm.facturer" />
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
import { ref, onMounted, reactive, computed } from 'vue'

const tableData = ref([])
const labList = ref([])
const titleSearch = ref("")
const numSearch = ref("")

// 添加过滤后的表格数据计算属性
const filterTableData = computed(() => {
    return tableData.value.filter(item => {
        const titleMatch = !titleSearch.value || 
            item.title.toLowerCase().includes(titleSearch.value.toLowerCase())
        const numMatch = !numSearch.value || 
            item.num.toLowerCase().includes(numSearch.value.toLowerCase())
        return titleMatch && numMatch
    })
})

onMounted(() => {
    getList()
    getLabList()
})

const getList = async () => {
    var res = await axios.get("/adminapi/gadget")
    tableData.value = res.data
}

const getLabList = async () => {
    var res = await axios.get("/adminapi/labs")
    labList.value = res.data
}

const getLabName = (labId) => {
    const lab = labList.value.find(item => item.id === labId)
    return lab ? lab.title : ''
}

//添加
const dialogVisible = ref(false)
const addFormRef = ref()
const addForm = reactive({
    title: "",
    num: "",
    lab: "",
    facturer: ""
})

const rules = reactive({
    title: [
        { required: true, message: '请输入设备名称', trigger: 'blur' },
    ],
    num: [
        { required: true, message: '请输入设备编号', trigger: 'blur' },
    ],
    lab: [
        { required: true, message: '请选择所属实验室', trigger: 'change' },
    ],
    facturer: [
        { required: true, message: '请输入生产厂商', trigger: 'blur' },
    ]
})

const handleAdd = () => {
    dialogVisible.value = true
}

const handleAddConfirm = async () => {
    addFormRef.value.validate(async (valid) => {
        if (valid) {
            dialogVisible.value = false
            await axios.post(`/adminapi/gadget`, addForm)
            await getList()
            addForm.title = ""
            addForm.num = ""
            addForm.lab = ""
            addForm.facturer = ""
        }
    })
}

//更新
const dialogUpdateVisible = ref(false)
const updateFormRef = ref()
const currentItem = ref({})
const updateForm = reactive({
    title: "",
    num: "",
    lab: "",
    facturer: ""
})

const handleUpdate = (item) => {
    dialogUpdateVisible.value = true
    updateForm.title = item.title
    updateForm.num = item.num
    updateForm.lab = item.lab
    updateForm.facturer = item.facturer
    currentItem.value = item
}

const handleUpdateConfirm = async () => {
    updateFormRef.value.validate(async (valid) => {
        if (valid) {
            dialogUpdateVisible.value = false
            await axios.put(`/adminapi/gadget/${currentItem.value.id}`, updateForm)
            await getList()
        }
    })
}

//删除
const handleDelete = async ({id}) => {
    await axios.delete(`/adminapi/gadget/${id}`)
    await getList()
}
</script>