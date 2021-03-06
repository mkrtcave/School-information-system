/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.ear.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable{
    
    @Id
    @GeneratedValue
    private Integer id;
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
}
