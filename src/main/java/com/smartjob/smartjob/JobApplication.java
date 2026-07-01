package com.smartjob.smartjob;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String candidateName; 
    private String email; // இதோ இங்க இருக்கு பாஸ் மின்னல் வேக ஃபீல்ட்!
    private String jobTitle;
    private String company;
    private LocalDate appliedDate;
    private String status; // Pending, Accepted, Rejected
    private int matchPercent; 
    
    private String salary;
    private String experience;
    private String qualification;
    private String skills;
    private String resumePath;

    public JobApplication() {}

    public JobApplication(Long userId, String candidateName, String email, String jobTitle, String company, LocalDate appliedDate, String status, int matchPercent) {
        this.userId = userId;
        this.candidateName = candidateName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.company = company;
        this.appliedDate = appliedDate;
        this.status = status;
        this.matchPercent = matchPercent;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public String getEmail() { return email; } // இந்த கெட்டர் முக்கியம் பாஸ்!
    public void setEmail(String email) { this.email = email; } // இந்த செட்டர் தான் கம்பைலருக்கு வேணும்!
    
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    
    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getMatchPercent() { return matchPercent; }
    public void setMatchPercent(int matchPercent) { this.matchPercent = matchPercent; }
    
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getResumePath() { 
        return resumePath; 
    }
    public void setResumePath(String resumePath) { 
        this.resumePath = resumePath; 
    }}