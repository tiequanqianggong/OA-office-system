package com.ruoyi.Annotation.APT;

import com.ruoyi.Annotation.Percentage;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

/**
 * 将Double转换为百分比         留着备用
 */
public class PercentageAnnotationProcessor {
    public static void process(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Percentage.class)) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if (value instanceof Double) {
                        double percentageValue = (Double) value;
                        DecimalFormat df = new DecimalFormat("0.##%");
                        String formattedValue = df.format(percentageValue);
                        field.set(object, formattedValue);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}