```java
package com.example.project.service;

import com.example.project.model.AttendanceRecord;
import com.example.project.model.AttendanceSoapData;
import com.example.project.util.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    private final AttendanceService attendanceService = new AttendanceService();

    @Test
    @DisplayName("Given valid SOAP data with IN and OUT directions, when processing, then return correct attendance record")
    void givenValidSoapDataWithInAndOutDirections_whenProcessSoapData_thenReturnCorrectAttendanceRecord() {
        // Arrange
        AttendanceSoapData inData = new AttendanceSoapData();
        inData.setUserId("1");
        inData.setTimeStamp("2025-11-25T09:00:00");
        inData.setDirection("IN");
        inData.setUserName("Alice");

        AttendanceSoapData outData = new AttendanceSoapData();
        outData.setUserId("1");
        outData.setTimeStamp("2025-11-25T17:00:00");
        outData.setDirection("OUT");
        outData.setUserName("Alice");

        List<AttendanceSoapData> soapData = Arrays.asList(inData, outData);

        // Act
        List<AttendanceRecord> result = attendanceService.processSoapData(soapData);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        AttendanceRecord record = result.get(0);
        assertEquals("1", record.getUserId());
        assertEquals("Alice", record.getUserName());
        assertEquals(LocalDate.of(2025, 11, 25), record.getDate());
        assertEquals(LocalDateTime.of(2025, 11, 25, 9, 0, 0), record.getInTime());
        assertEquals(LocalDateTime.of(2025, 11, 25, 17, 0, 0), record.getOutTime());
    }

    @Test
    @DisplayName("Given SOAP data with numeric direction codes, when processing, then return correct attendance record")
    void givenSoapDataWithNumericDirectionCodes_whenProcessSoapData_thenReturnCorrectAttendanceRecord() {
        // Arrange
        AttendanceSoapData inData = new AttendanceSoapData();
        inData.setUserId("2");
        inData.setTimeStamp("2025-11-25T08:30:00");
        inData.setDirection("0");
        inData.setUserName("Bob");

        AttendanceSoapData outData = new AttendanceSoapData();
        outData.setUserId("2");
        outData.setTimeStamp("2025-11-25T16:30:00");
        outData.setDirection("1");
        outData.setUserName("Bob");

        List<AttendanceSoapData> soapData = Arrays.asList(inData, outData);

        // Act
        List<AttendanceRecord> result = attendanceService.processSoapData(soapData);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        AttendanceRecord record = result.get(0);
        assertEquals("2", record.getUserId());