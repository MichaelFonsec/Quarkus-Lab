package infrastructure.repositories.entities;

import domain.Candidate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "candidates")

public class EntitityCandidate {
    @Id
    private String id;
    private String photo;
    @Column(name = "given_name")
    private String givenName;
    @Column(name = "family_name")
    private String familyName;
    private String email;
    private String phone;
    @Column(name="job_title")
    private String jobTitle;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getGivenName() {
        return givenName;
    }
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public static Candidate fromCandidate(domain.Candidate candidate domain){
        Candidate entity = new Candidate();

        entity.setId(candidate.getId());
        entity.setPhoto(candidate.getPhoto());
        entity.setGivenName(candidate.getGivenName());
        entity.setFamilyName(candidate.getFamilyName());
        entity.setEmail(candidate.getEmail());
        entity.setPhone(candidate.getPhone());
        entity.setJobTitle(candidate.getJobTitle());
        return entity;
    }

    public domain.Candidate toCandidate(){
        
        return new domain.Candidate(getId(),
        candidate.setId(this.getId());
        candidate.setPhoto(this.getPhoto());
        candidate.setGivenName(this.getGivenName());
        candidate.setFamilyName(this.getFamilyName());
        candidate.setEmail(this.getEmail());
        candidate.setPhone(this.getPhone());
        candidate.setJobTitle(this.getJobTitle()));
    }   
    
}
