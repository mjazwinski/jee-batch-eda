package pl.spiralarchitect.kplan.eda.consumer;

import javax.jms.JMSException;

public class MessageProcessingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageProcessingException(JMSException e) {
		super(e);
	}

}
