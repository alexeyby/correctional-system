package alexeyby.corsys.model;

import java.sql.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import alexeyby.corsys.model.enumeration.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Patient 
{
	@NotNull
	@Id
	@GeneratedValue
	@Column(name = "pt_uuid", nullable = false)
	@Type(type = "org.hibernate.type.UUIDCharType")
	private UUID patientUuid;
	@NotBlank
	@Pattern(regexp = "[0-9]+")
	@Column(name = "hnumber", unique = true, nullable = false)
	private String historyNumber;
	@NotBlank
	@Column(name = "lname", nullable = false)
	private String lastName;
	@NotBlank
	@Column(name = "fname", nullable = false)
	private String firstName;
	@NotBlank
	@Column(name = "sname", nullable = false)
	private String surname;
	@NotNull
	@PastOrPresent
	@Column(name = "bdate", nullable = false)
	private Date birthdate;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "sex_id", nullable = false)
	@JsonIgnoreProperties(value = { "sexId" })
	@JsonUnwrapped
	private Sex sex;
	@NotNull
	@Column(name = "failure", nullable = false)
	private boolean failure;
	@NotNull
	@Column(name = "hasckup", nullable = false)
	private boolean hasCheckup;
}