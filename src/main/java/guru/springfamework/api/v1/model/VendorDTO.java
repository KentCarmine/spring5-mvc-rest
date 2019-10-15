package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {
    @ApiModelProperty(value = "the name of the vendor", required = true)
    private String name;

    @ApiModelProperty(value = "the vendor's URL", required = false)
    private String vendorUrl;
}
