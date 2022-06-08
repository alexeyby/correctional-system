package alexeyby.corsys.model.calculation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "age_rng")
public class AgeRange 
{
	@NotNull
	@Id
	@Column(name = "agrg_id", nullable = false)
	private int ageRangeId;
	@NotNull
	@PositiveOrZero
	@Column(name = "age_low", nullable = false)
	private int ageLow;
	@NotNull
	@PositiveOrZero
	@Column(name = "age_high", nullable = false)
	private int ageHigh;
}
