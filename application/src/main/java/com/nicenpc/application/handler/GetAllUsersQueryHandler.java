package com.nicenpc.application.handler;

import com.nicenpc.application.query.GetAllUsersQuery;
import com.nicenpc.application.metrics.ApplicationMetrics;
import com.nicenpc.domain.User;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 查詢所有使用者處理器
 */
@Component
@RequiredArgsConstructor
public class GetAllUsersQueryHandler implements QueryHandler<GetAllUsersQuery, List<User>> {
    
    private final UserRepository userRepository;
    private final ApplicationMetrics applicationMetrics;
    
    @Override
    @Transactional(readOnly = true)
    public List<User> handle(GetAllUsersQuery query) {
        applicationMetrics.incrementUserQuery();
        List<User> users = userRepository.findAll();
        applicationMetrics.setActiveUsers(users.size());
        return users;
    }
}