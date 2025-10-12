package com.server.api.management.service;

import com.server.api.management.entity.Employe;
import com.server.api.management.entity.Entreprise;
import com.server.api.management.enums.ContractType;
import com.server.api.management.exception.ResourceNotFoundException;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

        employe = new Employe();
        employe.setId(1L);
        employe.setFirstName("John");
        employe.setLastName("Doe");
        employe.setContractType(ContractType.CDI);
        employe.setEntreprise(entreprise);
        employe.setSalary(BigDecimal.valueOf(3000));
    }

    @Test
    void testGetEmployeById_found() {
        when(employeRepository.findById(1L)).thenReturn(Optional.of(employe));

        Employe result = employeService.getEmployeById(1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetEmployeById_notFound() {
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
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testUpdateEmploye_notFoundEntreprise() {
        when(entrepriseRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> employeService.updateEmploye(1L, employe));
    }

    @Test
    void testDeleteEmploye_success() {
        when(employeRepository.findByIdAndEntrepriseId(1L, 1L)).thenReturn(Optional.of(employe));

        Employe result = employeService.deleteEmploye(1L, 1L);

        assertNotNull(result);
        verify(employeRepository).delete(employe);
    }

    @Test
    void testDeleteEmploye_notFound() {
        when(employeRepository.findByIdAndEntrepriseId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeService.deleteEmploye(1L, 1L));
    }

    @Test
    void testGetSalaryByEntrepriseIdAndContractType_min() {
        when(employeRepository.min(1L, "CDI")).thenReturn(BigDecimal.valueOf(2000));

        BigDecimal salary = employeService.getSalaryByEntrepriseIdAndContractType(1L, "CDI", "min");

        assertEquals(BigDecimal.valueOf(2000), salary);
    }

    @Test
    void testGetSalaryByEntrepriseIdAndContractType_max() {
        when(employeRepository.max(1L, "CDI")).thenReturn(BigDecimal.valueOf(5000));

        BigDecimal salary = employeService.getSalaryByEntrepriseIdAndContractType(1L, "CDI", "max");

        assertEquals(BigDecimal.valueOf(5000), salary);
    }

    @Test
    void testGetSalaryByEntrepriseIdAndContractType_moyen() {
        when(employeRepository.moyen(1L, "CDI")).thenReturn(BigDecimal.valueOf(3000));

        BigDecimal salary = employeService.getSalaryByEntrepriseIdAndContractType(1L, "CDI", "moy");

        assertEquals(BigDecimal.valueOf(3000), salary);
    }

    @Test
    void testFilterEmployes() {
        when(employeRepository.filterEmployes("John")).thenReturn(List.of(employe));

        List<Employe> result = employeService.filterEmployes("John");

        assertEquals(1, result.size());
    }
}
