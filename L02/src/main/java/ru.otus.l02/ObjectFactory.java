package ru.otus.l02;


import com.sun.org.apache.xpath.internal.operations.String;

public class ObjectFactory {
    public static Object createObject(ObjectType objectType) {
        switch (objectType) {
            case LONG:
                return new Long(0);
            case DOUBLE:
                return new Double(0.0);
            case INTEGR:
                return new Integer(0);
            case STRING:
                return new String();
            case CHARACTER:
                return new Character('0');
        }
        return null;
    }
}
