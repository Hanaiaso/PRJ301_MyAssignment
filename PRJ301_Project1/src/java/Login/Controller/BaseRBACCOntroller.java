package Login.Controller;
import Login.Entity.Feature;
import Login.Entity.Role;
import Login.Entity.User;
import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
public abstract class BaseRBACCOntroller extends BaseAuthenticationController{
    private boolean isAuthorized(HttpServletRequest req,User account)
    {
        String current_url  = req.getServletPath();
        UserDBContext db = new UserDBContext();
        ArrayList<Role> roles = db.getRoles(account.getUsername());
        account.setRoles(roles);
        
        for (Role role : account.getRoles()) {
            for (Feature feature : role.getFeatures()) {
                if(feature.getUrl().equals(current_url))
                    return true;
            }
        }       
        return false;
    }   
    protected abstract void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException;
    protected abstract void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException;  
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        if(isAuthorized(req, account))
        {
            doAuthorizedGet(req, resp, account);
        }
        else
            resp.sendError(403, "You do not have right to access this feature!");    
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
    if(isAuthorized(req, account))
        {
            doAuthorizedPost(req, resp, account);
        }
        else
            resp.sendError(403, "You do not have right to access this feature!");
    }   
}
