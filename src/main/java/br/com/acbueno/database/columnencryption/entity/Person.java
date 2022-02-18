package br.com.acbueno.database.columnencryption.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.acbueno.database.columnencryption.encrypter.AttributeEncryptor;
import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "nome")
	private String name;
	
	@Column(name = "idade")
	private Integer age;
	
	@Column(name = "cpf")
	@Convert(converter = AttributeEncryptor.class)
	private String cpf;
	
	@Column(name = "rg")
	@Convert(converter = AttributeEncryptor.class)
	private String rg;

}
