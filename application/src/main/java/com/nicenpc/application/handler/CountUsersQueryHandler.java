package com.nicenpc.application.handler;

import com.nicenpc.application.query.CountUsersQuery;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CountUsersQueryHandler implements QueryHandler<CountUsersQuery, Long> {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Long handle(CountUsersQuery query) {
        return userRepository.count();
    }
}
