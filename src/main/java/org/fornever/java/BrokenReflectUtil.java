package org.fornever.java;

import org.fornever.java.exceptions.NotImplementException;

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
        var declaredMethods = Arrays.asList(clazz.getDeclaredMethods());
        var allMethods = Arrays.asList(clazz.getMethods());
        methods.addAll(declaredMethods);
        methods.addAll(allMethods);
        return methods.stream().filter(method -> method.getName().equals(methodName)).collect(Collectors.toSet());
    }

    protected void validateObjectImplInterface(Object object, Class<?> anInterface) {
        var objectType = object.getClass();
        for (var method : anInterface.getMethods()) {
            var methodParamTypes = method.getParameterTypes();
            var target = getMethodsSafe(objectType, method.getName()).stream().filter(m -> this.matchMethodTypes(m, methodParamTypes)).findFirst();
            if (target.isEmpty()) {
                throw new NotImplementException(String.format("method '%s'.'%s'(%s) is not implemented by type '%s'", anInterface.getName(), method.getName(), String.join(", ", Arrays.asList(methodParamTypes).stream().map(t -> t.getName()).collect(Collectors.toList())), objectType.getName()));
            }
        }
    }

    /**
     * the from type instance could be soft assign to to type (by proxy) <br/>
     * check public methods only
     *
     * @param to
     * @param from
     * @return
     */
    protected Boolean isSoftAssignable(Class<?> to, Class<?> from) {

        for (var toMethod : to.getMethods()) {
            var totalMethod = this.getMethodsSafe(from, toMethod.getName())
                    .stream()
                    .filter(fromMethod -> this.matchMethodTypes(toMethod, fromMethod.getParameterTypes()))
                    .count();
            if (totalMethod == 0) {
                return false;
            }
        }

        return true;
    }

    private boolean isTypesAreSoftAssignable(Class<?>[] toMethodParamTypes, Class<?>[] fromMethodParamTypes) {
        for (int idx = 0; idx < toMethodParamTypes.length; idx++) {
            if (toMethodParamTypes[idx].isAssignableFrom(fromMethodParamTypes[idx])) {
                continue;
            }
            if (isSoftAssignable(toMethodParamTypes[idx], fromMethodParamTypes[idx])) {
                continue;
            }
            return false;
        }
        return true;
    }

    protected Boolean matchMethodTypes(Method method, Class<?>[] argsTypes) {
        var methodParameterTypes = method.getParameterTypes();
        if (methodParameterTypes.length == 0 && (argsTypes == null || argsTypes.length == 0)) {
            return true;
        }
        if (methodParameterTypes.length > argsTypes.length) {
            return false;
        }

        return isTypesAreSoftAssignable(methodParameterTypes, argsTypes);
    }

    protected Boolean matchMethodParams(Method method, Object[] args) {
        Class<?>[] argsTypes = new Class[args.length];
        for (int idx = 0; idx < args.length; idx++) {
            argsTypes[idx] = args[idx].getClass();
        }
        return matchMethodTypes(method, argsTypes);
    }
}
