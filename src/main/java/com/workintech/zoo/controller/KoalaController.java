package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Gender;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/koalas")
    public class KoalaController {

        private Map<Integer, Koala> koalas;

        @PostConstruct
        public void init(){
            koalas = new HashMap<>();
            koalas.put(1,new Koala(1, "Lena",35, Gender.FEMALE, 16));
        }

        @GetMapping("/")
        @ResponseStatus(HttpStatus.OK)
        public List<Koala> findAll(){
            return koalas.values().stream().toList();
        }

        @GetMapping("/{id}")
        public Koala find(@PathVariable int id){
            ZooValidation.isIdValid(id);
            ZooValidation.checkKoalaExistence(koalas, id, false);
            return koalas.get(id);
        }

        @PostMapping("/")
        public Koala save(@RequestBody Koala koala){
            ZooValidation.isIdValid(koala.getId());
            ZooValidation.checkKoalaExistence(koalas, koala.getId(), true);
            ZooValidation.checkKoalaWeight(koala.getWeight());
            koalas.put(koala.getId(), koala);
            return koala;
        }

        @PutMapping("/{id}")
        public Koala update(@PathVariable int id, @RequestBody Koala koala){
            ZooValidation.isIdValid(id);
            ZooValidation.checkKoalaWeight(koala.getWeight());
            koala.setId(id);
            if(koalas.containsKey(id)){
                koalas.put(id, koala);
                return koala;
            } else {
                return save(koala);
            }
        }

        @DeleteMapping("/{id}")
        public Koala remove(@PathVariable int id){
            ZooValidation.isIdValid(id);
            ZooValidation.checkKoalaExistence(koalas, id, false);
            return koalas.remove(id);
        }

    }

