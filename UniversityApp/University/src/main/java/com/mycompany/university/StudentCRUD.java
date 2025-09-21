package com.mycompany.university;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class StudentCRUD {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_University_jar_1.0-SNAPSHOTPU");

    public static void createStudent(String name, String email, Date dob, BigDecimal gpa, int departmentId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Departments department = em.find(Departments.class, departmentId);
            if (department != null) {
                Students student = new Students();
                student.setName(name);
                student.setEmail(email);
                student.setDob(dob);
                student.setGpa(gpa);
                student.setDepartmentId(department);
                em.persist(student);
                em.getTransaction().commit();
                System.out.println("✅ Student created: " + name);
            } else {
                System.out.println("Department not found");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    
    public static void findStudentById(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        TypedQuery<Students> query = em.createNamedQuery("Students.findById", Students.class);
        query.setParameter("id", id);
        Students student = query.getSingleResult();
        System.out.println("Student found: ID=" + student.getId() + ", Name=" + student.getName() + ", Email=" + student.getEmail() + ", GPA=" + student.getGpa() + ", Department=" + student.getDepartmentId().getName());
    } catch (NoResultException e) {
        System.out.println("Student not found");
    } finally {
        em.close();
    }
}


    
    public static void updateStudent(int id, String name, String email, Date dob, BigDecimal gpa) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        TypedQuery<Students> query = em.createNamedQuery("Students.findById", Students.class);
        query.setParameter("id", id);
        Students student = query.getSingleResult();
        
        if (student != null) {
            student.setName(name);
            student.setEmail(email);
            student.setDob(dob);
            student.setGpa(gpa);
            em.merge(student);
            em.getTransaction().commit();
            System.out.println("Student updated");
        } else {
            System.out.println("Student not found");
        }
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        e.printStackTrace();
    } finally {
        em.close();
    }
}

   
    public static void deleteStudent(int id) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        TypedQuery<Students> query = em.createNamedQuery("Students.findById", Students.class);
        query.setParameter("id", id);
        Students student = query.getSingleResult();
        
        if (student != null) {
          
            List<Enrollments> enrollments = em.createQuery("SELECT e FROM Enrollments e WHERE e.studentId = :student", Enrollments.class)
                    .setParameter("student", student)
                    .getResultList();
            for (Enrollments enrollment : enrollments) {
               
                em.createQuery("DELETE FROM Grades g WHERE g.enrollmentId = :enrollment")
                        .setParameter("enrollment", enrollment)
                        .executeUpdate();
                
              
                em.remove(enrollment);
            }
       
            em.remove(student);
            em.getTransaction().commit();
            System.out.println("Student deleted along with related enrollments and grades");
        } else {
            System.out.println("Student not found");
        }
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        System.out.println("Failed to delete student due to: " + e.getMessage());
        e.printStackTrace();
    } finally {
        em.close();
    }
}

    
    public static void findAllStudents() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Students> studentsList = em.createNamedQuery("Students.findAll", Students.class).getResultList();
            for (Students student : studentsList) {
                System.out.println("Student ID: " + student.getId() + " - Name: " + student.getName() + " - Department: " + student.getDepartmentId().getName());
            }
        } finally {
            em.close();
        }
    }
    
    



   
    public static void getStudentDetailsByName(String studentName) {
        EntityManager em = emf.createEntityManager(); 
        try {
            em.getTransaction().begin();

           
            TypedQuery<Students> query = em.createNamedQuery("Students.findByName", Students.class);
            query.setParameter("name", studentName);
            List<Students> students = query.getResultList();

            if (!students.isEmpty()) {
                Students student = students.get(0);
                System.out.println("Student Found:");
                System.out.println("ID: " + student.getId());
                System.out.println("Name: " + student.getName());
                System.out.println("Email: " + student.getEmail());
                System.out.println("Date of Birth: " + student.getDob());
                System.out.println("GPA: " + student.getGpa());

                
                if (student.getDepartmentId() != null) {
                    System.out.println("Department: " + student.getDepartmentId().getName());
                } else {
                    System.out.println("No department found for this student.");
                }

               
                if (student.getEnrollmentsCollection() != null && !student.getEnrollmentsCollection().isEmpty()) {
                    System.out.println("\nCourses enrolled by this student:");
                    for (Enrollments enrollment : student.getEnrollmentsCollection()) {
                        System.out.println("Course: " + enrollment.getCourseId().getTitle() +
                                           " | Semester: " + enrollment.getSemester() + 
                                           " | Year: " + enrollment.getYear());
                        
                      
                        for (Grades grade : enrollment.getGradesCollection()) {
                            System.out.println("Grade: " + grade.getGrade());
                        }
                    }
                } else {
                    System.out.println("No enrollments found for this student.");
                }
            } else {
                System.out.println("No student found with the name: " + studentName);
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

