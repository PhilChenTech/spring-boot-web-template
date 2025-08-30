# Spring Boot DDD Template 重構計畫

## 專案概況
- **專案名稱**: Nice NPC Spring Boot DDD Template
- **當前狀態**: 有多個架構和設計問題需要修復
- **目標**: 建立符合DDD和Clean Architecture原則的生產級模板

## 問題優先級分類

### 🔴 P0 - 立即修復 (阻擋性問題)
- [x] **TASK-001**: 修復Domain層Lombok污染
- [x] **TASK-002**: 統一應用程式啟動入口
- [x] **TASK-003**: 修正API空值處理問題
- [x] **TASK-004**: 添加全局異常處理器
- [x] **TASK-005**: 修正依賴注入配置

### 🟠 P1 - 緊急修復 (影響功能)
- [x] **TASK-006**: 重構事務管理策略
- [x] **TASK-007**: 實施統一驗證策略
- [x] **TASK-008**: 修正MapStruct配置
- [x] **TASK-009**: 添加基礎單元測試
- [x] **TASK-010**: 修正模組依賴關係

### 🟡 P2 - 重要改進 (提升品質)
- [ ] **TASK-011**: 統一配置管理
- [ ] **TASK-012**: 添加API文檔
- [ ] **TASK-013**: 實施安全性配置
- [ ] **TASK-014**: 優化日誌配置
- [ ] **TASK-015**: 添加健康檢查

### 🟢 P3 - 長期改進 (最佳實踐)
- [ ] **TASK-016**: 實施CQRS模式
- [ ] **TASK-017**: 添加監控和指標
- [ ] **TASK-018**: 實施CI/CD流程
- [ ] **TASK-019**: 性能優化
- [ ] **TASK-020**: 文檔完善

---

## 詳細任務規劃

### TASK-001: 修復Domain層Lombok污染
**狀態**: ⏳ 待執行
**預估時間**: 30分鐘
**描述**: 移除Domain層的Lombok依賴，使用純POJO實現
**檔案影響**:
- `domain/src/main/java/com/nicenpc/domain/User.java`

**執行步驟**:
1. 移除Lombok註解
2. 手動實現getter/setter/equals/hashCode
3. 確保Domain層純淨

---

### TASK-002: 統一應用程式啟動入口
**狀態**: ⏳ 待執行
**預估時間**: 45分鐘
**描述**: 移除重複的Application類，統一使用bootstrap模組
**檔案影響**:
- `adapter-inbound/src/main/java/com/nicenpc/adapterinbound/AdapterInboundApplication.java` (刪除)
- `bootstrap/src/main/java/com/nicenpc/bootstrap/Application.java` (優化)

**執行步驟**:
1. 分析現有啟動類
2. 移除重複的啟動類
3. 優化bootstrap Application類

---

### TASK-003: 修正API空值處理問題
**狀態**: ⏳ 待執行
**預估時間**: 30分鐘
**描述**: 修正Controller可能返回null導致NPE的問題
**檔案影響**:
- `adapter-inbound/src/main/java/com/nicenpc/adapterinbound/controller/UserController.java`

**執行步驟**:
1. 添加空值檢查
2. 返回適當的HTTP狀態碼
3. 使用Optional處理

---

### TASK-004: 添加全局異常處理器
**狀態**: ⏳ 待執行
**預估時間**: 45分鐘
**描述**: 創建統一的異常處理機制
**檔案影響**:
- `adapter-inbound/src/main/java/com/nicenpc/adapterinbound/exception/` (新建)

**執行步驟**:
1. 創建自定義異常類
2. 實現@ControllerAdvice
3. 定義錯誤響應格式

---

### TASK-005: 修正依賴注入配置
**狀態**: ⏳ 待執行
**預估時間**: 20分鐘
**描述**: 修正JpaConfig的包掃描路徑
**檔案影響**:
- `infrastructure/src/main/java/com/nicenpc/infrastructure/config/JpaConfig.java`

**執行步驟**:
1. 修正@EnableJpaRepositories路徑
2. 修正@EntityScan路徑
3. 確保依賴方向正確

---

