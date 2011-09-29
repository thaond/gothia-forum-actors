package se.gothiaforum.validator.actorsform;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Generic methods to support validation
 * 
 * @author Hans Gyllensten, vgrid=hangy2
 *  
 */
public class ValidatorUtils {
	private static final Log log = LogFactory.getLog(ValidatorUtils.class);
	
	/**
	 * Loop through the objects get-methods and retrieve the value and set a trimmed value in the corresponding set method.
	 * N.B. The methods works only for setters that take a String argument
	 * @param article
	 */
	public static void removeLeadingAndTrailingWhitespacesOnAllStringFields(Object article ) {
		Class clazz = article.getClass();
				
		Method[] theMethods = clazz.getDeclaredMethods();
		for( Method method : theMethods ){			
			String methodName = method.getName();   					
			Class returnType = method.getReturnType();
			Type[] getParameterTypes = method.getGenericParameterTypes();

			// Loop through all get methods that returns String and has no parameters
			if (methodName.toLowerCase().startsWith("get") && returnType.isAssignableFrom(String.class) && (getParameterTypes.length==0)) {
				
				// this method is a get method
				try {
					Object[] arglist = null;
					Object returnObject =  method.invoke(article, arglist);
					
					// we get the corresponding set method
					Method setMethod = null;
					String setMethodName = methodName.replaceFirst("get", "set"); 
					Class arguments[] = new Class[1];
					arguments[0] = String.class;					
					setMethod = clazz.getMethod(setMethodName, arguments);
					Type[] parameterTypes = setMethod.getGenericParameterTypes(); 
					
					// we only look for set methods with one String parameter
					if (parameterTypes.length==1 && parameterTypes[0].getClass().isInstance(String.class)) {
						
						// ok we have a set method that sets a string
						// get the value and trim it
						String setValue = "" + returnObject; 
						setValue = setValue.trim();

						Object setArguments[] = new Object[1];
						setArguments[0] = setValue; 
						returnObject = setMethod.invoke(article, setArguments); // set trimmed value

						// Let´s check what we got when we read it
						returnObject =  method.invoke(article, arglist);
					}
					
				}
				catch (Exception e) {
					log.error(e);
					throw new RuntimeException(e);
				}
			}			
		}		
		
	}
}
