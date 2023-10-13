package net.javaguides.springboot.repository;


import net.javaguides.springboot.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
    }
    //JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void whenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        //given --precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Burak")
//                .lastName("Koyuncu")
//                .email("burak@gmail.com")
//                .build();

        //when - action or the behaviour that we are going to test

        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    //jUnit Test For get all Employee List
    @Test
    @DisplayName("jUnit Test For get all Employee List")
    public void givenEmployeesList_whenFindAll_thenEmployeeList() {
        //given - precondition or setup

        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("Burak1")
                .lastName("Koyuncu1")
                .email("burak1@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        //when
        List<Employee> employeeList = employeeRepository.findAll();

        //then

        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    //jUnit Test For get employee by id operation
    @DisplayName("jUnit Test For get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when
        Employee employee1 = employeeRepository.findById(employee.getId()).get();

        //then
        Assertions.assertThat(employee1).isNotNull();
    }

    //jUnit Test For get employee by email operation
    @Test
    @DisplayName("jUnit Test For get employee by email operation")
    public void givenEmployeeEmail_whenFindByEmail_thenEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when
        Employee employee1 = employeeRepository.findByEmail(employee.getEmail()).get();
        //then
        Assertions.assertThat(employee1).isNotNull();
    }

    //jUnit Test For update employee operation
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when

        Employee employee1 = employeeRepository.findById(employee.getId()).get();
        employee1.setEmail("burakkemal@gmail.com");
        employee1.setFirstName("Kemal");
        Employee updatedEmployee = employeeRepository.save(employee1);
        //then
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Kemal");
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("burakkemal@gmail.com");
    }

    //jUnit Test For delete employee operation
    @Test
    @DisplayName("jUnit Test For delete employee operation")
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        //then
        Assertions.assertThat(employeeOptional).isEmpty();
    }

    //jUnit Test For custom query using JPQL with index
    @Test
    @DisplayName("jUnit Test For custom query using JPQL with index")
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Burak";
        String lastName = "Koyuncu";
        //when
        Employee savedEmployee = employeeRepository.findbyJPQL(firstName, lastName);
        //then
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("jUnit Test For custom query using JPQL with named Params")
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParam_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Burak";
        String lastName = "Koyuncu";
        //when
        Employee savedEmployee = employeeRepository.findbyJPQLNamedParams(firstName, lastName);
        //then
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    //jUnit Test For custom query using Native SQL with index
    @Test
    @DisplayName("jUnit Test For custom query using Native SQL with index")
    public void givenFirstNameAndLastName_whenFindByNativeParam_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        employeeRepository.save(employee);
        //String firstName="Burak";
        //String lastName="Koyuncu";
        //when
        Employee savedEmployee = employeeRepository.findNativeSQL(employee.getFirstName(), employee.getLastName());
        //then
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    //jUnit Test For custom query using Native SQL with index
    @Test
    @DisplayName("jUnit Test For custom query using Native SQL with index")
    public void givenFirstNameAndLastName_whenFindByNativeQueryWithNamed_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        employeeRepository.save(employee);
        //String firstName="Burak";
        //String lastName="Koyuncu";
        //when
        Employee savedEmployee = employeeRepository.findNativeSQLNamed(employee.getFirstName(), employee.getLastName());
        //then
        Assertions.assertThat(savedEmployee).isNotNull();
    }

}
