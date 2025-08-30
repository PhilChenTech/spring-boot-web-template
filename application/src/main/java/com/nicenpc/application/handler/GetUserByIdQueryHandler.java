package com.nicenpc.application.handler;

import com.nicenpc.application.query.GetUserByIdQuery;
import com.nicenpc.application.metrics.ApplicationMetrics;
import com.nicenpc.domain.User;
import com.nicenpc.domain.exception.UserNotFoundException;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 根據ID查詢使用者處理器
 */
@Component
@RequiredArgsConstructor
public class GetUserByIdQueryHandler implements QueryHandler<GetUserByIdQuery, User> {
    
    private final UserRepository userRepository;
    private final ApplicationMetrics applicationMetrics;
    
    @Override
    @Cacheable(value = "users", key = "'id:' + #p0.userId", unless = "#result == null")
    @Transactional(readOnly = true)
    public User handle(GetUserByIdQuery query) {
        applicationMetrics.incrementUserQuery();
        return userRepository.findById(query.getUserId())
                .orElseThrow(() -> new UserNotFoundException(query.getUserId()));
    }
}