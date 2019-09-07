package gt.edu.umg.invoice.utils;

import java.util.List;

public class ApiResponse {
	private String status;
	private String message;
	private List<?> data;
	private String singleResponse;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public String getSingleResponse() {
		return singleResponse;
	}

	public void setSingleResponse(String singleResponse) {
		this.singleResponse = singleResponse;
	}

}