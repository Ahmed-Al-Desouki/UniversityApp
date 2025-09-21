package com.mycompany.university;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class University {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_University_jar_1.0-SNAPSHOTPU");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
        emf.close();
        scanner.close();
    }

    private static void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nSelect entity to manage:");
            System.out.println("1. Departments");
            System.out.println("2. Courses");
            System.out.println("3. Professors");
            System.out.println("4. Students");
            System.out.println("5. Enrollments");
            System.out.println("6. Grades");
            System.out.println("7. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 7) {
                System.out.println("Exiting the program.");
                break;
            }

            switch (choice) {
                case 1: manageDepartments(scanner); break;
                case 2: manageCourses(scanner); break;
                case 3: manageProfessors(scanner); break;
                case 4: manageStudents(scanner); break;
                case 5: manageEnrollments(scanner); break;
                case 6: manageGrades(scanner); break;
                default: System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private static Date parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    // Departments Management
    private static void manageDepartments(Scanner scanner) {
        while (true) {
            System.out.println("\nDepartment Management:");
            System.out.println("1. Add Department");
            System.out.println("2. Find Department by ID");
            System.out.println("3. Update Department");
            System.out.println("4. Delete Department");
            System.out.println("5. Show All Departments");
            System.out.println("6. find Department By Name");
            System.out.println("7. Back to main menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 7) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter Department Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Location: ");
                    String location = scanner.nextLine();
                    System.out.print("Enter Head of Department: ");
                    String head = scanner.nextLine();
                    System.out.print("Enter Contact Number: ");
                    String contact = scanner.nextLine();
                    DepartmentCRUD.createDepartment(name, location, head, contact);
                    break;
                case 2:
                    System.out.print("Enter Department ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    DepartmentCRUD.findDepartmentById(id);
                    break;
                case 3:
                    System.out.print("Enter Department ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Location: ");
                    String newLocation = scanner.nextLine();
                    System.out.print("Enter new Head of Department: ");
                    String newHead = scanner.nextLine();
                    System.out.print("Enter new Contact Number: ");
                    String newContact = scanner.nextLine();
                    DepartmentCRUD.updateDepartment(updateId, newName, newLocation, newHead, newContact);
                    break;
                case 4:
                    System.out.print("Enter Department ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    DepartmentCRUD.deleteDepartment(deleteId);
                    break;
                case 5:
                    showAllDepartments();
                    break;
                case 6:
                    System.out.println("Enter the department Name:");
                    String departmentName = scanner.nextLine();
                    DepartmentCRUD.findDepartmentByName(departmentName);
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private static void showAllDepartments() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Departments> departments = em.createNamedQuery("Departments.findAll", Departments.class).getResultList();
            for (Departments dept : departments) {
                System.out.println("ID: " + dept.getId() + ", Name: " + dept.getName() + ", Location: " + dept.getLocation() + ", Head: " + dept.getHeadOfDepartment());
            }
        } finally {
            em.close();
        }
    }

    // Courses Management
    private static void manageCourses(Scanner scanner) {
        while (true) {
            System.out.println("\nCourse Management:");
            System.out.println("1. Add Course");
            System.out.println("2. Find Course by ID");
            System.out.println("3. Update Course");
            System.out.println("4. Delete Course");
            System.out.println("5. Show All Courses");
            System.out.println("6. find Courses Credits");
            System.out.println("7. find Departments By Course Title");
            System.out.println("8. find Professors By Course Title");
            System.out.println("9. Back to main menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 9) break;

            switch (choice) {
                case 1:
                    addCourse(scanner);
                    break;
                case 2:
                    System.out.print("Enter Course ID: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    findCourseById(courseId);
                    break;
                case 3:
                    updateCourse(scanner);
                    break;
                case 4:
                    System.out.print("Enter Course ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    CourseCRUD.deleteCourse(deleteId);
                    break;
                case 5:
                    showAllCourses();
                    break;
                case 6:
                    System.out.print("Enter a number: ");
                    int number = scanner.nextInt();
                    CourseCRUD.findCoursesCredits(number);
                    break;
                case 7:
                    System.out.print("Enter a course Title: ");
                    String courseTitle = scanner.nextLine();
                    CourseCRUD.findDepartmentsByCourseTitle(courseTitle);
                    break;
                case 8:
                    System.out.print("Enter a course Title: ");
                    String courseTitle1 = scanner.nextLine();
                    CourseCRUD.findProfessorsByCourseTitle(courseTitle1);
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private static void addCourse(Scanner scanner) {
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Department ID: ");
        int deptId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Professor ID: ");
        int profId = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Departments department = em.find(Departments.class, deptId);
            Professors professor = em.find(Professors.class, profId);
            if (department != null && professor != null) {
                Courses course = new Courses();
                course.setTitle(title);
                course.setCredits(credits);
                course.setDepartmentId(department);
                course.setProfessorId(professor);
                em.persist(course);
                em.getTransaction().commit();
                System.out.println("Course added successfully with ID: " + course.getId());
            } else {
                System.out.println("Department or Professor not found.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void findCourseById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Courses course = em.find(Courses.class, id);
            if (course != null) {
                System.out.println("Course ID: " + course.getId());
                System.out.println("Title: " + course.getTitle());
                System.out.println("Credits: " + course.getCredits());
                System.out.println("Department: " + (course.getDepartmentId() != null ? course.getDepartmentId().getName() : "N/A"));
                System.out.println("Professor: " + (course.getProfessorId() != null ? course.getProfessorId().getName() : "N/A"));
            } else {
                System.out.println("Course not found.");
            }
        } finally {
            em.close();
        }
    }

    private static void updateCourse(Scanner scanner) {
        System.out.print("Enter Course ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Courses course = em.find(Courses.class, id);
            if (course != null) {
                System.out.print("Enter new Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter new Credits: ");
                int credits = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new Department ID: ");
                int deptId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new Professor ID: ");
                int profId = scanner.nextInt();
                scanner.nextLine();
                Departments department = em.find(Departments.class, deptId);
                Professors professor = em.find(Professors.class, profId);
                if (department != null && professor != null) {
                    course.setTitle(title);
                    course.setCredits(credits);
                    course.setDepartmentId(department);
                    course.setProfessorId(professor);
                    em.merge(course);
                    em.getTransaction().commit();
                    System.out.println("Course updated successfully.");
                } else {
                    System.out.println("Department or Professor not found.");
                    em.getTransaction().rollback();
                }
            } else {
                System.out.println("Course not found.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void showAllCourses() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Courses> courses = em.createNamedQuery("Courses.findAll", Courses.class).getResultList();
            for (Courses course : courses) {
                System.out.println("ID: " + course.getId() + ", Title: " + course.getTitle() + ", Credits: " + course.getCredits() + ", Dept: " + (course.getDepartmentId() != null ? course.getDepartmentId().getName() : "N/A"));
            }
        } finally {
            em.close();
        }
    }

    // Professors Management
    private static void manageProfessors(Scanner scanner) {
        while (true) {
            System.out.println("\nProfessor Management:");
            System.out.println("1. Add Professor");
            System.out.println("2. Find Professor by ID");
            System.out.println("3. Update Professor");
            System.out.println("4. Delete Professor");
            System.out.println("5. Show All Professors");
            System.out.println("6. get Professors By Course And Department");
            System.out.println("7. Back to main menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 7) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter Professor Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Hire Date (yyyy-mm-dd): ");
                    String hireDateString = scanner.nextLine();
                    Date hireDate = parseDate(hireDateString);
                    if (hireDate == null) {
                        System.out.println("Invalid date format.");
                        break;
                    }
                    System.out.print("Enter Department ID: ");
                    int deptId = scanner.nextInt();
                    scanner.nextLine();
                    ProfessorCRUD.createProfessor(name, hireDate, deptId);
                    break;
                case 2:
                    System.out.print("Enter Professor ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    ProfessorCRUD.findProfessorById(id);
                    break;
                case 3:
                    System.out.print("Enter Professor ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Hire Date (yyyy-mm-dd): ");
                    String newHireDateString = scanner.nextLine();
                    Date newHireDate = parseDate(newHireDateString);
                    if (newHireDate == null) {
                        System.out.println("Invalid date format.");
                        break;
                    }
                    ProfessorCRUD.updateProfessor(updateId, newName, newHireDate);
                    break;
                case 4:
                    System.out.print("Enter Professor ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    ProfessorCRUD.deleteProfessor(deleteId);
                    break;
                case 5:
                    ProfessorCRUD.findAllProfessors();
                    break;
                case 6:
                    System.out.println("Enter Department name:");
                    String Dbname = scanner.nextLine();
                    System.out.println("Enter Course name:");
                    String Crname = scanner.nextLine();
                    ProfessorCRUD.getProfessorsByCourseAndDepartment(Crname, Dbname);
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    // Students Management
    private static void manageStudents(Scanner scanner) {
        while (true) {
            System.out.println("\nStudent Management:");
            System.out.println("1. Add Student");
            System.out.println("2. Find Student by ID");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Show All Students");
            System.out.println("6. get Student Details By Name");
            System.out.println("7. Back to main menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 7) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
                    String dobString = scanner.nextLine();
                    Date dob = parseDate(dobString);
                    if (dob == null) {
                        System.out.println("Invalid date format.");
                        break;
                    }
                    System.out.print("Enter GPA: ");
                    BigDecimal gpa;
                    try {
                        gpa = scanner.nextBigDecimal();
                    } catch (Exception e) {
                        System.out.println("Invalid GPA format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    System.out.print("Enter Department ID: ");
                    int deptId;
                    try {
                        deptId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid Department ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    StudentCRUD.createStudent(name, email, dob, gpa, deptId);
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    int id;
                    try {
                        id = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    StudentCRUD.findStudentById(id);
                    break;
                case 3:
                    System.out.print("Enter Student ID to update: ");
                    int updateId;
                    try {
                        updateId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter new Date of Birth (yyyy-mm-dd): ");
                    String newDobString = scanner.nextLine();
                    Date newDob = parseDate(newDobString);
                    if (newDob == null) {
                        System.out.println("Invalid date format.");
                        break;
                    }
                    System.out.print("Enter new GPA: ");
                    BigDecimal newGpa;
                    try {
                        newGpa = scanner.nextBigDecimal();
                    } catch (Exception e) {
                        System.out.println("Invalid GPA format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    StudentCRUD.updateStudent(updateId, newName, newEmail, newDob, newGpa);
                    break;
                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    int deleteId;
                    try {
                        deleteId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    StudentCRUD.deleteStudent(deleteId);
                    break;
                case 5:
                    StudentCRUD.findAllStudents();
                    break;
                 case 6:
                     System.out.println("Enter the student name:");
                     String Sname = scanner.nextLine();
                     StudentCRUD.getStudentDetailsByName(Sname);
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    // Enrollments Management
    private static void manageEnrollments(Scanner scanner) {
        while (true) {
            System.out.println("\nEnrollment Management:");
            System.out.println("1. Add Enrollment");
            System.out.println("2. Find Enrollment by ID");
            System.out.println("3. Update Enrollment");
            System.out.println("4. Delete Enrollment");
            System.out.println("5. Show All Enrollments");
            System.out.println("6. Back to main menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 6) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int studentId;
                    try {
                        studentId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    System.out.print("Enter Course ID: ");
                    int courseId;
                    try {
                        courseId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    System.out.print("Enter Semester: ");
                    String semester = scanner.nextLine();
                    System.out.print("Enter Year: ");
                    int year;
                    try {
                        year = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid year format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    EnrollmentCRUD.createEnrollment(studentId, courseId, semester, year);
                    break;
                case 2:
                    System.out.print("Enter Enrollment ID: ");
                    int id;
                    try {
                        id = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    EnrollmentCRUD.findEnrollmentById(id);
                    break;
                case 3:
                    System.out.print("Enter Enrollment ID to update: ");
                    int updateId;
                    try {
                        updateId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    System.out.print("Enter new Semester: ");
                    String newSemester = scanner.nextLine();
                    System.out.print("Enter new Year: ");
                    int newYear;
                    try {
                        newYear = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid year format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    EnrollmentCRUD.updateEnrollment(updateId, newSemester, newYear);
                    break;
                case 4:
                    System.out.print("Enter Enrollment ID to delete: ");
                    int deleteId;
                    try {
                        deleteId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    EnrollmentCRUD.deleteEnrollment(deleteId);
                    break;
                case 5:
                    EnrollmentCRUD.findAllEnrollments();
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    // Grades Management
    private static void manageGrades(Scanner scanner) {
        while (true) {
            System.out.println("\nGrade Management:");
            System.out.println("1. Add Grade");
            System.out.println("2. Find Grade by ID");
            System.out.println("3. Update Grade");
            System.out.println("4. Delete Grade");
            System.out.println("5. Show All Grades");
            System.out.println("6. Back to main menu");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 6) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter Enrollment ID: ");
                    int enrollmentId;
                    try {
                        enrollmentId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    System.out.print("Enter Grade: ");
                    String grade = scanner.nextLine();
                    GradeCRUD.createGrade(enrollmentId, grade);
                    break;
                case 2:
                    System.out.print("Enter Grade ID: ");
                    int id;
                    try {
                        id = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    GradeCRUD.findGradeById(id);
                    break;
                case 3:
                    System.out.print("Enter Grade ID to update: ");
                    int updateId;
                    try {
                        updateId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    System.out.print("Enter new Grade: ");
                    String newGrade = scanner.nextLine();
                    GradeCRUD.updateGrade(updateId, newGrade);
                    break;
                case 4:
                    System.out.print("Enter Grade ID to delete: ");
                    int deleteId;
                    try {
                        deleteId = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID format.");
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine();
                    GradeCRUD.deleteGrade(deleteId);
                    break;
                case 5:
                    GradeCRUD.findAllGrades();
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }
}