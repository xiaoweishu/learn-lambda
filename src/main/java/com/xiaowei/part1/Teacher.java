package com.xiaowei.part1;

import java.util.List;

/**
 * @author xiaowei
 */
public class Teacher {
    private Integer teachId;
    private String teacherName;
    private List<Student> students;

    public Integer getTeachId() {
        return teachId;
    }

    public void setTeachId(Integer teachId) {
        this.teachId = teachId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher(Integer teachId, String teacherName, List<Student> students) {
        this.teachId = teachId;
        this.teacherName = teacherName;
        this.students = students;
    }
}
