package com.nicenpc.application.bus;

import com.nicenpc.application.handler.CommandHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 指令匯流排
 * 負責將指令路由到對應的處理器
 */
@Component
public class CommandBus {
    
    private final ApplicationContext applicationContext;
    private final Map<Class<?>, CommandHandler<?>> handlerCache = new ConcurrentHashMap<>();
    
    public CommandBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    @SuppressWarnings("unchecked")
    public <T> void send(T command) {
        CommandHandler<T> handler = (CommandHandler<T>) handlerCache.computeIfAbsent(
            command.getClass(),
            this::findHandler
        );
        
        if (handler == null) {
            throw new IllegalArgumentException("沒有找到處理 " + command.getClass().getSimpleName() + " 的處理器");
        }
        
        handler.handle(command);
    }
    
    private CommandHandler<?> findHandler(Class<?> commandClass) {
        Map<String, CommandHandler> handlers = applicationContext.getBeansOfType(CommandHandler.class);
        
        return handlers.values().stream()
            .filter(handler -> canHandle(handler, commandClass))
            .findFirst()
            .orElse(null);
    }
    
    private boolean canHandle(CommandHandler<?> handler, Class<?> commandClass) {
        Type[] genericInterfaces = handler.getClass().getGenericInterfaces();
        
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType parameterizedType) {
                Type rawType = parameterizedType.getRawType();
                if (rawType.equals(CommandHandler.class)) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    if (typeArguments.length > 0 && typeArguments[0].equals(commandClass)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}