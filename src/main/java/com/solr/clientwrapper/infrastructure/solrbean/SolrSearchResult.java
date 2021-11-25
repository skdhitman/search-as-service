package com.solr.clientwrapper.infrastructure.solrbean;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.Data;


@Data
@Component
public class SolrSearchResult {
	private Long numDocs;
	private List<SolrCollectionIndex> solrCollectionIndex;


	@Override
	public String toString() {
		return "SolrSearchResult [numDocs=" + numDocs + ", solrCollectionindex=" + solrCollectionIndex + "]";
	}
	
}
