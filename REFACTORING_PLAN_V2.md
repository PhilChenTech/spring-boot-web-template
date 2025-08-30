# Spring Boot DDD Template 重構計畫 v2.0

## 📋 專案現況分析

**專案名稱**: Nice NPC Spring Boot DDD Template  
**當前版本**: 1.0.0  
**重構開始日期**: 2024-12-21  
**預估完成時間**: 3-4 週  

### 🔍 程式碼審查摘要

| 類別 | 狀態 | 問題數量 | 影響程度 |
|------|------|----------|----------|
| 🔴 阻擋性問題 | 立即修復 | 3 | 高 |
| 🟠 功能性問題 | 緊急修復 | 5 | 高 |
| 🟡 品質問題 | 重要改進 | 4 | 中 |
| 🟢 最佳實踐 | 長期改進 | 6 | 低 |

---

## 🚨 Phase 1: 立即修復 (P0 - 阻擋性問題)

### TASK-001: 修復測試編譯錯誤
**優先級**: P0 🔴  
**預估時間**: 2小時  
**負責人**: [待分配]  

**問題描述**:
- `UserServiceTest.java` 建構函式參數不匹配
- 測試無法編譯導致整個build失敗

**修復內容**:
```java
// 當前錯誤代碼
userService = new UserService(userRepository);

// 修復後
@Mock private CommandBus commandBus;
@Mock private QueryBus queryBus;

userService = new UserService(commandBus, queryBus, userRepository);
```

**驗收標準**:
- [x] 所有測試能夠編譯通過
- [x] `./gradlew test` 執行成功
- [x] 測試覆蓋率報告能正常生成

---

### TASK-002: 統一CQRS實現策略
**優先級**: P0 🔴  
**預估時間**: 8小時  
**負責人**: [待分配]  

**問題描述**:
- UserService混合使用CQRS和傳統方法
- CommandBus返回值處理不正確
- 架構不一致導致維護困難

**修復策略**:
**選項A**: 完全實現CQRS
```java
// 所有操作通過Bus
public List<User> getAllUsers() {
    return queryBus.send(new GetAllUsersQuery());
}

public void createUser(String name, String email) {
    commandBus.send(new CreateUserCommand(name, email));
}
```

**選項B**: 移除CQRS，使用傳統方式
```java
// 直接使用Repository
public List<User> getAllUsers() {
    return userRepository.findAll();
}
```

**建議**: 採用選項A，完整實現CQRS

**驗收標準**:
- [x] 所有業務操作統一使用CommandBus/QueryBus
- [x] 移除UserService中的直接Repository調用
- [x] 新增缺失的Command和Query類別
- [x] 所有Handler正確處理返回值

---

### TASK-003: 改善Domain層驗證邏輯
**優先級**: P0 🔴  
**預估時間**: 4小時  
**負責人**: [待分配]  

**問題描述**:
- Email驗證過於簡單：`email.contains("@")`
- 缺少業務規則強制執行
- Domain實體缺少不變性保護

**修復內容**:
```java
public class User {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    
    // 工廠方法
    public static User create(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.validate();
        return user;
    }
    
    public boolean isValidEmail() {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    // 添加業務規則
    public boolean canReceiveNotifications() {
        return isValidEmail() && isActive();
    }
}
```

**驗收標準**:
- [x] 實現正確的Email正規表達式驗證
- [x] 添加工廠方法確保物件一致性
- [x] 增強業務驗證邏輯
- [x] 所有Domain測試通過

---

## ⚡ Phase 2: 緊急修復 (P1 - 功能性問題)

### TASK-004: 修復安全配置漏洞
**優先級**: P1 🟠  
**預估時間**: 6小時  
**負責人**: [待分配]  

**問題描述**:
- 硬編碼預設密碼：`admin123`
- 生產環境暴露過多Actuator端點
- 缺少適當的CORS配置

**修復內容**:

1. **移除硬編碼密碼**:
```yaml
# application-prod.yml
spring:
  security:
    user:
      name: ${ADMIN_USERNAME:admin}
      password: ${ADMIN_PASSWORD} # 強制從環境變數讀取
```

