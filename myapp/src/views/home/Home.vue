<template>
  <div>
    <!-- 卡片样式数据 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header" style="display: flex; justify-content: center; align-items: center">
              <span>用户总数</span>
            </div>
          </template>
          <div class="card-content">
            <span class="number">{{ userCount }}</span>
            <span class="unit">人</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header" style="display: flex; justify-content: center; align-items: center">
              <span>实验室总数</span>
            </div>
          </template>
          <div class="card-content">
            <span class="number">{{ labCount }}</span>
            <span class="unit">间</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header" style="display: flex; justify-content: center; align-items: center">
              <span>设备总数</span>
            </div>
          </template>
          <div class="card-content">
            <span class="number">{{ gadgetCount }}</span>
            <span class="unit">台</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 实验室预约折线图 -->
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>实验室预约情况</span>
        </div>
      </template>
      <div ref="lineChartRef" style="width: 100%; height: 400px;"></div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import axios from 'axios';
import moment from 'moment';

// 卡片数据
const userCount = ref(0);
const labCount = ref(0);
const gadgetCount = ref(0);

// 折线图引用
const lineChartRef = ref(null);

// 获取卡片数据
const getCardData = async () => {
  try {
    const res = await axios.get('/adminapi/home/card');
    userCount.value = res.data.userCount;
    labCount.value = res.data.labCount;
    gadgetCount.value = res.data.gadgetCount;
  } catch (error) {
    console.error('获取卡片数据失败:', error);
  }
};

// 获取实验室预约数据
const getLabBookData = async () => {
  try {
    const res = await axios.get('/adminapi/home/labbook');
    const data = res.data;
    
    // 初始化折线图
    const lineChart = echarts.init(lineChartRef.value);
    
    // 生成日期数组（今天到未来7天）
    const dates = Array.from({ length: 8 }, (_, i) => {
      return moment().add(i, 'days').format('YYYY-MM-DD');
    });

    // 按实验室名称分组数据
    const labGroups = {};
    data.forEach(item => {
      if (!labGroups[item.labName]) {
        labGroups[item.labName] = {};
      }
      labGroups[item.labName][item.date] = item.count;
    });

    // 准备系列数据
    const series = Object.keys(labGroups).map(labName => ({
      name: labName,
      type: 'line',
      data: dates.map(date => labGroups[labName][date] || 0)
    }));

    // 配置项
    const option = {
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: Object.keys(labGroups),
        type: 'scroll',
        bottom: 0
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '15%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: dates
      },
      yAxis: {
        type: 'value',
        name: '预约数量',
        min: 0,       // 最小值设为0
        max: 4,       // 最大值设为4
        interval: 1,  // 间隔设为1
      },
      series: series
    };

    lineChart.setOption(option);

    // 响应式调整
    window.addEventListener('resize', () => {
      lineChart.resize();
    });
  } catch (error) {
    console.error('获取实验室预约数据失败:', error);
  }
};

onMounted(() => {
  getCardData();
  getLabBookData();
});
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
}

.card-content {
  text-align: center;
  padding: 10px 0;
}

.number {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.unit {
  font-size: 14px;
  color: #909399;
  margin-left: 5px;
}

.el-card {
  margin-bottom: 15px;
}

.el-card :deep(.el-card__header) {
  padding: 10px 15px;
}

.el-card :deep(.el-card__body) {
  padding: 10px;
}
</style>