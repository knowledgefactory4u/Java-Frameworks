package com.knf.demo;

import java.util.List;

import org.hibernate.Session;

import com.knf.demo.hibernate.entity.UserEntity;
import com.knf.demo.hibernate.util.HibernateUtil;

public class Hibernate5DemoApplication {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// Add new User object
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setEmail("dummyuser@mail.com");
		user.setFirstName("dummy");
		user.setLastName("dummy");
		UserEntity user1 = new UserEntity();
		user1.setId(2);
		user1.setEmail("dummyuser1@mail.com");
		user1.setFirstName("dummy1");
		user1.setLastName("dummy1");
		session.save(user1);
		session.save(user);
		session.getTransaction().commit();
		List<UserEntity> userList = session.createQuery("SELECT a FROM UserEntity a", UserEntity.class).getResultList();
		System.out.println("List of users:" + userList);
		HibernateUtil.shutdown();
	}

}
