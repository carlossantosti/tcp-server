package br.com.tcpserver.model;

/**
 * Modelo para manter o protocolo ACK.
 * 
 * @author Carlos Santos
 */
public class A0ACK {
	public static final byte[] ACK = new byte[] { (byte) 0x0A, (byte) 0x05, (byte) 0xA0, (byte) 0x28, (byte) 0x0D };
}
