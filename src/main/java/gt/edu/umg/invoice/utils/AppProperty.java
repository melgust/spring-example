package gt.edu.umg.invoice.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "settings")
public class AppProperty {
	
	private String jwtSecret;
	private int jwtExpiration;
	private int showException;
	
	public String getJwtSecret() {
		return jwtSecret;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public int getJwtExpiration() {
		return jwtExpiration;
	}

	public void setJwtExpiration(int jwtExpiration) {
		this.jwtExpiration = jwtExpiration;
	}

	public int getShowException() {
		return showException;
	}

	public void setShowException(int showException) {
		this.showException = showException;
	}

}