package com.yjm.doctor.model;

//import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zx on 2017/12/12.
 */
@Entity
public class MemberDoctor implements Serializable{

    private static final long serialVersionUID = -4832094621587828511L;
    @Id(autoincrement = false)
    private int id;
    private String createTime;
    private int department;
    private String departmentName;
    private String education;
    private String educationName;
    private int groupId;
    private int hospital;
    private String hospitalName;
    private String introduce;

    private boolean leader;
    private int level;
    private String levelName;
    private int sort;
    private String speciality;
    private String updateTime;


    @Generated(hash = 622691030)
    public MemberDoctor(int id, String createTime, int department,
            String departmentName, String education, String educationName,
            int groupId, int hospital, String hospitalName, String introduce,
            boolean leader, int level, String levelName, int sort,
            String speciality, String updateTime) {
        this.id = id;
        this.createTime = createTime;
        this.department = department;
        this.departmentName = departmentName;
        this.education = education;
        this.educationName = educationName;
        this.groupId = groupId;
        this.hospital = hospital;
        this.hospitalName = hospitalName;
        this.introduce = introduce;
        this.leader = leader;
        this.level = level;
        this.levelName = levelName;
        this.sort = sort;
        this.speciality = speciality;
        this.updateTime = updateTime;
    }


    @Generated(hash = 1139303844)
    public MemberDoctor() {
    }


    @Override
    public String toString() {
        return "MemberDoctor{" +
                "id=" + id +
                ", createTime='" + createTime + '\'' +
                ", department=" + department +
                ", departmentName='" + departmentName + '\'' +
                ", education='" + education + '\'' +
                ", educationName='" + educationName + '\'' +
                ", groupId=" + groupId +
                ", hospital=" + hospital +
                ", hospitalName='" + hospitalName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", leader=" + leader +
                ", level=" + level +
                ", levelName='" + levelName + '\'' +
                ", sort=" + sort +
                ", speciality='" + speciality + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }


    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getCreateTime() {
        return this.createTime;
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public int getDepartment() {
        return this.department;
    }


    public void setDepartment(int department) {
        this.department = department;
    }


    public String getDepartmentName() {
        return this.departmentName;
    }


    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    public String getEducation() {
        return this.education;
    }


    public void setEducation(String education) {
        this.education = education;
    }


    public String getEducationName() {
        return this.educationName;
    }


    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }


    public int getGroupId() {
        return this.groupId;
    }


    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


    public int getHospital() {
        return this.hospital;
    }


    public void setHospital(int hospital) {
        this.hospital = hospital;
    }


    public String getHospitalName() {
        return this.hospitalName;
    }


    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }


    public String getIntroduce() {
        return this.introduce;
    }


    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }


    public boolean getLeader() {
        return this.leader;
    }


    public void setLeader(boolean leader) {
        this.leader = leader;
    }


    public int getLevel() {
        return this.level;
    }


    public void setLevel(int level) {
        this.level = level;
    }


    public String getLevelName() {
        return this.levelName;
    }


    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }


    public int getSort() {
        return this.sort;
    }


    public void setSort(int sort) {
        this.sort = sort;
    }


    public String getSpeciality() {
        return this.speciality;
    }


    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }


    public String getUpdateTime() {
        return this.updateTime;
    }


    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
