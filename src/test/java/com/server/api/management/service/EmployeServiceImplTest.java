package com.server.api.management.service;

import com.server.api.management.entity.Employe;
import com.server.api.management.entity.Entreprise;
import com.server.api.management.enums.ContractType;
import com.server.api.management.exception.ResourceNotFoundException;
import com.server.api.management.exception.ValidationException;
import com.server.api.management.repository.EmployeRepository;
import com.server.api.management.repository.EntrepriseRepository;
import com.server.api.management.service.impl.EmployeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeServiceImplTest {

    @InjectMocks
    private EmployeServiceImpl employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Mock
    private EntrepriseRepository entrepriseRepository;

    private Employe employe;

    private Entreprise entreprise;

    @BeforeEach
    void setUp() {
        entreprise = new Entreprise();
        entreprise.setId(1L);
        entreprise.setAddress("123 Street");
        entreprise.setSiren("123456789");
        entreprise.setSiret("12345678900011");
        entreprise.setSocialReason("SOPRA STERIA");
        entreprise.setCreatedAt(new Date());

        employe = new Employe();
        employe.setId(1L);
        employe.setFirstName("John");
        employe.setLastName("Doe");
        employe.setContractType(ContractType.CDI);
        employe.setEntreprise(entreprise);
        employe.setSalary(BigDecimal.valueOf(3000));
    }

    @Test
    void testGetEmployeByIdSuccess() {
        when(employeRepository.findById(1L)).thenReturn(Optional.of(employe));
        Employe result = employeService.getEmployeById(1L);
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetEmployeByIdNotFound() {
        when(employeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeService.getEmployeById(1L));
    }

    @Test
    void testGetAllEmployes() {
        when(employeRepository.findAll()).thenReturn(List.of(employe));
        List<Employe> result = employeService.getAllEmployes();
        assertEquals(1, result.size());
    }

    @Test
    void testGetEmployesByEntrepriseId() {
        when(employeRepository.findAllByEntrepriseId(1L)).thenReturn(List.of(employe));
        List<Employe> result = employeService.getEmployesByEntrepriseId(1L);
        assertEquals(1, result.size());
        assertEquals("John", result.getFirst().getFirstName());
    }

    @Test
    void testUpdateEmployeSuccess() {
        employe.setContractType(ContractType.CDI);

        when(employeRepository.findById(1L)).thenReturn(Optional.of(employe));
        when(entrepriseRepository.existsById(1L)).thenReturn(true);
        when(employeRepository.save(any())).thenReturn(employe);

        Employe updated = new Employe();
        updated.setId(1L);
        updated.setContractType(ContractType.CDI);
        updated.setFirstName("Jane");
        updated.setLastName("Smith");
        updated.setSalary(BigDecimal.valueOf(3500));
        updated.setEntreprise(entreprise);

        Employe result = employeService.updateEmploye(1L, updated);
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
    }

    @Test
    void testUpdateEmployeInvalidContractType() {
        Employe old = new Employe();
        old.setId(1L);
        old.setContractType(ContractType.CDI);
        old.setSalary(BigDecimal.valueOf(3000));

        Employe update = new Employe();
        update.setId(1L);
        update.setContractType(ContractType.ALTERNANCE);
        update.setSalary(BigDecimal.valueOf(3000));
        update.setEntreprise(entreprise);

        when(employeRepository.findById(1L)).thenReturn(Optional.of(old));
        when(entrepriseRepository.existsById(1L)).thenReturn(true);

        assertThrows(ValidationException.class, () -> employeService.updateEmploye(1L, update));
    }

    @Test
    void testDeleteEmployeSuccess() {
        when(employeRepository.findByIdAndEntrepriseId(1L, 1L)).thenReturn(Optional.of(employe));
        Employe result = employeService.deleteEmploye(1L, 1L);
        assertNotNull(result);
        verify(employeRepository).delete(employe);
    }

    @Test
    void testDeleteEmployeNotFound() {
        when(employeRepository.findByIdAndEntrepriseId(1L, 1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeService.deleteEmploye(1L, 1L));
    }

    @Test
    void testGetSalaryByEntrepriseIdAndContractTypeMin() {
        when(employeRepository.min(1L, ContractType.CDI.name())).thenReturn(BigDecimal.valueOf(2000));
        BigDecimal salary = employeService.getSalaryByEntrepriseIdAndContractType(1L, ContractType.CDI.name(), "min");
        assertEquals(BigDecimal.valueOf(2000), salary);
    }

    @Test
    void testGetSalaryByEntrepriseIdAndContractTypeMax() {
        when(employeRepository.max(1L, ContractType.CDI.name())).thenReturn(BigDecimal.valueOf(5000));
        BigDecimal salary = employeService.getSalaryByEntrepriseIdAndContractType(1L, ContractType.CDI.name(), "max");
        assertEquals(BigDecimal.valueOf(5000), salary);
    }

    @Test
    void testCreateEmployeInvalidSalary() {
        employe.setSalary(BigDecimal.valueOf(-500));
        assertThrows(ValidationException.class, () -> employeService.createEmploye(1L, employe));
    }

    @Test
    void testGetSalaryByEntrepriseIdAndContractTypeMoyen() {
        when(employeRepository.moyen(1L, ContractType.CDI.name())).thenReturn(BigDecimal.valueOf(3000));
        BigDecimal salary = employeService.getSalaryByEntrepriseIdAndContractType(1L, ContractType.CDI.name(), "moy");
        assertEquals(BigDecimal.valueOf(3000), salary);
    }

    @Test
    void testFilterEmployes() {
        when(employeRepository.filterEmployes("John")).thenReturn(List.of(employe));
        List<Employe> result = employeService.filterEmployes("John");
        assertEquals(1, result.size());
    }
}
