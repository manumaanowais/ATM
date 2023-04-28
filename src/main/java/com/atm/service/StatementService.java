package com.atm.service;

import java.util.Set;

import com.atm.model.Statement;
import com.atm.model.User;

public interface StatementService {
	//Add Withdraw Statement
	public Statement addWithdrawStatement(Statement state,User user,int amountToWithdraw);
	
	//Add Deposit Statement
	public Statement addDepositStatement(Statement state,User user,int amountToDeposit);
	
	//Add Transfer Statement
	public Statement addTransferStatement(Statement senderState,Statement recieverState,User sender,User reciever,int amountToTransfer);
	
	//Get Statements By UId
	public Set<Statement> getByUid(Long id);
	
	//Get All Statements
	public Set<Statement> getStatements();
}
