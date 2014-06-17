/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.gothiaforum.validator.actorsform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.gothiaforum.util.Constants;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Generic methods to support validation.
 * 
 * @author Hans Gyllensten, vgrid=hangy2
 * 
 */
public final class ValidatorUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ValidatorUtils.class);

    private ValidatorUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Loop through the objects get-methods and retrieve the value and set a trimmed value in the corresponding set
     * method. N.B. The methods works only for setters that take a String argument
     * 
     * @param article
     *            the Actors article
     */
    public static void removeLeadingAndTrailingWhitespacesOnAllStringFields(Object article) {
        Class clazz = article.getClass();

        Method[] theMethods = clazz.getDeclaredMethods();
        for (Method method : theMethods) {
            String methodName = method.getName();
            Class returnType = method.getReturnType();
            Type[] getParameterTypes = method.getGenericParameterTypes();

            // Loop through all get methods that returns String and has no parameters
            if (methodName.toLowerCase(Constants.LOCALE).startsWith("get")
                    && returnType.isAssignableFrom(String.class) && (getParameterTypes.length == 0)) {

                // this method is a get method
                try {
                    Object[] arglist = null;
                    Object returnObject = method.invoke(article, arglist);

                    // we get the corresponding set method
                    Method setMethod = null;
                    String setMethodName = methodName.replaceFirst("get", "set");
                    Class[] arguments = new Class[1];
                    arguments[0] = String.class;
                    setMethod = clazz.getMethod(setMethodName, arguments);
                    Type[] parameterTypes = setMethod.getGenericParameterTypes();

                    // we only look for set methods with one String parameter
                    if (parameterTypes.length == 1 && parameterTypes[0].getClass().isInstance(String.class)) {

                        // ok we have a set method that sets a string
                        // get the value and trim it
                        String setValue = "" + returnObject;
                        setValue = setValue.trim();

                        Object setArguments[] = { setValue };
                        // setArguments[0] = setValue;
                        returnObject = setMethod.invoke(article, setArguments); // set trimmed value

                        // Let´s check what we got when we read it
                        returnObject = method.invoke(article, arglist);
                    }

                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
