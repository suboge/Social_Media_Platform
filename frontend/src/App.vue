<template>
  <div id="app">
    <div v-if="!currentUser">
      <nav class="nav">
        <button @click="currentTab = 'login'">登入</button>
        <button @click="currentTab = 'register'">註冊</button>
      </nav>
      <Login v-if="currentTab === 'login'" @login-success="onLoginSuccess" />
      <Register v-if="currentTab === 'register'" />
    </div>

    <div v-else>
      <header class="header">
        <span>你好，{{ currentUser.userName }}</span>
        <button @click="logout" class="logout-btn">登出</button>
      </header>
      <PostList :userId="currentUser.userId" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import PostList from './components/PostList.vue'

const currentTab = ref('login')
const currentUser = ref(null)

const onLoginSuccess = (userData) => {
  currentUser.value = userData
}

const logout = () => {
  currentUser.value = null
  currentTab.value = 'login'
}
</script>

<style>
.nav { margin-bottom: 20px; }
.header { background: #139444; color: white; padding: 10px 20px; display: flex; justify-content: space-between; align-items: center; }
.logout-btn { background: white; color: #139444; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer; }
</style>