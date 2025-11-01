package com.server.api.management.controller;

import com.server.api.management.service.EmployeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeControllerTest {

    @Mock
    private EmployeService employeService;

    @InjectMocks
    private EmployeController employeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
