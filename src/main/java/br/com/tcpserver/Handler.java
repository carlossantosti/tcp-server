package br.com.tcpserver;

import java.io.IOException;
import java.time.LocalTime;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import br.com.tcpserver.hibernate.HibernateProcess;
import br.com.tcpserver.model.A0ACK;
import br.com.tcpserver.model.A1TextMessage;
import br.com.tcpserver.model.A2UserData;
import br.com.tcpserver.model.A3DateTime;
import br.com.tcpserver.protocol.model.ProtocolModel;

/**
 * Classe responsável por trabalhar a informação decodificada em formato de protocolo.
 * 
 * @author Carlos Santos
 */
public class Handler extends IoHandlerAdapter {
	private static final Logger logger = LogManager.getLogger();

	/**
     * Chamado quando qualquer exceção é lançada pelo {@link IoHandler}
     * implementação ou pela MINA. Se a <code> causa </code> for uma instância de
     * {@link IOException}, MINA fechará a conexão automaticamente.
     *
     * @param session a sessão para a qual temos uma exceção.
     * @param cause a exceção que foi capturada.
     * @throws Exception se obtivermos uma exceção durante o processamento da exceção capturada.
    */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	/**
     * Chamado quando uma mensagem é recebida.
     * 
     * @param session a sessão que está recebendo a mensagem.
     * @param message a mensagem recebida.
     * @throws Exception se houver alguma exceção ao receber a mensagem.
     */
	@Override
	public void messageReceived(IoSession session, Object objPm) throws Exception {
		if (objPm != null && objPm.toString() != null) {			
			ProtocolModel pm = null;
			try {
				pm = (ProtocolModel) objPm;
			} catch(Exception e) {
				session.write(objPm.toString());
				logger.error("Mensagem não confere com o protocolo");
				return;
			}
			
			logger.log(Level.getLevel("MSG"), "Mensagem recebida: "+pm.getCompleteTextProtocol());
			
			switch(pm.getFrame()) {
				case 0xA1:
					logger.info("A1 - Mensagem de texto");
					
					session.write(A0ACK.ACK);
					A1TextMessage a1 = new A1TextMessage(pm.getData(), LocalTime.now());
					logger.info(a1.toString());
					HibernateProcess.save(a1);
				break;
				case 0xA2:
					logger.info("A2 - Informações do usuário");
					
					session.write(A0ACK.ACK);
					A2UserData a2 = new A2UserData(pm.getData());
					logger.info(a2.toString());
					HibernateProcess.save(a2);
				break;
				case 0xA3:
					logger.info("A3 - Solicitar data e hora");
					
					ProtocolModel pmAnswer = new A3DateTime(pm.getData()).getProtocolModel();
					session.write(pmAnswer.getByteArray());
				break;
			}
			
			session.closeNow();
			
		} else {
			logger.error("Mensagem não recebida");
		}
	}

	/**
     * Chamado com o {@link IdleStatus} relacionado quando uma conexão fica inativa.
     * 
     * @param session a sessão inativa.
     * @param status o status da sessão.
     * @throws Exception se obtivermos uma exceção durante o processamento do evento inativo.
     */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.info("IDLE " + session.getIdleCount(status));
	}
}
