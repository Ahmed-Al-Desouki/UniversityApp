package com.mycompany.university;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class GradeCRUD {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_University_jar_1.0-SNAPSHOTPU");

    public static void createGrade(int enrollmentId, String grade) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Enrollments enrollment = em.find(Enrollments.class, enrollmentId);
            if (enrollment != null) {
                Grades newGrade = new Grades();
                newGrade.setGrade(grade);
                newGrade.setEnrollmentId(enrollment);
                em.persist(newGrade);
                em.getTransaction().commit();
                System.out.println(" Grade assigned: " + grade);
            } else {
                System.out.println("Enrollment not found");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void findGradeById(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        TypedQuery<Grades> query = em.createNamedQuery("Grades.findById", Grades.class);
        query.setParameter("id", id);
        Grades grade = query.getSingleResult(); 
        if (grade != null) {
            System.out.println("Grade found: " + grade.getGrade() + " for Enrollment ID: " + grade.getEnrollmentId().getId());
        } else {
            System.out.println("Grade not found");
        }
    } catch (NoResultException e) {
        System.out.println("Grade not found");
    } finally {
        em.close();
    }
}


    public static void updateGrade(int id, String newGrade) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        TypedQuery<Grades> query = em.createNamedQuery("Grades.findById", Grades.class);
        query.setParameter("id", id);
        Grades grade = query.getSingleResult();
        
        if (grade != null) {
            grade.setGrade(newGrade);
            em.merge(grade);
            em.getTransaction().commit();
            System.out.println("Grade updated to: " + newGrade);
        } else {
            System.out.println("Grade not found");
        }
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


    
    public static void deleteGrade(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        TypedQuery<Grades> query = em.createNamedQuery("Grades.findById", Grades.class);
        query.setParameter("id", id);
        Grades grade = query.getSingleResult();
        
        if (grade != null) {
            em.remove(grade);
            em.getTransaction().commit();
            System.out.println("Grade deleted");
        } else {
            System.out.println("Grade not found");
        }
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


    public static void findAllGrades() {
        EntityManager em = emf.createEntityManager();
        try {
          
            List<Grades> gradesList = em.createNamedQuery("Grades.findAll", Grades.class).getResultList();
            for (Grades grade : gradesList) {
                System.out.println("Grade: " + grade.getGrade() + " for Enrollment ID: " + grade.getEnrollmentId().getId());
            }
        } finally {
            em.close();
        }
    }
}
