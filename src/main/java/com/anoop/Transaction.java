package com.anoop;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

/**
 *
 * Class representing the collections of instructions within one particular BEGIN.. block,
 * As soon as a next BEGIN is encountered, a new transaction is created
 *
 * Author : Anoop Hallur
 * Date   : 02/04/2014
 *
 */
public class Transaction {

    /**
     * The instance of database on which this transaction is active upon
     */
    private InMemoryDB inMemoryDB;

    /**
     * Stack of instructions. This is represented as a stack, as they have to be rolledback in
     * a last in first out order (LIFO structure)
     */
    Stack<Instruction> instructions;

    /**
     * Store the db instance on which to operate upon and initialize the stack of instructions
     * @param inMemoryDB
     */
    public Transaction(InMemoryDB inMemoryDB) {
        this.inMemoryDB = inMemoryDB;
        instructions = new Stack<Instruction>();
    }

    /**
     * Rolls back all the instructions in the stack till the stack is empty
     */
    public void rollBack() {
        while (!instructions.empty()){
            Instruction instruction = instructions.pop();
            try {
                instruction.method.invoke(inMemoryDB, instruction.args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves the particular method and its arguments in the form of instructions to be
     * rolled back later, in case a rollback is needed
     * @param method
     * @param args
     */
    public void save(Method method, Object[] args) {
        Instruction instruction = new Instruction(method, args);
        instructions.push(instruction);
    }


    /**
     * A inner class representing one particular instruction.
     * This represents the method(for example, set and unset) and the arguments on
     * which the method operates on
     */
    private class Instruction{
        Method method;
        Object[] args;

        public Instruction(Method method, Object[] args) {
            this.method = method;
            this.args = args;
        }
    }
}
