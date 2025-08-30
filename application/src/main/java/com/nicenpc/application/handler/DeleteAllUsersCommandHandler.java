package com.nicenpc.application.handler;

import com.nicenpc.application.command.DeleteAllUsersCommand;
import com.nicenpc.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteAllUsersCommandHandler implements CommandHandler<DeleteAllUsersCommand> {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void handle(DeleteAllUsersCommand command) {
        userRepository.deleteAll();
    }
}
