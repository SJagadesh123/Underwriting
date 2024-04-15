package com.zettamine.mpa.ucm.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;



public class FindProductByCriteriaResultExtractor implements ResultSetExtractor<Set<Integer>> {

	@Override
	public Set<Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Set<Integer> results = new HashSet<>();
		
		while(rs.next())
		{
			results.add(rs.getInt(1));
		}
		
		return results;
	}

}
