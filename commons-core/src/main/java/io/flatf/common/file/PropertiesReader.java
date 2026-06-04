package io.flatf.common.file;

import io.flatf.common.collections.MutableMaps;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.MutableSet;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static io.flatf.common.file.FileScanner.depthFirst;
import static io.flatf.common.util.StringSupport.notDecimal;

public final class PropertiesReader {

    private static final Logger log = Log4j2LoggerFactory.getLogger(PropertiesReader.class);

    // fileName-propertyName -> value
    private static final MutableMap<String, String> PropertiesItemMap = MutableMaps.newUnifiedMap();

    // fileName -> properties
    private static final MutableMap<String, Properties> PropertiesMap = MutableMaps.newUnifiedMap();

    // file extension
    private static final String FILE_SUFFIX = ".properties";

    static {
        MutableSet<File> files = depthFirst(new File(Objects
                        .requireNonNull(PropertiesReader.class.getResource("/")).getPath()),
                file -> file.getName().endsWith(FILE_SUFFIX));
        for (File file : files) {
            try {
                log.info("Properties file -> [{}] start load", file);
                String fileName = file.getName();
                Properties prop = new Properties();
                try (FileInputStream fis = new FileInputStream(file)) {
                    prop.load(fis);
                }
                PropertiesMap.put(deleteSuffix(fileName), prop);
                for (String propName : prop.stringPropertyNames()) {
                    String key = mergePropertiesKey(fileName, propName);
                    String value = prop.getProperty(propName);
                    String currentValue = PropertiesItemMap.get(key);
                    if (currentValue != null) {
                        log.warn("Current item value modified, propKey==[{}], currentValue==[{}], newValue==[{}]", key,
                                currentValue, value);
                    }
                    log.info("Put property item, propKey==[{}], propValue==[{}]", key, value);
                    PropertiesItemMap.put(key, value);
                }
            } catch (FileNotFoundException e) {
                log.error("File -> [{}] is not found", file, e);
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.error("File -> [{}] load failed", file, e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param fileName String
     * @param propName String
     * @return String
     */
    private static String mergePropertiesKey(String fileName, String propName) {
        return deleteSuffix(fileName) + "." + propName;
    }

    /**
     * @param fileName String
     * @return String
     */
    private static String deleteSuffix(String fileName) {
        if (fileName == null)
            return "";
        if (fileName.endsWith(FILE_SUFFIX))
            return fileName.split(FILE_SUFFIX)[0];
        return fileName;
    }

    /**
     * @param fileName String
     * @return Properties
     */
    public static Properties getProperty(String fileName) {
        Properties properties = PropertiesMap.get(deleteSuffix(fileName));
        if (properties == null)
            return new Properties();
        return properties;
    }

    /**
     * @param fileName String
     * @param propName String
     * @return String
     */
    public static String getProperty(String fileName, String propName) {
        String key = mergePropertiesKey(fileName, propName);
        String value = PropertiesItemMap.get(key);
        if (value == null) {
            log.error("Property name -> [{}] is not found of file name -> [{}], Key==[{}]", propName, fileName, key);
            throw new RuntimeException("Read property error.");
        }
        return value;
    }

    /**
     * @param fileName String
     * @param propName String
     * @return int
     */
    public static int getIntProperty(String fileName, String propName) {
        String value = getProperty(fileName, propName);
        if (notDecimal(value)) {
            log.error("Property name -> [{}] is not decimal of file name -> [{}]", propName, fileName);
            throw new NumberFormatException("Read property error.");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.error("Property name -> [{}], value -> [{}] from file name -> [{}] throw NumberFormatException",
                    propName, value, fileName, e);
            throw e;
        }
    }

    /**
     * @param fileName String
     * @param propName String
     * @return long
     */
    public static long getLongProperty(String fileName, String propName) {
        String value = getProperty(fileName, propName);
        if (notDecimal(value)) {
            log.error("Property name -> [{}] is not decimal of file name -> [{}]", propName, fileName);
            throw new NumberFormatException("Read property error.");
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.error("Property name -> [{}], value -> [{}] from file name -> [{}] throw NumberFormatException",
                    propName, value, fileName, e);
            throw e;
        }
    }

    /**
     * @param fileName String
     * @param propName String
     * @return double
     */
    public static double getDoubleProperty(String fileName, String propName) {
        String value = getProperty(fileName, propName);
        if (notDecimal(value)) {
            log.error("Property name -> [{}] is not decimal of file name -> [{}]", propName, fileName);
            throw new NumberFormatException("Read property error.");
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            log.error("Property name -> [{}], value -> [{}] from file name -> [{}] throw NumberFormatException",
                    propName, value, fileName, e);
            throw e;
        }
    }

    public static void main(String[] args) {
        File file = new File("");
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        File[] listFiles = file.listFiles();
        if (listFiles != null)
            for (File file2 : listFiles) {
                System.out.println(file2.getName());
            }
        System.out.println(System.getProperty("user.dir"));

    }

}
