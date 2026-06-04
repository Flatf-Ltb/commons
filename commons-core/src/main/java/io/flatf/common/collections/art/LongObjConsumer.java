/*
 * Copyright 2019-2020 Maksim Zheravin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.flatf.common.collections.art;

import org.eclipse.collections.api.block.procedure.primitive.LongObjectProcedure;

@FunctionalInterface
public interface LongObjConsumer<T> extends LongObjectProcedure<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param l the first input argument
     * @param t the second input argument
     */
    void accept(long l, T t);

    @Override
    default void value(long l, T t) {
        accept(l, t);
    }

}
