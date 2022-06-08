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
@Table(name = "nut_sts")
public class NutritionalStatus 
{
	@NotNull
	@Id
	@Column(name = "nsts_id", nullable = false)
	private int nutritionalStatusId;
	@NotBlank
	@Column(name = "nsts", unique = true, nullable = false)
	private String nutritionalStatus;
}
