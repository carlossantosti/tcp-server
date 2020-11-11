package br.com.tcpserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.tcpserver.util.Utils;

/**
 * Modelo responsável por armazenar os dados do frame A2
 * 
 * @author Carlos Santos
 */
@Entity(name = "USUARIO")
public class A2UserData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "weight")
	private int weight;
	
	@Column(name = "height")
	private int height;
	
	@Column(name = "name_size")
	private int nameSize;
	
	@Column(name = "name")
	private String name;
	
	public A2UserData() {
	}

	public A2UserData(int[] data) {
		super();
		this.age = (char) data[0];
		this.weight = (char) data[1];
		this.height = (char) data[2];
		this.nameSize = (char) data[3];
		int[] name = {};
		for (int i = 4; i < data.length; i++)
			name = Utils.addIntElement(name, data[i]);
		this.name = Utils.getTextFromData(name);
	}
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getNameSize() {
		return nameSize;
	}

	public void setNameSize(int nameSize) {
		this.nameSize = nameSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
        return "UserData [id=" + id + ", nome=" + name + ", idade=" + age + ", peso=" + weight + " , altura=" + height + " , tamanho do nome=" + nameSize +"]";
    }
	
}
