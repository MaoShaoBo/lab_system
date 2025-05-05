<template>
    <div>
        <el-radio-group v-model="listType" style="margin-bottom: 20px">
            <el-radio-button label="lab">实验室列表</el-radio-button>
            <el-radio-button label="gadget">设备列表</el-radio-button>
        </el-radio-group>

        <!-- 实验室列表 -->
        <el-table v-if="listType === 'lab'" :data="filterLabTableData" style="width: 100%">
            <el-table-column label="名称">
                <template #default="scope">
                    <el-link type="primary" @click="handleLocation(scope.row)">{{ scope.row.lab.title }}</el-link>
                </template>
            </el-table-column>

            <el-table-column label="预约人">
                <template #header>
                    <el-input v-model="labSearch" size="small" placeholder="请搜索" />
                </template>
                <template #default="scope">
                    <div>{{ scope.row.book_username }}</div>
                </template>
            </el-table-column>
            <el-table-column label="预约时间">
                <template #default="scope">
                    <el-tag style="height:40px;line-height: 20px;">{{ moment(scope.row.book_time).format("YYYY-MM-DD") }}
                        <br />
                        {{ class_method(scope.row.book_class) }}
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="预约原因">
                <template #default="scope">
                    <div>{{ scope.row.book_reason }}</div>
                </template>
            </el-table-column>            

            <el-table-column label="预约状态">
                <template #default="scope">
                    <el-tag :type="Book_state_type[scope.row.book_state]">
                        {{ Book_state_text[scope.row.book_state] }}
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作">
                <template #default="scope">
                    <el-popconfirm title="你确定要批准吗?" @confirm="handleUpdate(scope.row,APPROVE)" confirm-button-text="确定"
                        cancel-button-text="取消">
                        <template #reference>
                            <el-button type="primary" round>批准</el-button>
                        </template>
                    </el-popconfirm>
                    <el-popconfirm title="你确定要驳回吗?" @confirm="handleUpdate(scope.row,REJECT)" confirm-button-text="确定"
                        cancel-button-text="取消">
                        <template #reference>
                            <el-button type="danger" round>驳回</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!-- 设备列表 -->
        <el-table v-else :data="filterGadgetTableData" style="width: 100%">
            <el-table-column label="设备名称">
                <template #default="scope">
                    <div>{{ scope.row.gadget.title }}</div>
                </template>
            </el-table-column>

            <el-table-column label="预约人">
                <template #header>
                    <el-input v-model="gadgetSearch" size="small" placeholder="请搜索" />
                </template>
                <template #default="scope">
                    <div>{{ scope.row.bookgadget_username }}</div>
                </template>
            </el-table-column>
            <el-table-column label="预约时间">
                <template #default="scope">
                    <el-tag style="height:40px;line-height: 20px;">{{ moment(scope.row.bookgadget_time).format("YYYY-MM-DD") }}
                        <br />
                        {{ gadgetClass_method(scope.row.bookgadget_class) }}
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="预约原因">
                <template #default="scope">
                    <div>{{ scope.row.bookgadget_reason }}</div>
                </template>
            </el-table-column>

            <el-table-column label="预约状态">
                <template #default="scope">
                    <el-tag :type="Book_state_type[scope.row.bookgadget_state]">
                        {{ Book_state_text[scope.row.bookgadget_state] }}
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作">
                <template #default="scope">
                    <el-popconfirm title="你确定要批准吗?" @confirm="handleGadgetUpdate(scope.row,APPROVE)" confirm-button-text="确定"
                        cancel-button-text="取消">
                        <template #reference>
                            <el-button type="primary" round>批准</el-button>
                        </template>
                    </el-popconfirm>
                    <el-popconfirm title="你确定要驳回吗?" @confirm="handleGadgetUpdate(scope.row,REJECT)" confirm-button-text="确定"
                        cancel-button-text="取消">
                        <template #reference>
                            <el-button type="danger" round>驳回</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog v-model="dialogVisible" title="实验室位置" :fullscreen="true" v-if="dialogVisible">
            <LabMap :x="currentItem.x" :y="currentItem.y" :t="currentItem.title"></LabMap>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios';
import { ClassType, Book_state_text, Book_state_type, AUDIT, APPROVE, REJECT, GadgetClassType } from '../../util/type'
import LabMap from '../../components/lab-manage/LabMap.vue'
import { useUserStore } from '../../store/useUserStore'
import moment from 'moment'

const listType = ref('lab')
const labTableData = ref([])
const gadgetTableData = ref([])
const labSearch = ref("")
const gadgetSearch = ref("")

onMounted(() => {
    getLabList()
    getGadgetList()
})

// 实验室列表相关
const filterLabTableData = computed(() => {
    return labTableData.value.filter(item => !labSearch.value ||
        item.book_username.toLowerCase().includes(labSearch.value.toLowerCase()))
})

const getLabList = async () => {
    var res = await axios.get(`/adminapi/books?book_state=${AUDIT}`)
    labTableData.value = res.data
}

const class_method = (value) => {
    return ClassType.find(item => item.value === value).label
}

const currentItem = ref({})
const dialogVisible = ref(false)
const handleLocation = (item) => {
    dialogVisible.value = true
    currentItem.value = item.lab
}

const handleUpdate = async ({ id }, state) => {
    await axios.put(`/adminapi/books/${id}`, {
        book_state: state
    })
    await getLabList()
}

// 设备列表相关
const filterGadgetTableData = computed(() => {
    return gadgetTableData.value.filter(item => !gadgetSearch.value ||
        item.bookgadget_username.toLowerCase().includes(gadgetSearch.value.toLowerCase()))
})

const getGadgetList = async () => {
    var res = await axios.get(`/adminapi/bookgadget?bookgadget_state=${AUDIT}`)
    gadgetTableData.value = res.data
}

const gadgetClass_method = (value) => {
    return GadgetClassType.find(item => item.value === value).label
}

const handleGadgetUpdate = async ({ id }, state) => {
    await axios.put(`/adminapi/bookgadget/${id}`, {
        bookgadget_state: state
    })
    await getGadgetList()
}
</script>