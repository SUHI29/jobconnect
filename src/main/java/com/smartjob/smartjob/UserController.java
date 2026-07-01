package com.smartjob.smartjob;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.multipart.MultipartFile;
import java.io.File; 
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") 
public class UserController {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public UserController(UserRepository userRepository, 
                          JobRepository jobRepository, 
                          JobApplicationRepository jobApplicationRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

   
    @PostMapping(value = "/form-signup", consumes = "application/x-www-form-urlencoded")
    public RedirectView handleFormSignup(@RequestParam("fullName") String fullName,
                                         @RequestParam("email") String email, 
                                         @RequestParam("password") String password) {
        if(userRepository.findByEmail(email) != null) {
            return new RedirectView("/login.html?mode=signup&error=exists");
        }
        User newUser = new User();
        newUser.setFullname(fullName); 
        newUser.setEmail(email);
        newUser.setPassword(password);
        userRepository.save(newUser); 
        return new RedirectView("/login.html?success=registered");
    }

   
   @PostMapping(value = "/form-login", consumes = "application/x-www-form-urlencoded")
    public RedirectView handleFormLogin(@RequestParam("email") String email, 
                                        @RequestParam("password") String password) {
        
      
        if ("admin@gmail.com".equals(email.trim()) && "admin123".equals(password)) {
            System.out.println("====== ADMIN LOGGED IN SUCCESSFULLY ======");
            return new RedirectView("/admin.html"); 
        }

        
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null && existingUser.getPassword().equals(password)) {
            return new RedirectView("/profile.html?userId=" + existingUser.getId());
        }
        
        return new RedirectView("/login.html?error=invalid");
    }
   
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

   
    @PostMapping(value = "/update-profile", consumes = "application/x-www-form-urlencoded")
    public RedirectView updateProfile(@RequestParam("userId") Long userId,
                                      @RequestParam("fullName") String fullName, 
                                      @RequestParam("phoneNumber") String phoneNumber,
                                      @RequestParam("location") String location,
                                      @RequestParam("education") String education,
                                      @RequestParam("skills") String skills,
                                      @RequestParam("certifications") String certifications) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFullname(fullName); 
            user.setPhoneNumber(phoneNumber);
            user.setLocation(location);
            user.setEducation(education);
            user.setSkills(skills);
            user.setCertifications(certifications);
            userRepository.save(user);
            return new RedirectView("/dashboard.html?userId=" + userId);
        }
        return new RedirectView("/login.html?error=session_expired");
    }
