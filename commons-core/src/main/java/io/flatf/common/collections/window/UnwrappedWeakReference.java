package io.flatf.common.collections.window;

import java.lang.ref.WeakReference;

/**
 * Smarter WeakReference that "unwraps" the referenced object in a few methods
 * such as "equals()" which let a WeakReference be used in many other types of
 * collections and lists and have them still work correctly. For example,
 * CopyOnWriteArrayList can be directly used with a UnwrappedWeakReference and
 * have methods like "addIfAbsent" actually work correctly.
 *
 * @author joelauer (twitter: @jjlauer or
 * <a href="http://twitter.com/jjlauer" target=
 * window>http://twitter.com/jjlauer</a>)
 */
public class UnwrappedWeakReference<T> extends WeakReference<T> {

    public UnwrappedWeakReference(T ref) {
        super(ref);
    }

    @Override
    public boolean equals(Object obj) {
        // unwrap both objects!
        T thisObject = this.get();
        // if the other object is also a WeakRef
        if (obj instanceof WeakReference) {
            obj = ((WeakReference<?>) obj).get();
        }
        // check if anything is null (was garbage collected)
        if (thisObject == null) {
            return obj == null;
        }
        return thisObject.equals(obj);
    }

    @Override
    public int hashCode() {
        // unwrap me!
        T thisObject = this.get();
        // check if anything is null (was garbage collected)
        if (thisObject == null) {
            return 0;
        } else {
            return thisObject.hashCode();
        }
    }

}
