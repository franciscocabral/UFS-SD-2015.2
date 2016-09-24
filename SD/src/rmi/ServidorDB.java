/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author aluno
 */
public class ServidorDB {
    public static void main(String args[]) throws RemoteException{
        AlunosDB db = new AlunosDB();
        IAlunosDB stub = (IAlunosDB) UnicastRemoteObject.exportObject(db,8866);
        Registry reg = LocateRegistry.createRegistry(9955);
        reg.rebind("alunosdb", stub); 
    }
}
