package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;
   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      user.setCar(user.getCar());
      sessionFactory.openSession().save(user);

   }

   public void addCar(Car car) {
      sessionFactory.openSession().save(car);
   }

   @Override
   public User showUserOnCar(Car car) {
      return (User) sessionFactory.openSession()
              .createQuery("from User where car.model = :paramModel and car.series = :paramSeries").
              setParameter("paramModel", car.getModel()).setParameter("paramSeries" , car.getSeries())
              .stream().findAny().orElse(null);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

}
