package com.example.controller;


import com.example.domain.ToDo;
import com.example.repository.ToDoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private ToDoRepository repository;

    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDo>> getAllToDo() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("todo/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable String id) {
        Optional<ToDo> ToDoById = repository.findById(id);
        return ToDoById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id) {
        Optional<ToDo> toDo = repository.findById(id);
        if (toDo.isEmpty())
            return ResponseEntity.notFound().build();
        ToDo result = toDo.get();
        result.setCompleted(true);
        repository.save(result);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.ok()
                .header("Location", location.toString())
                .build();
    }
}
