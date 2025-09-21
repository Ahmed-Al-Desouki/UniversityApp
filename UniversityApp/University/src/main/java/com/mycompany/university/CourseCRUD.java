package com.mycompany.university;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CourseCRUD {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_University_jar_1.0-SNAPSHOTPU");

    public static void createCourse(String title, int credits) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Courses course = new Courses();
            course.setTitle(title);
            course.setCredits(credits);
            em.persist(course);
            em.getTransaction().commit();
            System.out.println("✅ Course created with ID: " + course.getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void findCourseById(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        Courses course = em.createNamedQuery("Courses.findById", Courses.class)
                           .setParameter("id", id)
                           .getSingleResult();
        System.out.println("Found Course: " + course.getTitle());
    } catch (NoResultException e) {
        System.out.println("Course not found");
    } finally {
        em.close();
    }
}


    public static void updateCourse(int id, String title, int credits) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        Courses course = em.createNamedQuery("Courses.findById", Courses.class)
                           .setParameter("id", id)
                           .getSingleResult();

        course.setTitle(title);
        course.setCredits(credits);
        em.merge(course);

        em.getTransaction().commit();
        System.out.println(" Course updated");
    } catch (NoResultException e) {
        System.out.println("Course not found");
        em.getTransaction().rollback();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


   public static void deleteCourse(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        Courses course = em.createNamedQuery("Courses.findById", Courses.class)
                           .setParameter("id", id)
                           .getSingleResult();

        em.remove(course);
        em.getTransaction().commit();
        System.out.println(" Course deleted");
    } catch (NoResultException e) {
        System.out.println("Course not found");
        em.getTransaction().rollback();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}

    
    public static void findCoursesCredits(int number) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        TypedQuery<Courses> query = em.createNamedQuery("Courses.findByCredits", Courses.class);
        query.setParameter("credits", number);
        List<Courses> courses = query.getResultList();

        if (!courses.isEmpty()) {
            System.out.println("Courses with " + number + " credits:");
            for (Courses course : courses) {
                System.out.println("Course ID: " + course.getId() + ", Name: " + course.getTitle());
            }
        } else {
            System.out.println("No courses found with " + number + " credits.");
        }

        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}
    
    public static void findDepartmentsByCourseTitle(String courseTitle) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        TypedQuery<Departments> query = em.createNamedQuery("Courses.findDepartmentByTitle", Departments.class);
        query.setParameter("title", courseTitle);
        List<Departments> departments = query.getResultList();

        if (!departments.isEmpty()) {
            System.out.println("Departments offering course: " + courseTitle);
            for (Departments dept : departments) {
                System.out.println("Department ID: " + dept.getId() + ", Name: " + dept.getName());
            }
        } else {
            System.out.println("No departments found for course: " + courseTitle);
        }

        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}
    
    public static void findProfessorsByCourseTitle(String courseTitle) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        TypedQuery<Professors> query = em.createNamedQuery("Courses.findProfessorsByTitle", Professors.class);
        query.setParameter("title", courseTitle);
        List<Professors> professors = query.getResultList();

        if (!professors.isEmpty()) {
            System.out.println("Professors teaching course: " + courseTitle);
            for (Professors prof : professors) {
                System.out.println("Professor ID: " + prof.getId() + ", Name: " + prof.getName());
            }
        } else {
            System.out.println("No professors found for course: " + courseTitle);
        }

        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


}
