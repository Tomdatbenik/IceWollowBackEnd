package usercomponent.logic;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import userlogic.dal.context.UserHibernateContext;
import userlogic.dal.repo.UserContainerRepo;
import userlogic.dal.hibernate.HibernateFactory;
import userlogic.interfaces.IUserContainerRepo;
import userlogic.interfaces.IUserContext;
import userlogic.logic.UserContainerLogic;
import userlogic.models.User;

import javax.persistence.TypedQuery;

public class UserContainerLogicTest {

    private IUserContext mockContext;
    private IUserContainerRepo repo;
    private UserContainerLogic userContainerLogic = new UserContainerLogic();
    private HibernateFactory hibernateFactory;

    @Before
    public void setUp() {
        hibernateFactory = HibernateFactory.getTestInstance(true);
        mockContext = new UserHibernateContext(hibernateFactory);

        repo = new UserContainerRepo(mockContext);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setEmail("test1@gmail.com");
        user.setDisplayName("testAccount");

        boolean result = userContainerLogic.addUser(user);

        Assert.assertTrue(result);

        User resultUser = new User();

        Session session = hibernateFactory.getSessionFactory().openSession();
        try {
            String hql = "SELECT c FROM User c WHERE c.email = :email";

            TypedQuery<User> typedQuery = session.createQuery(hql, User.class);
            typedQuery.setParameter("email", user.getEmail());

            resultUser = typedQuery.getSingleResult();
        } catch (Exception ex) {
        } finally {
            session.close();
        }

        Assert.assertEquals(user.getDisplayName(), resultUser.getDisplayName());
        Assert.assertEquals(user.getEmail(), resultUser.getEmail());
    }

    @Test
    public void testAddUserUserAlreadyExists() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setDisplayName("testAccount");

        boolean result = userContainerLogic.addUser(user);

        Assert.assertFalse(result);
    }

    @Test
    public void testGetUserByEmail() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setDisplayName("testAccount");

        userContainerLogic.addUser(user);

        User testUser = userContainerLogic.getUserByEmail(user.getEmail());

        Assert.assertEquals(testUser.getDisplayName(), user.getDisplayName());
        Assert.assertEquals(testUser.getEmail(), user.getEmail());
    }
}