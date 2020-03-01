package com.vczyh.miniframework.processor;


import com.vczyh.miniframework.annotation.Colum;
import com.vczyh.miniframework.annotation.Id;
import com.vczyh.miniframework.annotation.Table;

@Table("test")
public class Test {

    @Id
    @Colum("id")
    private Integer id;
    @Colum("name")
    private String name;
    @Colum("age")
    private Integer age;
    @Colum("sex")
    private String sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
