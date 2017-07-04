package simpleWebSvcAuth;

import simpleWebSvcAuth.models.Product;
import simpleWebSvcAuth.models.Category;
import simpleWebSvcAuth.repositories.CategoryRepository;
import simpleWebSvcAuth.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner
{
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String ... args) throws Exception
    {
        // одежда
        Category clothes = new Category("Одежда");
        categoryRepository.save(clothes);
        productRepository.save(new Product("Платье", 300, clothes));
        productRepository.save(new Product("Шляпка", 100, clothes));
        // еда
        Category foods = new Category("Еда");
        categoryRepository.save(foods);
        productRepository.save(new Product("Ананас", 50, foods));
        productRepository.save(new Product("Молоко", 35, foods));
        // техника
        Category technics = new Category("Техника");
        categoryRepository.save(technics);
        productRepository.save(new Product("Танк", 250000, technics));
        productRepository.save(new Product("Авианосец", 1000000, technics));
    }
    public static void main(String[] args) throws Throwable
    {
        SpringApplication.run(Application.class, args);
    }
}
