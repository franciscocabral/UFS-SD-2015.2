/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author aluno
 */
public class AlunosDB implements IAlunosDB {

    Map<Integer, Aluno> alunos = new Hashtable<Integer, Aluno>();

    @Override
    public boolean add(Aluno aluno) throws RemoteException {
        if (!alunos.containsKey(aluno.getMatricula())) {
            alunos.put(aluno.getMatricula(), aluno);
            return true;
        }
        return false;
    }

    @Override
    public Aluno get(int matricula) throws RemoteException {
        return alunos.get(matricula);
    }

    @Override
    public Aluno del(int matricula) throws RemoteException {
        return alunos.remove(matricula);
    }
}
