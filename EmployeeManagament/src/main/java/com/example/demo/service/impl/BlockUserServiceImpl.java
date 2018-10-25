package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BlockUser;
import com.example.demo.repository.BlockUserRepository;
import com.example.demo.service.BlockUserService;

@Service
public class BlockUserServiceImpl implements BlockUserService {
	@Autowired
	private BlockUserRepository blockUserRepository;

	/**
	* @summary add BlockUser object
	* @date Aug 22, 2018
	* @author ThaiLe O
	* @param blockUser
	* @return BlockUser
	 */
	@Override
	public BlockUser addBlockUser(BlockUser blockUser) {
		// TODO Auto-generated method stub
		return blockUserRepository.save(blockUser);
	}

	/**
	* @summary edit BlockUser object
	* @date Aug 22, 2018
	* @author ThaiLe
	* @param blockUser
	* @return BlockUser
	 */
	@Override
	public BlockUser editBlockUser(BlockUser blockUser) {
		// TODO Auto-generated method stub
		return blockUserRepository.save(blockUser);
	}

	/**
	* @summary deleteBlockUser
	* @date Aug 23, 2018
	* @author ThaiLe
	* @param id
	* @return boolean
	*/
	@Override
	public boolean deleteBlockUser(Long id) {
		// TODO Auto-generated method stub
		Optional<BlockUser> optionalBlockUser = blockUserRepository.findById(id);
		if (!optionalBlockUser.isPresent()) {
			return false;
		}
		blockUserRepository.deleteBlockUserById(id);
		return true;
	}

}
