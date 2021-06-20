package org.fornever.java;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BrokenObjectMap implements Map<String, Object> {

    protected BrokenObjectMap(Object object) {
        this.object = object;
        var objectType = object.getClass();
        var fields = new HashSet<Field>();
        fields.addAll(Arrays.asList(objectType.getFields()));
        fields.addAll(Arrays.asList(objectType.getDeclaredFields()));
        this.fieldsMap = new ConcurrentHashMap<>();
        for (var field : fields) {
            field.setAccessible(true);
            this.fieldsMap.put(field.getName(), field);
        }
    }

    private Object object;

    private Map<String, Field> fieldsMap;

    @Override
    public int size() {
        return fieldsMap.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.fieldsMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.fieldsMap.values().stream().anyMatch(field -> {
            try {
                var fieldValue = field.get(object);
                if (fieldValue == null && value == null) {
                    return true;
                }
                if (fieldValue != null) {
                    return fieldValue.equals(value);
                }
                return false;

            } catch (IllegalAccessException e) {
                return false;
            }
        });
    }

    @Override
    public Object get(Object key) {
        if (this.fieldsMap.containsKey(key)) {
            try {
                return this.fieldsMap.get(key).get(object);
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Object put(String key, Object value) {
        if (this.fieldsMap.containsKey(key)) {
            try {
                this.fieldsMap.get(key).set(object, value);
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return value;
    }

    @Override
    public Object remove(Object key) {
        if (this.fieldsMap.containsKey(key)) {
            try {
                this.fieldsMap.get(key).set(object, null);
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        m.forEach((key, value) -> {
            this.put(key, value);
        });
    }

    @Override
    public void clear() {
        this.fieldsMap.forEach((key, field) -> {
            try {
                field.set(object, null);
            } catch (IllegalAccessException e) {

            }
        });
    }


    @Override
    public Set<String> keySet() {
        return fieldsMap.keySet();
    }


    @Override
    public Collection<Object> values() {
        return fieldsMap.values().stream().map(field -> {
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                return null;
            }
        }).collect(Collectors.toList());
    }


    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return fieldsMap.entrySet().stream().map(entry -> {
            var newEntry = new Entry<String, Object>();
            newEntry.setKey(entry.getKey());
            try {
                newEntry.setValue(entry.getValue().get(object));
            } catch (IllegalAccessException e) {

            }
            return newEntry;
        }).collect(Collectors.toSet());
    }

    class Entry<K, V> implements Map.Entry<K, V> {

        private K key;
        private V value;

        @Override
        public K getKey() {
            return this.key;
        }

        public K setKey(K key) {
            this.key = key;
            return key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }
    }
}
