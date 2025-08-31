package com.nicenpc.infrastructure.adapter.outbound.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * 使用者JPA實體
 *
 * <p>對應資料庫中的TB_USER表，用於資料持久化操作。
 * 遵循JPA規範和Clean Architecture原則。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "TB_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    
    @Column(name = "USER_NAME", nullable = false, length = 100)
    private String name;
    
    @Column(name = "USER_EMAIL", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "IS_ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Instant createdAt;
    
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    @Builder.Default
    private Long createdBy = 1L;
    
    @UpdateTimestamp
    @Column(name = "UPDATED_AT", nullable = false)
    private Instant updatedAt;
    
    @Column(name = "UPDATED_BY", nullable = false)
    @Builder.Default
    private Long updatedBy = 1L;
    
    @Version
    @Column(name = "VERSION", nullable = false)
    @Builder.Default
    private Integer version = 0;
}
