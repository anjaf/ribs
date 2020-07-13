package uk.ac.ebi.biostudies;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@ServletComponentScan
@SpringBootApplication
public class BioStudiesUI extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BioStudiesUI.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BioStudiesUI.class, args);
    }
}
