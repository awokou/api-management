package com.server.api.management.controller;

import com.server.api.management.entity.Entreprise;
import com.server.api.management.service.EntrepriseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class EntrepriseControllerTest {

    @Mock
    private EntrepriseService entrepriseService;

    @InjectMocks
    private EntrepriseController entrepriseController;

    @Test
    void testGetEntrepriseById_Success() {
        // Given
        Entreprise entreprise = new Entreprise();
        entreprise.setId(1L);
        entreprise.setSocialReason("Test Entreprise");

        // When
        when(entrepriseService.getEntrepriseById(1L)).thenReturn(entreprise);

        ResponseEntity<Entreprise> response = entrepriseController.getEntrepriseById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Entreprise", response.getBody().getSocialReason());
        verify(entrepriseService, times(1)).getEntrepriseById(1L);
    }

    @Test
    void testGetEntrepriseById_NotFound() {
        // Mock the service to return null to simulate not found
        when(entrepriseService.getEntrepriseById(1L)).thenReturn(null);

        ResponseEntity<Entreprise> response = entrepriseController.getEntrepriseById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetEntrepriseById_Exception() {
        when(entrepriseService.getEntrepriseById(anyLong())).thenThrow(new RuntimeException("Erreur"));

        ResponseEntity<Entreprise> response = entrepriseController.getEntrepriseById(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllEntreprises_Success() {

        Entreprise entreprise = new Entreprise();
        entreprise.setId(1L);
        entreprise.setSocialReason("Test Entreprise");

        List<Entreprise> entreprises = Arrays.asList(entreprise);
        when(entrepriseService.getAllEntreprises()).thenReturn(entreprises);

        ResponseEntity<List<Entreprise>> response = entrepriseController.getAllEntreprises();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(entrepriseService, times(1)).getAllEntreprises();
    }

    @Test
    void testCreatePost_Success() {
        Entreprise entreprise = new Entreprise();
        entreprise.setId(1L);
        entreprise.setSocialReason("Test Entreprise");

        when(entrepriseService.createEntreprise(any(Entreprise.class))).thenReturn(entreprise);

        ResponseEntity<Entreprise> response = entrepriseController.createPost(entreprise);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Entreprise", response.getBody().getSocialReason());
        verify(entrepriseService, times(1)).createEntreprise(any(Entreprise.class));
    }

    @Test
    void testUpdateEntreprise_Success() {

        Entreprise entreprise = new Entreprise();
        entreprise.setId(1L);
        entreprise.setSocialReason("Test Entreprise");

        when(entrepriseService.updateEntreprise(eq(1L), any(Entreprise.class))).thenReturn(entreprise);

        ResponseEntity<Entreprise> response = entrepriseController.updateEntreprise(1L, entreprise);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(entrepriseService, times(1)).updateEntreprise(eq(1L), any(Entreprise.class));
    }

    @Test
    void testDeleteEntreprise_Success() {

        Entreprise entreprise = new Entreprise();
        entreprise.setId(1L);
        entreprise.setSocialReason("Test Entreprise");

        when(entrepriseService.deleteEntreprise(1L)).thenReturn(entreprise);

        ResponseEntity<Entreprise> response = entrepriseController.deleteEntreprise(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(entrepriseService, times(1)).deleteEntreprise(1L);
    }

    @Test
    void testDeleteEntreprise_Exception() {
        when(entrepriseService.deleteEntreprise(anyLong())).thenThrow(new RuntimeException("Erreur"));

        ResponseEntity<Entreprise> response = entrepriseController.deleteEntreprise(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
