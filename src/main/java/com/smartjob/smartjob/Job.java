package com.smartjob.smartjob;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String location;
    private String icon;
    private String category; 

    @Column(length = 1000)
    private String reqSkills; // Comma separated skills

    private String qualification; // e.g., B.E CSE
    private String experience;    // e.g., 0-2 years
    private String salary;        // e.g., 6 LPA
    
    @Column(length = 2000)
    private String jobDescription;
    private String lastDate;      // e.g., 2026-08-30

    public Job() {}

    public Job(String title, String company, String location, String icon, String category, 
               String reqSkills, String qualification, String experience, String salary, 
               String jobDescription, String lastDate) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.icon = icon;
        this.category = category;
        this.reqSkills = reqSkills;
        this.qualification = qualification;
        this.experience = experience;
        this.salary = salary;
        this.jobDescription = jobDescription;
        this.lastDate = lastDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getReqSkills() { return reqSkills; }
    public void setReqSkills(String reqSkills) { this.reqSkills = reqSkills; }
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
    public String getLastDate() { return lastDate; }
    public void setLastDate(String lastDate) { this.lastDate = lastDate; }
}