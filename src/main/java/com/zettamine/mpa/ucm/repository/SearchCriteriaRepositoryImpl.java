package com.zettamine.mpa.ucm.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zettamine.mpa.ucm.dto.SearchCriteriaDto;
import com.zettamine.mpa.ucm.dto.SearchResultDto;
import com.zettamine.mpa.ucm.utility.FindProductByCriteriaResultExtractor;
import com.zettamine.mpa.ucm.utility.SearchCriteriaResultExtractor;
import com.zettamine.mpa.ucm.utility.StringUtils;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class SearchCriteriaRepositoryImpl implements SearchCriteriaRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Set<SearchResultDto> getByCriteria(SearchCriteriaDto searchCriteria) {

		String query = "SELECT uc.uwco_id AS companyId," + " uc.name," + " uc.phone," + " uc.email," + " uc.website,"
				+ " uc.notes," + " uc.uw_claim_process AS uwClaimProcess" + " FROM mpa.underwriting_company uc"
				+ " INNER JOIN mpa.underwriting_service_area usa ON uc.uwco_id = usa.uwco_id" + " WHERE 1=1 ";

		if (searchCriteria.getCompanyName() != null) {
			query = query + " and uc.name like '%"
					+ StringUtils.trimSpacesBetween(searchCriteria.getCompanyName()).toUpperCase() + "%'";
		}
		if (searchCriteria.getCity() != null) {
			query = query + " and usa.city = '" + StringUtils.trimSpacesBetween(searchCriteria.getCity()).toUpperCase()
					+ "'";
		}
		if (searchCriteria.getState() != null) {
			query = query + " and usa.state = '"
					+ StringUtils.trimSpacesBetween(searchCriteria.getState()).toUpperCase() + "'";
		}
		if (searchCriteria.getZipcode() != null) {
			query = query + " and usa.zipcode = '"
					+ StringUtils.trimSpacesBetween(searchCriteria.getZipcode()).toUpperCase() + "'";
		}

//		List<SearchResultDto> searchResults = jdbcTemplate.query(query,
//				new BeanPropertyRowMapper<>(SearchResultDto.class));
		Set<SearchResultDto> searchResultDtos = new HashSet<SearchResultDto>();
		try {
			searchResultDtos = jdbcTemplate.query(query, new SearchCriteriaResultExtractor());
		} catch (EmptyResultDataAccessException ex) {

			return null;
		}
		return searchResultDtos;
	}

	@Override
	public Set<Integer> getLoanProductByCriteria(List<String> criteriaNames) {

		String query = "select prod_id from mpa.underwriting_criteria_loan_prod uctp inner join "
				+ "mpa.underwriting_criteria uct on uctp.criteria_id = uct.criteria_id where 1=1 and "
				+ "uct.criteria_name in(";

		int count = 0;

		for (String criteriaName : criteriaNames) {
			if (criteriaName != null) {
				String name = StringUtils.trimSpacesBetween(criteriaName).toUpperCase();

				query = query + "'" + name + "'";
				count++;
			}

			if (count != criteriaNames.size()) {
				query = query + ", ";
			}
		}

		query = query + ") group by prod_id " + "having count(prod_id) = " + criteriaNames.size();

		Set<Integer> result = new HashSet<Integer>();
		try {
			result = jdbcTemplate.query(query, new FindProductByCriteriaResultExtractor());
		} catch (EmptyResultDataAccessException ex) {

			return null;
		}
		
		return result;

		
	}

}
