package com.example.demo.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.ViolationRef;

@Repository
public interface ViolationRefRepository extends JpaRepository<ViolationRef, Integer>{

}
