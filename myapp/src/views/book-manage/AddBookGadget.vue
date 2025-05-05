<template>
    <div>

        <el-table :data="filterTableData" style="width: 100%">
            <!-- <el-table-column prop="roleName" label="角色名称" width="180" /> -->
            <!-- <el-table-column prop="icon" label="图标" width="180" /> -->
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

            <el-table-column label="预约设备">
                <template #default="scope">
                    <el-button round type="warning" @click="handleBook(scope.row)">预约</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 预约对话框 -->
        <el-dialog v-model="dialogVisible" title="预约设备">
            <el-form ref="bookFormRef" :model="bookForm" :rules="rules" label-width="100px" class="ruleForm" status-icon>
                <el-form-item label="预约日期" prop="bookgadget_time">
                    <el-date-picker v-model="bookForm.bookgadget_time" type="date" placeholder="请选择日期" style="width:100%"
                        :disabled-date="disabledDate" @change="handleSelectChange"/>
                </el-form-item>
                <el-form-item label="预约时间段" prop="bookgadget_class">
                    <el-select v-model="bookForm.bookgadget_class" placeholder="请选择时间段" style="width:100%">
                        <el-option v-for="item in GadgetClassType" :key="item.value" :label="item.label" :value="item.value" 
                            :disabled="disableSelect.includes(item.value)"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="预约原因" prop="bookgadget_reason">
                    <el-input v-model="bookForm.bookgadget_reason" type="textarea" />
                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="handleCancel()">取消</el-button>
                    <el-button type="primary" @click="handleBookConfirm()">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted, reactive, computed } from 'vue'
import { GadgetClassType } from '../../util/type'
import { useUserStore } from '../../store/useUserStore'
import { ElMessage } from 'element-plus'

const { user } = useUserStore()
const tableData = ref([])
const labList = ref([])
const currentItem = ref({})

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

//预约
const dialogVisible = ref(false)
const bookFormRef = ref()
const bookForm = reactive({
    bookgadget_time: "",
    bookgadget_class: "",
    bookgadget_reason: ""
})

const rules = reactive({
    bookgadget_time: [
        { required: true, message: '请选择日期', trigger: 'blur' },
    ],
    bookgadget_class: [
        { required: true, message: '请选择时间段', trigger: 'blur' },
    ],
    bookgadget_reason: [
        { required: true, message: '请输入原因', trigger: 'blur' },
    ]
})

const handleBook = (item) => {
        console.log('预约按钮被点击', item)
    currentItem.value = item
    dialogVisible.value = true
}

const handleBookConfirm = async () => {
    bookFormRef.value.validate(async (valid) => {
        if (valid) {
            dialogVisible.value = false
            await axios.post(`/adminapi/bookgadget`, {
                ...bookForm,
                bookgadget_username: user.username,
                gadget_id: currentItem.value.id
            })
            clear()
            ElMessage({
                message: '提交预约成功,审核中',
                type: 'success',
            })
        }
    })
}

const handleCancel = () => {
    dialogVisible.value = false
    clear()
}

const clear = () => {
    bookForm.bookgadget_time = ""
    bookForm.bookgadget_class = ""
    bookForm.bookgadget_reason = ""
}

//禁用日期选择
const disabledDate = (time) => {
    return time.getTime() < Date.now() || time.getTime() > (Date.now() + 3600 * 1000 * 24 * 7)
}

//改变日期的回调
const handleSelectChange = async () => {
    bookForm.bookgadget_class = ""
    var res = await axios.post(`/adminapi/bookgadget/select`, {
        bookgadget_time: bookForm.bookgadget_time,
        gadget_id: currentItem.value.id
    })
    disableSelect.value = res.data.map(item => item.bookgadget_class)
}

const disableSelect = ref([])
</script>
