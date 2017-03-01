package uk.ac.ebi.biostudies.configuration;

/**
 * Created by ehsan on 23/02/2017.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages = "uk.ac.ebi.biostudies")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("search");
        registry.addViewController("/search*").setViewName("search");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/publisher/browse").setViewName("publisher/browse");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/studies/*").setViewName("detail");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(6000);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(6000);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(6000);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(6000);
        registry.addResourceHandler("/page/**").addResourceLocations("/html/").setCachePeriod(6000);

    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}