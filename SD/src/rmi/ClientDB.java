/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author aluno
 */
public class ClientDB {
    public static void main(String args[]) throws RemoteException, NotBoundException{
        Registry reg = LocateRegistry.getRegistry("localhost", 9955);
        IAlunosDB db = (IAlunosDB)reg.lookup("alunosdb");
        
        Aluno a1 = new Aluno(13,"Tarcisio","190");
        Aluno a2 = new Aluno(12,"João","170");
        db.add(a1);
        db.add(a2);
        Aluno a3 = db.get(15);
        System.out.println(a3.getNome());
    }
}
