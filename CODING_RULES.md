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
├── adapter/              # 適配器層
│   ├── inbound/          # 入站適配器
│   │   ├── controller/   # REST 控制器
│   │   ├── dto/          # 資料傳輸物件
│   │   ├── mapper/       # DTO 映射器
│   │   └── exception/    # 全域異常處理
│   └── outbound/         # 出站適配器
│       ├── entity/       # JPA 實體
│       ├── repository/   # 倉庫實現
│       └── mapper/       # 實體映射器
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

### 2. Java Record 使用規範

#### 適用場景

Java Record (Java 14+) 適合用於不可變的資料載體類別，特別適用於以下場景：

```java
// ✅ 推薦使用 Record 的場景

// 1. DTO/VO 類別 - 資料傳輸物件
public record CreateUserRequest(
    @NotBlank(message = "姓名不能為空") String name,
    @Email(message = "信箱格式不正確") String email
) {}

public record UserResponse(
    Long id,
    String name,
    String email,
    Instant createdAt
) {}

// 2. 值物件 (Value Objects) - 領域層
public record Money(
    BigDecimal amount,
    Currency currency
) {
    public Money {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        Objects.requireNonNull(currency, "Currency cannot be null");
    }
    
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
}

// 3. 配置類別
public record DatabaseConfig(
    String url,
    String username,
    String password,
    int maxConnections
) {}

// 4. API 回應包裝
public record ApiResult<T>(
    boolean success,
    String message,
    T data,
    Instant timestamp
) {
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, "Success", data, Instant.now());
    }
    
    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(false, message, null, Instant.now());
    }
}

// 5. 查詢結果投影
public record UserSummary(
    Long id,
    String name,
    String email,
    boolean isActive
) {}

// 6. 事件物件 (領域事件)
public record UserCreatedEvent(
    Long userId,
    String email,
    Instant occurredAt
) {}

// 7. 指令物件 (CQRS Commands)
public record CreateUserCommand(
    String name,
    String email,
    String password
) {}
```

#### 不適用場景

```java
// ❌ 避免使用 Record 的場景

// 1. JPA 實體類別 - 需要可變性和 JPA 註解支援
@Entity
@Table(name = "TB_USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    // JPA 需要預設構造函數和 setter
    public UserEntity() {}
    
    // getters and setters...
}

// 2. 業務邏輯複雜的領域實體
public class User {
    private Long id;
    private String name;
    private String email;
    private UserStatus status;
    
    // 複雜的業務邏輯方法
    public void activate() {
        if (this.status == UserStatus.BANNED) {
            throw new UserCannotBeActivatedException();
        }
        this.status = UserStatus.ACTIVE;
    }
    
    public void changeEmail(String newEmail) {
        validateEmailFormat(newEmail);
        this.email = newEmail;
        // 發送事件等複雜邏輯
    }
}

// 3. 需要繼承的類別 - Record 不支援繼承
public abstract class BaseEntity {
    protected Long id;
    protected Instant createdAt;
    // ...
}

// 4. 需要可變狀態的服務類別
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    // ...
}
```

#### Record 最佳實踐

```java
// ✅ Record 最佳實踐

// 1. 使用 compact constructor 進行驗證
public record EmailAddress(String value) {
    public EmailAddress {
        Objects.requireNonNull(value, "Email cannot be null");
        if (!isValidEmail(value)) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
    }
    
    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}

// 2. 添加有用的工廠方法
public record UserFilter(
    String name,
    String email,
    Boolean isActive,
    Instant createdAfter,
    Instant createdBefore
) {
    public static UserFilter empty() {
        return new UserFilter(null, null, null, null, null);
    }
    
    public static UserFilter byEmail(String email) {
        return new UserFilter(null, email, null, null, null);
    }
    
    public static UserFilter activeUsers() {
        return new UserFilter(null, null, true, null, null);
    }
}

// 3. 實現有用的方法
public record Point(double x, double y) {
    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
    
    public Point translate(double dx, double dy) {
        return new Point(this.x + dx, this.y + dy);
    }
}

// 4. 與 Bean Validation 結合使用
public record CreateProductRequest(
    @NotBlank(message = "產品名稱不能為空")
    @Size(max = 100, message = "產品名稱不能超過100個字符")
    String name,
    
    @NotNull(message = "價格不能為空")
    @DecimalMin(value = "0.0", inclusive = false, message = "價格必須大於0")
    BigDecimal price,
    
    @Size(max = 500, message = "描述不能超過500個字符")
    String description
) {}
```

