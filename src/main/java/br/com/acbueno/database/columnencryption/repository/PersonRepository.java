package br.com.acbueno.database.columnencryption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import br.com.acbueno.database.columnencryption.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {



}
