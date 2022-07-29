package com.fpolydatn.api;

import com.fpolydatn.model.VaiTro;
import com.fpolydatn.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;


@RestController
@RequestMapping(value = "/vai-tro")
public class VaiTroController {

    @Autowired
    private VaiTroService vaiTroService;

    @GetMapping()
    public ResponseObject getList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size){
        int offset = page * size;
        return new ResponseObject(vaiTroService.getList(offset,size));
    }

    @PostMapping
    public ResponseObject addNew(@RequestBody CreateVaiTroRequest vaiTroRequest){
        return new ResponseObject(vaiTroService.addNew(vaiTroRequest));
    }


    @GetMapping("/{id}")
    public ResponseObject findByID(@PathVariable UUID id){
        return new ResponseObject(vaiTroService.findByID(id));
    }
    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable UUID id){
        return new ResponseObject(vaiTroService.delete(id));

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editProduct(@PathVariable("id") UUID id, @RequestBody CreateVaiTroRequest vaiTroRequest){
        return new ResponseEntity<>(vaiTroService.updateVaiTro( vaiTroRequest,id), HttpStatus.OK);

    }


}
