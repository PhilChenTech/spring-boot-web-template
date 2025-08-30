package com.nicenpc.domain;

import com.nicenpc.domain.exception.UserValidationException;
import java.util.Objects;

/**
 * 使用者領域實體
 * 純淨的領域物件，不包含任何基礎設施層的依賴
 */
public class User {
    private Long id;
    private String name;
    private String email;
    
    public User() {
    }
    
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // Domain validation methods
    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.length() > 3;
    }
    
    public boolean isValidName() {
        return name != null && !name.trim().isEmpty() && name.length() <= 100;
    }
    
    public void validate() {
        if (!isValidName()) {
            throw new UserValidationException("使用者名稱不能為空且長度不得超過100字元");
        }
        if (!isValidEmail()) {
            throw new UserValidationException("電子郵件格式不正確，必須包含@符號且長度大於3字元");
        }
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
               '}';
    }
}
