package com.dryox.water_pls_mobile.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ValueObject {

    private Iterable<Object> GetEqualityComponents() {
        List<Object> list = new ArrayList<>();

        for (Field f : getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                list.add(f.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private Iterable<Field> GetFields() {
        List<Field> list = new ArrayList<>();

        for (Field f : getClass().getDeclaredFields()) {
            f.setAccessible(true);
            list.add(f);
        }

        return list;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            throw new IllegalArgumentException(String
                    .format("Invalid comparison of Value Objects of different types: %s and %s",
                            this.getClass(), object.getClass()));
        }

        ValueObject valueObject = (ValueObject) object;
        return GetEqualityComponents().equals(valueObject.GetEqualityComponents());
    }

    public int hashCode() {
        return Objects.hash(GetEqualityComponents());
    }

    public String toString() {
        final ToStringHelper helper = MoreObjects.toStringHelper(this);

        for (Field o : GetFields()) {
            try {
                helper.add(o.getName(), o.get(this).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return helper.toString();
    }
}