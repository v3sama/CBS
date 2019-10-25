package com.cbs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbs.model.CardInformation;
import com.cbs.repository.CardRepository;
import com.cbs.repository.ProvinceRepository;

@Service
public class CardService {
	private final CardRepository cardRepository;

	@Autowired
	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
	
	public List<CardInformation> getAllCard() {
		return cardRepository.findAll();
	}

	public CardInformation getCardByID(Long id) {
		return cardRepository.getOne(id);
	}


	public void deleteCardByID(Long id) {
		if (getCardByID(id) != null) {
			cardRepository.deleteById(id);
		}
	}

	public void addCard(CardInformation card) {
		cardRepository.saveAndFlush(card);
	}
}
