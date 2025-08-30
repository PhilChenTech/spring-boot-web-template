package com.nicenpc.application.handler;

import com.nicenpc.application.query.ExistsByEmailQuery;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ExistsByEmailQueryHandler implements QueryHandler<ExistsByEmailQuery, Boolean> {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean handle(ExistsByEmailQuery query) {
        return userRepository.existsByEmail(query.getEmail());
    }
}
