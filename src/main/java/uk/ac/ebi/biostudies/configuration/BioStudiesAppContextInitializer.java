package uk.ac.ebi.biostudies.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import uk.ac.ebi.biostudies.auth.CookieFilter;
import uk.ac.ebi.biostudies.auth.AdminFilter;

import javax.servlet.Filter;

public class BioStudiesAppContextInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { MvcConfig.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CookieFilter()};
    }
 
}
