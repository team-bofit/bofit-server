package org.sopt.bofit.global.outbox.Repository;

import java.util.List;
import org.sopt.bofit.global.outbox.entity.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, String> {

    List<OutboxMessage> findAllByLastAttemptAtIsNull();
}
