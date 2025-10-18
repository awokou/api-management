package com.server.api.management.service;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.exception.ResourceNotFoundException;
import com.server.api.management.repository.EntrepriseRepository;
import com.server.api.management.service.impl.EntrepriseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrepriseServiceImplTest {

    @InjectMocks
    private EntrepriseServiceImpl entrepriseService;

    @Mock
    private EntrepriseRepository entrepriseRepository;

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
    }

    @Test
    void testGetEntrepriseById_found() {
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(entreprise));
        Entreprise result = entrepriseService.getEntrepriseById(1L);
        assertNotNull(result);
        assertEquals("SOPRA STERIA", result.getSocialReason());
        verify(entrepriseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEntrepriseById_notFound() {
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> entrepriseService.getEntrepriseById(1L));
    }

    @Test
    void testGetAllEntreprises() {
        List<Entreprise> entreprises = List.of(entreprise);
        when(entrepriseRepository.findAll()).thenReturn(entreprises);
        List<Entreprise> result = entrepriseService.getAllEntreprises();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("SOPRA STERIA", result.getFirst().getSocialReason());
        verify(entrepriseRepository, times(1)).findAll();
    }

    @Test
    void testCreateEntreprise() {
        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(entreprise);
        Entreprise result = entrepriseService.createEntreprise(entreprise);
        assertNotNull(result);
        assertEquals("SOPRA STERIA", result.getSocialReason());
        verify(entrepriseRepository, times(1)).save(entreprise);
    }

    @Test
    void testUpdateEntreprise_found() {
        Entreprise updateRequest = new Entreprise();
        updateRequest.setAddress("124 Street");
        updateRequest.setSiren("987654321");
        updateRequest.setSiret("98765432100022");
        updateRequest.setSocialReason("CIS");

        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(entreprise));
        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(entreprise);
        Entreprise result = entrepriseService.updateEntreprise(1L, updateRequest);
        assertNotNull(result);
        assertEquals("124 Street", result.getAddress());
        assertEquals("CIS", result.getSocialReason());
        verify(entrepriseRepository).save(entreprise);
    }

    @Test
    void testUpdateEntreprise_notFound() {
        Entreprise updateRequest = new Entreprise();
        updateRequest.setId(1L);
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> entrepriseService.updateEntreprise(1L, updateRequest));
    }

    @Test
    void testDeleteEntreprise_found() {
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(entreprise));
        Entreprise result = entrepriseService.deleteEntreprise(1L);
        assertNotNull(result);
        verify(entrepriseRepository).delete(entreprise);
    }

    @Test
    void testDeleteEntreprise_notFound() {
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> entrepriseService.deleteEntreprise(1L));
    }
}
