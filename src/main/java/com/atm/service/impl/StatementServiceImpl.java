package com.atm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.model.Statement;
import com.atm.model.User;
import com.atm.repo.StatementRepository;
import com.atm.service.StatementService;

@Service
public class StatementServiceImpl implements StatementService{
	
	@Autowired
	private StatementRepository statementRepository;
	
	@Override
	public Set<Statement> getStatements() {
		return new LinkedHashSet<>(statementRepository.findAll());
	}

	@Override
	public Set<Statement> getByUid(Long id) {
		return new LinkedHashSet<>(statementRepository.findByUid(id));
	}

	@Override
	public Statement addWithdrawStatement(Statement state, User user1, int amountToWithdraw) {
		Long uid = user1.getId();
		state.setUid(uid);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		state.setDate(formatter.format(new Date()));
		state.setWithdrawn(amountToWithdraw);
		return statementRepository.save(state);
	}

	@Override
	public Statement addDepositStatement(Statement state, User user1, int amountToDeposit) {
		Long uid = user1.getId();
		state.setUid(uid);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		state.setDate(formatter.format(new Date()));
		state.setDeposited(amountToDeposit);
		return statementRepository.save(state);
	}

	@Override
	public Statement addTransferStatement(Statement senderState, Statement recieverState, User sender, User reciever, int amountToTransfer) {
		Long senderId = sender.getId();
		Long recieverId =reciever.getId();
		senderState.setUid(senderId);
		recieverState.setUid(recieverId);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		senderState.setDate(formatter.format(new Date()));
		senderState.setDebited(amountToTransfer);
		recieverState.setDate(formatter.format(new Date()));
		recieverState.setCredited(amountToTransfer);
		statementRepository.save(recieverState);
		return statementRepository.save(senderState);
	}

}
