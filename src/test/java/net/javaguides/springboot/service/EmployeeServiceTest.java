package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.assertj.core.api.AssertDelegateTarget;
import org.assertj.core.api.Assertions;
import org.assertj.core.condition.AnyOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    @BeforeEach
    public void setup(){
        //employeeRepository= Mockito.mock(EmployeeRepository.class);
        //employeeService=new EmployeeServiceImpl(employeeRepository);

        employee = Employee.builder()
                .id(1L)
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
    }

     //jUnit Test For saveEmployee method
     @Test
     @DisplayName("jUnit Test For saveEmployee method")
     public void givenEnployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
         //given - precondition or setup
         BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                 .willReturn(Optional.empty());
         //we can write as a return
         BDDMockito.given((employeeRepository.save(employee))).willReturn(employee);
         System.out.println(employeeRepository);
         System.out.println(employeeService);
         //when
         Employee savedEmployee=employeeService.saveEmployee(employee);
         System.out.println(savedEmployee);
         //then
         Assertions.assertThat(savedEmployee).isNotNull();
     }

    //jUnit Test For saveEmployee method
    @Test
    @DisplayName("jUnit Test For saveEmployee method which throw exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        //given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
        //we can write as a return
        //BDDMockito.given((employeeRepository.save(employee))).willReturn(employee);
        System.out.println(employeeRepository);
        System.out.println(employeeService);
        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });

       // System.out.println(savedEmployee);
        //then
       // Assertions.assertThat(savedEmployee).isNotNull();
        BDDMockito.verify(employeeRepository,Mockito.never()).save(any(Employee.class));
    }

     //jUnit Test For getEmployees method
     @Test
     @DisplayName("jUnit Test For getEmployees method")
     public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList(){
         //given - precondition or setup

         Employee employee1 = Employee.builder()
                 .id(2L)
                 .firstName("Burak")
                 .lastName("Koyuncu")
                 .email("burak@gmail.com")
                 .build();
         BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));
         //when
         List<Employee> employeeList=employeeService.getAllEmployees();
         //then

         Assertions.assertThat(employeeList).isNotNull();
         Assertions.assertThat(employeeList.size()).isEqualTo(2);
     }
    //jUnit Test For getEmployees method
    @Test
    @DisplayName("jUnit Test For getEmployees method (negative scenario)")
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList(){
        //given - precondition or setup

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Burak")
                .lastName("Koyuncu")
                .email("burak@gmail.com")
                .build();
        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.EMPTY_LIST);
        //when
        List<Employee> employeeList=employeeService.getAllEmployees();
        //then

        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);
    }

     //jUnit Test For Get Employee by Id operation
     @Test
     @DisplayName("jUnit Test For Get Employee by Id operation")
     public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
         //given - precondition or setup
         BDDMockito.given(employeeRepository.findById(1l)).willReturn(Optional.of(employee));
         //when
         Employee savedEmployee=employeeService.getEmployeeById(employee.getId()).get();
         //then
         Assertions.assertThat(savedEmployee).isNotNull();
     }
      //jUnit Test For update Employee operation
      @Test
      public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
          //given - precondition or setup
          BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
          employee.setEmail("burakkkk@gmail.com");
          //when
          Employee updatedEmployee=employeeService.updateEmployee(employee);
          //then
          Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("burakkkk@gmail.com");
      }

       //jUnit Test For deleteEmployeeMethod
       @Test
       @DisplayName("jUnit Test For deleteEmployeeMethod")
       public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
           //given - precondition or setup
           BDDMockito.willDoNothing().given(employeeRepository).deleteById(employee.getId());
           //when
           employeeService.deleteEmployeeById(1l);
           //then
           BDDMockito.verify(employeeRepository,Mockito.times(1)).deleteById(employee.getId());
       }
}
