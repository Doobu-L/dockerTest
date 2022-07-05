package com.example.dockerTest;

import com.example.dockerTest.entity.Dump;
import org.springframework.data.repository.CrudRepository;

public interface DumpRepository extends CrudRepository<Dump,Long> {

}
