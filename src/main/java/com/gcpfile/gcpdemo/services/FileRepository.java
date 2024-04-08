package com.gcpfile.gcpdemo.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gcpfile.gcpdemo.entity.FileData;


@Repository
public interface FileRepository extends CrudRepository<FileData, Long> {


}
