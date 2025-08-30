package com.nicenpc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用者領域實體
 * 純淨的領域物件，不包含任何基礎設施層的依賴
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
}
