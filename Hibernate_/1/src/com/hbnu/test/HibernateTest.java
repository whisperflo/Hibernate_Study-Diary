package com.hbnu.test;

import com.hbnu.pojo.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.reflection.java.generics.IdentityTypeEnvironment;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.testng.annotations.Test;
import utils.HibernateUtils;

import java.util.List;

public class HibernateTest {
    /*
     * 往数据库中添加一条数据
     * */
    @Test
    public void insertDate() {
        //1、加载并解析hibernate配置文件（直接加载hibernate核心配置文件，间接加载hibernate映射关系配置文件）
        Configuration configuration = new Configuration().configure();
        //2、通过配置对象构建会话工厂
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        //3、通过会话工厂创建会话对象
        Session session = sessionFactory.openSession();
        //4、通过会话对象完成数据库的CRUD操作
        User user = new User();
        user.setUsername("花吉祥");
        user.setAddress("江苏镇江");
        session.save(user);//通过操作实体对象的方式操作数据库
        //5、关闭会话对象和会话工厂对象
        session.close();
        sessionFactory.close();
    }

    //查询
    @Test
    public void queryData() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

        Session session = sessionFactory.openSession();

        User user = session.get(User.class, 1);
        System.out.println(user);

        session.close();
        sessionFactory.close();
    }

    @Test
    public void updateData() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            //只要对数据库表中的数据做了改变，则需要添加事务处理操作
            //开启事务
            Transaction transaction = session.beginTransaction();
            //更新操作之前，先获取要更新的数据
            User user = session.get(User.class, 1);
            user.setAddress("浙江杭州");
            session.update(user);

            //提交事务
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    @Test
    public void deleteData() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

        //开启事务
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, 1);
        session.delete(user);

        //提交事务
        transaction.commit();

        session.close();
        sessionFactory.close();
    }

    @Test
    public void saveOrUpdateTest() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

/*        //瞬时态：该对象没有id(主键)值，与session对象没有关联
        User user = new User();
        user.setUsername("hjx");
        user.setAddress("江苏扬州");
        session.saveOrUpdate(user);//对于瞬时态对象，使用saveOrUpdate方法操作时，执行的是添加操作*/



/*      //托管态：该对象有id（值），与session没有关联
        Transaction transaction = session.beginTransaction();
        User user = new User();
        user.setUid(2);
        user.setUsername("花小祥");
        user.setAddress("江苏宝应");
        session.saveOrUpdate(user);//对于托管态对象，使用saveOrUpdate方法时，执行的是更新操作
        //托管态对象的id（主键）值必须在数据表中已存在
        System.out.println(user);

        //提交事务
        transaction.commit();*/

        //持久态:该对象有id（主键）值，与session对象有关联
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, 3);
        user.setAddress("江苏无锡");
        //对于持久态对象，不调用saveOrUpdate方法也可以执行更新操作，与Hibernate缓存机制有关

//        session.saveOrUpdate(user);//对于托管态对象，使用saveOrUpdate方法时，执行的是更新操作
//
        transaction.commit();

        session.close();
        sessionFactory.close();

    }
    /*
     * 验证一级缓存是否存在
     * */

    public void validateCache() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

        User user1 = session.get(User.class, 1);
        System.out.println(user1);

        User user2 = session.get(User.class, 1);
        System.out.println(user2);

        session.close();
        sessionFactory.close();
    }

    @Test
    public void transactionTest() {

        SessionFactory sessionFactory = null;
        Transaction transaction = null;
        Session session = null;
        try {
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();

            transaction = session.beginTransaction();

            User user = session.get(User.class, 2);
            user.setAddress("湖北黄石");

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }


    }

    @Test
    public void queryDataTest() {
//        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
//        Session session = sessionFactory.openSession();
        Session session = HibernateUtils.getSession();//获取与本地线程绑定的session对象，需要事务支持
        Transaction transaction = session.beginTransaction();

        //查询所有数据
//        String Hql = "from User";
//        Query<User> query = session.createQuery(Hql, User.class);
//        List<User> userList = query.list();
//        transaction.commit();
//        for (User user : userList) {
//            System.out.println(user);
//        }

        //查询单一属性
        String HQL = "select username from User";
        Query<String> query = session.createQuery(HQL, String.class);
        List<String> usernames = query.list();
        for (String username : usernames) {
            System.out.println(username);
        }

        session.close();
//        sessionFactory.close();
    }
}