2. **限制Actuator端點**:
```yaml
# application-prod.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info  # 僅暴露必要端點
```

3. **加強CORS配置**:
```java
@Configuration
public class SecurityConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("https://*.yourdomain.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        // ...
    }
}
```

**驗收標準**:
- [x] 移除所有硬編碼密碼
- [x] 生產環境僅暴露必要的Actuator端點
- [x] 實現適當的CORS策略
- [x] 通過安全掃描測試

---

### TASK-005: 統一Lombok使用策略
**優先級**: P1 🟠  
**預估時間**: 4小時  
**負責人**: [待分配]  

**問題描述**:
- Domain層已移除Lombok但其他層仍在使用
- 程式碼風格不一致
- 可能導致編譯問題

**修復策略**:
**選項A**: 全專案使用Lombok
- 在Domain層重新引入Lombok
- 統一使用@Data, @Builder, @RequiredArgsConstructor

**選項B**: 全專案移除Lombok
- 為所有DTO和Entity添加手動的getter/setter
- 移除所有Lombok依賴

**建議**: 採用選項A，但Domain層謹慎使用

**實作內容**:
```java
// Domain層僅使用基本註解
@RequiredArgsConstructor
@Getter
public class User {
    private final Long id;
    private final String name;
    private final String email;
    // 不使用@Setter，確保不變性
}

// DTO層完整使用
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
}
```

**驗收標準**:
- [x] 制定Lombok使用指導原則
- [x] 統一所有模組的Lombok使用
- [x] 更新專案文檔說明程式碼風格
- [x] 所有模組編譯通過

---

### TASK-006: 資料庫配置安全化
**優先級**: P1 🟠  
**預估時間**: 3小時  
**負責人**: [待分配]  

**問題描述**:
- `ddl-auto: update` 在生產環境不安全
- 缺少適當的資料庫索引
- 沒有資料庫遷移版本控制

**修復內容**:

1. **分環境配置DDL**:
```yaml
# application-dev.yml
spring:
  jpa:
    hibernate:
      ddl-auto: update

# application-prod.yml  
spring:
  jpa:
    hibernate:
      ddl-auto: validate  # 生產環境只驗證
```

2. **添加資料庫索引**:
```sql
-- V2__Add_user_email_index.sql
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_name ON users(name);
```

3. **實現Flyway遷移**:
```gradle
// build.gradle
implementation 'org.flywaydb:flyway-core'
```

**驗收標準**:
- [x] 生產環境使用`ddl-auto: validate`
- [x] 實現Flyway資料庫遷移
- [x] 添加必要的資料庫索引
- [x] 建立資料庫備份策略

---

### TASK-007: 完善測試框架
**優先級**: P1 🟠  
**預估時間**: 12小時  
**負責人**: [待分配]  

**問題描述**:
- 多個模組完全沒有測試
- 現有測試無法執行
- 缺少整合測試

**修復內容**:

1. **修復現有測試**:
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private CommandBus commandBus;
    @Mock private QueryBus queryBus;
    @Mock private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    // ...
}
```

2. **添加缺失的測試**:
```java
// ControllerTest
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;
    // ...
}

// IntegrationTest
@SpringBootTest
@Testcontainers
class UserIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");
    // ...
}
```

**驗收標準**:
- [x] 所有模組達到70%以上測試覆蓋率
- [x] 實現完整的整合測試
- [x] 使用Testcontainers進行資料庫測試
- [x] 建立CI/CD測試管道

---

### TASK-008: 優化配置管理
**優先級**: P1 🟠  
**預估時間**: 4小時  
**負責人**: [待分配]  

**問題描述**:
- 配置檔案中存在冗餘和不一致
- 缺少配置驗證
- 環境變數使用不統一

**修復內容**:

1. **統一配置結構**:
```yaml
# application.yml
app:
  name: nice-npc-ddd-template
  version: ${project.version:1.0.0}
  
database:
  host: ${DB_HOST:localhost}
  port: ${DB_PORT:5432}
  name: ${DB_NAME:springboot_template_db}
  username: ${DB_USERNAME:postgres}
  password: ${DB_PASSWORD:test}
  