#### Record vs 傳統類別選擇指南

```java
// 決策流程圖：

// 問題1：這個類別主要用途是什麼？
// - 純資料傳輸/載體 → 考慮 Record
// - 包含複雜業務邏輯 → 使用傳統類別

// 問題2：資料是否需要可變性？
// - 不可變 → 傾向 Record
// - 需要修改狀態 → 使用傳統類別

// 問題3：是否需要繼承？
// - 需要繼承或被繼承 → 使用傳統類別
// - 不需要 → 可考慮 Record

// 問題4：是否為 JPA 實體？
// - 是 → 使用傳統類別
// - 否 → 可考慮 Record

// ✅ 使用 Record 的典型模式
public record PageRequest(int page, int size, String sortBy, String sortDirection) {
    public PageRequest {
        if (page < 0) throw new IllegalArgumentException("Page must be >= 0");
        if (size <= 0) throw new IllegalArgumentException("Size must be > 0");
    }
    
    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, "id", "ASC");
    }
}

// ✅ 使用傳統類別的典型模式
@Entity
@Table(name = "TB_ORDER")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    public void confirm() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be confirmed");
        }
        this.status = OrderStatus.CONFIRMED;
    }
}
```

#### 常見錯誤避免

```java
// ❌ 錯誤：在 Record 中嘗試添加可變字段
public record BadRecord(String name) {
    // private String mutableField; // 編譯錯誤！Record 不允許額外字段
}

// ❌ 錯誤：在 Record 中嘗試繼承
// public record BadRecord(String name) extends SomeClass {} // 編譯錯誤！

// ❌ 錯誤：過度使用 Record 作為 JPA 實體
// @Entity
// public record UserRecord(Long id, String name) {} // 不推薦！

// ✅ 正確：適當的 Record 使用
public record SearchCriteria(
    String keyword,
    List<String> categories,
    DateRange dateRange
) {
    public SearchCriteria {
        categories = categories != null ? List.copyOf(categories) : List.of();
    }
}
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

    public static <T> ApiResponse<T> errorWithData(String message, T data) {
        return new ApiResponse<>(false, message, data, Instant.now());
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
            .body(ApiResponse.errorWithData("Validation failed", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("An unexpected error occurred"));
    }
}
```

## 🧪 測試規範

### 1. 測試分層結構

```java
// ✅ 單元測試 - Domain Layer
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("應該成功創建使用者")
    void shouldCreateUserSuccessfully() {
        // Given
        String name = "John Doe";
        String email = "john@example.com";
        User expectedUser = new User(name, email);
        
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // When
        User actualUser = userService.createUser(name, email);

        // Then
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getName()).isEqualTo(name);
        assertThat(actualUser.getEmail()).isEqualTo(email);
    }
}

// ✅ 整合測試 - Repository Layer
@DataJpaTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb"
})
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserJpaRepository userRepository;

    @Test
    @DisplayName("應該根據信箱查詢到使用者")
    void shouldFindUserByEmail() {
        // Given
        UserEntity user = new UserEntity();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        entityManager.persistAndFlush(user);

        // When
        Optional<UserEntity> foundUser = userRepository.findByEmail("john@example.com");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John Doe");
    }
}

// ✅ API 測試 - Controller Layer
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("應該成功創建使用者並回傳201狀態碼")
    void shouldCreateUserAndReturn201() {
        // Given
        CreateUserRequest request = new CreateUserRequest("John Doe", "john@example.com");

        // When
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
            "/api/v1/users", request, ApiResponse.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().isSuccess()).isTrue();
    }
}
```

