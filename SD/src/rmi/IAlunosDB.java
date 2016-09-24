/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;
import java.rmi.*;

/**
 *
 * @author aluno
 */
public interface IAlunosDB extends Remote{
    public boolean add(Aluno aluno) throws RemoteException;
    public Aluno get(int matricula) throws RemoteException;
    public Aluno del(int matricula) throws RemoteException;
    
}
