package com.smartjob.smartjob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // யூசரோட அப்ளிகேஷன்ஸை மட்டும் தனியா எடுக்க இந்த மெத்தட் கரெக்ட்டா இருக்கணும் பாஸ்!
    List<JobApplication> findByUserId(Long userId);
}