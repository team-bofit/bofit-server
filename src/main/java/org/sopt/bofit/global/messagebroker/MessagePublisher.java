package org.sopt.bofit.global.messagebroker;

import org.sopt.bofit.global.messagebroker.message.Message;

public interface MessagePublisher {

    void publish(Message message);
}
