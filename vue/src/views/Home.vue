<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 100px">
      <el-col :span="6">
        <el-card style="color:#409EFF">
          <div><i class="el-icon-user-solid"/> 會員數量</div>
          <div style="padding:10px 0;text-align: center;font-weight: bold">
            1,012
            <!--            <el-tag type="primary" style="font-size: large">100</el-tag>-->
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color:#F56C6C">
          <div><i class="el-icon-money"/> 銷售總數</div>
          <div style="padding:10px 0;text-align: center;font-weight: bold">
            $ 11,200,000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color:#67C23A">
          <div><i class="el-icon-bank-card"/> 收益額</div>
          <div style="padding:10px 0;text-align: center;font-weight: bold">
            $ 912,384,542
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color:#c7792a">
          <div><i class="el-icon-s-shop"/> 店面總數</div>
          <div style="padding:10px 0;text-align: center;font-weight: bold">
            10
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 100%; height: 500px;"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 100%; height: 500px;"></div>
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
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'horizontal',
        // left: 'left',
        x: 'top', // 'center' | 'left' | {number},
        y: 'bottom',
        borderWidth: 2,
        padding: 10,
        itemGap: 10,
        textStyle: {color: '#409EFF'},
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
          name: '會員數量',
          data: [],
          type: 'line'
        },
        {
          name: '會員數量',
          data: [],
          type: 'bar'
        },
        {
          name: 'test',
          data: [],
          type: 'bar'
        },
        {
          name: 'test',
          data: [],
          type: 'line'
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
          name: "會員數量",
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
      option.series[2].data = [5, 6, 62, 8]
      option.series[3].data = [5, 6, 62, 8]

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