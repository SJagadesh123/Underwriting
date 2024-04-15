package com.zettamine.mpa.ucm.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.zettamine.mpa.ucm.dto.SearchResultDto;

public class SearchCriteriaResultExtractor implements ResultSetExtractor<Set<SearchResultDto>>{

	@Override
	public Set<SearchResultDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Set<SearchResultDto> searchResultDtos = new HashSet<>();
		
		while(rs.next())
		{
			searchResultDtos.add(new SearchResultDto(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
		}
		
		
		return searchResultDtos;
	}

}
