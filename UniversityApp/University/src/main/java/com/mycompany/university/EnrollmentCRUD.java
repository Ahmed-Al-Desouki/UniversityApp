package com.mycompany.university;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class EnrollmentCRUD {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_University_jar_1.0-SNAPSHOTPU");

    
    public static void createEnrollment(int studentId, int courseId, String semester, int year) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Students student = em.find(Students.class, studentId);
            Courses course = em.find(Courses.class, courseId);
            if (student != null && course != null) {
                Enrollments enrollment = new Enrollments();
                enrollment.setSemester(semester);
                enrollment.setYear(year);
                enrollment.setStudentId(student);
                enrollment.setCourseId(course);
                em.persist(enrollment);
                em.getTransaction().commit();
                System.out.println(" Enrollment created for Student ID: " + studentId + " in Course ID: " + courseId);
            } else {
                System.out.println("Student or Course not found");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    
    public static void findEnrollmentById(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        Enrollments enrollment = em.createNamedQuery("Enrollments.findById", Enrollments.class)
                                   .setParameter("id", id)
                                   .getSingleResult();
        System.out.println("Enrollment found: " + enrollment.toString());
    } catch (NoResultException e) {
        System.out.println("Enrollment not found");
    } finally {
        em.close();
    }
}


    public static void updateEnrollment(int id, String semester, int year) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        Enrollments enrollment = em.createNamedQuery("Enrollments.findById", Enrollments.class)
                                   .setParameter("id", id)
                                   .getSingleResult();

        enrollment.setSemester(semester);
        enrollment.setYear(year);
        em.merge(enrollment);
        em.getTransaction().commit();
        System.out.println("Enrollment updated");

    } catch (NoResultException e) {
        System.out.println("Enrollment not found");
        em.getTransaction().rollback();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


  
    public static void deleteEnrollment(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        Enrollments enrollment = em.createNamedQuery("Enrollments.findById", Enrollments.class)
                                   .setParameter("id", id)
                                   .getSingleResult();

        em.remove(enrollment);
        em.getTransaction().commit();
        System.out.println("Enrollment deleted");

    } catch (NoResultException e) {
        System.out.println("Enrollment not found");
        em.getTransaction().rollback();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


   
    public static void findAllEnrollments() {
    EntityManager em = emf.createEntityManager();
    try {
        List<Enrollments> enrollmentsList = em.createNamedQuery("Enrollments.findAll", Enrollments.class)
                                              .getResultList();

        if (enrollmentsList.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            for (Enrollments enrollment : enrollmentsList) {
                System.out.println("📄 Enrollment ID: " + enrollment.getId()
                        + " | Student ID: " + enrollment.getStudentId().getId()
                        + " | Course ID: " + enrollment.getCourseId().getId());
            }
        }
    } finally {
        em.close();
    }
}

}
