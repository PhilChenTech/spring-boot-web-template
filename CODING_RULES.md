# Nice NPC Spring Boot DDD Template - 程式碼規範

本文檔定義了此專案的程式碼編寫標準和最佳實踐，確保程式碼的一致性、可讀性和可維護性。

## 📋 目錄

- [架構原則](#架構原則)
- [套件結構規範](#套件結構規範)
- [Java 程式碼規範](#java-程式碼規範)
- [Lombok 使用規範](#lombok-使用規範)
- [Spring 註解規範](#spring-註解規範)
- [API 設計規範](#api-設計規範)
- [異常處理規範](#異常處理規範)
- [測試規範](#測試規範)
- [文檔規範](#文檔規範)
- [數據庫設計規範](#數據庫設計規範)

## 🏗️ 架構原則

### 1. 分層架構原則

```
springboot-web-template/
├── bootstrap/              # 🚀 應用程式啟動和配置
├── domain/                 # 🎯 領域層 - 純粹的業務邏輯
├── application/            # 🏗️ 應用層 - CQRS 實現
├── infrastructure/        # 🔧 基礎設施層 - 外部依賴實現
├── adapter-inbound/       # 📥 入站適配器 - REST 控制器
├── adapter-outbound/      # 📤 出站適配器 - 資料庫存取
└── common/                # 🛠️ 共用工具和配置
```

### 2. 依賴方向規則

- **Domain Layer**: 不依賴任何其他層，保持純淨
- **Application Layer**: 只依賴 Domain Layer
- **Infrastructure Layer**: 可依賴 Domain 和 Application Layer
- **Adapter Layers**: 可依賴所有內層
- **Bootstrap Layer**: 可依賴所有層，負責組裝

### 3. Clean Architecture 原則

- 內層不依賴外層
- 業務邏輯與框架解耦
- 介面隔離和依賴反轉
- 單一職責原則

## 📦 套件結構規範

### 基礎套件結構

```
com.nicenpc
├── domain/                 # 領域層
│   ├── entity/            # 領域實體
│   ├── exception/         # 領域異常
│   └── repository/        # 倉庫介面
├── application/           # 應用層
│   ├── command/          # CQRS 指令
│   ├── query/            # CQRS 查詢
│   ├── handler/          # 指令/查詢處理器
│   └── bus/              # 指令/查詢匯流排
├── infrastructure/       # 基礎設施層
│   └── config/           # 配置類
├── adapterinbound/       # 入站適配器
│   ├── controller/       # REST 控制器
│   ├── dto/              # 資料傳輸物件
│   ├── mapper/           # DTO 映射器
│   └── exception/        # 全域異常處理
├── adapteroutbound/      # 出站適配器
│   ├── entity/           # JPA 實體
│   ├── repository/       # 倉庫實現
│   └── mapper/           # 實體映射器
├── bootstrap/            # 啟動層
│   ├── config/           # Spring 配置
│   └── metrics/          # 監控指標
└── common/               # 共用模組
    └── mapper/           # MapStruct 配置
```

## ☕ Java 程式碼規範

### 1. 命名規範

#### 類別命名
```java
// ✅ 正確：使用 PascalCase
public class UserService { }
public class CreateUserCommand { }
public class UserController { }

// ❌ 錯誤
public class userService { }
public class user_controller { }
```

#### 方法命名
```java
// ✅ 正確：使用 camelCase，動詞開頭
public User createUser(String name, String email) { }
public List<User> getAllUsers() { }
public boolean existsByEmail(String email) { }

// ❌ 錯誤
public User CreateUser() { }
public List<User> get_all_users() { }
```

#### 變數命名
```java
// ✅ 正確：使用 camelCase，有意義的名稱
private final UserRepository userRepository;
private String emailAddress;
private boolean isActive;

// ❌ 錯誤
private UserRepository ur;
private String e;
private boolean flag;
```

#### 常數命名
```java
// ✅ 正確：使用 UPPER_SNAKE_CASE
private static final String DEFAULT_EMAIL_DOMAIN = "@nicenpc.com";
private static final int MAX_RETRY_ATTEMPTS = 3;
private static final Pattern EMAIL_PATTERN = Pattern.compile("...");
```

### 2. 程式碼格式

#### 縮排和空格
```java
// ✅ 正確：使用 4 個空格縮排
public class UserService {
    
    private final UserRepository userRepository;
    
    public User createUser(CreateUserCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        
        return userRepository.save(user);
    }
}
```

#### 方法長度
- 單一方法不超過 50 行
- 複雜邏輯應拆分為多個私有方法

#### 類別長度
- 單一類別不超過 500 行
- 職責過多的類別應拆分

### 3. 程式碼結構

#### 類別成員順序
```java
public class UserService {
    // 1. 靜態常數
    private static final String DEFAULT_STATUS = "ACTIVE";
    
    // 2. 實例變數
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    // 3. 構造函數
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    
    // 4. 公有方法
    public User createUser(String name, String email) {
        // 實現邏輯
    }
    
    // 5. 私有方法
    private void validateUser(User user) {
        // 驗證邏輯
    }
}
```

## 🔧 Lombok 使用規範

### 1. 各層級 Lombok 使用指南

#### Domain Layer (領域層)
```java
// ✅ 推薦：最小化 Lombok 使用
public class User {
    private Long id;
    private String name;
    private String email;
    
    // 手寫構造函數和業務方法
    public User(String name, String email) {
        validateName(name);
        validateEmail(email);
        this.name = name;
        this.email = email;
    }
    
    // 必要時可使用基本註解
    // @Data, @NoArgsConstructor, @AllArgsConstructor
}

// ❌ 避免：複雜的 Lombok 註解
// @Builder, @Wither 等在領域層避免使用
```

#### Application Layer (應用層)
```java
// ✅ 推薦：使用 @RequiredArgsConstructor 進行依賴注入
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final CommandBus commandBus;
    
    // 業務方法...
}
```

#### Adapter Layers (適配器層)
```java
// ✅ 推薦：充分利用 Lombok 功能
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
}

// DTO 類
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Invalid email format")
    private String email;
}

// JPA 實體
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
}
```

#### 工具類
```java
// ✅ 推薦：使用 @UtilityClass
@UtilityClass
public class UserMapper {
    
    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(entity.getId(), entity.getName(), entity.getEmail());
    }
    
    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        return new UserEntity(domain.getId(), domain.getName(), domain.getEmail());
    }
}
```

### 2. Lombok 使用原則

- **領域層**：最小化使用，保持純淨
- **應用層**：主要用於依賴注入 (`@RequiredArgsConstructor`)
- **適配器層**：可充分使用各種 Lombok 功能
- **工具類**：使用 `@UtilityClass` 防止實例化

## 🌱 Spring 註解規範

### 1. 依賴注入規範

#### 推薦：構造函數注入 + @RequiredArgsConstructor
```java
// ✅ 推薦方式
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    // Spring 會自動注入 final 欄位
}
```

#### 避免：欄位注入
```java
// ❌ 避免使用
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;  // 不推薦
}
```

### 2. 元件註解規範

#### 分層註解使用
```java
// Domain Layer - 無 Spring 註解
public class User { }

// Application Layer
@Service
public class UserService { }

@Component
public class CreateUserCommandHandler { }

// Infrastructure Layer
@Configuration
public class JpaConfig { }

// Adapter-Inbound
@RestController
@RequestMapping("/api/v1/users")
public class UserController { }

@ControllerAdvice
public class GlobalExceptionHandler { }

// Adapter-Outbound
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> { }

@Component
public class UserRepositoryImpl implements UserRepository { }
```

### 3. 配置註解規範

```java
// ✅ 正確的配置類結構
@Configuration
@EnableJpaRepositories(basePackages = "com.nicenpc.adapteroutbound.repository")
@PropertySource("classpath:application.properties")
public class JpaConfig {
    
    @Bean
    @Primary
    public DataSource dataSource() {
        // 配置邏輯
    }
}
```

## 🌐 API 設計規範

### 1. REST API 路徑規範

```java
// ✅ 正確的 URL 設計
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @GetMapping                          // GET /api/v1/users
    @GetMapping("/{id}")                // GET /api/v1/users/123
    @PostMapping                        // POST /api/v1/users
    @PutMapping("/{id}")                // PUT /api/v1/users/123
    @DeleteMapping("/{id}")             // DELETE /api/v1/users/123
    @GetMapping("/search")              // GET /api/v1/users/search?email=...
}
```

### 2. HTTP 狀態碼規範

```java
// ✅ 正確的狀態碼使用
@PostMapping
public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
    UserResponse user = userService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED)  // 201 Created
        .body(ApiResponse.success(user, "User created successfully"));
}

@GetMapping("/{id}")
public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
    UserResponse user = userService.getUserById(id);
    return ResponseEntity.ok(ApiResponse.success(user));  // 200 OK
}

@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent()  // 204 No Content
        .build();
}
```

### 3. API 文檔規範

```java
// ✅ 使用 OpenAPI 3.0 註解
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "使用者管理相關 API")
public class UserController {
    
    @Operation(summary = "創建新使用者", description = "根據提供的資訊創建一個新的使用者")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "使用者創建成功",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "請求參數錯誤"),
        @ApiResponse(responseCode = "409", description = "使用者已存在")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
        @Parameter(description = "使用者創建請求") @Valid @RequestBody CreateUserRequest request) {
        // 實現邏輯
    }
}
```

### 4. DTO 設計規範

```java
// ✅ 請求 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    
    @NotBlank(message = "姓名不能為空")
    @Size(min = 2, max = 50, message = "姓名長度必須在 2-50 個字符之間")
    private String name;
    
    @NotBlank(message = "信箱不能為空")
    @Email(message = "信箱格式不正確")
    private String email;
}

// ✅ 回應 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
    private Instant createdAt;        // 使用 Instant 替代 LocalDateTime
    private Instant updatedAt;        // 使用 Instant 替代 LocalDateTime
}

// ✅ 統一回應格式
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    private Instant timestamp;        // 使用 Instant 替代 LocalDateTime
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data, Instant.now());
    }
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, Instant.now());
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, Instant.now());
    }
}
```

## ⚠️ 異常處理規範

### 1. 領域異常設計

```java
// ✅ 基礎領域異常
public abstract class DomainException extends RuntimeException {
    
    protected DomainException(String message) {
        super(message);
    }
    
    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}

// ✅ 具體領域異常
public class UserNotFoundException extends DomainException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public static UserNotFoundException withId(Long id) {
        return new UserNotFoundException("User not found with id: " + id);
    }
    
    public static UserNotFoundException withEmail(String email) {
        return new UserNotFoundException("User not found with email: " + email);
    }
}

public class UserAlreadyExistsException extends DomainException {
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    
    public static UserAlreadyExistsException withEmail(String email) {
        return new UserAlreadyExistsException("User already exists with email: " + email);
    }
}
```

### 2. 全域異常處理

```java
// ✅ 全域異常處理器
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(ex.getMessage()));
    }
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.warn("User already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(ex.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        log.warn("Validation failed: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error("Validation failed").data(errors));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("An unexpected error occurred"));
    }
}
```

## 🗄️ 數據庫設計規範

### 1. 表格命名規範

#### 表格命名規則
- **格式**: `TB_` + 表格功能描述
- **大小寫**: 全大寫
- **分隔符**: 底線 (`_`)

```sql
-- ✅ 正確的表格命名
TB_USER                    -- 使用者表
TB_USER_ROLE              -- 使用者角色表
TB_ORDER                  -- 訂單表
TB_ORDER_ITEM             -- 訂單項目表
TB_PRODUCT                -- 產品表
TB_PRODUCT_CATEGORY       -- 產品分類表
TB_SYSTEM_CONFIG          -- 系統配置表
TB_AUDIT_LOG              -- 審計日誌表

-- ❌ 錯誤的表格命名
user                      -- 沒有前綴，小寫
User                      -- 沒有前綴，駝峰命名
users                     -- 沒有前綴，小寫
tb_user                   -- 前綴小寫
USER                      -- 沒有前綴
```

#### 表格命名最佳實踐
```sql
-- 主實體表
TB_USER                   -- 使用者
TB_PRODUCT               -- 產品
TB_ORDER                 -- 訂單

-- 關聯表（多對多）
TB_USER_ROLE             -- 使用者-角色關聯
TB_PRODUCT_TAG           -- 產品-標籤關聯
TB_ORDER_PROMOTION       -- 訂單-促銷關聯

-- 配置表
TB_SYSTEM_CONFIG         -- 系統配置
TB_EMAIL_TEMPLATE        -- 郵件模板
TB_NOTIFICATION_SETTING  -- 通知設置

-- 日誌表
TB_AUDIT_LOG             -- 審計日誌
TB_ERROR_LOG             -- 錯誤日誌
TB_ACCESS_LOG            -- 訪問日誌
```

### 2. 欄位命名規範

#### 欄位命名規則
- **大小寫**: 全大寫
- **分隔符**: 底線 (`_`)
- **描述性**: 清楚表達欄位用途

```sql
-- ✅ 正確的欄位命名
CREATE TABLE TB_USER (
    USER_ID                BIGINT PRIMARY KEY,          -- 使用者ID
    USER_NAME              VARCHAR(50) NOT NULL,        -- 使用者姓名
    EMAIL_ADDRESS          VARCHAR(100) UNIQUE,         -- 信箱地址
    PASSWORD_HASH          VARCHAR(255) NOT NULL,       -- 密碼雜湊
    PHONE_NUMBER           VARCHAR(20),                 -- 電話號碼
    DATE_OF_BIRTH          DATE,                        -- 出生日期
    IS_ACTIVE              BOOLEAN DEFAULT TRUE,        -- 是否啟用
    IS_EMAIL_VERIFIED      BOOLEAN DEFAULT FALSE,       -- 信箱是否驗證
    LAST_LOGIN_TIME        TIMESTAMP,                   -- 最後登入時間
    CREATED_AT             TIMESTAMP DEFAULT NOW(),     -- 建立時間
    CREATED_BY             BIGINT,                      -- 建立者ID
    UPDATED_AT             TIMESTAMP DEFAULT NOW(),     -- 更新時間
    UPDATED_BY             BIGINT,                      -- 更新者ID
    VERSION                INTEGER DEFAULT 0            -- 版本號（樂觀鎖）
);

-- ❌ 錯誤的欄位命名
CREATE TABLE TB_USER (
    id                     BIGINT,                      -- 小寫
    userName               VARCHAR(50),                 -- 駝峰命名
    email_address          VARCHAR(100),                -- 部分小寫
    Password_Hash          VARCHAR(255),                -- 混合大小寫
    phone                  VARCHAR(20),                 -- 不夠描述性
    dob                    DATE,                        -- 縮寫不清楚
    active                 BOOLEAN,                     -- 不夠描述性
    create_time            TIMESTAMP                    -- 不一致的命名
);
```

#### 常用欄位命名模式
```sql
-- 主鍵欄位
{TABLE_NAME}_ID                 -- USER_ID, PRODUCT_ID, ORDER_ID

-- 外鍵欄位
{REFERENCED_TABLE}_ID           -- USER_ID, CATEGORY_ID, PARENT_ID

-- 狀態欄位
{ENTITY}_STATUS                 -- ORDER_STATUS, USER_STATUS, PAYMENT_STATUS

-- 布林欄位 (使用 IS_ 前綴)
IS_ACTIVE                       -- 是否啟用
IS_DELETED                      -- 是否刪除
IS_VERIFIED                     -- 是否驗證
IS_DEFAULT                      -- 是否預設

-- 時間欄位 (必須使用 UTC+0 時區)
CREATED_AT                      -- 建立時間 (UTC+0)
UPDATED_AT                      -- 更新時間 (UTC+0)
DELETED_AT                      -- 刪除時間 (UTC+0)
EXPIRED_AT                      -- 過期時間 (UTC+0)
LAST_LOGIN_TIME                 -- 最後登入時間 (UTC+0)
SCHEDULED_TIME                  -- 排程時間 (UTC+0)

-- 計數欄位
{ENTITY}_COUNT                  -- USER_COUNT, ORDER_COUNT, VIEW_COUNT

-- 金額欄位
{TYPE}_AMOUNT                   -- TOTAL_AMOUNT, DISCOUNT_AMOUNT, TAX_AMOUNT

-- 代碼欄位
{ENTITY}_CODE                   -- USER_CODE, PRODUCT_CODE, ORDER_CODE

-- 描述欄位
{ENTITY}_DESCRIPTION            -- PRODUCT_DESCRIPTION, ERROR_DESCRIPTION
{ENTITY}_REMARKS                -- ORDER_REMARKS, USER_REMARKS
```

### 8. 時間處理規範

#### 數據庫時間規範
- **時區**: 所有時間欄位必須使用 **UTC+0** 時區儲存
- **資料類型**: 使用 `TIMESTAMP` 或 `TIMESTAMPTZ` (PostgreSQL)
- **預設值**: 使用 `NOW()` 或 `CURRENT_TIMESTAMP` 設定預設時間

```sql
-- ✅ 正確的時間欄位定義
CREATE TABLE TB_USER (
    USER_ID                BIGSERIAL PRIMARY KEY,
    USER_NAME              VARCHAR(50) NOT NULL,
    EMAIL_ADDRESS          VARCHAR(100) UNIQUE NOT NULL,
    
    -- 時間欄位必須使用 UTC+0 時區
    CREATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC'),
    UPDATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC'),
    LAST_LOGIN_TIME        TIMESTAMPTZ,
    EXPIRED_AT             TIMESTAMPTZ,
    
    VERSION                INTEGER DEFAULT 0
);

-- ❌ 錯誤的時間欄位定義
CREATE TABLE TB_USER (
    CREATED_TIME           TIMESTAMP,                    -- 沒有明確指定 UTC
    UPDATE_DATE            DATE,                         -- 使用 DATE 而非 TIMESTAMP
    login_time             TIMESTAMP DEFAULT NOW()       -- 沒有 UTC 轉換
);
```

#### Java 時間處理規範
- **強制使用**: 所有時間處理必須使用 `java.time.Instant` 類
- **禁止使用**: `java.util.Date`, `java.sql.Timestamp`, `LocalDateTime`, `LocalDate`, `LocalTime`, `ZonedDateTime`, `OffsetDateTime` (除非明確需要時區轉換顯示)
- **時區轉換**: 僅在顯示層進行時區轉換，轉換後立即丟棄，不可存儲

```java
// ✅ 正確的時間處理
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "TB_USER")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;
    
    // 使用 Instant 處理所有時間欄位
    @Column(name = "CREATED_AT")
    private Instant createdAt;
    
    @Column(name = "UPDATED_AT")
    private Instant updatedAt;
    
    @Column(name = "LAST_LOGIN_TIME")
    private Instant lastLoginTime;
    
    @Column(name = "EXPIRED_AT")
    private Instant expiredAt;
    
    @Column(name = "DATE_OF_BIRTH")
    private Instant dateOfBirth;        // 即使是日期也使用 Instant
    
    // JPA 生命週期方法
    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();  // 自動獲取 UTC 時間
        createdAt = now;
        updatedAt = now;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();    // 自動更新為 UTC 時間
    }
}

// ✅ 正確的服務層時間處理
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public User createUser(CreateUserCommand command) {
        User user = User.builder()
            .name(command.getName())
            .email(command.getEmail())
            .createdAt(Instant.now())        // 使用 UTC 時間
            .updatedAt(Instant.now())
            .dateOfBirth(command.getDateOfBirth())  // 接收 Instant 類型
            .build();
            
        return userRepository.save(user);
    }
    
    public void updateLastLoginTime(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> UserNotFoundException.withId(userId));
            
        user.setLastLoginTime(Instant.now());  // 記錄 UTC 登入時間
        userRepository.save(user);
    }
    
    // 僅在顯示時進行時區轉換，不可存儲轉換後的結果
    public String formatUserCreatedTime(User user, String timeZone) {
        ZoneId zone = ZoneId.of(timeZone);
        ZonedDateTime zonedTime = user.getCreatedAt().atZone(zone);
        return zonedTime.toString();  // 立即轉換為字串，不保存 ZonedDateTime
    }
    
    // 日期計算也使用 Instant
    public boolean isUserAdult(User user) {
        Instant eighteenYearsAgo = Instant.now().minus(Duration.ofDays(365 * 18));
        return user.getDateOfBirth().isBefore(eighteenYearsAgo);
    }
}

// ❌ 錯誤的時間處理 - 完全禁止
public class UserEntity {
    
    // 禁止使用的所有時間類型
    private Date createdAt;              // 禁止使用 java.util.Date
    private Timestamp updatedAt;         // 禁止使用 java.sql.Timestamp
    private LocalDateTime lastLogin;     // 禁止使用 LocalDateTime
    private LocalDate dateOfBirth;      // 禁止使用 LocalDate
    private LocalTime loginTime;         // 禁止使用 LocalTime
    private ZonedDateTime zonedTime;     // 禁止存儲 ZonedDateTime
    private OffsetDateTime offsetTime;   // 禁止存儲 OffsetDateTime
    private Calendar expiredTime;        // 禁止使用 Calendar
}

// ❌ 錯誤的服務層實現
@Service
public class UserService {
    
    public void wrongTimeHandling() {
        // 禁止的操作
        LocalDateTime now = LocalDateTime.now();     // 禁止
        LocalDate today = LocalDate.now();           // 禁止
        ZonedDateTime zoned = ZonedDateTime.now();   // 禁止存儲
        
        // 正確做法：只使用 Instant
        Instant now = Instant.now();
    }
}
```
#### DTO 時間處理規範
```java
// ✅ 正確的 DTO 時間處理
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
    
    // DTO 中也使用 Instant
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastLoginTime;
    
    // 可選：提供格式化方法供前端使用
    public String getFormattedCreatedAt(String timeZone) {
        if (createdAt == null) return null;
        
        ZoneId zone = ZoneId.of(timeZone);
        return createdAt.atZone(zone).toString();
    }
}

// ✅ 或者提供專門的時間格式化 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormattedUserResponse {
    
    private Long id;
    private String name;
    private String email;
    
    // 原始 UTC 時間
    private Instant createdAt;
    private Instant updatedAt;
    
    // 格式化後的本地時間 (可選)
    private String formattedCreatedAt;
    private String formattedUpdatedAt;
}
```

#### 控制器時間處理規範
```java
// ✅ 正確的控制器時間處理
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "UTC") String timeZone) {
        
        User user = userService.getUserById(id);
        
        // 在控制器層處理時區轉換
        UserResponse response = UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())      // 保持 UTC 時間
            .updatedAt(user.getUpdatedAt())
            .build();
            
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        
        CreateUserCommand command = CreateUserCommand.builder()
            .name(request.getName())
            .email(request.getEmail())
            .requestTime(Instant.now())          // 記錄請求時間 (UTC)
            .build();
            
        User user = userService.createUser(command);
        UserResponse response = mapToResponse(user);
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, "User created successfully"));
    }
}
```

#### 配置規範
```java
// ✅ JPA 配置 - 確保時區處理正確
@Configuration
public class JpaConfig {
    
    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydb");
        config.setUsername("user");
        config.setPassword("password");
        
        // 強制使用 UTC 時區
        config.addDataSourceProperty("serverTimezone", "UTC");
        config.addDataSourceProperty("useTimezone", "true");
        
        return new HikariDataSource(config);
    }
}

// ✅ Jackson 配置 - JSON 序列化時間格式
@Configuration
public class JacksonConfig {
    
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // 註冊 JavaTimeModule 處理 Instant
        mapper.registerModule(new JavaTimeModule());
        
        // 設定 Instant 序列化格式為 ISO-8601 UTC
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        return mapper;
    }
}
```
## 📚 文檔規範

### 1. JavaDoc 規範

```java
/**
 * 使用者領域實體
 * 
 * <p>此類代表系統中的使用者，包含基本的使用者資訊和業務邏輯。
 * 遵循 Domain-Driven Design 原則，保持領域模型的純淨性。</p>
 * 
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
public class User {
    
    /**
     * 創建新的使用者實例
     * 
     * @param name  使用者姓名，不能為空且長度在 2-50 個字符之間
     * @param email 使用者信箱，必須符合標準信箱格式
     * @return 新創建的使用者實例
     * @throws UserValidationException 當輸入參數不符合業務規則時
     */
    public static User create(String name, String email) {
        // 實現邏輯
    }
}
```

### 2. README 文檔結構

每個模組都應該包含 README.md 文檔，說明：

- 模組職責
- 主要類別和介面
- 使用範例
- 注意事項

### 3. API 文檔

使用 OpenAPI 3.0 (Swagger) 自動生成 API 文檔，確保：

- 所有端點都有清楚的描述
- 請求/回應範例完整
- 錯誤碼說明詳細

## ✅ 程式碼檢查清單

### 提交前檢查

- [ ] 程式碼遵循命名規範
- [ ] 已添加必要的 JavaDoc 註解
- [ ] 已編寫相應的單元測試
- [ ] 單元測試覆蓋率達到 80% 以上
- [ ] 異常處理完整且適當
- [ ] API 文檔已更新
- [ ] 無編譯警告或錯誤
- [ ] 遵循 Clean Architecture 原則
- [ ] Mock 物件使用適當

### Code Review 檢查點

- [ ] 業務邏輯清晰且正確
- [ ] 安全性考量充分
- [ ] 性能影響評估
- [ ] 代碼重複性檢查
- [ ] 依賴注入正確使用
- [ ] 異常處理適當
- [ ] 測試案例充分

## 📖 參考資源

- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Domain-Driven Design](https://domainlanguage.com/ddd/)
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Lombok Documentation](https://projectlombok.org/features/all)
- [OpenAPI 3.0 Specification](https://swagger.io/specification/)

---

**最後更新**: 2025年8月31日  
**版本**: 1.0.0  
**維護者**: Nice NPC Team
