package gt.edu.umg.invoice.utils;

public enum ResponseResult {

	fail("error"), success("OK"), warning("warning");
	private final String value;

	private ResponseResult(String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}

	public String getMessage() {
		String msg;
		switch (value.toLowerCase()) {
		case "ok":
			msg = "Proceso completado con éxito";
			break;
		case "warning":
			msg = "El proceso se ejecuto con errores";
			break;
		default:
			msg = "Error al realizar el proceso, favor de intentarlo en otro momento";
			break;
		}
		return msg;
	}

}
