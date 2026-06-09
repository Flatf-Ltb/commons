package io.flatf.common.concurrent.disruptor;

import io.flatf.common.concurrent.disruptor.EventPublisher.EventPublisherArg1;
import io.flatf.common.concurrent.disruptor.EventPublisher.EventPublisherArg3;
import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.junit.Assert.assertFalse;

public class EventPublisherFactoryTest {

    @Test
    public void publisherConstructorsAreNotPublic() {
        assertHasNoPublicConstructor(EventPublisherArg1.class);
        assertHasNoPublicConstructor(EventPublisherArg3.class);
    }

    private static void assertHasNoPublicConstructor(Class<?> type) {
        for (var constructor : type.getDeclaredConstructors())
            assertFalse(type.getName() + " constructor must not be public",
                    Modifier.isPublic(constructor.getModifiers()));
    }
}
