package org.sid.GestionCinema.service;

import java.text.DateFormat;
 import java.text.ParseException; 
 import java.text.SimpleDateFormat;
 import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import javax.transaction.Transactional;

import org.sid.GestionCinema.dao.CategoryRepository;
import org.sid.GestionCinema.dao.CinemaRepository;
import org.sid.GestionCinema.dao.FilmRepository;
import org.sid.GestionCinema.dao.PlaceRepository;
import org.sid.GestionCinema.dao.ProjectionRepository;
import org.sid.GestionCinema.dao.SalleRepository;
import org.sid.GestionCinema.dao.SeanceRepository;
import org.sid.GestionCinema.dao.TicketRepository;
import org.sid.GestionCinema.dao.VilleRepository;
import org.sid.GestionCinema.entities.Categorie;
import org.sid.GestionCinema.entities.Cinema;
import org.sid.GestionCinema.entities.Film;
import org.sid.GestionCinema.entities.Place;
import org.sid.GestionCinema.entities.Projection;
import org.sid.GestionCinema.entities.Salle;
import org.sid.GestionCinema.entities.Seance;
import org.sid.GestionCinema.entities.Ticket;
import org.sid.GestionCinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{
	@Autowired private VilleRepository villeRepository;
	@Autowired private CinemaRepository cinemaRepository;
	@Autowired private SalleRepository salleRepository;
	@Autowired private PlaceRepository placeRepository;
	@Autowired private SeanceRepository seanceRepository;
	@Autowired private FilmRepository filmRepository;
	@Autowired private ProjectionRepository projectionRepository;
	@Autowired private CategoryRepository categorieRepository;
	@Autowired private TicketRepository ticketRepository;
	
	@Override
	public void initVilles() {
		Stream.of("Casablanca", "Marrakech", "Rabat", "Tanger").forEach(nameVille -> {
			Ville ville = new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
	}


	@Override
	public void initCinemas() {
		// TODO Auto-generated method stub
		villeRepository.findAll().forEach(v->{
			Stream.of("MegaRama","IMAX","FOUNOUN","CHAHRAZAD","DAOULIZ")
			.forEach(nameCinema->{
			Cinema cinema=new Cinema();
			cinema.setName(nameCinema);
			cinema.setNombreSalles(3+(int)(Math.random()*7));
			cinema.setVille(v);
			cinemaRepository.save(cinema);
			});
			});
	}


	@Override
	public void initSalles() {
		// TODO Auto-generated method stub
		cinemaRepository.findAll().forEach(cinema->{
			for(int i=0;i<cinema.getNombreSalles();i++) {
			Salle salle=new Salle();
			salle.setName("Salle "+(i+1));
			salle.setCinema(cinema);
			salle.setNombrePlace(15+(int)(Math.random()*20));
			salleRepository.save(salle);
			}});
	}


	@Override
	public void initPalces() {
		// TODO Auto-generated method stub
		salleRepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNombrePlace();i++) {
			Place place=new Place();
			place.setNumero(i+1);
			place.setSalle(salle);
			placeRepository.save(place);
			}
			});
	}


	@Override
	public void initSeances() {
		// TODO Auto-generated method stub
		DateFormat dateFormat=new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
		Seance seance=new Seance();
		try {
		seance.setHeureDebut(dateFormat.parse(s));
		seanceRepository.save(seance);
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		});
	}


	@Override
	public void initCategories() {
		// TODO Auto-generated method stub
		Stream.of("Histoire","Actions","Fiction","Drama").forEach(cat->{
			Categorie categorie=new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
			});
	}


	@Override
	public void initFilms() {
		// TODO Auto-generated method stub
		double[] durees=new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories=categorieRepository.findAll();
		Stream.of("12 Hommes en colere","Forrest Gump","Green Book","La Ligne Verte","Le Parrain","Le Seigneur des anneaux")
		.forEach(titreFilm->{
		Film film=new Film();
		film.setTitre(titreFilm);
		film.setDuree(durees[new Random().nextInt(durees.length)]);
		film.setPhoto(titreFilm.replaceAll(" ", "")+".jpg");
		film.setCategorie(categories.get(new Random().nextInt(categories.size())));
		filmRepository.save(film);
		});
	}

/*
	@Override
	public void initProjections() {
		// TODO Auto-generated method stub
		double[] prices = new double[] { 30, 50, 60, 70, 90, 100 };
		villeRepository.findAll().forEach(ville -> {
			ville.getCinemas().forEach(cinema -> {
				cinema.getSalles().forEach(salle -> {
					filmRepository.findAll().forEach(film -> {
						seanceRepository.findAll().forEach(seance -> {
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionRepository.save(projection);
						});
					});
				});
			});
		});
	}
*/
	@Override
	public void initProjections()
	{
		double[] prices=new double[] {30,50,60,70,90,100};
		List<Film> films=filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int index= new Random().nextInt(films.size());
					Film film =films.get(index);
						seanceRepository.findAll().forEach(seance->{
							Projection projection=new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionRepository.save(projection);
						});
				});
			});
		});
	}

	@Override
	public void initTickets() {
		// TODO Auto-generated method stub
		projectionRepository.findAll().forEach(p->{
				p.getSalle().getPlaces().forEach(place->{
					Ticket ticket=new Ticket();
					ticket.setPlace(place);
					ticket.setPrix(p.getPrix());
					ticket.setProjection(p);
					ticket.setReserve(false);
					ticketRepository.save(ticket);
			});
		});
	}
}

	
	
	
	
	

