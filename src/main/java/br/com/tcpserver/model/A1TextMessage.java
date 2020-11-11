package br.com.tcpserver.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.tcpserver.util.Utils;

/**
 * Modelo responsável por armazenar os dados do frame A1
 * 
 * @author Carlos Santos
 */
@Entity
@Table(name = "TEXT_MESSAGE")
public class A1TextMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "message")
	private String message;
	
	@Column(name = "received_hour")
	private LocalTime receivedHour;

	public A1TextMessage() {
	}

	public A1TextMessage(int[] data, LocalTime receivedHour) {
		super();
		this.message = Utils.getTextFromData(data);
		this.receivedHour = receivedHour;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalTime getReceivedHour() {
		return receivedHour;
	}

	public void setReceivedHour(LocalTime receivedHour) {
		this.receivedHour = receivedHour;
	}
	
	@Override
    public String toString() {
        return "A1TextMessage [id=" + id + ", mensagem=" + message + ", hora do recebimento=" + receivedHour + "]";
    }
}