### 2. 測試命名規範

```java
// ✅ 正確的測試方法命名
@Test
@DisplayName("當提供有效的使用者資料時，應該成功創建使用者")
void shouldCreateUser_WhenValidUserDataProvided() { }

@Test
@DisplayName("當信箱已存在時，應該拋出 UserAlreadyExistsException")
void shouldThrowUserAlreadyExistsException_WhenEmailAlreadyExists() { }

@Test
@DisplayName("當使用者ID不存在時，應該拋出 UserNotFoundException")
void shouldThrowUserNotFoundException_WhenUserIdNotExists() { }

// ❌ 錯誤的測試方法命名
@Test
void test1() { }  // 不描述測試內容

@Test
void createUser() { }  // 不描述預期結果

@Test
void testCreateUserWithInvalidEmail() { }  // 缺少預期行為
```

### 3. 測試覆蓋率要求

- **Domain Layer**: 90% 以上
- **Application Layer**: 85% 以上
- **Adapter Layer**: 80% 以上
- **整體專案**: 85% 以上

## 📚 文檔規範

### 1. API 文檔規範

```java
// ✅ 完整的 API 文檔
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "使用者管理相關 API")
public class UserController {

    @Operation(
        summary = "創建新使用者",
        description = "根據提供的姓名和信箱創建一個新的使用者帳號"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "使用者創建成功",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "請求參數驗證失敗",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "信箱地址已被使用",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
        @Parameter(description = "使用者創建請求資料", required = true)
        @Valid @RequestBody CreateUserRequest request
    ) {
        // 實作邏輯
    }
}
```

### 2. README 文檔規範

每個模組都應包含詳細的 README.md 文件，包含：

- 模組目的和職責
- 依賴關係說明
- 設定和使用方式
- 範例程式碼
- 故障排除指南

### 3. 程式碼註釋規範

```java
/**
 * 使用者服務類
 * 
 * <p>負責處理使用者相關的業務邏輯，包括使用者的創建、查詢、更新和刪除操作。
 * 所有操作都會進行適當的權限檢查和資料驗證。</p>
 * 
 * <p>使用範例：</p>
 * <pre>{@code
 * UserService userService = new UserService(userRepository);
 * User user = userService.createUser("John Doe", "john@example.com");
 * }</pre>
 * 
 * @author Nice NPC Team
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class UserService {
    
    /**
     * 根據使用者 ID 查詢使用者資訊
     * 
     * @param userId 使用者的唯一識別碼，不能為 null
     * @return 使用者資訊物件
     * @throws IllegalArgumentException 當 userId 為 null 時
     * @throws UserNotFoundException 當指定 ID 的使用者不存在時
     */
    public User getUserById(Long userId) {
        // 實作邏輯
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
-- ✅ 正確的欄位命名 (修正版本)
CREATE TABLE TB_USER (
    USER_ID                BIGSERIAL PRIMARY KEY,       -- 使用者ID
    USER_NAME              VARCHAR(50) NOT NULL,        -- 使用者姓名
    EMAIL_ADDRESS          VARCHAR(100) UNIQUE,         -- 信箱地址
    PASSWORD_HASH          VARCHAR(255) NOT NULL,       -- 密碼雜湊
    PHONE_NUMBER           VARCHAR(20),                 -- 電話號碼
    DATE_OF_BIRTH          TIMESTAMPTZ,                 -- 出生日期 (修正為 TIMESTAMPTZ)
    IS_ACTIVE              BOOLEAN DEFAULT TRUE,        -- 是否啟用
    IS_EMAIL_VERIFIED      BOOLEAN DEFAULT FALSE,       -- 信箱是否驗證
    LAST_LOGIN_TIME        TIMESTAMPTZ,                 -- 最後登入時間
    CREATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC'),
    CREATED_BY             BIGINT,                      -- 建立者ID
    UPDATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC'),
    UPDATED_BY             BIGINT,                      -- 更新者ID
    VERSION                INTEGER DEFAULT 0            -- 版本號（樂觀鎖）
);
```

