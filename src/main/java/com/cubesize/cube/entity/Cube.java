package com.cubesize.cube.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cube {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int length;
    private int breadth;
    private int height;
    private int volume;
    private LocalDateTime dateTime;
}
