package io.flatf.infra.attr;

import io.flatf.common.collections.CollectionUtil;
import io.flatf.common.collections.MutableSets;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.set.MutableSet;

public final class Topics {

    private final MutableSet<String> saved = MutableSets.newUnifiedSet();

    private Topics(String[] topics) {
        CollectionUtil.addAll(this.saved, topics);
    }

    /**
     * @return Topics
     */
    public static Topics empty() {
        return with();
    }

    /**
     * @param topics String[]
     * @return Topics
     */
    public static Topics with(String... topics) {
        return new Topics(topics);
    }

    /**
     * @return MutableSet<String>
     */
    public MutableSet<String> getSaved() {
        return saved;
    }

    /**
     * @return int
     */
    public int getCount() {
        return saved.size();
    }

    /**
     * @param topics String[]
     * @return Topics
     */
    public Topics add(String... topics) {
        CollectionUtil.addAll(this.saved, topics);
        return this;
    }

    /**
     * @param procedure Procedure<String>
     */
    public void each(Procedure<String> procedure) {
        saved.each(procedure);
    }

}
