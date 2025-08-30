package com.nicenpc.application.bus;

import com.nicenpc.application.handler.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 查詢匯流排
 * 負責將查詢路由到對應的處理器
 */
@Component
public class QueryBus {
    
    private static final Logger log = LoggerFactory.getLogger(QueryBus.class);
    
    private final ApplicationContext applicationContext;
    private final Map<Class<?>, QueryHandler<?, ?>> handlerCache = new ConcurrentHashMap<>();
    
    public QueryBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    @SuppressWarnings("unchecked")
    public <Q, R> R send(Q query) {
        QueryHandler<Q, R> handler = (QueryHandler<Q, R>) handlerCache.computeIfAbsent(query.getClass(), qc -> {
            QueryHandler<?, ?> found = findHandler(qc);
            if (found == null) {
                log.warn("handlerCache miss, still NOT found handler for {}", qc.getSimpleName());
            } else {
                log.debug("handlerCache populated for {} -> {}", qc.getSimpleName(), found.getClass().getName());
            }
            return found;
        });
        if (handler == null) {
            throw new IllegalArgumentException("沒有找到處理 " + query.getClass().getSimpleName() + " 的處理器");
        }
        return handler.handle(query);
    }
    
    private QueryHandler<?, ?> findHandler(Class<?> queryClass) {
        Map<String, QueryHandler> handlers = applicationContext.getBeansOfType(QueryHandler.class);
        
        log.info("DEBUG: Looking for handler for {}", queryClass.getSimpleName());
        log.info("DEBUG: Found {} query handlers:", handlers.size());
        handlers.forEach((name, handler) -> log.info("  - {}: {}", name, handler.getClass().getName()));
        
        return handlers.values().stream()
            .filter(handler -> canHandle(handler, queryClass))
            .findFirst()
            .orElse(null);
    }
    
    private boolean canHandle(QueryHandler<?, ?> handler, Class<?> queryClass) {
        // 先嘗試使用 Spring ResolvableType 解析實際目標類（解決 @Transactional 產生的 CGLIB 代理）
        Class<?> targetClass = AopUtils.getTargetClass(handler);
        ResolvableType resolvable = ResolvableType.forClass(targetClass).as(QueryHandler.class);
        if (resolvable != ResolvableType.NONE) {
            Class<?> resolvedQueryType = resolvable.getGeneric(0).resolve();
            if (resolvedQueryType != null) {
                boolean match = resolvedQueryType.equals(queryClass);
                log.debug("canHandle (ResolvableType) targetClass={} queryType={} candidate={} match={}",
                    targetClass.getName(), resolvedQueryType.getSimpleName(), queryClass.getSimpleName(), match);
                if (match) return true;
            }
        }
        // 回退舊有反射邏輯（多數非代理類仍可用）
        Type[] genericInterfaces = targetClass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType parameterizedType) {
                Type rawType = parameterizedType.getRawType();
                if (rawType.equals(QueryHandler.class)) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    if (typeArguments.length > 0 && typeArguments[0].equals(queryClass)) {
                        log.debug("canHandle (fallback) targetClass={} matched via ParameterizedType", targetClass.getName());
                        return true;
                    }
                }
            }
        }
        log.debug("canHandle NO targetClass={} queryClass={}", targetClass.getName(), queryClass.getSimpleName());
        return false;
    }
}