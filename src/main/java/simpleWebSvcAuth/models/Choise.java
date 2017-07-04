package simpleWebSvcAuth.models;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Entity;

@ResponseBody
public class Choise
{
    @ApiModelProperty(notes = "Product")
    public Product product;
    @ApiModelProperty(notes = "Count of product")
    public Integer count;

    public Choise()
    {}

    public Choise(Product product, Integer count)
    {
        this.product = product;
        this.count = count;
    }
}
