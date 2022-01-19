package backend;

import java.io.Serializable;

public class serverError implements Serializable{
	private String msg;
	
	public serverError(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return this.msg;
	}
}
