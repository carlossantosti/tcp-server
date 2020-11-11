package br.com.tcpserver.protocol.service;

import br.com.tcpserver.util.Crc8Api;

/**
 * Utilizada para controlar requisições externas da aplicação.
 * 
 * @author Carlos Santos
 */
public class ProtocolService {
	
	/**
	 * Obtem o CRC através da API e devolve no tipo necessário.
	 * 
	 * @param crcValue - Junção dos campos Bytes, Frames e Data (o tipo deve ser informado no próximo campo).
	 * @return CRC - valor cálculado do CRC para certificar que os dados estão corretos.
	 */
	public static int getCRC8FromAPI(String crcValue) {
		String crc = Crc8Api.getCRC8FromAPI(crcValue, "hex", "dec");
		return Integer.parseInt(crc);
	}
}