### TASK-006: 重構事務管理策略
**狀態**: ⏳ 待執行
**預估時間**: 30分鐘
**描述**: 優化@Transactional的使用
**檔案影響**:
- `application/src/main/java/com/nicenpc/application/UserService.java`

**執行步驟**:
1. 移除類級別的@Transactional
2. 只在寫操作方法上添加@Transactional
3. 查詢方法使用@Transactional(readOnly = true)

---

### TASK-007: 實施統一驗證策略
**狀態**: ⏳ 待執行
**預估時間**: 60分鐘
**描述**: 將驗證邏輯移到Domain層
**檔案影響**:
- `domain/src/main/java/com/nicenpc/domain/User.java`
- `application/src/main/java/com/nicenpc/application/UserService.java`

**執行步驟**:
1. 在Domain實體中添加驗證方法
2. 移除Service層的驗證邏輯
3. 使用Domain事件處理驗證

---

### TASK-008: 修正MapStruct配置
**狀態**: ⏳ 待執行
**預估時間**: 25分鐘
**描述**: 統一MapStruct配置使用
**檔案影響**:
- `adapter-inbound/src/main/java/com/nicenpc/adapterinbound/mapper/UserDTOMapper.java`
- `adapter-outbound/src/main/java/com/nicenpc/adapteroutbound/mapper/UserMapper.java`

**執行步驟**:
1. 修改Mapper使用共同配置
2. 確保配置一致性
3. 測試映射功能

---

### TASK-009: 添加基礎單元測試
**狀態**: ⏳ 待執行
**預估時間**: 90分鐘
**描述**: 為核心類添加單元測試
**檔案影響**:
- `domain/src/test/` (新建)
- `application/src/test/` (新建)
- `adapter-inbound/src/test/` (新建)

**執行步驟**:
1. 創建測試目錄結構
2. 為Domain實體添加測試
3. 為Service添加測試
4. 為Controller添加測試

---

### TASK-010: 修正模組依賴關係
**狀態**: ⏳ 待執行
**預估時間**: 40分鐘
**描述**: 優化模組間的依賴關係
**檔案影響**:
- `bootstrap/build.gradle`
- 各模組的build.gradle檔案

**執行步驟**:
1. 分析當前依賴關係
2. 移除不必要的依賴
3. 確保依賴方向正確

---

## 執行進度追蹤

**開始時間**: 2025-08-31
**預估完成時間**: 2025-09-01
**當前進度**: 10/20 任務完成 (50%)

### 完成的任務
- ✅ TASK-001: 修復Domain層Lombok污染 (移除Lombok依賴，實現純POJO)
- ✅ TASK-002: 統一應用程式啟動入口 (移除重複的AdapterInboundApplication)
- ✅ TASK-003: 修正API空值處理問題 (添加空值檢查和適當HTTP狀態碼)
- ✅ TASK-004: 添加全局異常處理器 (創建統一異常處理機制)
- ✅ TASK-005: 修正依賴注入配置 (確保JPA配置正確)
- ✅ TASK-006: 重構事務管理策略 (優化@Transactional使用)
- ✅ TASK-007: 實施統一驗證策略 (在Domain層添加驗證邏輯和自定義異常)
- ✅ TASK-008: 修正MapStruct配置 (確認配置一致性)
- ✅ TASK-009: 添加基礎單元測試 (為Domain、Application、Controller層創建測試)
- ✅ TASK-010: 修正模組依賴關係 (優化Clean Architecture依賴方向，移除Lombok全局依賴)

### 進行中的任務
(無)

### 遇到的問題
(無)

---

## 驗證檢查清單

### 建置驗證
- [ ] `./gradlew clean build` 成功
- [ ] 無編譯警告
- [ ] 所有測試通過

### 功能驗證
- [ ] Web模式啟動成功
- [ ] Desktop模式啟動成功
- [ ] API端點正常運作
- [ ] 資料庫連接正常

### 代碼品質
- [ ] SonarQube分析通過
- [ ] 測試覆蓋率 > 80%
- [ ] 無代碼異味

### 架構驗證
- [ ] 依賴方向正確
- [ ] 層次邊界清晰
- [ ] DDD原則遵守

---

**最後更新**: 2025-08-31 15:45:00
**更新者**: GitHub Copilot
