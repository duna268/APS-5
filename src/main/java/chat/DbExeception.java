/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author Daniel
 */
public class DbExeception extends RuntimeException{
    
    private static final long serialVersionUID = 1L;
    
    public DbExeception(String msg){
        super(msg);
    }
    
}
