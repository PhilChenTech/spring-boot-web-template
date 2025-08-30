package com.nicenpc.application.handler;

import com.nicenpc.application.query.FindByEmailDomainQuery;
import com.nicenpc.domain.User;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindByEmailDomainQueryHandler implements QueryHandler<FindByEmailDomainQuery, List<User>> {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> handle(FindByEmailDomainQuery query) {
        return userRepository.findByEmailDomain(query.getDomain());
    }
}