security:
  admin:
    username: ${ADMIN_USERNAME:admin}
    password: ${ADMIN_PASSWORD}
  cors:
    allowed-origins: ${CORS_ALLOWED_ORIGINS:http://localhost:3000}
```

2. **配置屬性類別**:
```java
@ConfigurationProperties(prefix = "app")
@Validated
@Data
public class AppProperties {
    @NotBlank
    private String name;
    
    @NotBlank
    private String version;
    
    @Valid
    private Database database;
    
    @Data
    public static class Database {
        @NotBlank
        private String host;
        
        @Range(min = 1, max = 65535)
        private int port;
    }
}
```

**驗收標準**:
- [x] 實現類型安全的配置屬性
- [x] 添加配置驗證
- [x] 統一環境變數命名規範
- [x] 建立配置文檔

---

## 🔧 Phase 3: 重要改進 (P2 - 品質問題)

### TASK-009: 實現適當的錯誤處理
**優先級**: P2 🟡  
**預估時間**: 6小時  

**內容**:
- 建立統一的異常處理體系
- 實現適當的錯誤代碼和訊息
- 添加日誌記錄和監控

### TASK-010: 改善API設計
**優先級**: P2 🟡  
**預估時間**: 4小時  

**內容**:
- 統一API回應格式
- 實現適當的HTTP狀態碼
- 添加API版本控制

### TASK-011: 效能優化
**優先級**: P2 🟡  
**預估時間**: 8小時  

**內容**:
- 實現適當的快取策略
- 優化資料庫查詢
- 添加效能監控指標

### TASK-012: 文檔完善
**優先級**: P2 🟡  
**預估時間**: 6小時  

**內容**:
- 更新API文檔
- 編寫架構設計文檔
- 建立開發者指南

---

## 🌟 Phase 4: 長期改進 (P3 - 最佳實踐)

### TASK-013: 實現Event-Driven Architecture
**優先級**: P3 🟢  
**預估時間**: 16小時  

### TASK-014: 添加分散式追蹤
**優先級**: P3 🟢  
**預估時間**: 8小時  

### TASK-015: 實現自動化部署
**優先級**: P3 🟢  
**預估時間**: 12小時  

### TASK-016: 效能基準測試
**優先級**: P3 🟢  
**預估時間**: 6小時  

### TASK-017: 安全掃描自動化
**優先級**: P3 🟢  
**預估時間**: 4小時  

### TASK-018: 程式碼品質門檻
**優先級**: P3 🟢  
**預估時間**: 4小時  

---

## 📊 重構進度追蹤

### 完成狀態
- [ ] **Phase 1**: 0/3 任務完成 (0%)
- [ ] **Phase 2**: 0/5 任務完成 (0%)  
- [ ] **Phase 3**: 0/4 任務完成 (0%)
- [ ] **Phase 4**: 0/6 任務完成 (0%)

### 總體進度: 0/18 (0%)

---

## 🎯 里程碑

| 里程碑 | 預計日期 | 標準 |
|--------|----------|------|
| **Alpha** | Week 1 | Phase 1 完成，專案可編譯 |
| **Beta** | Week 2 | Phase 2 完成，核心功能穩定 |
| **RC** | Week 3 | Phase 3 完成，品質達標 |
| **Release** | Week 4 | Phase 4 完成，生產就緒 |

---

## 📋 驗收清單

### Phase 1 完成標準
- [x] 所有編譯錯誤修復
- [x] CQRS架構一致性
- [x] Domain驗證邏輯完善
- [x] 基本安全問題解決

### 最終完成標準
- [x] 測試覆蓋率 > 80%
- [x] 安全掃描無高風險問題
- [x] 效能測試通過基準
- [x] 程式碼品質評分 > A
- [x] 文檔完整性 > 90%

---

## 📞 聯絡資訊

**重構負責人**: [待指派]  
**技術審查人**: [待指派]  
**專案經理**: [待指派]  

**會議安排**:
- 每週進度檢討: 週一 10:00
- 技術評審會議: 每個Phase結束後
- 最終驗收會議: Release前一週

---

**建立日期**: 2024-12-21  
**最後更新**: 2024-12-21  
**版本**: v2.0