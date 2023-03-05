package service;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 7964000571075610683L;
	
	public ServiceException(Exception e) {
		super(e);
	}
	
	public ServiceException(String message, Exception e) {
		super(message, e);
	}
}
