/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.dao.enviroment.Generator;
import cz.cvut.fel.ear.SchoolProjectApplication;
import cz.cvut.fel.ear.dao.ProgramDao;
import cz.cvut.fel.ear.dao.SemesterDao;
import cz.cvut.fel.ear.dao.StudentDao;
import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.User;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@ComponentScan(basePackageClasses = SchoolProjectApplication.class)
public class BaseDaoTest {
    
    @Autowired
    private TestEntityManager em;
    
    @Autowired
    private StudentDao sd;
    
    @Autowired
    private SemesterDao semd;
    
    @Autowired
    private ProgramDao pd;
    
    @Test
    public void findAllRetrievesAllInstancesOfType() {
        final Program progOne = Generator.generateProgram();
        em.persistAndFlush(progOne);
        final Program progTwo = Generator.generateProgram();
        em.persistAndFlush(progTwo);
        
        final List<Program> result = pd.findAll();
        
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(progOne.getId())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(progTwo.getId())));
    }
    
    @Test
    public void updateUpdatesExistingInstance() {
        final Semester sem = Generator.generateSemester();
        em.persistAndFlush(sem);

        final Semester updated = new Semester();
        updated.setId(sem.getId());
        updated.setCode("AA777AA");
        semd.update(updated);

        final Semester result = semd.find(sem.getId());
        assertNotNull(result);
        assertEquals(sem.getCode(), result.getCode());
    }
    
    @Test
    public void removeRemovesSpecifiedInstance() {
        final Program program = Generator.generateProgram();
        em.persistAndFlush(program);
        assertNotNull(em.find(Program.class, program.getId()));
        em.detach(program);

        pd.remove(program);
        assertNull(em.find(Program.class, program.getId()));
    }
    
    @Test
    public void removeDoesNothingWhenInstanceDoesNotExist() {
        final Semester semester = Generator.generateSemester();
        semester.setId(123);
        assertNull(em.find(Semester.class, semester.getId()));

        semd.remove(semester);
        assertNull(em.find(Semester.class, semester.getId()));
    }
   
    @Test
    public void existsReturnsTrueForExistingIdentifier() {
        final Semester semester = Generator.generateSemester();
        em.persistAndFlush(semester);
        assertTrue(semd.exists(semester.getId()));
        assertFalse(semd.exists(-1));
    }
    
}
