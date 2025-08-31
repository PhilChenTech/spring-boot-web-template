package com.nicenpc.domain;

import com.nicenpc.domain.exception.UserValidationException;

import java.time.Instant;
import java.util.Objects;
import java.util.regex.Pattern;

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

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_NAME_LENGTH = 2;

    private Long id;
    private String name;
    private String email;
    private boolean active = true;
    private Instant createdAt;
    private Long createdBy;
    private Instant updatedAt;
    private Long updatedBy;
    private Integer version;

    /**
     * 預設建構函數
     */
    public User() {
    }

    /**
     * 完整建構函數
     *
     * @param id 使用者ID
     * @param name 使用者姓名
     * @param email 使用者信箱
     */
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * 建構函數包含所有欄位
     */
    public User(Long id, String name, String email, boolean active, 
                Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy, Integer version) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.version = version;
    }

    /**
     * 創建新的使用者實例
     *
     * @param name  使用者姓名，不能為空且長度在 2-100 個字符之間
     * @param email 使用者信箱，必須符合標準信箱格式
     * @return 新創建的使用者實例
     * @throws UserValidationException 當輸入參數不符合業務規則時
     */
    public static User create(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.validate();
        return user;
    }

    /**
     * 驗證信箱格式是否正確
     *
     * @return true 如果信箱格式正確，否則返回 false
     */
    public boolean isValidEmail() {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 驗證姓名是否符合業務規則
     *
     * @return true 如果姓名符合規則，否則返回 false
     */
    public boolean isValidName() {
        return name != null
               && !name.trim().isEmpty()
               && name.length() >= MIN_NAME_LENGTH
               && name.length() <= MAX_NAME_LENGTH;
    }

    /**
     * 驗證使用者所有欄位是否符合業務規則
     *
     * @throws UserValidationException 當驗證失敗時拋出異常
     */
    public void validate() {
        if (!isValidName()) {
            throw new UserValidationException(
                String.format("使用者名稱不能為空且長度必須在 %d-%d 個字符之間",
                             MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
        if (!isValidEmail()) {
            throw new UserValidationException("電子郵件格式不正確");
        }
    }

    /**
     * 檢查使用者是否可以接收通知
     *
     * @return true 如果使用者可以接收通知，否則返回 false
     */
    public boolean canReceiveNotifications() {
        return isValidEmail() && isActive();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
               Objects.equals(name, user.name) &&
               Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", active=" + active +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               ", version=" + version +
               '}';
    }
}