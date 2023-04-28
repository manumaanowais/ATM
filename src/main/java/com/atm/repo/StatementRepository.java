package com.atm.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.Statement;

public interface StatementRepository extends JpaRepository<Statement, Long>{
	public Set<Statement> findByUid(Long id);
}
