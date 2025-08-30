package com.nicenpc.application.bus;

import com.nicenpc.application.handler.QueryHandler;
import org.springframework.context.ApplicationContext;
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
    
    private final ApplicationContext applicationContext;
    private final Map<Class<?>, QueryHandler<?, ?>> handlerCache = new ConcurrentHashMap<>();
    
    public QueryBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    @SuppressWarnings("unchecked")
    public <Q, R> R send(Q query) {
        QueryHandler<Q, R> handler = (QueryHandler<Q, R>) handlerCache.computeIfAbsent(
            query.getClass(),
            this::findHandler
        );
        
        if (handler == null) {
            throw new IllegalArgumentException("沒有找到處理 " + query.getClass().getSimpleName() + " 的處理器");
        }
        
        return handler.handle(query);
    }
    
    private QueryHandler<?, ?> findHandler(Class<?> queryClass) {
        Map<String, QueryHandler> handlers = applicationContext.getBeansOfType(QueryHandler.class);
        
        return handlers.values().stream()
            .filter(handler -> canHandle(handler, queryClass))
            .findFirst()
            .orElse(null);
    }
    
    private boolean canHandle(QueryHandler<?, ?> handler, Class<?> queryClass) {
        Type[] genericInterfaces = handler.getClass().getGenericInterfaces();
        
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType parameterizedType) {
                Type rawType = parameterizedType.getRawType();
                if (rawType.equals(QueryHandler.class)) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    if (typeArguments.length > 0 && typeArguments[0].equals(queryClass)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}