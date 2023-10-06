package com.generic.utils.excel;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.common.base.Optional;

public class ReflectUtil {

    public static boolean set(Object object, String fieldName, Object fieldValue) {
        if(fieldName==null)
            return false;
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Type pt=null;
                try{
                    pt = field.getGenericType();
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(pt!=null && pt.getTypeName().equals("com.google.common.base.Optional<java.lang.String>"))
                    field.set(object, Optional.fromNullable(fieldValue));
                else if(pt!=null && pt.getTypeName().equals("java.lang.String"))
                    if(fieldValue instanceof Double)
                        field.set(object, String.valueOf(((Double)fieldValue).intValue()));
                    else
                        field.set(object, String.valueOf(fieldValue));
                else if(pt!=null && (pt.getTypeName().equals("java.lang.Integer") || pt.getTypeName().equals("int")))
                    if(fieldValue instanceof Double)
                        field.set(object, ((Double) fieldValue).intValue());
                    else
                        field.set(object, Integer.parseInt(String.valueOf(fieldValue)));
                else
                    field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
}
