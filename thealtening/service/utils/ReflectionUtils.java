package com.teaclient.tea.api.thealtening.service.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Trol.
 */
public class ReflectionUtils {

    private String className;

    public ReflectionUtils(String className)
    {
        try
        {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private Class<?> clazz;
    public void setStaticField(String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException
    {
        Field field = clazz.getDeclaredField(fieldName);

        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

}
