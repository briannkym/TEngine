package com.briannakayama.configure;

public class ConfigurationError extends RuntimeException{



	/**
	 * 
	 */
	private static final long serialVersionUID = -9187088905120275485L;

	public ConfigurationError(String message){
		super(message);
	}
	
	public ConfigurationError(String message, Throwable cause){
		super(message, cause);
	}
	
	public ConfigurationError(int lineNo, String file, String message){
		super(message + "\n\tin configuration "+ file+ ":" + lineNo);
	}
	
	public ConfigurationError(int lineNo, String file,  String message, Throwable cause){
		super(message + "\n\tin configuration "+ file+ ":" + lineNo, cause);
	}
	
}
