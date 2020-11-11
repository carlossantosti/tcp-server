package br.com.tcpserver.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Esta classe foi criada para consultar uma api de cálculo do CRC e tratar a sua resposta.
 * 
 * @author Carlos Santos
 */
public class Crc8Api {
	
	/**
	 * Esse método foi criado para consultar uma API passando as informações
	 * necessárias para o cálculo do CRC, e retornar o resultado em hexadecimal ou em decimal.
	 * 
	 * @param crcValue - Junção dos campos Bytes, Frames e Data (o tipo deve ser informado no próximo campo).
	 * @param dataType - Tipo do crcValue, que pode ser: "ASCII" ou "hex"
	 * @param outType - Tipo de retorno do CRC que pode ser: "hex" ou "dec"
	 * @return CRC - valor cálculado do CRC para certificar que os dados estão corretos.
	 */
	public static String getCRC8FromAPI(String crcValue, String dataType, String outType) {

		HttpPost post = new HttpPost("https://crccalc.com/crc/crccalc.php");

		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("crc", crcValue));
		urlParameters.add(new BasicNameValuePair("method", "crc8"));
		urlParameters.add(new BasicNameValuePair("datatype", dataType));
		urlParameters.add(new BasicNameValuePair("order", "0"));
		urlParameters.add(new BasicNameValuePair("outtype", "hex"));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			String result = EntityUtils.toString(response.getEntity());
			return decode(result, outType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Método utilizado para decodificar o retorno da API 
	 * e identificar onde está o CRC
	 * 
	 * @param result - Dado completo do retorno que deve ser processado.
	 * @param outType - Tipo em que deve ser devolvido a informação processada ("hex" ou "dec").
	 * @return CRC - valor cálculado do CRC para certificar que os dados estão corretos.
	 */
	private static String decode(String result, String outType) {
		try {
			int start = result.indexOf("CRC-8                </a>");
			if (start != -1) {
				result = result.substring(start);
				
				start = result.indexOf("0x");
				result = result.substring(start);
				
				result = result.substring(0, result.indexOf(" "));
				result = result.trim();
			}
			
			if (outType.equals("dec")) {
				result = result.replace("0x", "");
				int resultInt = Integer.parseInt(result, 16);
				return String.valueOf(resultInt);
			}
			
			if (result.length() <= 4)
				return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
