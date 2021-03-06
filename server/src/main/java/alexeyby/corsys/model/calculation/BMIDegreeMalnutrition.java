package alexeyby.corsys.model.calculation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import alexeyby.corsys.model.enumeration.DegreeMalnutrition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bmi_dm")
public class BMIDegreeMalnutrition 
{
	@NotNull
	@Id
	@Column(name = "bmi_id", nullable = false)
	private int bmiId;
	@NotNull
	@PositiveOrZero
	@Column(name = "bmi_low", nullable = false)
	private double bmiLow;
	@NotNull
	@PositiveOrZero
	@Column(name = "bmi_high", nullable = false)
	private double bmiHigh;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "degm_id", nullable = false)
	private DegreeMalnutrition degreeMalnutrition;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "agrg_id", nullable = false)
	private AgeRange ageRange;
}
