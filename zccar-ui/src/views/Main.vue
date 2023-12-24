<template>
  <div class="main-container">
    <Menu mode="horizontal" theme="dark" active-name="1">
      <div class="logo">
        <img src="../assets/logo.png" width="30px" height="30px" alt="">
      </div>
      <div class="header">
        <MenuItem name="1">
          <Icon type="iso-navigate"></Icon>
          餐饮车数据展示区域
        </MenuItem>
      </div>
      <div class="content">
        <div class="data-content">
          <Table stripe border :columns="columnList" :data="carDataList">
          </Table>
        </div>
        <Page :total="total" show-total class="page-class" @on-change="pageChange"></Page>
        <Modal
            v-model="nearCar"
            title="附近的餐饮车"
            width="1000px"
            @on-cancel="cancel">
          <Table :columns="nearColumnList" :data="carDataList" stripe border></Table>
        </Modal>
      </div>
      <div class="footer">
        2019-2024 &copy; demo公司
      </div>
    </Menu>
  </div>
</template>

<script>
// 导入餐饮车api
import {carList, carNear} from '@/apis/car'
window.selfs = {}
export default {
  name: "Main",
  data() {
    return {
      columnList: [
        {
          title: '申请人',
          key: 'applicant',
          width: 200,
          align: 'center'
        },
        {
          title: '设备类型',
          key: 'facilityType',
          width: 100,
          align: 'center'
        },
        {
          title: '状态',
          key: 'status',
          align: 'center',
        },
        {
          title: '经度',
          key: 'longitude',
          align: 'center'
        },
        {
          title: '维度',
          key: 'latitude',
          align: 'center'
        },
        {
          title: '许可',
          key: 'permit',
          align: 'center'
        },
        {
          title: '邮政编码',
          key: 'zipCodes',
          align: 'center'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          render(h, params) {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary'
                },
                on: {
                  click: () => {
                    window.selfs.showCar(params.row)
                  }
                }
              }, '查看附近餐饮车')
            ]);
          }
        }
      ],
      pageNo: 1,
      pageSize: 10,
      carDataList: [],
      total: 0,
      nearCarList: [],
      nearCar: false,
      nearColumnList: [
        {
          title: '申请人',
          key: 'applicant',
          width: 200,
          align: 'center'
        },
        {
          title: '设备类型',
          key: 'facilityType',
          width: 100,
          align: 'center'
        },
        {
          title: '状态',
          key: 'status',
          align: 'center',
        },
        {
          title: '经度',
          key: 'longitude',
          align: 'center'
        },
        {
          title: '维度',
          key: 'latitude',
          align: 'center'
        },
        {
          title: '邮政编码',
          key: 'zipCodes',
          align: 'center'
        },
      ],
    }
  },
  created() {
    window.selfs = this
    this.getCarList()
  },
  methods: {
    
    // 获取餐饮车列表数据
    async getCarList() {
      await carList(this.pageNo, this.pageSize).then(res => {
        this.carDataList = res.data.data.records
        this.total = res.data.data.total
      })
    },
  
    // 页码变了
    pageChange(index) {
      this.pageNo = index
      this.getCarList()
    },
    
    // 显示附近餐饮车
    async showCar(row) {
      const id = row.id
      const distance = 1
      const size = 8
      await carNear(id, distance, size).then(res => {
        this.nearCarList = res.data.data
        this.nearCar = true
      })
    },
    
    cancel() {
      this.nearCar = false
    }
  }
}
</script>

<style lang="less" scoped>
.logo {
  width: 100px;
  height: 30px;
  background: #5b6270;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 15px;
  left: 20px;
  display: flex;
  justify-content: center;

}
.header {
  width: 200px;
  margin: 0 auto;
}

.content {
  min-height: 560px;
  position: relative;
  top: 60px;
  background: #eeeeee;
  .data-content {
    width: 1280px;
    margin: 0 auto;
    padding: 15px 15px;
    min-height: inherit;
    background: #fff;
    display: flex;
    justify-content: center;
  }
  .page-class {
    text-align: center;
  }
}

.footer {
  text-align: center;
  bottom: 0;
  padding: 10px 0 20px;
  margin-top: 40px;
  height: 95px;
  line-height: 95px;
  color: #fff;
  background: #3e3e3e;
}

</style>