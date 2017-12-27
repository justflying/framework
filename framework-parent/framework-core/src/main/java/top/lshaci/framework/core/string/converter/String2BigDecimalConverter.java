package top.lshaci.framework.core.string.converter;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Convert the string to big decimal
 * 
 * @author lshaci
 * @version 0.0.1
 */
@Slf4j
public class String2BigDecimalConverter implements StringConverter<BigDecimal> {
	

	@Override
	public BigDecimal convert(String source) {
		log.debug("The string is : " + source);

        if (StringUtils.isEmpty(source)) {
            return null;
        }
        source = trimSource(source);
        
        try {
			return new BigDecimal(source);
		} catch (Exception e) {
			log.warn("Parse string to decimal is error!  --> " + source, e);
		}
        return null;
	}

}
