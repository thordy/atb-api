package com.thord.atb.exception;

public class AtBException extends Exception {
	private static final long serialVersionUID = -8011002574356722373L;

	public AtBException() {
		super();
	}

	public AtBException(String message) {
		super(message);
	}

	public AtBException(Throwable thrw) {
		super(thrw);
	}

	public AtBException(String message, Throwable thrw) {
		super(message, thrw);
	}
}
