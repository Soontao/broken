package org.fornever.java;

import org.fornever.java.exceptions.FieldNotFoundException;
import org.fornever.java.exceptions.MethodExecutionException;
import org.fornever.java.exceptions.MethodNotFoundException;
import org.fornever.java.exceptions.ValueNotAssignException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Map;

public class Broken {

    private static Broken defaultBroken = new Broken();

    private BrokenReflectUtil util = new BrokenReflectUtil();

    public synchronized static Broken getDefaultBroken() {
        if (defaultBroken == null) {
            defaultBroken = new Broken();
        }
        return defaultBroken;
    }


    public void setValue(Object object, String fieldName, Object value) {
        if (object == null) {
            throw new NullPointerException("provided object is null");
        }
        if (fieldName == null) {
            throw new NullPointerException("provided field name is null");
        }
        var objectType = object.getClass();
        Field field = util.getFieldSafe(objectType, fieldName);
        if (field == null) {
            throw new FieldNotFoundException(String.format("field '%s'.'%s' is not found", objectType.getName(), fieldName));
        }

        var fieldType = field.getType();
        try {
            field.setAccessible(true);
            if (value == null) {
                field.set(object, null);
            } else {
                var valueType = value.getClass();
                if (fieldType.isAssignableFrom(valueType)) {
                    field.set(object, value);
                } else {
                    throw new ValueNotAssignException(String.format("field '%s' with class '%s' can not be assign with instance which class '%s'", fieldName, fieldType.getName(), valueType.getName()));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * convert an object to a map, so that you can operate the fields' values with key
     *
     * @param object
     * @return
     */
    public Map<String, Object> toMap(Object object) {
        return new BrokenObjectMap(object);
    }

    /**
     * create a proxy with an given interface
     *
     * @param iInterface
     * @param object
     * @param <T>
     * @return
     */
    public <T> T proxy(Class<T> iInterface, Object object) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{iInterface}, (proxy, method, args) -> {
            try {
                return this.call(object, method.getName(), args);
            } catch (MethodExecutionException e) {
                // throw the original exception
                throw e.getInnerException();
            }
        });
    }

    /**
     * @param object
     * @param methodName
     * @param params
     * @return
     * @throws MethodExecutionException when method throw the exception, the exception will be carried in the un-checked exception
     * @throws MethodNotFoundException  when method not found
     */
    public Object call(Object object, String methodName, Object... params) {
        if (object == null) {
            throw new NullPointerException("provided object is null");
        }
        if (methodName == null) {
            throw new NullPointerException("provided method name is null");
        }
        var objectType = object.getClass();
        var methods = util.getMethodsSafe(objectType, methodName);
        for (var method : methods) {
            if (util.matchMethodParams(method, params)) {
                method.setAccessible(true);
                try {
                    return method.invoke(object, params);
                } catch (InvocationTargetException e) {
                    throw new MethodExecutionException(e);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new MethodNotFoundException(String.format("method not found for method '%s'.'%s'", objectType.getName(), methodName));
    }


}
