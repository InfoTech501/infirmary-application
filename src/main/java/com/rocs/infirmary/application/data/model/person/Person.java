/**
 * The com.rocs.infirmary.application.data.model.person package contains classes representing person-related models.
 */
package com.rocs.infirmary.application.data.model.person;

import java.util.Date;

/**
 * Person model class representing person information.
 */
public class Person {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;
    private Date birthdate;
    private String gender;
    private String email;
    private String address;
    private String contactNumber;
    private String section;

    /**
     * Constructs a new Person interface with the provided properties.
     *
     * @param id            The id of the person.
     * @param firstName     The first name of the person.
     * @param middleName    The middle name of the person.
     * @param lastName      The last name of the person.
     * @param age           The age of the person.
     * @param birthdate     The date of birth of the person.
     * @param gender        The gender of the person.
     * @param email         The email address of the person.
     * @param address       The home address of the person.
     * @param contactNumber The contact number of the person.
     */
    public Person(Long id, String firstName, String middleName, String lastName, int age, Date birthdate, String gender, String email, String address, String contactNumber) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    /**
     * Default constructor of the Person class.
     */
    public Person() {
    }

    /**
     * This gets the first name.
     * @return the First Name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This sets the first name.
     * @param firstName is the First Name to be set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This gets the middle name.
     * @return the Middle Name.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * This sets the middle name.
     * @param middleName is the Middle Name to be set.
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * This gets the Last name.
     * @return the Last Name.
     */
    public String getLastName() {

        return lastName;
    }

    /**
     * This sets the last name.
     * @param lastName is the Last Name to be set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This gets the age.
     * @return the Age.
     */
    public int getAge() {
        return age;
    }

    /**
     * This sets the age.
     * @param age is the Age to be set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * This gets the gender.
     * @return the Gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * This sets the gender.
     * @param gender is the Gender to be set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * This gets the section.
     * @return the Section.
     */
    public String getSection() {
        return section;
    }

    /**
     * This sets the section.
     * @param section is the Section to be set.
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * This gets the contact number.
     * @return the Contact Number.
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * This sets the contact number.
     * @param contactNumber is the Contact Number to be set.
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * This gets the home address.
     * @return the Home Address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * This sets the home address.
     * @param address is the Address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This gets the email address.
     * @return the Email Address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This sets the email address.
     * @param email is the Email to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", birthdate=" + birthdate +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber=" + contactNumber +
                '}';
    }

}
