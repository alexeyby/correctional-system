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
import alexeyby.corsys.model.enumeration.Indicator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lab_dm")
public class LabDegreeMalnutrition 
{
	@NotNull
	@Id
	@Column(name = "lab_id", nullable = false)
	private int labId;
	@NotNull
	@PositiveOrZero
	@Column(name = "res_low", nullable = false)
	private double resultLow;
	@NotNull
	@PositiveOrZero
	@Column(name = "res_high", nullable = false)
	private double resultHigh;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "degm_id", nullable = false)
	private DegreeMalnutrition degreeMalnutrition;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "ind_id", nullable = false)
	private Indicator indicator;
}
