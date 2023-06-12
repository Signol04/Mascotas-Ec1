package com.example.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

import com.example.accessingdatamysql.Cliente;


public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

}