package pe.isil.Saturno_1431.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Promedio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float ep1;
    private float ep2;
    private float ep3;
    private float ep4;
    private float promedio_ep;
    private float parcial;
    private float finall;
    private float notafinal;

}
