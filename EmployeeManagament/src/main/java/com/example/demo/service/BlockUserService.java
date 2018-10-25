package com.example.demo.service;

import com.example.demo.entity.BlockUser;

public interface BlockUserService {
	/**
	 * @summary add BlockUser object
	 * @date Aug 22, 2018
	 * @author ThaiLe
	 * @param blockUser
	 * @return BlockUser
	 */
	BlockUser addBlockUser(BlockUser blockUser);

	/**
	 * @summary edit BlockUser object
	 * @date Aug 22, 2018
	 * @author ThaiLe
	 * @param blockUser
	 * @return BlockUser
	 */
	BlockUser editBlockUser(BlockUser blockUser);

	/**
	 * @summary deleteBlockUser
	 * @date Aug 23, 2018
	 * @author ThaiLe
	 * @param id
	 * @return boolean
	 */
	boolean deleteBlockUser(Long id);
}
