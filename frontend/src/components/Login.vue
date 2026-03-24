<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="title">E.SUN Social 登入</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-item">
          <label>手機號碼</label>
          <input v-model="loginForm.phone" type="text" placeholder="請輸入註冊的手機" required />
        </div>
        <div class="form-item">
          <label>密碼</label>
          <input v-model="loginForm.password" type="password" placeholder="請輸入密碼" required />
        </div>
        <button type="submit" class="login-btn">登入系統</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import axios from 'axios'

const emit = defineEmits(['login-success'])

const loginForm = reactive({
  phone: '',
  password: ''
})

const handleLogin = async () => {
  try {
    // 這裡我們假設後端已經有一個 /api/users/login (或是你現在直接測通)
    // 暫時邏輯：如果註冊過，我們直接呼叫 API 驗證
    const response = await axios.post('/api/users/login', loginForm)
    
    // 登入成功，將使用者資訊傳回給 App.vue
    alert('歡迎回來！')
    emit('login-success', response.data) // 假設回傳包含 userId, userName
  } catch (error) {
    alert('登入失敗，請檢查帳號密碼')
  }
}
</script>

<style scoped>
.login-container { display: flex; justify-content: center; margin-top: 50px; }
.login-box { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 350px; }
.title { color: #139444; margin-bottom: 20px; }
.form-item { margin-bottom: 15px; text-align: left; }
label { display: block; margin-bottom: 5px; font-size: 14px; }
input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
.login-btn { width: 100%; padding: 10px; background: #139444; color: white; border: none; border-radius: 5px; cursor: pointer; }
</style>