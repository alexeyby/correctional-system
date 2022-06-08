package alexeyby.corsys.model;

import java.sql.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import alexeyby.corsys.model.enumeration.Injury;
import alexeyby.corsys.model.enumeration.Mobility;
import alexeyby.corsys.model.enumeration.Temperature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checkup")
public class Checkup 
{
	@NotNull
	@Id
	@GeneratedValue
	@Column(name = "ckup_uuid", nullable = false)
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID checkupUuid;
	@NotNull
	@PositiveOrZero
	@Column(name = "weight", nullable = false)
	private double weight;
	@NotNull
	@PositiveOrZero
	@Column(name = "height", nullable = false)
	private double height;
	@NotNull
	@PositiveOrZero
	@Column(name = "sft", nullable = false)
	private double skinfoldThickness;
	@NotNull
	@PositiveOrZero
	@Column(name = "armcir", nullable = false)
	private double armCircumference;
	@NotNull
	@PositiveOrZero
	@Column(name = "alb", nullable = false)
	private double albumin;
	@NotNull
	@PositiveOrZero
	@Column(name = "tf", nullable = false)
	private double transferrin;
	@NotNull
	@PositiveOrZero
	@Column(name = "lym", nullable = false)
	private double lymphocyte;
	@NotNull
	@PositiveOrZero
	@Column(name = "sktest", nullable = false)
	private double skinTest;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "mob_id", nullable = false)
	@JsonIgnoreProperties(value = { "mobilityId" })
	@JsonUnwrapped
	private Mobility mobility;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "temp_id", nullable = false)
	@JsonIgnoreProperties(value = { "temperatureId" })
	@JsonUnwrapped
	private Temperature temperature;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "inj_id", nullable = false)
	@JsonIgnoreProperties(value = { "injuryId" })
	@JsonUnwrapped
	private Injury injury;
	@NotNull
	@PastOrPresent
	@Column(name = "ckdate", nullable = false)
	private Date checkupDate;
	@Column(name = "hfac")
	private String healthFacility;
	
	@NotNull
	@OneToOne(optional = false)
    @JoinColumn(name = "pt_uuid", unique = true, nullable = false)
	@Type(type = "org.hibernate.type.UUIDCharType")
	@JsonIgnoreProperties(value = { 
		"historyNumber", "lastName", "firstName",
		"surname", "birthdate", "sex", "failure", "hasCheckup"
	})
	@JsonUnwrapped
	private Patient patient;
}
