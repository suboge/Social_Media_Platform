<template>
  <div class="register-container">
    <div class="register-box">
      <h2>加入 E.SUN Social</h2>
      <p>建立帳號，開始分享你的生活</p>

      <form @submit.prevent="handleRegister">
        <div class="form-item">
          <label>手機號碼 (登入帳號)</label>
          <input v-model="form.phoneNumber" type="text" placeholder="0912345678" required />
        </div>

        <div class="form-item">
          <label>使用者名稱</label>
          <input v-model="form.userName" type="text" placeholder="你的暱稱" required />
        </div>

        <div class="form-item">
          <label>電子郵件</label>
          <input v-model="form.email" type="email" placeholder="example@esunbank.com.tw" />
        </div>

        <div class="form-item">
          <label>密碼</label>
          <input v-model="form.password" type="password" placeholder="至少 6 位數" required />
        </div>

        <div class="form-item">
          <label>個人簡介</label>
          <textarea v-model="form.biography" placeholder="跟大家介紹一下自己吧..."></textarea>
        </div>

        <button type="submit" :disabled="loading">
          {{ loading ? '註冊中...' : '立即註冊' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import axios from 'axios'

const loading = ref(false)
const form = reactive({
  phoneNumber: '',
  userName: '',
  email: '',
  password: '',
  biography: ''
})

const handleRegister = async () => {
  loading.value = true
  try {
    // 呼叫我們在 Spring Boot 寫好的 API
    const response = await axios.post('/api/users/register', form)
    alert('註冊成功！快去登入吧')
    console.log(response.data)
  } catch (error) {
    console.error(error)
    alert('註冊失敗：' + (error.response?.data || '伺服器錯誤'))
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
  background-color: #f4f7f6;
}
.register-box {
  background: white;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.08);
  width: 100%;
  max-width: 400px;
}
h2 { color: #139444; margin-bottom: 8px; }
p { color: #666; margin-bottom: 24px; font-size: 14px; }
.form-item { margin-bottom: 16px; text-align: left; }
label { display: block; margin-bottom: 6px; font-weight: bold; font-size: 14px; }
input, textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  box-sizing: border-box;
}
button {
  width: 100%;
  padding: 12px;
  background-color: #139444;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;
}
button:hover { background-color: #0f7a38; }
button:disabled { background-color: #ccc; }
</style>