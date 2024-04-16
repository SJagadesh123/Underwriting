
package com.zettamine.mpa.ucm.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zettamine.mpa.ucm.dto.UnderwritingCriteriaDto;
import com.zettamine.mpa.ucm.entities.UnderwritingCriteria;
import com.zettamine.mpa.ucm.entities.UnderwritingCriteriaLoanProduct;
import com.zettamine.mpa.ucm.exception.DuplicationException;
import com.zettamine.mpa.ucm.exception.ResourceNotFoundException;
import com.zettamine.mpa.ucm.mapper.UnderwritingCriteriaMapper;
import com.zettamine.mpa.ucm.repository.SearchCriteriaRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingCriteriaLoanProductRepository;
import com.zettamine.mpa.ucm.repository.UnderwritingCriteriaRepository;
import com.zettamine.mpa.ucm.service.clients.LoanProductFeignClient;
import com.zettamine.mpa.ucm.utility.StringUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnderwritingCriteriaServiceImpl implements IUnderwritingCriteriaService {

	private UnderwritingCriteriaRepository underwritingCriteriaRepository;
	private UnderwritingCriteriaLoanProductRepository criteriaLoanProductRepository;
	private LoanProductFeignClient loanProductFeignClient;
	private SearchCriteriaRepository searchCriteriaRepository;

	@Override
	public void save(UnderwritingCriteriaDto underwritingCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		String name = StringUtils.trimSpacesBetween(underwritingCriteriaDto.getCriteriaName());
		Optional<UnderwritingCriteria> criteria = underwritingCriteriaRepository.findByCriteriaName(name.toUpperCase());

		if (criteria.isPresent()) {
			throw new DuplicationException("Criteria exist with name " + name);
		}
		toUpper(underwritingCriteriaDto);

		toUpper(underwritingCriteriaDto);
		UnderwritingCriteria underwritingCriteria = UnderwritingCriteriaMapper.toEntity(underwritingCriteriaDto,
				new UnderwritingCriteria());

		underwritingCriteriaRepository.save(underwritingCriteria);
	}

	public void update(Long id, UnderwritingCriteriaDto underwritingCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		UnderwritingCriteria underwritingCriteria = underwritingCriteriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Criteria not found with Id : " + id));

		toUpper(underwritingCriteriaDto);

		UnderwritingCriteria entity = UnderwritingCriteriaMapper.toEntity(underwritingCriteriaDto,
				underwritingCriteria);

		entity.setCriteriaId(id);

		underwritingCriteriaRepository.save(underwritingCriteria);
	}

	@Override
	public UnderwritingCriteriaDto get(Long id) {

		UnderwritingCriteria underwritingCriteria = underwritingCriteriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Criteria not found with Id : " + id));

		UnderwritingCriteriaDto underwritingCriteriaDto = UnderwritingCriteriaMapper.toDto(underwritingCriteria,
				new UnderwritingCriteriaDto());

		return underwritingCriteriaDto;
	}

	@Override
	public List<UnderwritingCriteriaDto> getAll() {

		List<UnderwritingCriteria> underwritingCriteriaList = underwritingCriteriaRepository.findAll();

		List<UnderwritingCriteriaDto> underwritingCriteriaDtos = new ArrayList<>();

		for (UnderwritingCriteria underwritingCriteria : underwritingCriteriaList) {
			UnderwritingCriteriaDto dto = UnderwritingCriteriaMapper.toDto(underwritingCriteria,
					new UnderwritingCriteriaDto());
			underwritingCriteriaDtos.add(dto);
		}

		return underwritingCriteriaDtos;

	}

	private static void toUpper(UnderwritingCriteriaDto underwritingCriteriaDto)
			throws IllegalArgumentException, IllegalAccessException {

		Field[] fields = underwritingCriteriaDto.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);

			if (field.getType().equals(String.class)) {

				String value = (String) field.get(underwritingCriteriaDto);

				if (value != null) {
					field.set(underwritingCriteriaDto, StringUtils.trimSpacesBetween(value.toUpperCase()));

				}
			}

		}
	}

	@Override
	public void addCriteriaToLoanProd(List<String> criteriaNames, String loanProductName) {

		String loanProdName = StringUtils.trimSpacesBetween(loanProductName.toUpperCase());

		ResponseEntity<Integer> loanProductResponse = (ResponseEntity<Integer>) loanProductFeignClient
				.getLoanProductIdByName(loanProductName);

		Integer loanProdId = loanProductResponse.getBody();

		if (loanProdId == null) {
			throw new ResourceNotFoundException("Loan Product not fount with name : " + loanProdName);
		}

		List<UnderwritingCriteria> underwritingCriterias = new ArrayList<>();

		for (String criteriaName : criteriaNames) {
			if (criteriaName == null) {
				throw new NullPointerException("Please provide not null values");
			}
			String name = StringUtils.trimSpacesBetween(criteriaName.toUpperCase());

			UnderwritingCriteria underwritingCriteria = underwritingCriteriaRepository.findByCriteriaName(name)
					.orElseThrow(() -> new ResourceNotFoundException("Criteria not found with name : " + name));

			underwritingCriterias.add(underwritingCriteria);
		}

		for (UnderwritingCriteria criteria : underwritingCriterias) {
			UnderwritingCriteriaLoanProduct criteriaLoanProduct = new UnderwritingCriteriaLoanProduct();

			criteriaLoanProduct.setProdId(loanProdId);
			criteriaLoanProduct.setUnderwritingCriteria(criteria);

			criteriaLoanProductRepository.save(criteriaLoanProduct);
		}

		loanProductFeignClient.updateLoanProductStatus1(loanProdId);

	}

	@Override
	@Transactional
	public void removeCriteriaToLoanProd(List<String> criteriaNames, String loanProductName) {

		String loanProdName = StringUtils.trimSpacesBetween(loanProductName.toUpperCase());

		ResponseEntity<Integer> loanProductResponse = (ResponseEntity<Integer>) loanProductFeignClient
				.getLoanProductIdByName(loanProductName);

		Integer loanProdId = loanProductResponse.getBody();

		if (loanProdId == null) {
			throw new ResourceNotFoundException("Loan Product not fount with name : " + loanProdName);
		}

		List<UnderwritingCriteria> underwritingCriterias = new ArrayList<>();

		for (String criteriaName : criteriaNames) {
			if (criteriaName == null) {
				throw new NullPointerException("Please provide not null values");
			}
			String name = StringUtils.trimSpacesBetween(criteriaName.toUpperCase());

			UnderwritingCriteria underwritingCriteria = underwritingCriteriaRepository.findByCriteriaName(name)
					.orElseThrow(() -> new ResourceNotFoundException("Criteria not found with name : " + name));

			underwritingCriterias.add(underwritingCriteria);
		}

		for (UnderwritingCriteria criteria : underwritingCriterias) {
			UnderwritingCriteriaLoanProduct criteriaLoanProduct = new UnderwritingCriteriaLoanProduct();

			criteriaLoanProduct.setProdId(loanProdId);
			criteriaLoanProduct.setUnderwritingCriteria(criteria);

			criteriaLoanProductRepository.delete(criteriaLoanProduct);
		}

	}

	@Override
	public Set<Integer> getByCriterias(List<String> criteriaNames) {

		return searchCriteriaRepository.getLoanProductByCriteria(criteriaNames);
	}

	@Override
	public List<String> getAllCriteriaNames() {

		List<UnderwritingCriteria> underwritingCriteriaList = underwritingCriteriaRepository.findAll();

		List<String> criteriaNames = new ArrayList<>();

		for (UnderwritingCriteria criteria : underwritingCriteriaList) {
			criteriaNames.add(criteria.getCriteriaName());
		}

		return criteriaNames;
	}

	@Override
	public List<UnderwritingCriteriaDto> getByLoanId(Integer id) {

		List<UnderwritingCriteriaLoanProduct> list = criteriaLoanProductRepository.findByProdId(id);


		List<UnderwritingCriteriaDto> underwritingCriteriaDtos = new ArrayList<>();

		for (UnderwritingCriteriaLoanProduct criteriaLoanProduct : list) {
			UnderwritingCriteriaDto dto = UnderwritingCriteriaMapper
					.toDto(criteriaLoanProduct.getUnderwritingCriteria(), new UnderwritingCriteriaDto());
			underwritingCriteriaDtos.add(dto);
		}

		return underwritingCriteriaDtos;
	}
	
	@Override
	public void saveLoanProdCriteria(Integer prodctId, List<String> criterias) {
		
		List<String> criteriaProcess = new ArrayList<>();
		criterias.forEach(criter -> criteriaProcess.add(StringUtils.trimSpacesBetween(criter).toUpperCase()));
		
		List<String> notExists = new ArrayList<>();
		List<String> exists = new ArrayList<>();
		
		for(String criteria : criteriaProcess) {
			Optional<UnderwritingCriteria> byCriteriaName = underwritingCriteriaRepository.findByCriteriaName(criteria);
			if (byCriteriaName.isPresent()) {
				exists.add(criteria);
			}else {
				notExists.add(criteria);
			}
		}
		if (notExists.size()>0) {
			throw new ResourceNotFoundException(String.format("Underwirting criteria not exists: ", notExists.toString()));
		}
		
		List<UnderwritingCriteria> underwriteCriteria = new ArrayList<>();
		for(String eachCriteriaName: exists) {
			UnderwritingCriteria underwritingCriteria = underwritingCriteriaRepository.findByCriteriaName(eachCriteriaName).get();
			underwriteCriteria.add(underwritingCriteria);
		
		}
		for (UnderwritingCriteria underWritecriteria : underwriteCriteria) {
			UnderwritingCriteriaLoanProduct criteriaLoanProduct = new UnderwritingCriteriaLoanProduct();

			criteriaLoanProduct.setProdId(prodctId);
			criteriaLoanProduct.setUnderwritingCriteria(underWritecriteria);

			criteriaLoanProductRepository.save(criteriaLoanProduct);
		}
		
		
	}
}
