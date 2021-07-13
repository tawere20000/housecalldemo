package com.example.demo.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.bean.ViolationRule;

@Repository
public interface ViolationRuleRepository extends JpaRepository<ViolationRule,Integer> {

}
