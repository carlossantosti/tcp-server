package br.com.tcpserver.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

import br.com.tcpserver.protocol.model.ProtocolModel;
import br.com.tcpserver.util.Utils;

/**
 * Modelo responsável por modelar os dados do frame A3
 * 
 * @author Carlos Santos
 */
public class A3DateTime {
	private int day;
	private int month;
	private int year;
	private int hour;
	private int minute;
	private int seconds;
	
	public A3DateTime(int[] data) {
		try {
			ZoneId zone = ZoneId.of(Utils.getTextFromData(data));
			LocalDateTime localDateTime = LocalDateTime.now(zone);
			
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(localDateTime.getYear(), localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth(),
		              localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
			
			this.day = cal.get(Calendar.DAY_OF_MONTH);
			this.month = cal.get(Calendar.MONTH) + 1;
			this.year = cal.get(Calendar.YEAR);
			this.hour = cal.get(Calendar.HOUR_OF_DAY);
			this.minute = cal.get(Calendar.MINUTE);
			this.seconds = cal.get(Calendar.SECOND);
		} catch (Exception e) {
			this.day = -1;
			this.month = -1;
			this.year = -1;
			this.hour = -1;
			this.minute = -1;
			this.seconds = -1;
			
			e.printStackTrace();
		}
	}
    	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * Tranforma o modelo em um protocolo.
	 * 
	 * @return protocolo a ser enviado ao cliente.
	 */
	public ProtocolModel getProtocolModel() {
		int[] data = {(byte) day, (byte) month, (byte) year, (byte) hour, (byte) minute, (byte) seconds};
		return new ProtocolModel(0x0A, 0x0B, 0xA3, data, 0x9C, 0x0D);
	}
}
