# Lombok 整合指南 - 乾淨架構 DDD 實作

本文檔說明如何在遵循乾淨架構 (Clean Architecture) 和 領域驅動設計 (DDD) 原則的前提下，適當地導入和使用 Lombok。

## 📋 目錄
- [專案結構概覽](#專案結構概覽)
- [Lombok 配置](#lombok-配置)
- [各層級的 Lombok 應用](#各層級的-lombok-應用)
- [最佳實踐](#最佳實踐)
- [注意事項](#注意事項)

## 🏗️ 專案結構概覽

```
springboot-web-template/
├── domain/               # 領域層 (Domain Layer)
├── application/          # 應用層 (Application Layer)  
├── adapter-inbound/      # 入站適配器層 (Inbound Adapters)
├── adapter-outbound/     # 出站適配器層 (Outbound Adapters)
└── common/              # 共用模組 (Common)
```

## ⚙️ Lombok 配置

### 根目錄 build.gradle 配置

在 `build.gradle` 的 `subprojects` 區塊中添加了 Lombok 依賴：

```gradle
subprojects {
    // ... 其他配置
    
    dependencies {
        // Lombok 依賴 - 在所有子專案中可用
        compileOnly 'org.projectlombok:lombok'
        annotationProcessor 'org.projectlombok:lombok'
        
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
        testCompileOnly 'org.projectlombok:lombok'
        testAnnotationProcessor 'org.projectlombok:lombok'
    }
}
```

## 📦 各層級的 Lombok 應用

### 1. Domain Layer (領域層)

**文件**: `domain/src/main/java/com/nicenpc/domain/User.java`

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
}
```

**應用的 Lombok 註解**:
- `@Data`: 自動生成 getter、setter、equals、hashCode、toString
- `@NoArgsConstructor`: 生成無參構造函數
- `@AllArgsConstructor`: 生成全參構造函數

**原則**: 
- 領域實體保持純淨，不依賴任何基礎設施
- 使用 Lombok 減少樣板代碼，讓業務邏輯更清晰

### 2. Application Layer (應用層)

**文件**: `application/src/main/java/com/nicenpc/application/UserService.java`

```java
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    // 業務邏輯方法...
}
```

**應用的 Lombok 註解**:
- `@RequiredArgsConstructor`: 自動生成 final 欄位的構造函數

**原則**:
- 替代 `@Autowired` 構造函數注入
- 遵循依賴注入最佳實踐
- 確保依賴的不可變性

### 3. Adapter-Inbound Layer (入站適配器層)

#### 控制器類

**文件**: `adapter-inbound/src/main/java/com/nicenpc/adapterinbound/controller/UserController.java`

```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    // REST 端點方法...
}
```

#### DTO 類

**文件**: `adapter-inbound/src/main/java/com/nicenpc/adapterinbound/dto/CreateUserRequest.java`

```java
@Data
public class CreateUserRequest {
    
    @NotBlank(message = "姓名不能為空")
    private String name;
    
    @NotBlank(message = "Email 不能為空")
    @Email(message = "Email 格式不正確")
    private String email;
}
```

**文件**: `adapter-inbound/src/main/java/com/nicenpc/adapterinbound/dto/UserResponse.java`

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
}
```

**應用的 Lombok 註解**:
- `@RequiredArgsConstructor`: 控制器的依賴注入
- `@Data`: DTO 的 getter/setter 自動生成
- `@NoArgsConstructor` / `@AllArgsConstructor`: DTO 的構造函數

**原則**:
- DTO 用於外部接口的數據傳輸
- 與領域模型解耦
- 支持 Bean Validation

### 4. Adapter-Outbound Layer (出站適配器層)

#### JPA 實體

**文件**: `adapter-outbound/src/main/java/com/nicenpc/adapteroutbound/entity/UserEntity.java`

```java
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

#### 儲存庫實作

**文件**: `adapter-outbound/src/main/java/com/nicenpc/adapteroutbound/repository/UserRepositoryImpl.java`

```java
@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    
    // 實作方法...
}
```

#### 映射器類

**文件**: `adapter-outbound/src/main/java/com/nicenpc/adapteroutbound/mapper/UserMapper.java`

```java
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

**應用的 Lombok 註解**:
- `@Data`: JPA 實體的 getter/setter
- `@RequiredArgsConstructor`: 儲存庫的依賴注入
- `@UtilityClass`: 工具類，防止實例化

**原則**:
- JPA 實體與領域實體分離
- 映射器負責兩者之間的轉換
- 遵循基礎設施層的實作細節

## 🎯 最佳實踐

### 1. 依賴注入優化
```java
// ✅ 推薦：使用 @RequiredArgsConstructor
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}

// ❌ 避免：使用 @Autowired 欄位注入
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
```

### 2. 實體類設計
```java
// ✅ 推薦：領域實體使用 @Data + 構造函數註解
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
}

// ✅ 推薦：JPA 實體使用相同模式
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    // JPA 註解...
}
```

### 3. 工具類設計
```java
// ✅ 推薦：使用 @UtilityClass
@UtilityClass
public class UserMapper {
    public static User toDomain(UserEntity entity) {
        // 映射邏輯
    }
}
```

## ⚠️ 注意事項

### 1. 層級邊界維護
- **領域層**: 只使用基本的 Lombok 註解，不引入任何基礎設施依賴
- **應用層**: 專注於業務邏輯，使用 Lombok 簡化依賴注入
- **適配器層**: 可以充分利用 Lombok 功能，但要注意與外部系統的兼容性

### 2. 性能考量
- `@Data` 包含 `equals()` 和 `hashCode()`，在 JPA 實體中要小心使用
- 考慮在必要時使用 `@EqualsAndHashCode(exclude = {"field"})` 排除特定欄位

### 3. 測試友好性
- 構造函數注入使單元測試更容易
- `@AllArgsConstructor` 有助於測試數據的建立

### 4. IDE 支援
- 確保 IDE 安裝了 Lombok 插件
- 在建置時確保 Lombok 註解處理器正確配置

## 🚀 建置和執行

```bash
# 清潔建置
./gradlew clean build -x test

# 運行應用程式
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## 📝 總結

通過適當使用 Lombok，我們成功地：

1. **減少了樣板代碼**: 消除了大量的 getter、setter、構造函數
2. **提高了代碼可讀性**: 讓業務邏輯更加突出
3. **維護了架構純淨性**: 每一層都遵循乾淨架構原則
4. **改善了依賴注入**: 使用構造函數注入替代欄位注入
5. **保持了測試友好性**: 便於單元測試的編寫

這種實作方式確保了在享受 Lombok 帶來的便利的同時，依然遵循乾淨架構和 DDD 的核心原則。
