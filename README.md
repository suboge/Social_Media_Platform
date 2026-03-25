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

---

## 技術棧 (Tech Stack)

* **前端 (Frontend)**: Vue 3 (Composition API), Vite, Axios
* **後端 (Backend)**: Java, Spring Boot 3.x, Spring Data JPA, Spring Security (BCrypt 加密)
* **資料庫 (Database)**: PostgreSQL 17

---

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
