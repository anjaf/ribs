package uk.ac.ebi.biostudies.auth;

/**
 * Created by ehsan on 16/03/2017.
 */
public class Session {
    private static final ThreadLocal<User> context = new ThreadLocal<User>();

    public static void setSession(User user) {
        context.set(user);
    }

    public static User getSession() {
        return context.get();
    }
    public static void clear() {
        context.remove();
    }
}
