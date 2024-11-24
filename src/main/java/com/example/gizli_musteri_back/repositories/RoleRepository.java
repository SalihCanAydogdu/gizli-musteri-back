package com.example.gizli_musteri_back.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gizli_musteri_back.models.ERole;
import com.example.gizli_musteri_back.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
	  Optional<Role> findByName(ERole name);
}
