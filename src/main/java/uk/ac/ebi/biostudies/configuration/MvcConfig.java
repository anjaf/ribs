package uk.ac.ebi.biostudies.configuration;

/**
 * Created by ehsan on 23/02/2017.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
@ComponentScan(basePackages = "uk.ac.ebi.biostudies")
@PropertySource("classpath:scheduler.properties")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/studies").setViewName("search");
        registry.addViewController("/studies/*").setViewName("search");
        registry.addViewController("/studies/{accession:.+}").setViewName("detail");
        registry.addViewController("/studies/{accession:.+}/").setViewName("detail");
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
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

        properties.setLocation(new ClassPathResource( "scheduler.properties" ));
        properties.setIgnoreResourceNotFound(false);

        return properties;
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