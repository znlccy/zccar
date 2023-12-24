<template>
  <div class="login-container">
    <div class="login-box">
      <h1>用户登录</h1>
      <Form ref="loginForm" style="margin-top: 15px" :model="loginForm" :rules="loginFormRules" :label-width="80">
        <FormItem label="用户名:" prop="username">
          <Input type="text" v-model="loginForm.username" placeholder="请输入用户名">
            <Icon type="ios-person-outline" slot="prepend"/>
          </Input>
        </FormItem>
        <FormItem label="密码:" prop="password">
          <Input type="password" v-model="loginForm.password" placeholder="请输入密码">
            <Icon type="ios-lock-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <FormItem label="验证码:" prop="code">
          <Row>
            <Col span="16">
              <Input type="text" v-model="loginForm.code" placeholder="请输入验证码" />
            </Col>
            <Col span="8">
              <img :src="captcha" width="120px" height="32px" alt="" @click="getCaptcha"/>
            </Col>
          </Row>
        </FormItem>
        <FormItem>
          <Button class="login-btn" @click="login" type="primary">登录</Button>
        </FormItem>
      </Form>
    </div>
  </div>
</template>

<script>
import { userLogin, userCaptcha } from '@/apis/user'

export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: '',
        code: ''
      },
      captcha: '',
      loginFormRules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 6, max: 32, message: '密码长度是6~32位', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '验证码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getCaptcha()
  },
  methods: {
    // 用户登录
    async login () {
      
      // 1.校验登录参数
      await userLogin(this.loginForm).then(res => {
        if (res.data.code === 20000) {
          this.$store.commit('user/setUserInfo', res.data.data)
          this.$Message.success(res.data.message)
          this.$router.push('/')
        } else {
          this.$Message.error(res.data.message)
        }
      })
    },
    
    // 获取验证码
    async getCaptcha() {
      await userCaptcha().then(res => {
        this.captcha = window.URL.createObjectURL(res.data)
      })
    }
  }
}
</script>

<style lang="less" scoped>
.login-container {
  width: 100%;
  height: 100%;
  display: flex;
  overflow-y: hidden;
  background: #fff;
  .login-box {
    width: 520px;
    height: 330px;
    margin: 12% auto;
    padding-top: 10px;
    text-align: center;
    padding-right: 40px;
    //background: red;
    border-radius: 4px;
    border: 1px solid #eeeeee;
    box-shadow: 0px 0px 0px 1px rgba(0,0,0,.1);
    h3 {
      font-weight: bolder;
      text-align: center;
      margin: 10px 0px;
    }
  }
  .login-btn {
    width: 100%;
  }
}
</style>
