package top.lshaci.framework.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Swagger 2 config properties</p><br>
 * <b>1.0.1: </b>添加分组配置<br>
 * <b>1.0.2: </b>修改swagger配置属性前缀
 *
 * @author lshaci
 * @since 0.0.4
 * @version 1.0.2
 */
@Data
@ConfigurationProperties(prefix = FrameworkSwaggerProperties.SWAGGER_PREFIX)
public class FrameworkSwaggerProperties {

	/**
	 * The swagger properties prefix
	 */
	public final static String SWAGGER_PREFIX = "framework.swagger";

	/**
	 * 是否开启swagger配置(默认开启)
	 */
	private Boolean enabled = true;

	/**
	 * The controller base package
	 */
	private String basePackage;
	/**
	 * The swagger api title
	 */
	private String title = "Swagger2 RESTful APIs";
	/**
	 * The swagger api description
	 */
	private String description = "Spring boot Project Use Swaggers UI Build RESTful APIs";
	/**
	 * The swagger api version
	 */
	private String version = "1.0";
	/**
	 * The swagger api license
	 */
	private String license;
	/**
	 * The swagger api license url
	 */
	private String licenseUrl;
	/**
	 * The swagger api terms of service url
	 */
	private String termsOfServiceUrl;
	/**
	 * The swagger api
	 */
	private String host;
    /**
     * Url rules that need to be parsed
     */
    private List<String> basePath = new ArrayList<>();
    /**
     * Url rules that need to be excluded
     */
    private List<String> excludePath = new ArrayList<>();

	/**
	 * The swagger api maintenance personnel
	 */
	private Contact contact = new Contact();

	/**
	 * Group information
	 */
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();

    /**
     * Contact information
     *
     * @author lshaci
     * @since 0.0.4
     */
	@Data
	public static class Contact {
		/**
		 * name
		 */
		private String name = "lshaci";
		/**
		 * url
		 */
		private String url = "http://www.lshaci.top";
		/**
		 * email
		 */
		private String email = "lshaci@qq.com";

		public springfox.documentation.service.Contact get() {
			return new springfox.documentation.service.Contact(name, url, email);
		}
	}

	/**
	 * Group docket information
	 *
	 * @author lshaci
	 * @since 1.0.1
	 */
	@Data
    public static class DocketInfo {
		/**
		 * The controller base package
		 */
		private String basePackage;
		/**
		 * The swagger api title
		 */
		private String title = "";
		/**
		 * The swagger api description
		 */
		private String description = "";
		/**
		 * The swagger api version
		 */
		private String version = "";
		/**
		 * The swagger api license
		 */
		private String license = "";
		/**
		 * The swagger api license url
		 */
		private String licenseUrl = "";
		/**
		 * The swagger api terms of service url
		 */
		private String termsOfServiceUrl = "";
		/**
		 * The swagger api
		 */
		private String host = "";
	    /**
	     * Url rules that need to be parsed
	     */
	    private List<String> basePath = new ArrayList<>();
	    /**
	     * Url rules that need to be excluded
	     */
	    private List<String> excludePath = new ArrayList<>();
		/**
		 * The swagger api maintenance personnel
		 */
		private Contact contact = new Contact();

    }

}