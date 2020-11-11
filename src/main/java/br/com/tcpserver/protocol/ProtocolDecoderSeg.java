package br.com.tcpserver.protocol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import br.com.tcpserver.protocol.model.ProtocolModel;

/**
 * Classe responsável por decodificar o protocolo ao ser recebido do cliente Em
 * seguida enviar para o tratador (handler).
 * 
 * @author Carlos Santos
 */
public class ProtocolDecoderSeg extends CumulativeProtocolDecoder {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Método que decodifica a mensagem recebida do cliente.
	 * 
	 * @param session a sessão atual.
     * @param in um buffer acumulativo.
     * @param out o {@link ProtocolDecoderOutput} que receberá a mensagem decodificada.
     * @return <tt>true</tt> se e somente se houver mais dados para decodificar no buffer
     * 						 e você deseja que o método <tt> doDecode </tt> seja chamado novamente.
     * 						 Retorna <tt> falso </tt> se os dados restantes não forem suficientes para decodificar,
     * 						 então este método será chamado novamente quando mais dados forem
     * 						 acumulados.
     * @throws Exception se não decodificar <tt>in</tt>.
	 */
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		StringBuffer buffer = new StringBuffer(in.getHexDump());
		ProtocolModel pm = new ProtocolModel(buffer.toString());

		if (pm.checkCRC8())
			out.write(pm);
		else {
			out.write(buffer.toString());
			logger.error("Erro ao receber dados ou CRC8 não confere: "+buffer.toString());
		}
		
		return false;
	}

}
