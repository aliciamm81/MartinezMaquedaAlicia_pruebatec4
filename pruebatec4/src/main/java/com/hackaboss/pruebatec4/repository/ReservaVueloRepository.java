package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.ReservaVuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaVueloRepository extends JpaRepository<ReservaVuelo, Long> {

}