@GetMapping("/{id}/recommendations")
    public ResponseEntity<?> getJobRecommendations(@PathVariable Long id, @RequestParam(value = "category", required = false) String category) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        String userSkillsRaw = user.getSkills() != null ? user.getSkills().toLowerCase() : "";

       
        if (jobRepository.count() == 0) {
            
        
            jobRepository.save(new Job("Java Backend Engineer", "TechNova Solutions", "Bangalore", "💻", "Software Development", 
                    "java, data structures", "B.E / B.Tech / MCA", "0-2 years", "6 LPA", "Build enterprise systems using modern Java frameworks.", "2026-08-30"));
            jobRepository.save(new Job("Spring Boot Developer", "CodeWave Technologies", "Chennai", "💻", "Software Development", 
                    "java, sql", "Any Graduate", "1-2 years", "4.5 LPA", "Develop robust microservices and cloud applications.", "2026-07-25"));
            jobRepository.save(new Job("Associate Java Developer", "NextGen Software", "Hyderabad", "💻", "Software Development", 
                    "java, python, data structures", "B.E / B.Tech", "Fresher", "4 LPA", "Maintain scale-out backend applications and automation services.", "2026-08-05"));
            jobRepository.save(new Job("Backend Java Consultant", "ByteFusion Technologies", "Kochi", "💻", "Software Development", 
                    "java, html, css", "B.Tech CSE", "1-3 years", "5 LPA", "Work on client enterprise architectures and backend modules.", "2026-08-14"));
            jobRepository.save(new Job("Java Platform Developer", "FutureStack Pvt. Ltd.", "Bangalore", "💻", "Software Development", 
                    "java, data structures, sql", "B.E / MCA", "Fresher", "4.8 LPA", "Design database-driven Java APIs and services.", "2026-09-02"));

            jobRepository.save(new Job("Data Scientist / Python Engineer", "InnovateX Labs", "Bangalore", "🤖", "Software Development", 
                    "python, data structures", "B.E / M.Tech / PhD", "1-3 years", "14 LPA", "Work on core machine learning models and dataset pipelining.", "2026-10-01"));
            jobRepository.save(new Job("Python Web Developer", "SmartEdge Technologies", "Chennai", "🤖", "Software Development", 
                    "python, html, css, js", "Any Graduate", "Fresher", "5 LPA", "Develop backend application logic using Django or FastAPI platforms.", "2026-07-30"));
            jobRepository.save(new Job("AI Prompt Engineer / Developer", "Apex Digital Solutions", "Coimbatore", "🤖", "Software Development", 
                    "python, openai api integration, data structures", "B.E / B.Tech / MCA", "Fresher", "8 LPA", "Integrate large language models and build smart AI features.", "2026-10-05"));
            jobRepository.save(new Job("Python Backend Engineer", "TechNova Solutions", "Trichy", "🤖", "Software Development", 
                    "python, sql", "B.Sc / BCA / B.E", "0-2 years", "4.5 LPA", "Write robust Python automation scripts and data extraction models.", "2026-08-20"));
            jobRepository.save(new Job("Machine Learning Associate", "CodeWave Technologies", "Hyderabad", "💻", "Software Development", 
                    "python, tensorflow, data structures", "B.E CSE / IT", "1-2 years", "6.5 LPA", "Deploy and tune predictive models on enterprise software structures.", "2026-09-12"));

            jobRepository.save(new Job("Full Stack JavaScript Developer", "NextGen Software", "Chennai", "💻", "Software Development", 
                    "html, css, js, node.js", "B.E / B.Tech / BSc", "Fresher", "6.5 LPA", "Craft amazing UI layouts and integrate Node.js backend services.", "2026-07-15"));
            jobRepository.save(new Job("Backend Node.js Developer", "ByteFusion Technologies", "Noida", "💻", "Software Development", 
                    "js, node.js, data structures", "B.E / MCA", "1-3 years", "7.5 LPA", "Build scalable fintech REST APIs using Express and Node.js.", "2026-08-12"));
            jobRepository.save(new Job("Frontend UI Engineer", "PixelSoft Solutions", "Chennai", "💻", "Software Development", 
                    "html, css, javascript, react", "B.E/B.Tech", "Fresher", "4.5 LPA", "Create clean web layouts and highly reactive component design.", "2026-07-15"));
            jobRepository.save(new Job("MERN Full Stack Engineer", "CloudNest Systems", "Madurai", "💻", "Software Development", 
                    "html, css, js, node.js, mongodb", "Any Graduate", "1-3 years", "5.5 LPA", "Develop end-to-end web applications with Node and MongoDB.", "2026-09-10"));
            jobRepository.save(new Job("React UI Developer", "InnovateX Labs", "Tenkasi", "⚛️", "Software Development", 
                    "html, css, javascript, react.js", "Any Graduate", "Fresher", "5.2 LPA", "Build modular and scalable UI frameworks using React ecosystem.", "2026-08-28"));

            jobRepository.save(new Job("Core Systems Engineer", "FutureStack Pvt. Ltd.", "Chennai", "💻", "Software Development", 
                    "c, c++, data structures", "B.E CSE / IT", "0-2 years", "7 LPA", "Develop high-performance core platform products.", "2026-08-30"));
            jobRepository.save(new Job("Embedded Systems Developer", "SmartEdge Technologies", "Bangalore", "💻", "Software Development", 
                    "c, c++", "B.E ECE / CSE", "Fresher", "5.5 LPA", "Work on next-generation automotive embedded systems software.", "2026-07-20"));
            jobRepository.save(new Job("R&D Systems Architect", "Apex Digital Solutions", "Hyderabad", "💻", "Software Development", 
                    "c++, data structures", "M.Tech / B.E", "1-3 years", "11 LPA", "Optimize system firmware and core software libraries.", "2026-09-15"));
            jobRepository.save(new Job("Systems Programmer", "TechNova Solutions", "Bangalore", "💻", "Software Development", 
                    "c, c++, data structures", "B.E / B.Tech", "Fresher", "8.5 LPA", "Write optimized drivers and memory management architectures.", "2026-08-18"));
            jobRepository.save(new Job("Software Engineer - C++", "CodeWave Technologies", "Pune", "💻", "Software Development", 
                    "c++, data structures", "B.E / M.E CSE", "1-3 years", "12 LPA", "Accelerate computer graphics algorithms and low-level processing subsystems.", "2026-10-10"));

           
            jobRepository.save(new Job("Cloud Infrastructure Engineer", "CloudNest Systems", "Bangalore", "☁️", "Software Development", 
                    "java, python, aws, docker", "B.E CSE / IT", "0-2 years", "10 LPA", "Manage application deployment and automate cloud setups.", "2026-08-22"));
            jobRepository.save(new Job("DevOps Automation Associate", "PixelSoft Solutions", "Chennai", "💻", "Software Development", 
                    "python, aws, docker", "B.E / B.Tech", "Fresher", "4.5 LPA", "Implement CI/CD deployment pipelines using secure cloud containers.", "2026-07-29"));
            jobRepository.save(new Job("Cloud Operations Specialist", "ByteFusion Technologies", "Hyderabad", "☁️", "Software Development", 
                    "java, sql, azure", "Any Graduate", "1-3 years", "7 LPA", "Maintain global cloud database services and handle server orchestration.", "2026-08-11"));
            jobRepository.save(new Job("Full Stack Cloud Engineer", "NextGen Software", "Kochi", "🏢", "Software Development", 
                    "html, css, js, node.js, aws", "B.E / MCA", "Fresher", "5.8 LPA", "Build modern micro-frontend stacks and tie them with cloud storage APIs.", "2026-09-05"));
            jobRepository.save(new Job("Systems & Cloud Administrator", "FutureStack Pvt. Ltd.", "Bangalore", "🌐", "Software Development", 
                    "c, python, aws", "B.E / B.Tech", "0-2 years", "8 LPA", "Develop routing script tools and scale secure virtual network servers.", "2026-08-25"));
        }

        List<Job> allDbJobs = jobRepository.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Job job : allDbJobs) {
            String[] reqSkills = job.getReqSkills().split(",");
            int totalReqSkills = reqSkills.length;
            int matchedCount = 0;
            List<String> userMatchedChips = new ArrayList<>();

            for (String skill : reqSkills) {
                String cleanSkill = skill.trim().toLowerCase();
                if (!cleanSkill.isEmpty() && userSkillsRaw.contains(cleanSkill)) {
                    matchedCount++;
                    userMatchedChips.add(skill.trim()); 
                }
            }

            int finalMatch = 0;
            if (totalReqSkills > 0) {
                finalMatch = (matchedCount * 100) / totalReqSkills;
            }

            if (!userSkillsRaw.trim().isEmpty() && finalMatch == 0) {
                continue; 
            }

            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("id", job.getId());
            jobMap.put("title", job.getTitle());
            jobMap.put("company", job.getCompany() + " • " + job.getLocation());
            jobMap.put("icon", job.getIcon());
            jobMap.put("reqSkills", job.getReqSkills());
            jobMap.put("matchedSkills", userMatchedChips); 
            jobMap.put("matchPercent", finalMatch);
            jobMap.put("qualification", job.getQualification());
            jobMap.put("experience", job.getExperience());
            jobMap.put("salary", job.getSalary());

            responseList.add(jobMap);
        }

        responseList.sort((a, b) -> Integer.compare((int) b.get("matchPercent"), (int) a.get("matchPercent")));
        return ResponseEntity.ok(responseList);
    }
   
   
    @PostMapping(value = "/apply", consumes = "multipart/form-data")
    public RedirectView submitJobApplication(@RequestParam("userId") Long userId,
                                             @RequestParam("jobTitle") String jobTitle,
                                             @RequestParam("company") String company,
                                             @RequestParam("matchPercent") String matchPercentStr, 
                                             @RequestParam(value = "salary", required = false, defaultValue = "Not Disclosed") String salary,
                                             @RequestParam(value = "experience", required = false, defaultValue = "Fresher") String experience,
                                             @RequestParam("resumeFile") MultipartFile resumeFile) {
        try {
            System.out.println("====== APPLYING FOR JOB: " + jobTitle + " BY USER: " + userId + " ======");
            
            Optional<User> userOpt = userRepository.findById(userId);
            String candidateName = "Anonymous Candidate";
            String candidateEmail = "N/A";
            String userSkills = "Not Specified";
            String userQual = "Not Specified";
            
            if (userOpt.isPresent()) {
                User currentUser = userOpt.get();
                candidateName = currentUser.getFullname();
                candidateEmail = currentUser.getEmail(); 
                if(currentUser.getSkills() != null) userSkills = currentUser.getSkills();
                if(currentUser.getEducation() != null) userQual = currentUser.getEducation();
            }

           
String fileName = "default_resume.pdf";
if (resumeFile != null && !resumeFile.isEmpty()) {
    fileName = System.currentTimeMillis() + "_" + resumeFile.getOriginalFilename();
    
   
    String uploadDir = "C:/smartjob_springboot/smartjob/src/main/resources/static/uploads/";
    
    File dir = new File(uploadDir);
    if (!dir.exists()) {
        dir.mkdirs(); 
    }
    
   
    resumeFile.transferTo(new File(uploadDir + fileName));
}

            int matchPercent = 0;
            try {
                if(matchPercentStr != null && !matchPercentStr.trim().isEmpty() && !matchPercentStr.equals("NaN")) {
                    matchPercent = Integer.parseInt(matchPercentStr.trim());
                }
            } catch(Exception numEx) {
                System.out.println("Match percent parse format issue, using 0.");
            }

            JobApplication application = new JobApplication();
            application.setUserId(userId);
            application.setCandidateName(candidateName);
            application.setEmail(candidateEmail); 
            application.setJobTitle(jobTitle);
            application.setCompany(company);
            application.setAppliedDate(LocalDate.now());
            application.setStatus("Pending"); 
            application.setMatchPercent(matchPercent);
            
            application.setSalary(salary);
            application.setExperience(experience);
            application.setQualification(userQual); 
            application.setSkills(userSkills); 
            application.setResumePath(fileName); 
            
            jobApplicationRepository.save(application);
            System.out.println("====== APPLICATION SAVED! REDIRECTING TO SUCCESS PAGE ======");
            
            return new RedirectView("/success.html?userId=" + userId);

        } catch (Exception e) {
            
            System.out.println("❌ ERROR IN APPLICATION SUBMISSION: " + e.getMessage());
            e.printStackTrace();
            return new RedirectView("/dashboard.html?userId=" + userId + "&error=failed");
        }
    }

    @GetMapping("/{userId}/applications")
    @ResponseBody
    public List<JobApplication> getUserApplications(@PathVariable("userId") Long userId) {
        System.out.println("====== FETCHING APPS FOR USER: " + userId + " ======");
        return jobApplicationRepository.findByUserId(userId);
    }

    @GetMapping("/admin/applications")
    @ResponseBody
    public List<JobApplication> getAllApplications() {
        System.out.println("====== FETCHING ALL APPS FOR ADMIN ======");
        return jobApplicationRepository.findAll();
    }

    @PostMapping("/admin/applications/{id}/review")
    @ResponseBody
    public ResponseEntity<String> reviewApplication(@PathVariable("id") Long id, @RequestParam("status") String status) {
        Optional<JobApplication> appOpt = jobApplicationRepository.findById(id);
        if (appOpt.isPresent()) {
            JobApplication app = appOpt.get();
            app.setStatus(status);
            jobApplicationRepository.save(app);
            return ResponseEntity.ok("Status updated to " + status);
        }
        return ResponseEntity.status(404).body("Not Found");
    }

   
    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<?> signupUser(@RequestBody User user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(existingUser);
        }
        return ResponseEntity.status(404).body("Invalid credentials!");
    }

    @DeleteMapping("/admin/applications/clear-all")
    @ResponseBody
    public ResponseEntity<String> clearAllApplications() {
        try {
            jobApplicationRepository.deleteAll(); 
            System.out.println("====== ALL APPLICATIONS CLEARED BY ADMIN ======");
            return ResponseEntity.ok("All applications cleared successfully!");
        } catch (Exception e) {
          e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to clear applications.");
        }
    }
}