package io.flatf.common.http;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Collections;
import java.util.Set;

import static io.flatf.common.collections.MutableSets.newUnifiedSet;

public final class PathParamSet {

    private final Set<PathParam> set = newUnifiedSet();

    private PathParamSet() {
    }

    public static PathParamSet empty() {
        return new PathParamSet();
    }

    public static PathParamSet with(PathParam... params) {
        return new PathParamSet().addParams(params);
    }

    public PathParamSet addParams(Object name, Object value) {
        return addParams(new PathParam(name, value == null ? "" : value));
    }

    public PathParamSet addParams(PathParam... params) {
        Collections.addAll(set, params);
        return this;
    }

    public String toUriParams() {
        StringBuilder sb = new StringBuilder("?");
        set.forEach(param -> sb.append(param).append("&"));
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public URI toFullUri(@Nonnull String uri) {
        return URI.create(uri + toUriParams());
    }

}
