package com.atm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{
	public Photo findByUid(Long id);
}
