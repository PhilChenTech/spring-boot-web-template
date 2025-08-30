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
    
    @SuppressWarnings("unchecked")
    private CommandHandler<?> findHandler(Class<?> commandClass) {
        Map<String, CommandHandler<?>> handlers =
            (Map<String, CommandHandler<?>>) (Map<String, ?>) applicationContext.getBeansOfType(CommandHandler.class);

        return handlers.values().stream()
            .filter(handler -> canHandle(handler, commandClass))
            .findFirst()
            .orElse(null);
    }
    
    private boolean canHandle(CommandHandler<?> handler, Class<?> commandClass) {
        // 獲取實際的類別（處理 Spring 代理類）
        Class<?> actualClass = getActualClass(handler);

        // 檢查所有泛型接口和父類
        return checkGenericTypes(actualClass, commandClass);
    }

    /**
     * 獲取實際的類別，處理 Spring CGLIB 代理
     */
    private Class<?> getActualClass(Object obj) {
        Class<?> clazz = obj.getClass();

        // 如果是 CGLIB 代理類，獲取父類
        if (clazz.getName().contains("$$")) {
            return clazz.getSuperclass();
        }

        return clazz;
    }

    /**
     * 遞歸檢查類別及其父類的泛型類型
     */
    private boolean checkGenericTypes(Class<?> clazz, Class<?> commandClass) {
        if (clazz == null || clazz == Object.class) {
            return false;
        }

        // 檢查直接實現的介面
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (matchesCommandHandler(genericInterface, commandClass)) {
                return true;
            }
        }

        // 檢查父類的泛型類型
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (matchesCommandHandler(genericSuperclass, commandClass)) {
            return true;
        }

        // 遞歸檢查父類
        return checkGenericTypes(clazz.getSuperclass(), commandClass);
    }

    /**
     * 檢查給定的類型是否匹配 CommandHandler<指定命令類型>
     */
    private boolean matchesCommandHandler(Type type, Class<?> commandClass) {
        if (type instanceof ParameterizedType parameterizedType) {
            Type rawType = parameterizedType.getRawType();
            if (rawType.equals(CommandHandler.class)) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                return typeArguments.length > 0 && typeArguments[0].equals(commandClass);
            }
        }
        return false;
    }
}