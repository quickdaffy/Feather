package me.quick.feather;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;

public class ModLoaderUtil<C> {

    public C loadClass(String directory, String classpath, Class<C> parentClass, File jar) throws ClassNotFoundException {
        File pluginsDir = new File(System.getProperty("user.dir") + directory);
        try {
            ClassLoader loader = URLClassLoader.newInstance(
                    new URL[] { jar.toURI().toURL() },
                    getClass().getClassLoader()
            );
            Class<?> clazz = Class.forName(classpath, true, loader);
            Class<? extends C> newClass = clazz.asSubclass(parentClass);
            Constructor<? extends C> constructor = newClass.getConstructor();
            return constructor.newInstance();

        } catch (ClassNotFoundException e) {
        } catch (MalformedURLException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new ClassNotFoundException("Class " + classpath
                + " wasn't found in directory " + System.getProperty("user.dir") + directory);
    }
}