package dao;

public class DAOException extends Exception {
	private static final long serialVersionUID = 1189344538341364061L;
	
	public DAOException(Exception e) {
		super(e);
	}
	
	public DAOException(String message, Exception e) {
		super(message, e);
	}
}
