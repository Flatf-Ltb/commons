package io.flatf.common.sys;

import io.flatf.common.character.Separator;

import java.io.File;
import java.lang.reflect.Field;

public final class LibraryPathManager {

    private LibraryPathManager() {
    }

    /**
     * 获取LibraryPath, System.getProperty("java.library.path")
     *
     * @return String
     */
    public static String getLibraryPath() {
        return SysProperties.JAVA_LIBRARY_PATH;
    }

    /**
     * 添加java.library.path
     *
     * @param libraryFile File
     * @throws Exception e
     */
    public static void addLibraryDir(File libraryFile) throws Exception {
        addLibraryDir(libraryFile.getAbsolutePath());
    }

    /**
     * @param libraryPath String
     * @throws Exception e
     */
    public static void addLibraryDir(String libraryPath) throws Exception {
        Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
        usrPathsField.setAccessible(true);
        String[] paths = (String[]) usrPathsField.get(null);
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            if (libraryPath.equals(path))
                continue;
            sb.append(path).append(Separator.PATH_SEPARATOR);
        }
        sb.append(libraryPath);
        System.setProperty("java.library.path", sb.toString());
        final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
    }

}
