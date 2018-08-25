package ar.edu.unq.inscripcionunq.spring.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Commission")
public class Commission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Interval> intervals = new ArrayList<Interval>();
	private Integer quota;
	@ManyToOne
	private Matter matter;
	@Enumerated(EnumType.STRING)
	private TypeStatus status = TypeStatus.ENABLED;

	public Commission(String name, Matter matter, Integer quota) {
		this.name = name;
		this.matter = matter;
		this.quota = quota;
	}

	public void addHours(TypeDay day, LocalTime startHour, Integer countHours) {
		List<Interval> intervalsResults = intervals.stream().filter(d -> d.getDay().equals(day))
				.collect(Collectors.toList());
		if (intervalsResults.isEmpty()) {
			intervals.add(new Interval(day, startHour, countHours));
		}
	}

	public Commission() {
	}

	public long getId() {
		return id;
	}

	public void disabled() {
		this.status = TypeStatus.DISABLED;
	}

}
