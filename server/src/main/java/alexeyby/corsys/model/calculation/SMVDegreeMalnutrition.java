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
@Table(name = "smv_dm")
public class SMVDegreeMalnutrition 
{
	@NotNull
	@Id
	@Column(name = "smv_id", nullable = false)
	private int smvId;
	@NotNull
	@PositiveOrZero
	@Column(name = "smv_low", nullable = false)
	private double smvLow;
	@NotNull
	@PositiveOrZero
	@Column(name = "smv_high", nullable = false)
	private double smvHigh;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "degm_id", nullable = false)
	private DegreeMalnutrition degreeMalnutrition;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "sex_id", nullable = false)
	private Sex sex;
}
