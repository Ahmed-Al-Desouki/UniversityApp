package com.mycompany.university;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ProfessorCRUD {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_University_jar_1.0-SNAPSHOTPU");

 
    public static void createProfessor(String name, Date hireDate, int departmentId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Departments department = em.find(Departments.class, departmentId);
            if (department != null) {
                Professors professor = new Professors();
                professor.setName(name);
                professor.setHireDate(hireDate);
                professor.setDepartmentId(department);
                em.persist(professor);
                em.getTransaction().commit();
                System.out.println("Professor created: " + name);
            } else {
                System.out.println("Department not found");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    public static void findProfessorById(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        TypedQuery<Professors> query = em.createNamedQuery("Professors.findById", Professors.class);
        query.setParameter("id", id);
        Professors professor = query.getSingleResult(); 
        System.out.println("Professor found: " + professor.toString());
    } catch (NoResultException e) {
        System.out.println("Professor not found");
    } finally {
        em.close();
    }
}


  
    public static void updateProfessor(int id, String name, Date hireDate) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        TypedQuery<Professors> query = em.createNamedQuery("Professors.findById", Professors.class);
        query.setParameter("id", id);
        Professors professor = query.getSingleResult(); 
        
        if (professor != null) {
            professor.setName(name);
            professor.setHireDate(hireDate);
            em.merge(professor);
            em.getTransaction().commit();
            System.out.println(" Professor updated");
        } else {
            System.out.println("Professor not found");
        }
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


  
    public static void deleteProfessor(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        TypedQuery<Professors> query = em.createNamedQuery("Professors.findById", Professors.class);
        query.setParameter("id", id);
        Professors professor = query.getSingleResult();
        
        if (professor != null) {
            em.remove(professor);
            em.getTransaction().commit();
            System.out.println("Professor deleted");
        } else {
            System.out.println("Professor not found");
        }
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


    
    public static void findAllProfessors() {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Fetching all professors...");
            List<Professors> professorsList = em.createNamedQuery("Professors.findAll", Professors.class).getResultList();
            for (Professors professor : professorsList) {
                System.out.println("Professor ID: " + professor.getId() + " - Name: " + professor.getName() + " - Department: " + professor.getDepartmentId().getName());
            }
        } finally {
            em.close();
        }
    }
    
     public static void getProfessorsByCourseAndDepartment(String courseName, String departmentName) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        // تعديل الاستعلام ليتناسب مع الـ JPA
        String jpql = "SELECT p FROM Professors p " +
                      "JOIN p.coursesCollection c " +
                      "JOIN c.departmentId d " +
                      "WHERE c.title = :courseName " +
                      "AND d.name = :departmentName";
        
        TypedQuery<Professors> query = em.createQuery(jpql, Professors.class);
        query.setParameter("courseName", courseName);
        query.setParameter("departmentName", departmentName);

        List<Professors> professors = query.getResultList();

        if (!professors.isEmpty()) {
            System.out.println("Professors teaching the course '" + courseName + "' in the department '" + departmentName + "':");
            for (Professors professor : professors) {
                System.out.println("Professor: " + professor.getName() + " | Department: " + professor.getDepartmentId().getName());
            }
        } else {
            System.out.println("No professors found for the course '" + courseName + "' in the department '" + departmentName + "'.");
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
