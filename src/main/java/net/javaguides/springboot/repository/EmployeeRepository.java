package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    //define custom query using JPQL with index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName= ?2")
    Employee findbyJPQL(String firstName, String lastName);

    //define custom query using JPQL with named params
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName=:lastName")
    Employee findbyJPQLNamedParams(@Param("firstName") String firstName,@Param("lastName") String lastName);

    //define custom query using Native JPQL with index params
    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name =?2",nativeQuery = true)
    Employee findNativeSQL(String firstName, String lastName);

    //define custom query using Native JPQL with named params
    @Query(value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName",nativeQuery = true)
    Employee findNativeSQLNamed(@Param("firstName") String firstName,@Param("lastName") String lastName);

}
