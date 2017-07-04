package simpleWebSvcAuth.controllers;

import simpleWebSvcAuth.models.*;
import simpleWebSvcAuth.repositories.CategoryRepository;
import simpleWebSvcAuth.repositories.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Api(
        value = "Product store",
        description = "Products catalog controller")
@RestController
@RequestMapping("/")
public class CatalogController
{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    //
    // Категории товаров
    // (Операции на чтение не требуют аутентификации)
    //

    //
    // Поиск всех доступных категорий товаров
    //
    @ApiOperation(
            value = "Search all category",
            response = Iterable.class)
    @RequestMapping(
            value = "/categories",
            method= RequestMethod.GET,
            produces = "application/json")
    public  @ResponseBody Iterable<Category> allProductCategories(@RequestParam int pageNumber, Model model)
    {
        Iterable<Category> result = Pager.getPage(categoryRepository.findAll(), pageNumber);
        return result;
    }

    //
    // Поиск категории по идентификатору
    //
    @ApiOperation(
            value = "Search category by ID",
            response = Category.class)
    @RequestMapping(
            value = "/category/get",
            method= RequestMethod.GET,
            produces = "application/json")
    public  @ResponseBody Category categoryById(@RequestParam Long id, Model model)
    {
        return categoryRepository.findOne(id);
    }

    //
    // (Операции записи - требуют аутентификации пользователя)
    //

    //
    // Добавить новую категорию товара
    //
    @ApiOperation(
            value = "Add new category")
    @RolesAllowed(
            value = "ROLE_USER")
    @PreAuthorize(
            value = "hasRole('ROLE_USER')")
    @RequestMapping(
            value = "/category/add",
            method = RequestMethod.POST,
            produces = "application/json")
    public @ResponseBody HttpStatus saveCategory(@RequestParam String name, Model model)
    {
        Category productCategory = new Category(name);
        categoryRepository.save(productCategory);
        return HttpStatus.OK;
    }

    //
    // Удалить категорию товара с заданым идентификатором
    //
    @ApiOperation(
            value = "Delete category by ID")
    @RolesAllowed(
            value = "ROLE_USER")
    @PreAuthorize(
            value = "hasRole('ROLE_USER')")
    @RequestMapping(
            value = "/category/del",
            method = RequestMethod.POST,
            produces = "application/json")
    public  @ResponseBody HttpStatus deleteCategory(@RequestParam Long id, Model model)
    {
        if (categoryRepository.findOne(id) != null)
        {
            categoryRepository.delete(id);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    //
    // Продукты
    // (Операции на чтение не требуют аутентификации)
    //

    //
    // Вывести список всех продуктов
    //
    @ApiOperation(
            value = "Search all products",
            response = Iterable.class)
    @RequestMapping(
            value = "/products",
            method= RequestMethod.GET,
            produces = "application/json")
    public  @ResponseBody Iterable<Product> allProducts(@RequestParam int pageNumber, Model model)
    {
        Iterable<Product> result = Pager.getPage(productRepository.findAll(), pageNumber);
        return result;
    }

    //
    // Поиск продукта по идентификатору
    //
    @ApiOperation(
            value = "Search product by ID",
            response = Product.class)
    @RequestMapping(
            value = "/product/get",
            method= RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody Product productById(@RequestParam Long id, Model model)
    {
        return productRepository.findOne(id);
    }

    //
    // Поиск продуктов по категории товара
    //
    @ApiOperation(
            value = "Search products by category",
            response = Iterable.class)
    @RequestMapping(
            value = "/category/products",
            method= RequestMethod.GET,
            produces = "application/json")
    public  @ResponseBody Iterable<Product> productsByCategory(@RequestParam Long id, @RequestParam int pageNumber, Model model)
    {
        Iterable<Product> products = productRepository.findAll();
        List<Product> resultProducts = new ArrayList<>();
        for (Product product: products)
        {
            if (product.getCategory().getId() == id)
                resultProducts.add(product);
        }
        Iterable<Product> result = Pager.getPage(resultProducts, pageNumber);
        return result;
    }

    //
    // Поиск товаров по категории и цене
    //
    @ApiOperation(
            value = "Search product by category and price",
            response = Iterable.class)
    @RequestMapping(
            value = "/category/priceSelection",
            method= RequestMethod.GET,
            produces = "application/json")
    public  @ResponseBody Iterable<Choise> productsByCategoryAndPrice(@RequestParam  Long categoryId, @RequestParam Integer price,
                                                                      @RequestParam int pageNumber, Model model)
    {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productsByCategory = new ArrayList<>();
        for (Product product: products)
        {
            if (product.getCategory().getId() == categoryId)
                productsByCategory.add(product);
        }

        List<Choise> resultChoise = new ArrayList<>();
        for (Product product : productsByCategory )
        {
            Integer count = price / product.getPrice();
            if ( count > 0)
                resultChoise.add(new Choise(product, count));
        }
        Iterable<Choise> result = Pager.getPage(resultChoise, pageNumber);

        return result;
    }

    //
    // (Операции записи - требуют аутентификации пользователя)
    //

    //
    // Добавить новый продукт
    //
    @ApiOperation(
            value = "Add new product")
    @RolesAllowed(
            value = "ROLE_USER")
    @PreAuthorize(
            value = "hasRole('ROLE_USER')")
    @RequestMapping(
            value = "/product/add",
            method = RequestMethod.POST,
            produces = "application/json")
    public @ResponseBody HttpStatus addProduct(@RequestParam Long categoryId, @RequestParam String name,  @RequestParam int price, Model model)
    {
        Category productCategory = categoryRepository.findOne(categoryId);
        if ( productCategory != null)
        {
            Product product = new Product(name, price, productCategory);
            productRepository.save(product);
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_FOUND;
    }

    //
    // Обновить товар
    //
    @ApiOperation(
            value = "Update product price by ID")
    @RolesAllowed(
            value = "ROLE_USER")
    @PreAuthorize(
            value = "hasRole('ROLE_USER')")
    @RequestMapping(
            value = "/product/update",
            method= RequestMethod.POST,
            produces = "application/json")
    public @ResponseBody HttpStatus updateProductPrice(@RequestParam Long id, @RequestParam int price, Model model)
    {
        Product product = productRepository.findOne(id);
        if (product != null)
        {
            product.setPrice(price);
            productRepository.save(product);
            return  HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    //
    // Удалить продукт с заданным идентификатором
    //
    @ApiOperation(
            value = "Delete product by ID")
    @RolesAllowed(
            value = "ROLE_USER")
    @RequestMapping(
            value = "/product/del",
            method = RequestMethod.POST,
            produces = "application/json")
    public @ResponseBody  HttpStatus deleteProduct(@RequestParam Long id, Model model)
    {
        if (productRepository.findOne(id) != null)
        {
            productRepository.delete(id);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}