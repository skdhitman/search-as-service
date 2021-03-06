package com.solr.clientwrapper.domain.dto.solr;

import com.solr.clientwrapper.infrastructure.Enum.SolrFieldType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class SolrFieldDTO {

	
	String name;
	SolrFieldType type;
	String default_;
	boolean isRequired;
	boolean isFilterable;
	boolean isStorable;
	boolean isMultiValue;
	boolean isSortable;
}
