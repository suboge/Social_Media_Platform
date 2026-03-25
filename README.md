# Social Media Platform

這是一個基於 Vue 3 與 Spring Boot 開發的簡易社群媒體平台。本專案嚴格遵守實作測驗要求，落實了「三層式實體架構」與「後端邏輯分層設計」，並整合 PostgreSQL 的預存程序 (Stored Procedure) 進行資料存取，同時確保使用者資料與操作的安全性。

## 核心系統架構與設計理念

### 1. 實體三層式架構 (3-Tier Architecture)
- **Web Server (展示層)**：採用 Vue 3 + Vite，負責畫面渲染與路由管理。
- **Application Server (應用層)**：採用 Java Spring Boot，負責商業邏輯運算與權限驗證。
- **Relational Database (資料層)**：採用 PostgreSQL 17，負責資料持久化，並透過預存程序優化效能。

### 2. 後端邏輯分層設計 (Logical Layers)
- **展示層 (Controller)**：負責接收 HTTP 請求、定義 API 路由 (RESTful API)。
- **業務層 (Service)**：負責核心商業邏輯、權限防呆 (如：僅作者可修改/刪除貼文) 與跨表資料組裝。
- **資料層 (Repository)**：負責與資料庫溝通，呼叫 Stored Procedure 與 Functions。
- **共用層 (Model/Config)**：包含 Entity、DTO 物件轉換，以及 Spring Security 密碼加密等全域設定。

## 實作功能清單
[x] 使用者登入 (驗證密碼 Hash)

[x] 瀏覽所有貼文與對應留言

[x] 發布新貼文

[x] 針對貼文新增留言

[x] 權限控管：僅有貼文原作者可以進行「修改」與「刪除」操作

---

## 技術棧 (Tech Stack)

* **前端 (Frontend)**: Vue 3 (Composition API), Vite, Axios
* **後端 (Backend)**: Java, Spring Boot 3.x, Spring Data JPA, Spring Security (BCrypt 加密)
* **資料庫 (Database)**: PostgreSQL 17

---

如何啟動專案 (How to Run)
步驟一：準備資料庫環境
請準備 PostgreSQL 17 環境，並建立相對應的資料庫 (例如 esun_social_db)。

請執行專案提供的 SQL 腳本建立 users, posts, comments 資料表。

請務必建立專案所需的預存程序 (Stored Procedures) 與 函數 (Functions)，包含：發布貼文、新增留言、以及連表查詢所有貼文 (fn_get_all_posts 需包含 user_id 回傳以供前端權限驗證)。

步驟二：啟動後端 (Backend)
進入 backend 目錄。

開啟 src/main/resources/application.properties，確認資料庫的 URL、帳號、密碼設定是否與您的本地環境一致。

透過 IDE 開啟 backend 專案，執行 ESumSocialMediaPlatformApplication.java。

伺服器預設將於 http://localhost:8080 啟動。

步驟三：啟動前端 (Frontend)
開啟終端機，進入 frontend 目錄：

Bash
cd frontend
安裝所需依賴套件：

Bash
npm install
啟動 Vite 開發伺服器：

Bash
npm run dev
開啟瀏覽器訪問終端機顯示的網址，即可開始體驗系統。

## 📂 專案目錄結構

```text
e_sun_social_media_platform/
├── backend/                  # Spring Boot 後端專案目錄
│   ├── src/main/java/com/popo/esun/socialmedia/
│   │   ├── config/           # 安全性與跨域設定
│   │   ├── controller/       # API 端點
│   │   ├── service/          # 商業邏輯
│   │   ├── repository/       # 資料庫存取介面
│   │   └── model/            # 實體 (Entity) 與傳遞物件 (DTO)
│   └── pom.xml
│
├── frontend/                 # Vue 3 前端專案目錄
│   ├── src/
│   │   ├── components/       # Vue 元件 (如登入、貼文列表)
│   │   └── App.vue           # 前端主入口
│   ├── package.json
│   └── vite.config.js
│
├── schema.sql                # 資料庫建表和建立 Stored Procedure
└── README.md                 # 專案說明文件
