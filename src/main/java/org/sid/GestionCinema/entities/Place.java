package org.sid.GestionCinema.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Place {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private int numero;
private double longitude,latidude,altitude;
@ManyToOne
private Salle salle;
@OneToMany(mappedBy="place")
private Collection<Ticket> tickets;
}
