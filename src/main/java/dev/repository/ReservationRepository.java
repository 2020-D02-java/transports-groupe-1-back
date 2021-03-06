package dev.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.entites.Collegue;
import dev.entites.Reservation;
import dev.entites.VehiculeSociete;
import dev.entites.utiles.StatutDemandeChauffeur;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findAllByResponsable(Collegue collegue);

	List<Reservation> findAllByVehicule(VehiculeSociete vehicule);

	List<Reservation> findAllByChauffeur(Collegue collegue);

	List<Reservation> findAllByStatutDemandeChauffeur(StatutDemandeChauffeur enAttente);

	@Query("select r from Reservation r where (r.chauffeur = :chauffeur) and not (r.dateArrivee < :debut or r.dateDepart > :fin)")
	List<Reservation> findAllInPeriodeByChauffeur(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin,
			@Param("chauffeur") Collegue chauffeur);

	@Query("select r from Reservation r where  (r.vehicule= :vehicule) and (r.dateDepart > current_date())  ")
	List <Reservation> findAllByVehiculeWithDateDepartAfter(@Param("vehicule") VehiculeSociete vehicule);
	

	@Query("select r from Reservation r where  (r.vehicule= :vehicule) and (r.dateArrivee < current_date())  ")
	List<Reservation> findAllByVehiculeWithdateArriveeBerore(@Param("vehicule") VehiculeSociete vehicule);

	int countByResponsable(Collegue collegue);

	@Query("select count(*) from Reservation r where r.responsable = :collegue and r.dateArrivee < current_date()")
	int countByResponsableTermine(@Param("collegue") Collegue collegue);
}
