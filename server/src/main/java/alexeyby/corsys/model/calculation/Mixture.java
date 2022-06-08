package alexeyby.corsys.model.calculation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mixture")
public class Mixture 
{
	@NotNull
	@Id
	@Column(name = "mix_id", nullable = false)
	private int mixtureId;
	@NotBlank
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	@Column(name = "data", columnDefinition = "json")
	private String data;
}
