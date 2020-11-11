package br.com.tcpserver.protocol;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;

/**
 * Encoder para codificar a nossa mensagem da maneira que desejamos.
 * 
 * @author Carlos Santos
 */
public class ProtocolEncoderSeg implements ProtocolEncoder {
	private static final Logger logger = LogManager.getLogger();

	private static final AttributeKey ENCODER = new AttributeKey(ProtocolEncoderSeg.class, "encoder");

	private final Charset charset = Charset.forName("UTF-8");

	private int maxLineLength = Integer.MAX_VALUE;

	private final LineDelimiter delimiter = LineDelimiter.WINDOWS;

	/**
	 * Método para encodificar dados para um protocolo específico.
	 * 
	 * @param session sessão atual.
	 * @param message a mensagem para codificar.
	 * @param out     o {@link ProtocolEncoderOutput} que receberá a mensagem
	 *                encodificada.
	 * @throws Exception se a mensagem violou o especificação do protocolo.
	 * 
	 */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		CharsetEncoder encoder = (CharsetEncoder) session.getAttribute(ENCODER);

		if (encoder == null) {
			encoder = charset.newEncoder();
			session.setAttribute(ENCODER, encoder);
		}

		String value = message == null ? "" : message.toString();
		IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);
		buf.putString(value, encoder);

		if (buf.position() > maxLineLength) {
			logger.error("Erro tamanho excedido: " + buf.position());
			throw new IllegalArgumentException("Tamanho da Linha: " + buf.position());
		}

		buf.putString(delimiter.getValue(), encoder);
		buf.flip();
		out.write(buf);

		logger.info("Mensagem encodificada: " + message.toString());
	}

	/**
	 * Libera todos os recursos relacionados ao encoder.
	 *
	 * @param session a sessão atual.
	 * @throws Exception se falhar ao liberar os recursos.
	 */
	@Override
	public void dispose(IoSession session) throws Exception {
		session.closeNow();
	}

}
