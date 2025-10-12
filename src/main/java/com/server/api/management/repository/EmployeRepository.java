package com.server.api.management.repository;

import com.server.api.management.entity.Employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

    Optional<Employe> findByIdAndEntrepriseId(Long entrepriseId, Long employeId);
    Page<Employe> findAllByEntrepriseId(Long entrepriseId , Pageable pageable);

    @Query(value = "SELECT min(emp.salary) FROM employe emp where emp.contract_type = :contractType and emp.entreprise_id = :entrepriseId", nativeQuery = true)
    BigDecimal min(@Param("entrepriseId") Long entrepriseId,@Param("contractType") String contractType);

    @Query(value = "SELECT max(emp.salary) FROM employe emp where emp.contract_type = :contractType and emp.entreprise_id = :entrepriseId" , nativeQuery = true)
    BigDecimal max(@Param("entrepriseId") Long entrepriseId,@Param("contractType") String contractType);

    @Query(value = "SELECT avg(emp.salary) FROM employe emp where emp.contract_type = :contractType and emp.entreprise_id = :entrepriseId" , nativeQuery = true)
    BigDecimal moyen(@Param("entrepriseId") Long entrepriseId,@Param("contractType") String contractType);

    @Query(value ="select * from employe emp " +
            " where (emp.salary) like concat((:search),'%') " +
            " or  (emp.entreprise_id) like concat((:search),'%') " +
            " or  (emp.hiring_date) like concat((:search),'%') " +
            " or (emp.contract_type) like concat((:search),'%')"
            ,nativeQuery = true)
    Page<Employe> filterEmployes(@Param("search") String search, Pageable pageable);
}