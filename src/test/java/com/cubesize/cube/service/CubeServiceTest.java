package com.cubesize.cube.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cubesize.cube.dto.RequestMapper;
import com.cubesize.cube.dto.ReturnResponse;
import com.cubesize.cube.entity.Cube;
import com.cubesize.cube.repository.CubeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CubeServiceTest {

    @Mock
    private CubeRepository repository;

    @Mock
    private CaffeineCacheManager cacheManager;

    @InjectMocks
    private CubeService cubeService;

    private Cube cube;

    @BeforeEach
    void setUp() {
        cube = new Cube();
        cube.setLength(2);
        cube.setHeight(3);
        cube.setBreadth(4);
        cube.setDateTime(LocalDateTime.now());
        cube.setVolume(24);
    }

    @Test
    void testGetCubeByUUID_whenCubeExistsInRepository_thenReturnsCubeWithHttpStatusOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(cube));
        ResponseEntity<ReturnResponse> response = cubeService.getCubeByUUID(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(24, response.getBody().getVolume());
    }

    @Test
    void testGetCubeByUUID_whenCubeDoesNotExistInRepository_thenReturnsHttpStatusNO_CONTENT() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<ReturnResponse> response = cubeService.getCubeByUUID(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testCreateCube_whenValidRequestMapperIsProvided_thenReturnsCreatedCubeWithHttpStatusCREATED() {
        RequestMapper mapper = new RequestMapper();
        mapper.setLength(2);
        mapper.setHeight(3);
        mapper.setBreadth(4);
        Cube cube = new Cube(1,2,3,4,24,LocalDateTime.now());
        when(repository.save(any())).thenReturn(cube);
        ResponseEntity<ReturnResponse> response = cubeService.createCube(mapper);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(24, response.getBody().getVolume());
    }

    @Test
    void testGetCubeByDimension_whenCubeExistsInRepository_thenReturnsCubeWithHttpStatusOK() {
        when(repository.findByVolume(anyInt())).thenReturn(Optional.of(cube));
        ResponseEntity<ReturnResponse> response = cubeService.getCubeByDimension("2-3-4");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(24, response.getBody().getVolume());
    }

    @Test
    void testGetCubeByDimension_whenCubeDoesNotExistInRepository_thenReturnsHttpStatusNO_CONTENT() {
        when(repository.findByVolume(anyInt())).thenReturn(Optional.empty());
        ResponseEntity<ReturnResponse> response = cubeService.getCubeByDimension("2-3-4");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetCubeByUUIDNotFound() {
        Long id = 100L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<ReturnResponse> responseEntity = cubeService.getCubeByUUID(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateCube() {
        RequestMapper mapper = new RequestMapper();
        mapper.setLength(10);
        mapper.setHeight(20);
        mapper.setBreadth(30);
        int expectedVolume = 10 * 20 * 30;
        Cube savedCube = new Cube();
        savedCube.setId(1L);
        savedCube.setLength(10);
        savedCube.setHeight(20);
        savedCube.setBreadth(30);
        savedCube.setVolume(expectedVolume);
        savedCube.setDateTime(LocalDateTime.now());
        when(repository.save(any(Cube.class))).thenReturn(savedCube);
        ResponseEntity<ReturnResponse> responseEntity = cubeService.createCube(mapper);
        ReturnResponse response = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(response);
        assertEquals(expectedVolume, response.getVolume());
        assertNotNull(response.getCreatedTime());
    }

    @Test
    public void testGetCubeByDimensionNotFound() {
        String dimension = "10-20-30";
        int volume = 10 * 20 * 30;
        when(repository.findByVolume(volume)).thenReturn(Optional.empty());
        ResponseEntity<ReturnResponse> responseEntity = cubeService.getCubeByDimension(dimension);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllCubes() {
        List<Cube> expectedCubes = new ArrayList<>();
        expectedCubes.add(new Cube());
        when(repository.findAll()).thenReturn(expectedCubes);
        List<Cube> actualCubes = cubeService.getAllCubes();
        assertEquals(expectedCubes, actualCubes);
    }

    @Test
    public void testClearCache() {
        // Test that the cache is cleared without throwing an exception
        cubeService.clearCache();
    }

// Negative tests

    @Test
    public void testGetCubeByUUIDInvalidId() {
        Long id = null;
        ResponseEntity<ReturnResponse> responseEntity = cubeService.getCubeByUUID(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateCubeInvalidDimensions() {
        RequestMapper mapper = new RequestMapper();
        mapper.setLength(-1);
        mapper.setHeight(20);
        mapper.setBreadth(30);
        ResponseEntity<ReturnResponse> responseEntity = cubeService.createCube(mapper);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetCubeByDimensionInvalidDimension() {
        String dimension = "10-20";
        ResponseEntity<ReturnResponse> responseEntity = cubeService.getCubeByDimension(dimension);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
