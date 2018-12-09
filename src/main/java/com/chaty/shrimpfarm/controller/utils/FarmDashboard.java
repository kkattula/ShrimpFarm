package com.chaty.shrimpfarm.controller.utils;

//imports as static
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import com.chaty.shrimpfarm.model.Pond;
import com.chaty.shrimpfarm.model.PondInfo;

public class FarmDashboard {

	@Autowired
	MongoTemplate mongoTemplate;

	public List<PondInfo> getDashboard(final String site, final String season) {

		List<PondInfo> pondInfoList = new ArrayList<>();
		
		LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("Stock")
                .localField("dept_id")
                .foreignField("_id")
                .as("stockList");

		Aggregation agg = newAggregation(
				
				
				match(Criteria.where("_id").lt(10)), 
				
				group("hosting").count().as("total"),
				
				
				project("total").and("hosting").previousOperation(), 
				
				sort(Sort.Direction.DESC, "total")

		);

		// Convert the aggregation result into a List
		AggregationResults<PondInfo> groupResults = mongoTemplate.aggregate(agg, Pond.class, PondInfo.class);
		List<PondInfo> result = groupResults.getMappedResults();

		return pondInfoList;

	}

}
