package com.qdu.jw.app.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by wang on 5/27/14.
 */
public class User {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String studentId;
    @DatabaseField
    private String studentPassword;
    @DatabaseField
    private String fullName;
    @DatabaseField
    private String gender;
    @DatabaseField
    private String college;
    @DatabaseField
    private String schoolYear;
    @DatabaseField
    private String team;
    @DatabaseField
    private String course;
    @DatabaseField
    private String specialty;
    @DatabaseField(defaultValue = "false")
    private boolean loginStatus;

    public boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public User(){

    }

}
