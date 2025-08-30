package com.nicenpc.application.handler;

/**
 * 查詢處理器標記介面
 */
public interface QueryHandler<Q, R> {
    R handle(Q query);
}