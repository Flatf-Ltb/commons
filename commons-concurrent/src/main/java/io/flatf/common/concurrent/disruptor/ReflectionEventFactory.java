package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;
import io.flatf.common.util.JreReflection;
import io.flatf.common.util.JreReflection.RuntimeReflectionException;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

import static io.flatf.common.lang.Validator.nonNull;
import static io.flatf.common.log4j2.Log4j2LoggerFactory.getLogger;

/**
 * @param <T>
 * @author yellow013 <br>
 * <br>
 * @implNote 通过反射实现[EventFactory]
 */
public final class ReflectionEventFactory<T> implements EventFactory<T> {

    private static final Logger LOG = getLogger(ReflectionEventFactory.class);

    private final Class<T> type;

    private ReflectionEventFactory(Class<T> type) {
        this.type = type;
    }

    /**
     * @param type Event class type
     * @return ReflectionEventFactory<T>
     * @throws RuntimeReflectionException re
     */
    public static <T> ReflectionEventFactory<T> newFactory(@Nonnull Class<T> type)
            throws RuntimeReflectionException {
        return newFactory(type, null);
    }

    /**
     * @param type Event class type
     * @param log  Error logger
     * @return ReflectionEventFactory<T>
     * @throws RuntimeReflectionException re
     */
    public static <T> ReflectionEventFactory<T> newFactory(@Nonnull Class<T> type, Logger log)
            throws RuntimeReflectionException {
        nonNull(type, "type");
        ReflectionEventFactory<T> factory = new ReflectionEventFactory<>(type);
        try {
            // Try call newInstance() function
            factory.newInstance();
            return factory;
        } catch (RuntimeReflectionException e) {
            if (log != null)
                log.error("Class -> {} :: new instance exception -> {}", type, e.getMessage(), e);
            else
                LOG.error("Class -> {} :: new instance exception -> {}", type, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public T newInstance() {
        return JreReflection.invokeConstructor(type);
    }

}
