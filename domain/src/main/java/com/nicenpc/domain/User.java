package com.nicenpc.domain;

import com.nicenpc.domain.exception.UserValidationException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 使用者領域實體
 * 純淨的領域物件，不包含任何基礎設施層的依賴
 */
public class User {
    private Long id;
    private String name;
    private String email;
    private boolean active = true; // Add active field for business rule

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    public User() {
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static User create(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.validate();
        return user;
    }

    // Domain validation methods
    public boolean isValidEmail() {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidName() {
        return name != null && !name.trim().isEmpty() && name.length() <= 100;
    }

    public void validate() {
        if (!isValidName()) {
            throw new UserValidationException("使用者名稱不能為空且長度不得超過100字元");
        }
        if (!isValidEmail()) {
            throw new UserValidationException("電子郵件格式不正確");
        }
    }

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
        return "User{"
               + "id=" + id +
               ", name='" + name + "'" +
               ", email='" + email + "'" +
               '}';
    }
}