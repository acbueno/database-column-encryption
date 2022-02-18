package br.com.acbueno.database.columnencryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.transaction.TransactionScoped;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import br.com.acbueno.database.columnencryption.entity.Person;
import br.com.acbueno.database.columnencryption.repository.PersonRepository;

@SpringBootTest
public class ColumnEncryptionTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ColumnEncryptionTest.class);

	private static final String NAME = "Rambo";

	private static final int AGE = 70;

	private static final String CPF = "123.123.123-12";

	private static final String RG = "12.123.123-0";

	private long id;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PersonRepository personRepository;

	@Test
	@TransactionScoped
	public void setUp() {
		Person person = createPerson();
		Optional<Person> findOptional = personRepository.findById(person.getId());
		assertEquals(person.getRg(), findOptional.get().getRg());
		LOGGER.info(
				String.format("RG from Entity %s, RG from Database %s", person.getRg(), findOptional.get().getRg()));
	}

	private Person createPerson() {
		Person person = new Person();
		person.setName(NAME);
		person.setAge(AGE);
		person.setCpf(NAME);
		person.setCpf(CPF);
		person.setRg(RG);
		personRepository.save(person);
		return person;
	}

	@Test
	public void readEncrypted() throws SQLException {
		Person createPerson = createPerson();
		Connection con = connectionJDBC();
		ResultSet rs = selectJDBC(createPerson, con);
		String personRg = rs.getString("rg");
		assertNotEquals(createPerson.getRg(), personRg);
	}

	private ResultSet selectJDBC(Person createPerson, Connection con) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("select * from person where id = ?");
		stmt.setLong(1, createPerson.getId());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs;
	}

	private Connection connectionJDBC() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:h2:mem:db", "sa", "");
		return con;
	}
	
}
