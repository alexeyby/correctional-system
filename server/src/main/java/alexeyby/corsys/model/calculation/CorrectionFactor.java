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
@Table(name = "cor_factor")
public class CorrectionFactor 
{
	@NotNull
	@Id
	@Column(name = "corf_id", nullable = false)
	private int correctionFactorId;
	@NotNull
	@PositiveOrZero
	@Column(name = "corf", nullable = false)
	private double correctionFactor;
	@ManyToOne(optional = false)
	@JoinColumn(name = "inj_id")
	private Injury injury;
	@ManyToOne(optional = false)
	@JoinColumn(name = "temp_id")
	private Temperature temperature;
	@ManyToOne(optional = false)
	@JoinColumn(name = "mob_id")
	private Mobility mobility;
	@ManyToOne(optional = false)
	@JoinColumn(name = "degm_id")
	private DegreeMalnutrition degreeMalnutrition;
}
