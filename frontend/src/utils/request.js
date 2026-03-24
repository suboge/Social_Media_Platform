import axios from 'axios'

const request = axios.create({
  baseURL: '', // 因為設定了 proxy，這裡留空即可
  timeout: 5000
})

// 攔截器：可以在這裡統一處理錯誤訊息
request.interceptors.response.use(
  response => response.data,
  error => {
    alert(error.response?.data || '網路錯誤')
    return Promise.reject(error)
  }
)

export default request