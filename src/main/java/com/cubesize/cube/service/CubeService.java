package com.cubesize.cube.service;


import com.cubesize.cube.dto.RequestMapper;
import com.cubesize.cube.dto.ReturnResponse;
import com.cubesize.cube.entity.Cube;
import com.cubesize.cube.repository.CubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CubeService {

    @Autowired
    private CubeRepository repository;

    @Autowired
    private CaffeineCacheManager cacheManager;

    @Cacheable(value = "cubeCache", key="#p0")
    public ResponseEntity<ReturnResponse> getCubeByUUID(Long id) {
        Optional<Cube> cube = repository.findById(id);
        return cube.map(
                value -> new ResponseEntity<>(new ReturnResponse(value.getVolume(), value.getDateTime()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

//    @Cacheable(key = "#cube.uuid")
    public ResponseEntity<ReturnResponse> createCube(RequestMapper mapper) {
        Cube cube = new Cube();
        cube.setLength(mapper.getLength());
        cube.setHeight(mapper.getHeight());
        cube.setBreadth(mapper.getBreadth());
        cube.setDateTime(LocalDateTime.now());
        int volume = mapper.getLength() * mapper.getBreadth() * mapper.getHeight();
        if(volume < 0) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        cube.setVolume(volume);
        Cube save = repository.save(cube);
        ReturnResponse response = new ReturnResponse(save.getVolume(), save.getDateTime());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Cacheable(value = "cubeCache", key = "#p0")
    public ResponseEntity<ReturnResponse> getCubeByDimension(String dimension) {
        String[] array = dimension.split("-");
        if (array.length < 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int volume = Integer.parseInt(array[0]) * Integer.parseInt(array[1]) * Integer.parseInt(array[2]);
        Optional<Cube> cube = repository.findByVolume(volume);
        return cube.map(
                value -> new ResponseEntity<>(new ReturnResponse(value.getVolume(), value.getDateTime()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Cacheable(value = "cubeCache")
    public List<Cube> getAllCubes() {
        return repository.findAll();
    }

    @CacheEvict(value = "cubeCache", allEntries = true)
    public void clearCache() {
        // Clears all entries from the cube cache
    }


}
