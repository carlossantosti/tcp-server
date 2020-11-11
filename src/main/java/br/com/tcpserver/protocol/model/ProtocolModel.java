package br.com.tcpserver.protocol.model;

import java.util.Arrays;

import br.com.tcpserver.protocol.service.ProtocolService;
import br.com.tcpserver.util.Utils;

/**
 * Classe modelo do protocolo.
 * Contém todos os atributos contidos no protocolo e como tratá-los.
 * 
 * @author Carlos Santos
 */
public class ProtocolModel {
	private int init;
	private int bytes;
	private int frame;
	private int[] data = {};
	private int crc;
	private int end;
	private String textToCheck;
	private String completeTextProtocol;
	
	public ProtocolModel(int init, int bytes, int frame, int[] data, int crc, int end) {
		super();
		this.init = init;
		this.bytes = bytes;
		this.frame = frame;
		this.data = data;
		this.crc = crc;
		this.end = end;
	}

	public ProtocolModel(String protocolText) {
		if (protocolText != null && protocolText.toString() != null) {
			this.completeTextProtocol = protocolText;
			
			String str[] = protocolText.toString().split(" ");
			String[] dataText = Arrays.copyOfRange(str, 3, str.length-2);
			
			this.init = Integer.parseInt(str[0], 16);
			this.bytes = Integer.parseInt(str[1], 16);
			this.frame = Integer.parseInt(str[2], 16);
			
			this.textToCheck = str[1] + "" + str[2];
			for (int i = 0; i < dataText.length; i++) {
				this.textToCheck += "" + dataText[i];
				this.data = Utils.addIntElement(this.data, Integer.parseInt(dataText[i], 16));
			}
			
			this.crc = Integer.parseInt(str[str.length-2], 16);
			this.end = Integer.parseInt(str[str.length-1], 16);
		}
	}

	public int getInit() {
		return init;
	}

	public int getBytes() {
		return bytes;
	}

	public int getFrame() {
		return frame;
	}

	public int[] getData() {
		return data;
	}

	public int getCrc() {
		return crc;
	}

	public int getEnd() {
		return end;
	}
	
	public String getTextToCheck() {
		return textToCheck;
	}

	public String getCompleteTextProtocol() {
		return completeTextProtocol;
	}

	/**
	 * Obtem o cálculo do CRC8 para o modelo e verifica se confere com o CRC enviado pelo cliente.
	 * @return retorna um boolean informando se o crc confere ou não.
	 */
	public boolean checkCRC8() {
		if (this.getTextToCheck() != null) {
			int crc = ProtocolService.getCRC8FromAPI(this.getTextToCheck());
			return this.getCrc() == crc;
		}
		
		return false;
	}
	
	/**
	 * Transforma o modelo todo em um array de bytes.
	 * 
	 * @return retona um array de bytes contendo todo o protocolo.
	 */
	public byte[] getByteArray() {
		byte[] array = {(byte) init, (byte) bytes, (byte) frame};
		for (int i = 0; i < data.length; i++)
			array = Utils.addByteElement(array, data[i]);
		array = Utils.addByteElement(array, crc);
		array = Utils.addByteElement(array, end);
		return array;
	}
	
}
