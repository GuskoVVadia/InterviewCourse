/**
 * Создать builder для класса Person со следующими полями: String firstName, String lastName,
 * String middleName, String country, String address, String phone, int age, String gender.
 */

package hw1;

public class Person {
    private String firstName;
    private String lastName;
    private String middleName;
    private String country;
    private String address;
    private String phone;
    private int age;
    private String gender;

    private Person() {}

    public static class Builder{
        // default-значения полей не указываю для простоты.
        private String firstName;
        private String lastName;
        private String middleName;
        private String country;
        private String address;
        private String phone;
        private int age;
        private String gender;

        public Builder() {
        }

        public Builder addFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder addLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder addMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder addCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder addAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder addPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder addAge(int age) {
            this.age = age;
            return this;
        }

        public Builder addGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }

    private Person(Builder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.country = builder.country;
        this.address = builder.address;
        this.phone = builder.phone;
        this.age = builder.age;
        this.gender = builder.gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
