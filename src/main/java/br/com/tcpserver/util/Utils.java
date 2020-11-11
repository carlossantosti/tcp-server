package br.com.tcpserver.util;

/**
 * Classe para adicionar métodos simples e úteis para a aplicação.
 * 
 * @author Carlos Santos
 */
public class Utils {
	
	/**
	 * Conversão dos dados em bytes/hexadecimal transferidos no protocolo para texto.
	 * 
	 * @param data - array contendo os dados transferido no protocolo.
	 * @return string - contendo os dados convertidos.
	 */
	public static String getTextFromData(int[] data) {
		String text = "";
		for (int i = 0; i < data.length; i++)
			text += (char) data[i];
		return text;
	}
	
	/**
	 * Adiciona um elemento inteiro ao array.
	 * 
	 * @param arr - vetor de inteiros.
	 * @param element - um inteiro a ser inserido no array.
	 * @return um novo array com os dados do antigo acrescido do novo dado.
	 */
	public static int[] addIntElement(int [] arr, int element) {
		int i;
		int n = arr.length;
		  
        int newarr[] = new int[n + 1];
        
        for (i = 0; i < n; i++) 
            newarr[i] = arr[i]; 
  
        newarr[n] = element; 
  
        return newarr;
	}
	
	/**
	 * Adiciona um elemento byte ao array.
	 * 
	 * @param arr - vetor de bytes.
	 * @param element - um byte a ser inserido no array.
	 * @return um novo array com os dados do antigo acrescido do novo dado.
	 */
	public static byte[] addByteElement(byte [] arr, int element) {
		int i;
		int n = arr.length;
		  
		byte newarr[] = new byte[n + 1];
        
        for (i = 0; i < n; i++) 
            newarr[i] = arr[i]; 
  
        newarr[n] = (byte) element; 
  
        return newarr;
	}
}
