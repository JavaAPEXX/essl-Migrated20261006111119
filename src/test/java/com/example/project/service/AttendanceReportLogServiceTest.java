```java
package com.example.project.service;

import com.example.project.model.AttendanceReportLog;
import com.example.project.repository.AttendanceReportLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceReportLogServiceTest {

    @Mock
    private AttendanceReportLogRepository repository;

    @InjectMocks
    private AttendanceReportLogService service;

    @Test
    @DisplayName("Given a valid attendance report log, when saving, then the repository saves and returns the log")
    void givenValidAttendanceReportLog_whenSave_thenReturnSavedLog() {
        // Arrange
        AttendanceReportLog log = new AttendanceReportLog();
        when(repository.save(log)).thenReturn(log);

        // Act
        AttendanceReportLog result = service.save(log);

        // Assert
        assertNotNull(result);
        assertSame(log, result);
        verify(repository, times(1)).save(log);
    }

    @Test
    @DisplayName("Given a null attendance report log, when saving, then the repository is called with null")
    void givenNullAttendanceReportLog_whenSave_thenRepositoryCalledWithNull() {
        // Arrange
        when(repository.save(null)).thenReturn(null);

        // Act
        AttendanceReportLog result = service.save(null);

        // Assert
        assertNull(result);
        verify(repository, times(1)).save(null);
    }

    @Test
    @DisplayName("Given the repository throws an exception on save, when saving, then the exception is propagated")
    void givenRepositoryThrowsException_whenSave_thenExceptionPropagated() {
        // Arrange
        AttendanceReportLog log = new AttendanceReportLog();
        when(repository.save(log)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.save(log);
        });
        assertEquals("Database error", exception.getMessage());
        verify(repository, times(1)).save(log);
    }

    @Test
    @DisplayName("Given the repository returns a modified log, when saving, then the modified log is returned")
    void givenRepositoryReturnsModifiedLog_whenSave_thenReturnModifiedLog() {
        // Arrange
        AttendanceReportLog inputLog = new AttendanceReportLog();
        AttendanceReportLog savedLog = new AttendanceReportLog();
        when(repository.save(inputLog)).thenReturn(savedLog);

        // Act
        AttendanceReportLog result = service.save(inputLog);

        // Assert
        assertNotNull(result);
        assertSame(savedLog, result);
        assertNotSame(inputLog, result);
        verify(repository, times(1)).save(inputLog);
    }

    @Test
    @DisplayName("Given the repository returns an empty list, when finding all, then an empty list is returned")
    void givenEmptyRepository_whenFindAll_thenReturnEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<AttendanceReportLog> result = service.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Given the repository returns a list of logs, when finding all, then the list is returned")
    void givenRepositoryWithLogs_whenFindAll_thenReturnListOfLogs() {
        // Arrange