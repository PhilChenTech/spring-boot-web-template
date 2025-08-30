package com.nicenpc.application.handler;

/**
 * 指令處理器標記介面
 */
public interface CommandHandler<T> {
    void handle(T command);
}