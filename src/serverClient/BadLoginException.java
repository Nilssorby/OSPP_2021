package serverClient;

public class BadLoginException extends RuntimeException {
	private String msg;
	
  public BadLoginException(String err) {
      super(err);
      this.msg = err;
  }
  
  public String getMessage() {
	  return this.msg;
  }
}
