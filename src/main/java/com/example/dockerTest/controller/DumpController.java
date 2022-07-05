package com.example.dockerTest.controller;

import com.example.dockerTest.DumpRepository;
import com.example.dockerTest.entity.Dump;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dump")
public class DumpController {

  @Autowired
  private DumpRepository dumpRepository;

  @GetMapping("/new/{dump}")
  public void newDump(@PathVariable String dump){
    Dump dumpEntity = new Dump(dump);
    dumpRepository.save(dumpEntity);
  }

  @GetMapping
  public ResponseEntity getDump(){
    List<Dump> dumpList = (List<Dump>) dumpRepository.findAll();
    return ResponseEntity.ok().body(dumpList);
  }
}
