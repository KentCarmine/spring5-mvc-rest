package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
//    private Long id;
    @ApiModelProperty(value = "This is the first name", required = true)
    private String firstName;

    @ApiModelProperty(required = true)
    private String lastName;

    private String customerUrl;
}
