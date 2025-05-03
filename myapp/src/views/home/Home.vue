<template>
  <div>
    <!-- 饼图容器 -->
    <div ref="pieChartRef" style="width: 600px; height: 400px; margin-bottom: 20px;"></div>
    
    <!-- 柱状图容器 -->
    <div ref="barChartRef" style="width: 600px; height: 400px;"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

// 获取两个图表的 DOM 引用
const pieChartRef = ref(null);
const barChartRef = ref(null);

onMounted(() => {
  // 1. 初始化饼图
  if (pieChartRef.value) {
    const pieChart = echarts.init(pieChartRef.value);
    const pieOption = {
      title: {
        text: 'Referer of a Website',
        subtext: 'Fake Data',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    pieChart.setOption(pieOption);
  }

  // 2. 初始化柱状图
  if (barChartRef.value) {
    const barChart = echarts.init(barChartRef.value);
    const barOption = {
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [120, 200, 150, 80, 70, 110, 130],
          type: 'bar'
        }
      ]
    };
    barChart.setOption(barOption);
  }
});
</script>