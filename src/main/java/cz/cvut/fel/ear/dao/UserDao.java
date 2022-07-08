package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.User;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {
    
    public UserDao() {
        super(User.class);
    }
    
    public User findByUsername(String userName){
        try{
            return em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("userName", userName).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        
    }
    
}
