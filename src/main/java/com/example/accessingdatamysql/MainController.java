package com.example.accessingdatamysql;

import javax.management.ConstructorParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;
import java.lang.String;
import java.lang.Object;

@Controller
@RequestMapping(path="/mascotas")
public class MainController {
  @Autowired 

  private ClienteRepository clienteRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;



  @PostMapping(path="/add") // POST http://localhost:8080/mascotas/add
  public @ResponseBody String addNewCliente (@RequestParam String nombre
      , @RequestParam String raza
      , @RequestParam String propietario) {

    Cliente n = new Cliente();
    n.setNombre(nombre);
    n.setRaza(raza);
    n.setPropietario(propietario);
    clienteRepository.save(n);
    return "Saved";
  }

  @GetMapping(path="/all") // GET http://localhost:8080/mascotas/all
  public @ResponseBody Iterable<Cliente> getAllClientes() {
    return clienteRepository.findAll();
  }

  @GetMapping(path="/ver/{id}") // GET http://localhost:8080/mascotas/all
  public @ResponseBody Cliente getCliente(@PathVariable("id") Integer id) {
    return clienteRepository.findById(id).orElse(null);
  }

  @PutMapping(path="/edit")
  public @ResponseBody String editCliente(@RequestParam Integer id, @RequestParam String nombre, @RequestParam String raza, @RequestParam String propietario) {
    Cliente cliente = clienteRepository.findById(id).orElse(null);
    if (cliente != null) {
      cliente.setNombre(nombre);
      cliente.setRaza(raza);
      cliente.setPropietario(propietario);
      clienteRepository.save(cliente);
      return "Edited";
    }
    return "Not found";
  }

  @DeleteMapping(path="/del")
  public @ResponseBody String deleteCliente(@RequestParam Integer id) {
    Cliente cliente = clienteRepository.findById(id).orElse(null);
    if (cliente != null) {
      clienteRepository.delete(cliente);
      return "Deleted";
    }
    return "Not found";
  }

  @GetMapping(path="/get/report")
  public @ResponseBody List getReport() {
    String sql = "SELECT CONCAT(name, ' ==> ', email) as reporte FROM cliente";
    List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(sql);
    return queryResult;
  }


}