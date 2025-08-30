# MapStruct 整合指南

本專案已整合 MapStruct 程式碼生成器來自動處理物件映射，取代手動撰寫映射程式碼。

## 什麼是 MapStruct？

MapStruct 是一個程式碼生成器，它根據註解處理器來簡化 Java Bean 類型之間的映射。它會在編譯時生成映射實作程式碼，具有高效能和類型安全的特性。

## 專案中的 MapStruct 配置

### 1. Gradle 依賴配置

在根專案的 `build.gradle` 中已添加以下依賴：

```gradle
// MapStruct 依賴
implementation 'org.mapstruct:mapstruct:1.5.5.Final'
annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'

// MapStruct 與 Lombok 整合配置
annotationProcessor 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
```

### 2. 全域配置

在 `common` 模組中定義了 `MapStructConfig` 介面，統一配置所有 Mapper：

```java
@MapperConfig(
    componentModel = "default",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface MapStructConfig {
}
```

## 現有的 Mapper

### 1. UserMapper (adapter-outbound)

負責 Domain 實體與 JPA 實體之間的轉換：

```java
@Mapper(config = MapStructConfig.class)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    User toDomain(UserEntity entity);
    UserEntity toEntity(User domain);
}
```

**使用方式：**
```java
// Entity 轉 Domain
User user = UserMapper.INSTANCE.toDomain(userEntity);

// Domain 轉 Entity
UserEntity entity = UserMapper.INSTANCE.toEntity(user);
```

### 2. UserDTOMapper (adapter-inbound)

負責 Domain 實體與 DTO 之間的轉換：

```java
@Mapper(config = MapStructConfig.class)
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);
    
    UserResponse toResponse(User user);
    
    @Mapping(target = "id", ignore = true)
    User toDomain(CreateUserRequest request);
}
```

**使用方式：**
```java
// Domain 轉 Response DTO
UserResponse response = UserDTOMapper.INSTANCE.toResponse(user);

// Request DTO 轉 Domain (id 會被忽略)
User user = UserDTOMapper.INSTANCE.toDomain(createRequest);
```

## 建立新 Mapper 的指南

### 1. 基本 Mapper

```java
@Mapper(config = MapStructConfig.class)
public interface YourMapper {
    YourMapper INSTANCE = Mappers.getMapper(YourMapper.class);
    
    TargetClass toTarget(SourceClass source);
    SourceClass toSource(TargetClass target);
}
```

### 2. 複雜映射

```java
@Mapper(config = MapStructConfig.class)
public interface ComplexMapper {
    ComplexMapper INSTANCE = Mappers.getMapper(ComplexMapper.class);
    
    // 自訂欄位映射
    @Mapping(source = "fullName", target = "name")
    @Mapping(source = "emailAddress", target = "email")
    TargetClass toTarget(SourceClass source);
    
    // 忽略特定欄位
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    TargetClass toTargetWithoutId(SourceClass source);
    
    // 使用自訂方法
    @Mapping(source = "status", target = "active", qualifiedByName = "statusToActive")
    TargetClass toTargetWithCustomMapping(SourceClass source);
    
    @Named("statusToActive")
    default boolean statusToActive(String status) {
        return "ACTIVE".equals(status);
    }
}
```

### 3. 集合映射

```java
@Mapper(config = MapStructConfig.class)
public interface CollectionMapper {
    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);
    
    List<TargetClass> toTargetList(List<SourceClass> sourceList);
    Set<TargetClass> toTargetSet(Set<SourceClass> sourceSet);
}
```

## 與 Spring 整合

如果需要在 Mapper 中注入 Spring Bean，可以使用 `componentModel = "spring"`：

```java
@Mapper(config = MapStructConfig.class, componentModel = "spring")
public interface SpringMapper {
    
    @Mapping(source = "userId", target = "user", qualifiedByName = "userIdToUser")
    TargetClass toTarget(SourceClass source);
    
    @Named("userIdToUser")
    default User userIdToUser(Long userId) {
        // 這裡可以注入 UserService 或其他 Spring Bean
        return userService.findById(userId);
    }
}
```

然後在使用時直接注入：

```java
@Service
public class SomeService {
    private final SpringMapper mapper;
    
    public SomeService(SpringMapper mapper) {
        this.mapper = mapper;
    }
}
```

## 編譯時程式碼生成

MapStruct 在編譯時會在 `build/generated/sources/annotationProcessor` 目錄下生成實際的映射實作程式碼。這些生成的類別是純 Java 程式碼，沒有反射，因此具有優秀的效能。

## 最佳實踐

1. **統一配置**：使用 `MapStructConfig` 統一配置所有 Mapper
2. **明確映射**：當欄位名稱不同時，使用 `@Mapping` 明確指定映射關係
3. **忽略不需要的欄位**：使用 `@Mapping(target = "field", ignore = true)` 忽略不需要映射的欄位
4. **類型安全**：MapStruct 提供編譯時類型檢查，確保映射的正確性
5. **效能優化**：生成的程式碼是純 Java 程式碼，無反射開銷

## 故障排除

1. **編譯錯誤**：確保所有必要的欄位都有對應的映射規則
2. **找不到 Mapper**：確保 `annotationProcessor` 依賴已正確配置
3. **Lombok 衝突**：已配置 `lombok-mapstruct-binding` 解決相容性問題

## 更多資源

- [MapStruct 官方文檔](https://mapstruct.org/)
- [MapStruct 參考指南](https://mapstruct.org/documentation/stable/reference/html/)
