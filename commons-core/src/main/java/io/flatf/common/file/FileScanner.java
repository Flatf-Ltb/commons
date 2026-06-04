package io.flatf.common.file;

import io.flatf.common.collections.MutableSets;
import io.flatf.common.lang.Validator;
import org.eclipse.collections.api.set.MutableSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileFilter;

public final class FileScanner {

    private FileScanner() {
    }

    /**
     * 收集指定路径下全部文件
     *
     * @param path File
     * @return MutableSet<File>
     */
    @Nonnull
    public static MutableSet<File> depthFirst(@Nonnull File path) {
        Validator.nonNull(path, "path");
        return depthFirst(path, null);
    }

    /**
     * 根据过滤器收集指定路径下全部文件, 如果过滤器为null, 默认收集全部文件
     *
     * @param path   File
     * @param filter FileFilter
     * @return MutableSet<File>
     */
    @Nonnull
    public static MutableSet<File> depthFirst(@Nonnull File path, @Nullable FileFilter filter) {
        Validator.nonNull(path, "path");
        if (filter == null)
            filter = any -> true;
        MutableSet<File> files = MutableSets.newUnifiedSet();
        depthFirst0(files, path, filter);
        return files;
    }

    private static void depthFirst0(@Nonnull MutableSet<File> files,
                                    @Nonnull File path,
                                    @Nonnull FileFilter filter) {
        File[] listFiles = path.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                // 如果文件是一个目录, 递归执行
                if (file.isDirectory()) {
                    depthFirst0(files, file, filter);
                }
                // 如果文件符合过滤器断言, 则加入Set
                else if (filter.accept(file)) {
                    files.add(file);
                }
                // 否则忽略此文件
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            if (i == 0)
                System.out.println("00" + 0);
            else if (i % 2 == 0)
                System.out.println(i);
            else
                System.out.println("PPP");
        }
    }

}
