package uk.ac.ebi.biostudies.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by ehsan on 16/03/2017.
 */
public class Session {
   /** private static final ThreadLocal<User> context = new ThreadLocal<User>();

    public static void setCurrentUser(User user) {
        context.set(user);
    }

    public static User getCurrentUser() {
        return context.get();
    }
    public static void clear() {
        context.remove();
    }**/

   public static String getUserName(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if(authentication==null)
           return  null;
       if (!(authentication instanceof AnonymousAuthenticationToken)) {
           String currentUserName = authentication.getName();
           return currentUserName;
       }
       return null;
   }

   public static User getCurrentUser(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if(authentication==null)
           return null;
       if(authentication instanceof AnonymousAuthenticationToken)
           return null;
       return (User)authentication.getPrincipal();
   }
}
