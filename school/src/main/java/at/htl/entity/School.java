package at.htl.entity;

import javax.persistence.*;

@Entity
@Table(name = "T_SCHOOL")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "S_ID")
    private long id;

    @Column(name = "S_NAME")
    private String name;

    @Column(name = "S_STUDENTS")
    private int students;

    public void CopyProperties(School other){
        this.name = other.name;
        this.students = other.students;
    }

    public School() {
    }

    public School(String name, int students) {
        this.name = name;
        this.students = students;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}