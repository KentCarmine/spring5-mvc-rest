package guru.springfamework.api.v1.model;

import guru.springfamework.domain.Vendor;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorListDTO {
    @ApiModelProperty(value="A list of vendors", required = true)
    private List<VendorDTO> vendors;
}
