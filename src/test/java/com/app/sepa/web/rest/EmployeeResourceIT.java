package com.app.sepa.web.rest;

import com.app.sepa.SepaApp;
import com.app.sepa.domain.Employee;
import com.app.sepa.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TLF = "AAAAAAAAAA";
    private static final String UPDATED_TLF = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_INTERNAL = false;
    private static final Boolean UPDATED_IS_INTERNAL = true;

    private static final String DEFAULT_MAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MAT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIT = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_DIRECTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FLOOR = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIZACION = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIZACION = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DISABLED = false;
    private static final Boolean UPDATED_IS_DISABLED = true;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .email(DEFAULT_EMAIL)
            .tlf(DEFAULT_TLF)
            .isInternal(DEFAULT_IS_INTERNAL)
            .matNumber(DEFAULT_MAT_NUMBER)
            .cuit(DEFAULT_CUIT)
            .addressDirection(DEFAULT_ADDRESS_DIRECTION)
            .addressNumber(DEFAULT_ADDRESS_NUMBER)
            .floor(DEFAULT_FLOOR)
            .departament(DEFAULT_DEPARTAMENT)
            .degree(DEFAULT_DEGREE)
            .especializacion(DEFAULT_ESPECIALIZACION)
            .celular(DEFAULT_CELULAR)
            .comentario(DEFAULT_COMENTARIO)
            .isDisabled(DEFAULT_IS_DISABLED);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .tlf(UPDATED_TLF)
            .isInternal(UPDATED_IS_INTERNAL)
            .matNumber(UPDATED_MAT_NUMBER)
            .cuit(UPDATED_CUIT)
            .addressDirection(UPDATED_ADDRESS_DIRECTION)
            .addressNumber(UPDATED_ADDRESS_NUMBER)
            .floor(UPDATED_FLOOR)
            .departament(UPDATED_DEPARTAMENT)
            .degree(UPDATED_DEGREE)
            .especializacion(UPDATED_ESPECIALIZACION)
            .celular(UPDATED_CELULAR)
            .comentario(UPDATED_COMENTARIO)
            .isDisabled(UPDATED_IS_DISABLED);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployee.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getTlf()).isEqualTo(DEFAULT_TLF);
        assertThat(testEmployee.isIsInternal()).isEqualTo(DEFAULT_IS_INTERNAL);
        assertThat(testEmployee.getMatNumber()).isEqualTo(DEFAULT_MAT_NUMBER);
        assertThat(testEmployee.getCuit()).isEqualTo(DEFAULT_CUIT);
        assertThat(testEmployee.getAddressDirection()).isEqualTo(DEFAULT_ADDRESS_DIRECTION);
        assertThat(testEmployee.getAddressNumber()).isEqualTo(DEFAULT_ADDRESS_NUMBER);
        assertThat(testEmployee.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testEmployee.getDepartament()).isEqualTo(DEFAULT_DEPARTAMENT);
        assertThat(testEmployee.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEmployee.getEspecializacion()).isEqualTo(DEFAULT_ESPECIALIZACION);
        assertThat(testEmployee.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testEmployee.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testEmployee.isIsDisabled()).isEqualTo(DEFAULT_IS_DISABLED);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setName(null);

        // Create the Employee, which fails.


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setSurname(null);

        // Create the Employee, which fails.


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMatNumber(null);

        // Create the Employee, which fails.


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCuitIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setCuit(null);

        // Create the Employee, which fails.


        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].tlf").value(hasItem(DEFAULT_TLF)))
            .andExpect(jsonPath("$.[*].isInternal").value(hasItem(DEFAULT_IS_INTERNAL.booleanValue())))
            .andExpect(jsonPath("$.[*].matNumber").value(hasItem(DEFAULT_MAT_NUMBER)))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT)))
            .andExpect(jsonPath("$.[*].addressDirection").value(hasItem(DEFAULT_ADDRESS_DIRECTION)))
            .andExpect(jsonPath("$.[*].addressNumber").value(hasItem(DEFAULT_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].departament").value(hasItem(DEFAULT_DEPARTAMENT)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].especializacion").value(hasItem(DEFAULT_ESPECIALIZACION)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].isDisabled").value(hasItem(DEFAULT_IS_DISABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.tlf").value(DEFAULT_TLF))
            .andExpect(jsonPath("$.isInternal").value(DEFAULT_IS_INTERNAL.booleanValue()))
            .andExpect(jsonPath("$.matNumber").value(DEFAULT_MAT_NUMBER))
            .andExpect(jsonPath("$.cuit").value(DEFAULT_CUIT))
            .andExpect(jsonPath("$.addressDirection").value(DEFAULT_ADDRESS_DIRECTION))
            .andExpect(jsonPath("$.addressNumber").value(DEFAULT_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.departament").value(DEFAULT_DEPARTAMENT))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE))
            .andExpect(jsonPath("$.especializacion").value(DEFAULT_ESPECIALIZACION))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.isDisabled").value(DEFAULT_IS_DISABLED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .tlf(UPDATED_TLF)
            .isInternal(UPDATED_IS_INTERNAL)
            .matNumber(UPDATED_MAT_NUMBER)
            .cuit(UPDATED_CUIT)
            .addressDirection(UPDATED_ADDRESS_DIRECTION)
            .addressNumber(UPDATED_ADDRESS_NUMBER)
            .floor(UPDATED_FLOOR)
            .departament(UPDATED_DEPARTAMENT)
            .degree(UPDATED_DEGREE)
            .especializacion(UPDATED_ESPECIALIZACION)
            .celular(UPDATED_CELULAR)
            .comentario(UPDATED_COMENTARIO)
            .isDisabled(UPDATED_IS_DISABLED);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployee)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getTlf()).isEqualTo(UPDATED_TLF);
        assertThat(testEmployee.isIsInternal()).isEqualTo(UPDATED_IS_INTERNAL);
        assertThat(testEmployee.getMatNumber()).isEqualTo(UPDATED_MAT_NUMBER);
        assertThat(testEmployee.getCuit()).isEqualTo(UPDATED_CUIT);
        assertThat(testEmployee.getAddressDirection()).isEqualTo(UPDATED_ADDRESS_DIRECTION);
        assertThat(testEmployee.getAddressNumber()).isEqualTo(UPDATED_ADDRESS_NUMBER);
        assertThat(testEmployee.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testEmployee.getDepartament()).isEqualTo(UPDATED_DEPARTAMENT);
        assertThat(testEmployee.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEmployee.getEspecializacion()).isEqualTo(UPDATED_ESPECIALIZACION);
        assertThat(testEmployee.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testEmployee.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testEmployee.isIsDisabled()).isEqualTo(UPDATED_IS_DISABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
