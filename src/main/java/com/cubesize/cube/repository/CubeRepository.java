package com.cubesize.cube.repository;

import com.cubesize.cube.entity.Cube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CubeRepository extends JpaRepository<Cube, Long> {
    Optional<Cube> findByVolume(int volume);
}
