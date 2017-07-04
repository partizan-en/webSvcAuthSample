package simpleWebSvcAuth.models;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ResponseBody
public  class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "ProductCategory ID")
    private Long id;

    @ApiModelProperty(notes = "ProductCategory Name")
    private String name;

    @OneToMany
    @ApiModelProperty(notes = "Products of current ProductCategory")
    private List<Product> products;

    public Category()
    {
        super();
    }

    public Category(String name)
    {
        super();
        this.name = name;
        this.products = new ArrayList<Product>();
    }

    public Category(String name, List<Product> products)
    {
        super();
        this.name = name;
        this.products = products;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Product> getProducts()
    {
        return products;
    }
}
