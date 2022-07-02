<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 400px;"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px;"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data() {
    return {}
  },
  mounted() { // 頁面元素渲染後才觸發

    let chartDom = document.getElementById('main');
    let myChart = echarts.init(chartDom)
    let option;
    option = {
      title: {
        text: '各季會員數量統計',
        subtext: '趨勢圖',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ['第一季', '第二季', '第三季', '第四季']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };

    // 圓餅圖
    let pieOption = {
      title: {
        text: '各季會員數量統計',
        subtext: '比例圖',
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
          type: 'pie',
          radius: '70%',
          label: { // 圓餅圖的文字標籤
            normal: {
              show: true,
              position: 'inner',//標籤的位置
              textStyle: {
                fontWeight: 200,
                fontSize: 14,
                color: '#fff'
              },
              formatter: '{d}%'
            }
          },
          data: [
            // {value: 1048, name: 'Search Engine'},
            // {value: 735, name: 'Direct'},
            // {value: 580, name: 'Email'},
            // {value: 484, name: 'Union Ads'},
            // {value: 300, name: 'Video Ads'}
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

    let pieChartDom = document.getElementById('pie');
    let pieChart = echarts.init(pieChartDom)


    this.request.get('/echarts/members').then(res => {
      // option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data

      pieOption.series[0].data = [
        {name: '第一季', value: res.data[0]},
        {name: '第二季', value: res.data[1]},
        {name: '第三季', value: res.data[2]},
        {name: '第四季', value: res.data[3]},

      ]
      // 資料準備完畢才set
      myChart.setOption(option)
      pieChart.setOption(pieOption)
    })
  }
}
</script>

<style scoped>

</style>