### 3. 資料類型規範

#### 常用資料類型對應
| 用途 | PostgreSQL 類型 | 說明 |
|------|----------------|------|
| 主鍵 | `BIGSERIAL` | 自增長整數 |
| 外鍵 | `BIGINT` | 64位元整數 |
| 短文字 | `VARCHAR(n)` | 可變長度字串 |
| 長文字 | `TEXT` | 不限長度文字 |
| 布林值 | `BOOLEAN` | 真/假值 |
| 時間 | `TIMESTAMPTZ` | 帶時區時間戳 |
| 金額 | `DECIMAL(19,4)` | 高精度小數 |
| JSON | `JSONB` | 二進制JSON格式 |

### 4. 索引設計規範

```sql
-- ✅ 正確的索引設計
-- 單欄位索引
CREATE INDEX IDX_USER_EMAIL ON TB_USER(EMAIL_ADDRESS);
CREATE INDEX IDX_USER_CREATED_AT ON TB_USER(CREATED_AT);

-- 複合索引
CREATE INDEX IDX_USER_STATUS_CREATED ON TB_USER(IS_ACTIVE, CREATED_AT);

-- 唯一索引
CREATE UNIQUE INDEX UNQ_USER_EMAIL ON TB_USER(EMAIL_ADDRESS) WHERE IS_ACTIVE = TRUE;

-- 部分索引
CREATE INDEX IDX_USER_ACTIVE ON TB_USER(USER_ID) WHERE IS_ACTIVE = TRUE;
```

### 5. 外鍵約束規範

```sql
-- ✅ 正確的外鍵定義
ALTER TABLE TB_ORDER 
ADD CONSTRAINT FK_ORDER_USER 
FOREIGN KEY (USER_ID) REFERENCES TB_USER(USER_ID)
ON DELETE RESTRICT 
ON UPDATE CASCADE;

-- 命名規範：FK_{子表}_{父表}
-- 或 FK_{子表}_{欄位名稱}
```

### 6. 檢查約束規範

```sql
-- ✅ 資料驗證約束
ALTER TABLE TB_USER 
ADD CONSTRAINT CHK_USER_EMAIL_FORMAT 
CHECK (EMAIL_ADDRESS ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$');

ALTER TABLE TB_ORDER 
ADD CONSTRAINT CHK_ORDER_AMOUNT_POSITIVE 
CHECK (TOTAL_AMOUNT > 0);
```

### 7. 審計欄位規範

```sql
-- ✅ 標準審計欄位 (所有表格必須包含)
CREATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC') NOT NULL,
CREATED_BY             BIGINT NOT NULL,
UPDATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC') NOT NULL,
UPDATED_BY             BIGINT NOT NULL,
VERSION                INTEGER DEFAULT 0 NOT NULL,

-- 軟刪除支援 (可選)
IS_DELETED             BOOLEAN DEFAULT FALSE NOT NULL,
DELETED_AT             TIMESTAMPTZ,
DELETED_BY             BIGINT
```

### 8. 時間處理規範

#### 數據庫時間規範
- **時區**: 所有時間欄位必須使用 **UTC+0** 時區儲存
- **資料類型**: 使用 `TIMESTAMPTZ` (PostgreSQL 推薦) 或 `TIMESTAMP`
- **預設值**: 使用 `NOW()` 或 `CURRENT_TIMESTAMP` 設定預設時間
- **PostgreSQL 強制規範**: 必須使用 `TIMESTAMPTZ` 確保時區處理正確

