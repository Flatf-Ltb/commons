package io.flatf.common.collections;

import io.flatf.common.util.BitFormatter;
import io.flatf.common.util.BitOperator;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

import static io.flatf.common.collections.MutableMaps.newIntObjectMap;

public enum Capacity {

    /**
     * 0x8 == 8
     */
    HEX_8(0x8),

    /**
     * 0x10 == 16
     */
    HEX_10(0x10),

    /**
     * 0x20 == 32
     */
    HEX_20(0x20),

    /**
     * 0x40 == 64
     */
    HEX_40(0x40),

    /**
     * 0x80 == 128
     */
    HEX_80(0x80),

    /**
     * 0x100 == 256
     */
    HEX_100(0x100),

    /**
     * 0x200 == 512
     */
    HEX_200(0x200),

    /**
     * 0x400 == 1024
     */
    HEX_400(0x400),

    /**
     * 0x800 == 2048
     */
    HEX_800(0x800),

    /**
     * 0x1_000 == 4096
     */
    HEX_1_000(0x1_000),

    /**
     * 0x2_000 == 8192
     */
    HEX_2_000(0x2_000),

    /**
     * 0x4_000 == 16384
     */
    HEX_4_000(0x4_000),

    /**
     * 0x8_000 == 32768
     */
    HEX_8_000(0x8_000),

    /**
     * 0x10_000 == 65536
     */
    HEX_10_000(0x10_000),

    /**
     * 0x20_000 == 131072
     */
    HEX_20_000(0x20_000),

    /**
     * 0x40_000 == 262144
     */
    HEX_40_000(0x40_000),

    /**
     * 0x80_000 == 524288
     */
    HEX_80_000(0x80_000),

    /**
     * 0x100_000 == 1048576
     */
    HEX_100_000(0x100_000),

    /**
     * 0x200_000 == 2097152
     */
    HEX_200_000(0x200_000),

    /**
     * 0x400_000 == 4194304
     */
    HEX_400_000(0x400_000),

    /**
     * 0x800_000 == 8388608
     */
    HEX_800_000(0x800_000),

    /**
     * 0x1_000_000 == 16777216
     */
    HEX_1_000_000(0x1_000_000),

    /**
     * 0x2_000_000 == 33554432
     */
    HEX_2_000_000(0x2_000_000),

    /**
     * 0x4_000_000 == 67108864
     */
    HEX_4_000_000(0x4_000_000),

    ;

    public static final int DEFAULT_SIZE = 16;

    private static final ImmutableIntObjectMap<Capacity> VALUE_MAP =
            newIntObjectMap(Capacity::size, Capacity.values()).toImmutable();

    private final int size;

    Capacity(int size) {
        this.size = size;
    }

    /**
     * @param capacity Capacity
     * @return int
     */
    public static int checkAndGet(Capacity capacity) {
        return capacity == null ? DEFAULT_SIZE : capacity.size();
    }

    /**
     * @return int
     */
    public int size() {
        return size;
    }

    /**
     * @return min size 16
     */
    public Capacity halfSize() {
        return get(size >> 1);
    }

    /**
     * @param value int
     * @return Capacity
     */
    public Capacity get(int value) {
        int pow2 = BitOperator.minPow2(value);
        return pow2 >= DEFAULT_SIZE ? HEX_10 : VALUE_MAP.get(pow2);
    }

    public static void main(String[] args) {

        System.out.println(1 << 26);
        System.out.println(BitFormatter.intBinaryFormat(1 << 30));
        System.out.println(1 << 31);
        System.out.println(BitFormatter.intBinaryFormat(1 << 31));
        System.out.println(BitFormatter.intBinaryFormat(Integer.MIN_VALUE));

    }

}
