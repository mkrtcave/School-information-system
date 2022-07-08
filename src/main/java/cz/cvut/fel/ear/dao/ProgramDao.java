package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.Program;
import org.springframework.stereotype.Repository;

@Repository
public class ProgramDao extends BaseDao<Program>{
    
    public ProgramDao() {
        super(Program.class);
    }
}
