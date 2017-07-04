package simpleWebSvcAuth.models;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.persistence.*;

@Entity
@ResponseBody
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "Product ID")
    private Long id;

    @ApiModelProperty(notes = "Product Name")
    private String name;

    @ApiModelProperty(notes = "Product Price")
    private int price;

    @ManyToOne
    @ApiModelProperty(notes = "Product Category")
    private Category category;

    public Product()
    {
        super();
    }

    public Product(String name, int price, Category category)
    {
        super();
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }
}


