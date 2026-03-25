<template>
  <div class="feed-container">
    
    <div class="post-card create-post">
      <textarea v-model="newPost.content" placeholder="分享你的想法..."></textarea>
      <div class="post-actions">
        <button @click="handleCreatePost" :disabled="!newPost.content">發布貼文</button>
      </div>
    </div>

    <div v-for="post in posts" :key="post.post_id" class="post-card">
      
      <div class="post-header">
        <span class="author">👤 {{ post.user_name }}</span>
        <span class="date">{{ formatDate(post.created_at) }}</span>
      </div>
      
      <div class="post-content">
        {{ post.content }}
      </div>
      
<div class="post-footer">
        
        <span v-if="post.user_name === 'popo'" class="author-actions" style="display: flex; gap: 16px;">
          
          <button class="btn-text" @click="handleEdit(post)">✏️ 修改</button>
          <button class="btn-text" @click="handleDelete(post.post_id)">🗑 刪除</button>
          
        </span>
        
      </div>
      
      <div class="comment-section">
        
        <div class="comment-input-group">
          <input 
            v-model="commentInputs[post.post_id]" 
            placeholder="寫下你的留言..." 
            @keyup.enter="handleSendComment(post.post_id)"
          />
          <button @click="handleSendComment(post.post_id)">發送</button>
        </div>

        <div v-if="post.comments && post.comments.length > 0" class="comments-list">
          <div v-for="c in post.comments" :key="c.comment_id" class="comment-item">
            <strong>{{ c.user_name }}:</strong> {{ c.content }}
          </div>
        </div>
        
      </div> </div> </div> </template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import axios from 'axios'

const currentUserId = ref(Number(localStorage.getItem('userId')))
const posts = ref([])
const newPost = reactive({
  content: '',
  userId: currentUserId.value
})

const fetchPosts = async () => {
  try {
    const response = await axios.get('/api/posts')
    posts.value = response.data
  } catch (error) {
    console.error('讀取文章失敗', error)
  }
}

// 新增發文
const handleCreatePost = async () => {
  const userId = Number(localStorage.getItem('userId')); 
  console.log("準備發文！目前抓到的 userId 是:", userId);

  if (!userId || userId === 0) {
    alert("抓不到使用者 ID，請確認你登入時存進 localStorage 的名稱是不是 'userId'！");
    return;
  }

  try {
    await axios.post('/api/posts', {
      content: newPost.content,
      userId: userId
    }); 
    newPost.content = '';
    await fetchPosts();
    alert('發布成功！');
  } catch (error) {
    console.error('發布失敗的詳細原因:', error);
    alert('發布失敗，請打開 F12 看 Console 的紅字！');
  }
}

const handleEdit = async (post) => {
  const newContent = prompt('請輸入修改後的內容：', post.content)
  
  if (newContent !== null && newContent.trim() !== '') {
    try {
      await axios.put(`/api/posts/${post.post_id}`, {
        userId: currentUserId.value,
        content: newContent
      })
      alert('修改成功！')
      await fetchPosts() // 重新刷新畫面
    } catch (error) {
      console.error('修改失敗:', error)
      alert('修改失敗，請檢查後端 API 是否有寫好')
    }
  }
}

// 刪除發文
const handleDelete = async (postId) => {
  if (!confirm('確定要刪除這篇貼文嗎？')) return
  try {
    await axios.delete(`/api/posts/${postId}?userId=${currentUserId.value}`)
    await fetchPosts()
  } catch (error) {
    alert('刪除失敗')
  }
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleString()
}

onMounted(() => {
  fetchPosts()
})

const commentInputs = ref({}) // 用來存放每一篇文對應的輸入內容

const handleSendComment = async (postId) => {
  const content = commentInputs.value[postId];

  console.log("準備發送留言！文章ID:", postId, "留言內容:", content);

  if (!content || content.trim() === '') {
      alert("請輸入留言內容！");
      return;
  }
  
  try {
      const response = await axios.post('/api/comments', {
          userId: currentUserId.value,
          postId: postId,
          content: content
      });
      
      alert("留言成功！");
      commentInputs.value[postId] = ""; // 清空輸入框
      fetchPosts(); // 重新載入文章與留言
  } catch (error) {
      console.error(error);
      alert("留言失敗");
  }
}
</script>

<style scoped>
.feed-container { max-width: 600px; margin: 20px auto; padding: 0 15px; }
.post-card {
  background: white;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  text-align: left;
}
.create-post textarea {
  width: 100%;
  height: 80px;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 10px;
  resize: none;
  font-family: inherit;
}
.post-actions { text-align: right; margin-top: 8px; }
.post-header { display: flex; justify-content: space-between; margin-bottom: 12px; }
.author { font-weight: bold; color: #139444; }
.date { font-size: 12px; color: #999; }
.post-content { line-height: 1.6; color: #333; white-space: pre-wrap; }
.post-footer { border-top: 1px solid #f0f0f0; margin-top: 12px; padding-top: 8px; display: flex; gap: 15px; }
button {
  background: #139444; color: white; border: none; padding: 8px 16px; border-radius: 20px; cursor: pointer;
}
.btn-text { background: none; color: #666; padding: 0; font-size: 14px; }
.text-red { color: #ff4d4f; }
.comment-section {
  margin-top: 15px;
  background: #f9f9f9;
  padding: 10px;
  border-radius: 4px;
}
.comment-input-group {
  display: flex;
  gap: 8px;
  margin-bottom: 10px;
}
.comment-input-group input {
  flex: 1;
  padding: 6px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
.comments-list {
  font-size: 13px;
  border-top: 1px solid #eee;
  padding-top: 8px;
}
.comment-item {
  margin-bottom: 4px;
  color: #555;
}
</style>