package org.sid.GestionCinema.entities;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="p1",types = { org.sid.GestionCinema.entities.Projection.class})

public interface ProjectionProj {
	public long geId();
	public double getPrix();
	public Date getDateProjection();
	public Film getFilm();
	public Collection<Ticket> getTickets();
	public Seance getSeance();
}
