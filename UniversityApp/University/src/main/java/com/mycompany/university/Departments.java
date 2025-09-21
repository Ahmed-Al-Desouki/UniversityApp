/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.university;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author mosa
 */
@Entity
@Table(name = "departments")
@NamedQueries({
    @NamedQuery(name = "Departments.findAll", query = "SELECT d FROM Departments d"),
    @NamedQuery(name = "Departments.findById", query = "SELECT d FROM Departments d WHERE d.id = :id"),
    @NamedQuery(name = "Departments.findByName", query = "SELECT d FROM Departments d WHERE d.name = :name"),
    @NamedQuery(name = "Departments.findByLocation", query = "SELECT d FROM Departments d WHERE d.location = :location"),
    @NamedQuery(name = "Departments.findByHeadOfDepartment", query = "SELECT d FROM Departments d WHERE d.headOfDepartment = :headOfDepartment"),
    @NamedQuery(name = "Departments.findByContactNumber", query = "SELECT d FROM Departments d WHERE d.contactNumber = :contactNumber"),
    @NamedQuery(name = "Departments.findByEstablishedDate", query = "SELECT d FROM Departments d WHERE d.establishedDate = :establishedDate")})
public class Departments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "head_of_department")
    private String headOfDepartment;
    @Column(name = "contact_number")
    private String contactNumber;
    @Column(name = "established_date")
    @Temporal(TemporalType.DATE)
    private Date establishedDate;
    @Lob
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "departmentId")
    private Collection<Courses> coursesCollection;
    @OneToMany(mappedBy = "departmentId")
    private Collection<Professors> professorsCollection;
    @OneToMany(mappedBy = "departmentId")
    private Collection<Students> studentsCollection;

    public Departments() {
    }

    public Departments(Integer id) {
        this.id = id;
    }

    public Departments(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(Date establishedDate) {
        this.establishedDate = establishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Courses> getCoursesCollection() {
        return coursesCollection;
    }

    public void setCoursesCollection(Collection<Courses> coursesCollection) {
        this.coursesCollection = coursesCollection;
    }

    public Collection<Professors> getProfessorsCollection() {
        return professorsCollection;
    }

    public void setProfessorsCollection(Collection<Professors> professorsCollection) {
        this.professorsCollection = professorsCollection;
    }

    public Collection<Students> getStudentsCollection() {
        return studentsCollection;
    }

    public void setStudentsCollection(Collection<Students> studentsCollection) {
        this.studentsCollection = studentsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departments)) {
            return false;
        }
        Departments other = (Departments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.university.Departments[ id=" + id + " ]";
    }
    
}
