package com.nicenpc.application.handler;

import com.nicenpc.application.query.GetUserByEmailQuery;
import com.nicenpc.application.metrics.ApplicationMetrics;
import com.nicenpc.domain.User;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 根據電子郵件查詢使用者處理器
 */
@Component
@RequiredArgsConstructor
public class GetUserByEmailQueryHandler implements QueryHandler<GetUserByEmailQuery, User> {
    
    private final UserRepository userRepository;
    private final ApplicationMetrics applicationMetrics;
    
    @Override
    @Cacheable(value = "users", key = "'email:' + #p0.email", unless = "#result == null")
    @Transactional(readOnly = true)
    public User handle(GetUserByEmailQuery query) {
        applicationMetrics.incrementUserQuery();
        return userRepository.findByEmail(query.getEmail()).orElse(null);
    }
}