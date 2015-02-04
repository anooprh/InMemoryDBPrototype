package com.anoop;

import java.util.Stack;

/**
 * Class for  handling a specified from the stdin
 * Valid commands are SET, GET, UNSET, NUMEQUALTO, BEGIN, ROLLBACK and COMMIT
 *
 * Author : Anoop Hallur
 * Date   : 02/04/2014
 */
public class CommandHandler {

    /**
     * inMemoryDB as a acting instance for the DB
     * transactions is the collections of BEGIN..ROLLBACK, to be saved till they are committed.
     * This is represented as a stack, because instructions have to rolled back in LIFO order
     * if they ever have to be rolled back
     */
    private InMemoryDB<String, Integer> inMemoryDB;
    private Stack<Transaction> transactions;

    /**
     * Initializes the inMemory Database for this particular set of commands.
     * Ideally there should be only one instance of inMemoryDB per program, but since we
     * are reading from stdin, avoiding the complexity here
     */
    public CommandHandler() {
        inMemoryDB = new InMemoryDB<String, Integer>();
        transactions = new Stack<Transaction>();
    }

    /**
     * Takes a specifed action based on the command given to it and commandArguments
     * @param command
     * @param commandArgs
     */
    public void handle(String command, String[] commandArgs) {

        /**
         * Delegation of responsibilites to various individual command handlers
         */
        if ("END".equals(command)) {
            endCommandHandler();
        } else if("SET".equals(command)){
            setCommandHandler(commandArgs);
        } else if("GET".equals(command)){
            getCommandHandler(commandArgs);
        } else if("UNSET".equals(command)){
            unsetCommandHandler(commandArgs);
        } else if("NUMEQUALTO".equals(command)){
            numequaltoCommandHandler(commandArgs);
        } else if("BEGIN".equals(command)){
            beginCommandHandler();
        } else if("ROLLBACK".equals(command)){
            rollbackCommandHandler();
        } else if("COMMIT".equals(command)){
            commitCommandHandler();
        } else {
            System.out.println("ILLEGAL COMMAND!!");
        }
    }


    /**
     * Commits the instructions executed and clears all the pending
     * transactions which could be potentially rolled back
     */
    private void commitCommandHandler() {
        transactions.clear();
    }


    /**
     * Rolls back the instructions in the most recent transaction and the transaction is flushed.
     * If no transaction has started, then the corresponding message is printed on stdout
     */
    private void rollbackCommandHandler() {
        if(transactions.isEmpty()){
            System.out.println("NO TRANSACTION");
        } else {
            Transaction transaction = transactions.pop();
            transaction.rollBack();
        }
    }


    /**
     * Initiates a new Transaction.
     */
    private void beginCommandHandler() {
        transactions.push(new Transaction(inMemoryDB));
    }


    /**
     * Prints the number of keys whose value is set to the specified argument.
     * The logic is implemented in inMemoryDB.numEqualTo(), only format conversions are
     * handled here
     * @param commandArgs
     */
    private void numequaltoCommandHandler(String[] commandArgs) {
        Integer countKey = Integer.parseInt(commandArgs[0]);
        Integer countValue = inMemoryDB.numEqualTo(countKey);
        System.out.println(countValue);
    }

    /**
     * Exit the program when END command is encountered as it dentoes end of file
     */
    private void endCommandHandler() {
        System.exit(0);
    }

    /**
     * Unsets the spcified the key in the db.
     * The logic is implemented in inMemoryDB.unset(), only format conversions are
     * handled here
     *
     * This also saves in transaction, the method which has to be executed if the transaction
     * were ever to be rolled back. The exact instruction is found out by reading the previously
     * set value
     *
     * @param commandArgs
     */
    private void unsetCommandHandler(String[] commandArgs) {
        String key = commandArgs[0];
        Integer value = inMemoryDB.unset(key);

        if(!transactions.empty()){
            Transaction recentTransaction = transactions.peek();
            try {
                recentTransaction.save(InMemoryDB.class.getMethod("set", Object.class, Object.class),
                        new Object[]{key, value});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fetches the value corresponding to a specified key.
     * The logic is implemented in inMemoryDB.get(), only format conversions are
     * handled here
     * @param commandArgs
     */
    private void getCommandHandler(String[] commandArgs) {
        String key = commandArgs[0];
        Integer value = inMemoryDB.get(key);
        System.out.println(value);
    }

    /**
     * Sets the spcified the key in the db.
     * The logic is implemented in inMemoryDB.set(), only format conversions are
     * handled here
     *
     * This also saves in transaction, the method which has to be executed if the transaction
     * were ever to be rolled back. The exact instruction is found out by reading the previously
     * set value
     *
     * @param commandArgs
     */
    private void setCommandHandler(String[] commandArgs) {
        String key = commandArgs[0];
        Integer value = Integer.parseInt(commandArgs[1]);
        Integer oldValue = inMemoryDB.set(key, value);

        if(!transactions.empty()){
            Transaction recentTransaction = transactions.peek();
            try {
                if(oldValue == null)
                    recentTransaction.save(InMemoryDB.class.getMethod("unset", Object.class), new Object[]{key});
                else
                    recentTransaction.save(InMemoryDB.class.getMethod("set", Object.class, Object.class),
                            new Object[]{key, oldValue});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
