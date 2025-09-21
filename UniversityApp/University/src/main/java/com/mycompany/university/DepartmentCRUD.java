package com.mycompany.university;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DepartmentCRUD {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_University_jar_1.0-SNAPSHOTPU");

    public static void createDepartment(String name, String location, String headOfDepartment, String contactNumber) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Departments department = new Departments();
            department.setName(name);
            department.setLocation(location);
            department.setHeadOfDepartment(headOfDepartment);
            department.setContactNumber(contactNumber);
            em.persist(department);
            em.getTransaction().commit();
            System.out.println(" Department created with ID: " + department.getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void findDepartmentById(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        Departments department = em.createNamedQuery("Departments.findById", Departments.class)
                                   .setParameter("id", id)
                                   .getSingleResult();
        System.out.println("Found Department: " + department.getName());
    } catch (NoResultException e) {
        System.out.println("Department not found");
    } finally {
        em.close();
    }
}


    public static void updateDepartment(int id, String name, String location, String headOfDepartment, String contactNumber) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        Departments department = em.createNamedQuery("Departments.findById", Departments.class)
                                   .setParameter("id", id)
                                   .getSingleResult();

        department.setName(name);
        department.setLocation(location);
        department.setHeadOfDepartment(headOfDepartment);
        department.setContactNumber(contactNumber);

        em.merge(department);
        em.getTransaction().commit();
        System.out.println("✅ Department updated");

    } catch (NoResultException e) {
        System.out.println("Department not found");
        em.getTransaction().rollback();
    } catch (Exception e) {
        em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


    public static void deleteDepartment(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Departments> query = em.createNamedQuery("Departments.findById", Departments.class);
            query.setParameter("id", id);
            Departments department = query.getSingleResult();
            if (department != null) {
                em.remove(department);
                em.getTransaction().commit();
                System.out.println("✅ Department deleted");
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
    
    public static void findDepartmentByName(String departmentName) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        TypedQuery<Departments> query = em.createNamedQuery("Departments.findByName", Departments.class);
        query.setParameter("name", departmentName);
        List<Departments> departments = query.getResultList();

        if (!departments.isEmpty()) {
            Departments dept = departments.get(0);
            System.out.println("Department Found:");
            System.out.println("ID: " + dept.getId());
            System.out.println("Name: " + dept.getName());
            System.out.println("Location: " + dept.getLocation());
            System.out.println("Head: " + dept.getHeadOfDepartment());
            System.out.println("Contact: " + dept.getContactNumber());
            System.out.println("Established Date: " + dept.getEstablishedDate());
        } else {
            System.out.println("No department found with name: " + departmentName);
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
