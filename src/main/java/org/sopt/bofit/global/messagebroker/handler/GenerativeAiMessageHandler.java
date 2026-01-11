package org.sopt.bofit.global.messagebroker.handler;

import org.sopt.bofit.global.messagebroker.message.GenerativeAiMessage;

public interface GenerativeAiMessageHandler <T extends GenerativeAiMessage> extends MessageHandler<T> {

}
