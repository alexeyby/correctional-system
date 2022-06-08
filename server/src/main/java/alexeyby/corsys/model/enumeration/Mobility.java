package alexeyby.corsys.model.enumeration;

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
@Table(name = "mobility")
public class Mobility 
{
	@NotNull
	@Id
	@Column(name = "mob_id", nullable = false)
	private int mobilityId;
	@NotBlank
	@Column(name = "mob", unique = true, nullable = false)
	private String mobility;
}
