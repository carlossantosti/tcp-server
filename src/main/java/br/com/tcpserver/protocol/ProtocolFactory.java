package br.com.tcpserver.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Classe implementada a partir da interface ProtocolCodecFactory.
 * Contendo o encoder e decoder da nossa aplicação.
 *
 * @author Carlos Santos
 */
public class ProtocolFactory implements ProtocolCodecFactory {
	
	private final ProtocolEncoderSeg encoder;
    private final ProtocolDecoderSeg decoder;
 
    public ProtocolFactory() {
    	encoder = new ProtocolEncoderSeg();
        decoder = new ProtocolDecoderSeg();
    }

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
