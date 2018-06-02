package com.FT05.CloudCA.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name = "users")
// This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "current_city")
    private String currentCity;

    @Column(name ="email")
    private  String email;

    @Column (name ="firstname")
    private  String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private int active;

    @Column(name = "High_School")
    private String highSchool;

    @Column(name = "University")
    private String university;

    @Column(name = "Bio")
    private String bio;

    @Column(name = "image_Url")
    private String image;

    @Column(name = "tokenId")
    private String tokenId;

//    @Column(name = "Gender")
//    private String gender;

    @Transient
    private String followIndicator;

    @OneToMany
    private Set<Post> posts = new HashSet<>();


    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<User> following;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setEmail(String email) { this.email = email; }

    public String getEmail() {  return email; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public int getActive() { return active; }

    public void setActive(int active) { this.active = active; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender){
//        this.gender = gender;
//    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
//                ", tokenId='" + tokenId + '\'' +
                ", currentCity='" + currentCity + '\'' +
                ", highSchool='" + highSchool + '\'' +
                ", university='" + university + '\'' +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
//                ", gender='" + gender + '\'' +
                '}';
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getFollowIndicator() {
        return followIndicator;
    }

    public void setFollowIndicator(String followIndicator) {
        this.followIndicator = followIndicator;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
