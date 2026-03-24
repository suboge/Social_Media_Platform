import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue' // 這裡要加上 /plugin-vue

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})