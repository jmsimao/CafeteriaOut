package br.com.josemarcelo.CafeteriaOut.ErrorResponse;

public class FoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FoundException(String mensagem) {
		super(mensagem);
	}

}
