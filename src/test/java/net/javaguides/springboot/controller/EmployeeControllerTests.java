package net.javaguides.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

     //jUnit Test For
     @Test
     public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
         //given - precondition or setup
         Employee employee=Employee.builder()
                 .firstName("burak")
                 .lastName("koyuncu")
                 .email("burak@gmail.com")
                 .build();
         BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                 .willAnswer((invocation)->invocation.getArgument(0));
         //when
         ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(employee)));
         //then
         response.andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.status().isCreated())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                         CoreMatchers.is(employee.getFirstName())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                         CoreMatchers.is(employee.getLastName())))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                         CoreMatchers.is(employee.getEmail())));
         ;
     }

      //jUnit Test For Get All employees REST API
      @Test
      public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmpoyeesList() throws Exception{
          //given - precondition or setup
          Employee employee=Employee.builder()
                  .firstName("burak")
                  .lastName("koyuncu")
                  .email("burak@gmail.com")
                  .build();
          Employee employee1=Employee.builder()
                  .firstName("burak")
                  .lastName("koyuncu")
                  .email("burak@gmail.com")
                  .build();
          List<Employee> listOfEmployees=new ArrayList<>();
          listOfEmployees.add(employee);
          listOfEmployees.add(employee1);
          BDDMockito.given(employeeService.getAllEmployees()).willReturn(listOfEmployees);
          //when
          ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));
          //then
          response.andExpect(MockMvcResultMatchers.status().isOk())
                  .andDo(MockMvcResultHandlers.print())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.size()"
                          ,CoreMatchers.is(listOfEmployees.size())));
      }
       //jUnit Test For get Employee by id REST API
    //positive scenario - valid employee
       @Test
       public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
           // given - precondition or setup
           long employeeId = 1L;
           Employee employee = Employee.builder()
                   .firstName("Ramesh")
                   .lastName("Fadatare")
                   .email("ramesh@gmail.com")
                   .build();
           BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

           // when -  action or the behaviour that we are going test
           ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employeeId));

           // then - verify the output
           response.andExpect(MockMvcResultMatchers.status().isOk())
                   .andDo(MockMvcResultHandlers.print())
                   .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                   .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                   .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
       }
    // negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }
    // JUnit test for update employee REST API - positive scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .email("ram@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));
    }

    // JUnit test for update employee REST API - negative scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .email("ram@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
     }

    //jUnit Test For delete employee REST API
     @Test
     public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
         //given - precondition or setup
         long employeeId=1l;
         BDDMockito.willDoNothing().given(employeeService).deleteEmployeeById(employeeId);
         //when
         ResultActions response= mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}",employeeId));
         //then
         response.andExpect(MockMvcResultMatchers.status().isOk())
                 .andDo(MockMvcResultHandlers.print());
     }

}
