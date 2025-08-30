package com.nicenpc.application.query;

/**
 * 根據電子郵件查詢使用者是否存在
 */
public class ExistsByEmailQuery {

    private final String email;

    public ExistsByEmailQuery(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
