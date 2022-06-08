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
@Table(name = "sft_dm")
public class SFTDegreeMalnutrition 
{
	@NotNull
	@Id
	@Column(name = "sft_id", nullable = false)
	private int sftId;
	@NotNull
	@PositiveOrZero
	@Column(name = "sft_low", nullable = false)
	private double sftLow;
	@NotNull
	@PositiveOrZero
	@Column(name = "sft_high", nullable = false)
	private double sftHigh;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "degm_id", nullable = false)
	private DegreeMalnutrition degreeMalnutrition;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "agrg_id", nullable = false)
	private AgeRange ageRange;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "sex_id", nullable = false)
	private Sex sex;
}
