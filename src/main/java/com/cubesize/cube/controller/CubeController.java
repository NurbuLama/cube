package com.cubesize.cube.controller;

import com.cubesize.cube.config.SpringCachingConfig;
import com.cubesize.cube.dto.RequestMapper;
import com.cubesize.cube.dto.ReturnResponse;
import com.cubesize.cube.entity.Cube;
import com.cubesize.cube.service.CubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dimension")
public class CubeController {

    @Autowired
    private CubeService service;

    @GetMapping("/uuid/{id}")
    public ResponseEntity<ReturnResponse> getCubeByUUID(@PathVariable Long id) {
        return service.getCubeByUUID(id);
    }

    @PostMapping
    public ResponseEntity<ReturnResponse> createCube(@RequestBody RequestMapper cube) {
        return service.createCube(cube);
    }

    @GetMapping("/{dimensions}")
    public ResponseEntity<ReturnResponse> getCubeByDimensions(@PathVariable String dimensions) {
        return service.getCubeByDimension(dimensions);
    }

    @GetMapping()
    public List<Cube> findAll() {
        return service.getAllCubes();
    }
}
