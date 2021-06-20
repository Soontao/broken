package org.fornever.java;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * util of broken project
 */
class BrokenReflectUtil {

    protected Field getFieldSafe(Class clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (Throwable e) {

        }
        try {
            return clazz.getField(fieldName);
        } catch (Throwable e) {

        }

        return null;

    }

    protected Set<Method> getMethodsSafe(Class clazz, String methodName) {
        var methods = new HashSet<Method>();
        methods.addAll(Arrays.asList(clazz.getMethods()));
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        return methods.stream().filter(method -> method.getName().equals(methodName)).collect(Collectors.toSet());
    }

    protected Boolean matchMethodParams(Method method, Object[] args) {
        Class[] methodParameterTypes = method.getParameterTypes();
        if (methodParameterTypes.length != args.length) {
            return false;
        }
        for (int idx = 0; idx < methodParameterTypes.length; idx++) {
            if (!methodParameterTypes[idx].isAssignableFrom(args[idx].getClass())) {
                return false;
            }
        }
        return true;
    }
}
