package br.com.tcpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import br.com.tcpserver.protocol.ProtocolFactory;

/**
 * Classe principal, inicia e configura o servidor.
 *
 * @author Carlos Santos
 */
public class Main {
	private static final Logger logger = LogManager.getLogger();
	private static final int PORT = 9123;
	
	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ProtocolFactory()));
        
        acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));
        acceptor.setHandler(new Handler());
        acceptor.bind();
        
        logger.info("TCP Server started on: "+acceptor.getDefaultLocalAddress().toString());
    }
}