```sql
-- ✅ 正確的時間欄位定義 (PostgreSQL)
CREATE TABLE TB_USER (
    USER_ID                BIGSERIAL PRIMARY KEY,
    USER_NAME              VARCHAR(50) NOT NULL,
    EMAIL_ADDRESS          VARCHAR(100) NOT NULL,
    
    -- PostgreSQL 必須使用 TIMESTAMPTZ 類型
    CREATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC'),
    UPDATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC'),
    LAST_LOGIN_TIME        TIMESTAMPTZ,
    EXPIRED_AT             TIMESTAMPTZ,
    DATE_OF_BIRTH          TIMESTAMPTZ,        -- 即使是日期也使用 TIMESTAMPTZ

    VERSION                INTEGER DEFAULT 0
);

-- ✅ 其他時間欄位範例
CREATE TABLE TB_ORDER (
    ORDER_ID               BIGSERIAL PRIMARY KEY,
    USER_ID                BIGINT NOT NULL,

    -- 所有時間相關欄位都使用 TIMESTAMPTZ
    ORDER_DATE             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC'),
    DELIVERY_DATE          TIMESTAMPTZ,
    CANCELLED_AT           TIMESTAMPTZ,
    PAYMENT_TIME           TIMESTAMPTZ,

    FOREIGN KEY (USER_ID) REFERENCES TB_USER(USER_ID)
);

-- ❌ 錯誤的時間欄位定義
CREATE TABLE TB_USER (
    CREATED_TIME           TIMESTAMP,                    -- 禁止：沒有時區資訊
    UPDATE_DATE            DATE,                         -- 禁止：只有日期，缺少時間
    LOGIN_TIME             TIME,                         -- 禁止：只有時間，沒有日期
    BIRTH_DATE             DATE,                         -- 禁止：即使是日期也要用 TIMESTAMPTZ
    TIMESTAMP_FIELD        TIMESTAMP WITHOUT TIME ZONE   -- 禁止：明確排除時區
);
```

#### PostgreSQL 時間類型規範
| 類型 | 使用規範 | 說明 |
|------|----------|------|
| `TIMESTAMPTZ` | ✅ **強制使用** | 包含時區資訊，自動轉換為 UTC 儲存 |
| `TIMESTAMP` | ❌ **禁止使用** | 沒有時區資訊，容易產生混亂 |
| `DATE` | ❌ **禁止使用** | 只有日期，缺少時間精度 |
| `TIME` | ❌ **禁止使用** | 只有時間，沒有日期資訊 |
| `TIMESTAMP WITHOUT TIME ZONE` | ❌ **禁止使用** | 明確排除時區處理 |

### 9. 資料庫版本控制規範

#### Flyway 遷移腳本命名
```
V{版本號}__{描述}.sql

範例：
V1__Create_users_table.sql
V2__Add_user_email_index.sql
V3__Alter_user_add_phone_column.sql
V4__Insert_default_admin_user.sql
```

#### 遷移腳本最佳實踐
```sql
-- ✅ 正確的遷移腳本結構
-- V1__Create_users_table.sql

-- 表格創建
CREATE TABLE TB_USER (
    USER_ID                BIGSERIAL PRIMARY KEY,
    USER_NAME              VARCHAR(50) NOT NULL,
    EMAIL_ADDRESS          VARCHAR(100) NOT NULL,
    
    -- 審計欄位
    CREATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC') NOT NULL,
    CREATED_BY             BIGINT NOT NULL,
    UPDATED_AT             TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'UTC') NOT NULL,
    UPDATED_BY             BIGINT NOT NULL,
    VERSION                INTEGER DEFAULT 0 NOT NULL
);

-- 索引創建
CREATE UNIQUE INDEX UNQ_USER_EMAIL ON TB_USER(EMAIL_ADDRESS);
CREATE INDEX IDX_USER_CREATED_AT ON TB_USER(CREATED_AT);

-- 註釋添加
COMMENT ON TABLE TB_USER IS '使用者資料表';
COMMENT ON COLUMN TB_USER.USER_ID IS '使用者唯一識別碼';
COMMENT ON COLUMN TB_USER.USER_NAME IS '使用者姓名';
COMMENT ON COLUMN TB_USER.EMAIL_ADDRESS IS '使用者信箱地址';
```
