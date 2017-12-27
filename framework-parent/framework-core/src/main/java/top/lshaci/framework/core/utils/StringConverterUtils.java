package top.lshaci.framework.core.utils;

import top.lshaci.framework.core.string.StringConverterFactory;
import top.lshaci.framework.core.string.converter.StringConverter;

/**
 * String converter util
 * 
 * @author lshaci
 * @version 0.0.1
 */
public abstract class StringConverterUtils {

	/**
	 * The default string converter factory
	 */
	private static StringConverterFactory stringConverterFactory = StringConverterFactory.buildDefaultFactory();
	
	/**
	 * Get target value
	 * 
	 * @param targetClass the target class type
	 * @param source the string source
	 * @return the target value
	 */
	public static <T> T getTargetValue(Class<T> targetClass, String source) {
		StringConverter<T> converter = stringConverterFactory.getConverter(targetClass);
		
		if (converter != null) {
			return converter.convert(source);
		}
		
		return null;
	}
}
