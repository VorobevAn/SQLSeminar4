package ru.geekbrains.lesson4.homework;

import javax.persistence.*;

@Entity
@Table(name = "Courses")
public class Course {

    @Id
    @Column(name = "id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String nameCourse;
    @Column(name = "duration")
    private int duration;

    public Course(int id, String nameCourse, int duration) {
        this.id = id;
        this.nameCourse = nameCourse;
        this.duration = duration;
    }

    public Course(String nameCourse, int duration) {
        this.nameCourse = nameCourse;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void updateCourse(String nameCourse, int duration){
        this.nameCourse = nameCourse;
        this.duration = duration;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", nameCourse='" + nameCourse + '\'' +
                ", duration=" + duration +
                '}';
    }
}
