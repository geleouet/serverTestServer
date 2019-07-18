package net.egaetan.OptServre.server;

public class CheckResponse {

	boolean status;
	String reason;

	public CheckResponse() {
	}
	
	private CheckResponse(boolean b) {
		status = b;
	}

	public CheckResponse(String string) {
		status = false;
		reason = string;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public static CheckResponse ok() {
		return new CheckResponse(true);
	}

	public static CheckResponse fail(String string) {
		return new CheckResponse(string);
	}

}
