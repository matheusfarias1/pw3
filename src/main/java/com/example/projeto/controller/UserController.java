package com.example.projeto.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.model.UserModel;
import com.example.projeto.service.UserService;
import com.example.projeto.dtos.UserDTO;
import com.example.projeto.dtos.UserDTOResposta;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDTOResposta> salvar(@RequestBody UserDTO dto) {
        UserModel model = service.insert(dto.transformaParaObjeto());
        return new ResponseEntity<>(UserDTOResposta.transformaEmDTO(model), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTOResposta>> getAllUsers() {
        List<UserModel> listaNormal = service.getAll();
        List<UserDTOResposta> listaDtos = listaNormal.stream().map(usuario -> new UserDTOResposta(usuario))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listaDtos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserModel> find(@PathVariable Integer id) {
        UserModel model = service.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody UserModel model, @PathVariable Integer id) {
        model.setId(id);
        model = service.update(model);
        return ResponseEntity.noContent().build();
    }

}
