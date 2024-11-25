package com.mango.infrastructure.controllers.adapter.out;

import com.mango.customer.domain.User;
import com.mango.customer.infrastructure.adapter.out.SloganEntity;
import com.mango.customer.infrastructure.adapter.out.SloganRepositoryAdapter;
import com.mango.customer.infrastructure.adapter.out.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SloganRepositoryAdapterTest {

	private SloganRepositoryAdapter sloganRepository;
	private UserEntity user;
	private SloganEntity slogan;

	@BeforeEach
	void setUp() {
		sloganRepository = new SloganRepositoryAdapter();
		user = new UserEntity(1L, "Santi", "Lara", "AD", "City", "test@test.com");
		slogan = new SloganEntity(null, user, "slogan");

	}

	@Test
	void save_shouldAssignIdToNewSlogan() {
		SloganEntity savedSlogan = sloganRepository.save(slogan);

		Assertions.assertNotNull(savedSlogan.getId());
		Assertions.assertEquals(slogan.getSlogan(), savedSlogan.getSlogan());
		Assertions.assertEquals(user, savedSlogan.getUser());
	}

	@Test
	void findByUserId_shouldReturnSlogansForGivenUser() {
		SloganEntity slogan1 = new SloganEntity(null, user, "Slogan 1");
		SloganEntity slogan2 = new SloganEntity(null, user, "Slogan 2");
		sloganRepository.save(slogan1);
		sloganRepository.save(slogan2);

		List<SloganEntity> userSlogans = sloganRepository.findByUserId(user.getId());

		Assertions.assertEquals(2, userSlogans.size());
		Assertions.assertTrue(userSlogans.contains(slogan1));
		Assertions.assertTrue(userSlogans.contains(slogan2));
	}

	@Test
	void findByUserId_shouldReturnEmptyListIfNoSlogansForGivenUser() {
		List<SloganEntity> userSlogans = sloganRepository.findByUserId(user.getId());

		Assertions.assertTrue(userSlogans.isEmpty());
	}

	@Test
	void countByUserId_shouldReturnCorrectCountOfSlogans() {
		SloganEntity slogan1 = new SloganEntity(null, user, "Slogan 1");
		SloganEntity slogan2 = new SloganEntity(null, user, "Slogan 2");
		sloganRepository.save(slogan1);
		sloganRepository.save(slogan2);

		int count = sloganRepository.countByUserId(user.getId());

		Assertions.assertEquals(2, count);
	}

	@Test
	void countByUserId_shouldReturnZeroIfNoSlogansForUser() {
		int count = sloganRepository.countByUserId(user.getId());

		Assertions.assertEquals(0, count);
	}

	@Test
	void save_shouldOverwriteExistingSloganIfIdIsProvided() {
		SloganEntity savedSlogan = sloganRepository.save(slogan);

		savedSlogan.setSlogan("Updated Slogan");
		SloganEntity updatedSlogan = sloganRepository.save(savedSlogan);

		assertEquals("Updated Slogan", updatedSlogan.getSlogan());
		assertEquals(savedSlogan.getId(), updatedSlogan.getId());
	}

}
