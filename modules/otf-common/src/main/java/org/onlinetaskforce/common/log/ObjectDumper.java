/*
* Copyright (c) 2010-2011 MVG. All Rights Reserved.
*/
package org.onlinetaskforce.common.log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * The Class ObjectDumper.
 * 
 * @author $Author: jordens
 * @version $LastChangedRevision: 1027 $, $LastChangedDate: 2010-05-21 09:49:52 +0200 (vr, 21 mei 2010) $.
 */
public class ObjectDumper {

    /**
     * Dumpt het object recursief in een String met het formaat
     * naam : type = { value }
     * idnei depth > 0 wordt hetzelfgde gedaan voor de velden , zoniet wordt de toString() van het veld uitgevoerd
     * @param o het Obhject te dumpen
     * @param depth diepte om te dumpen (ie max aantal keer recursief aan te roepen)
     * @param fieldSeparator String die na elk veld toegeveogd wordt
     * @return String de dump van het object
     */
    // package level
    private static String dumpInstance(Object o, int depth, String fieldSeparator) {
        if(o == null) {
            return "{ null }";
        }
        Class cls = o.getClass();
        Field fieldlist[] = cls.getDeclaredFields();
        StringBuffer value = new StringBuffer(4096);
            for (Field fld : fieldlist) {
                // skip static fields
                if(Modifier.isStatic(fld.getModifiers())) {
                    continue;
                }
               value.append('{').append(fld.getName()).append(":").append(fld.getType()).append("=");
                fld.setAccessible(true);
                try {
                    Object child = fld.get(o);
                    if (child != null) {
                        if(depth <= 0) {
                            value.append(child.toString());
                        }
                        else {
                            value.append(dumpInstance(child, --depth, fieldSeparator));
                        }
                    }
                } catch(IllegalAccessException e) {
                    value.append(e);
                }
           value.append("}").append(fieldSeparator);
          }
        return value.toString();
       }

    /**
     * Dumpt het object recursief in een String met het formaat
     * naam : type = { value }
     * idnei depth > 0 wordt hetzelfgde gedaan voor de velden , zoniet wordt de toString() van het veld uitgevoerd
     * @param o het Obhject te dumpen
     * @param depth diepte om te dumpen (ie max aantal keer recursief aan te roepen)
     * @param fieldSeparator String die na elk veld toegeveogd wordt
     * @return String de dumpInstance van het object
     */
    static String dump(Object o, int depth, String fieldSeparator) {
        if(o == null) {
            return "{ null }";
        }
        Class cls = o.getClass();
        if(cls.isArray()) {
            StringBuffer value = new StringBuffer("[");
            for(int i = 0; i < Array.getLength(o); ++i) {
                Object o2 = Array.get(o, i);
                value.append(dumpInstance(o2, depth, ""));
                if(i+1 < Array.getLength(o)) {
                    value.append(", ").append(fieldSeparator);
                }
            }
               value.append("]").append(fieldSeparator);
            return value.toString();
        }   else {
            return dumpInstance(o, depth, fieldSeparator);
        }
       }


    /**
     * Dumpt het object met een default field separator \n
     * @param o het Obhject te dumpen
     * @param depth diepte om te dumpen (ie max aantal keer recursief aan te roepen)
     * @return String de dumpInstance van het object
     */
    static String dump(Object o, int depth) {
        return dump(o, depth, "\n");
    }

    /**
     * Dumpt het object o uit met diepte 0 en fieldseparator \n   
     * @param o het Obhject te dumpen
     * @return String de dumpInstance van het object
     */
    static String dump(Object o) {
        return dump(o, 0, "\n");
    }
   
}
