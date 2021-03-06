package com.solr.clientwrapper.rest;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solr.clientwrapper.domain.dto.solr.SolrSchemaDTO;
import com.solr.clientwrapper.usecase.solr.schema.CreateSolrSchema;
import com.solr.clientwrapper.usecase.solr.schema.DeleteSolarSchema;
import com.solr.clientwrapper.usecase.solr.schema.GetSolarSchema;
import com.solr.clientwrapper.usecase.solr.schema.UpdateSolarSchema;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/solr-schema")
public class SolrSchemaResource {

	private final Logger log = LoggerFactory.getLogger(SolrSchemaResource.class);
	
	private final CreateSolrSchema createSolrSchema;
	private final DeleteSolarSchema deleteSolrSchema;
	private final UpdateSolarSchema updateSolrSchema;
	private final GetSolarSchema getSolarSchema;
	public SolrSchemaResource(CreateSolrSchema createSolrSchema, DeleteSolarSchema deleteSolrSchema, UpdateSolarSchema updateSolrSchema, GetSolarSchema getSolarSchema) {
		
		this.createSolrSchema = createSolrSchema;
		this.deleteSolrSchema = deleteSolrSchema;
		this.updateSolrSchema = updateSolrSchema;
		this.getSolarSchema = getSolarSchema;
	}
	
	@PostMapping("/create")
	@Operation(summary = "/create-schema", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<SolrSchemaDTO> create(@RequestBody SolrSchemaDTO solrSchemaDTO) throws SolrServerException, IOException, URISyntaxException, ParserConfigurationException, InterruptedException{
		log.debug("Solr Schema Create");
		SolrSchemaDTO solrSchemaDTO2 = createSolrSchema.create(solrSchemaDTO.getTableName(),solrSchemaDTO.getName(), solrSchemaDTO.getAttributes());
			return ResponseEntity.status(HttpStatus.OK).body(solrSchemaDTO2);
	
	}
	
	@DeleteMapping("/delete")
	@Operation(summary = "/delete-schema",security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<SolrSchemaDTO> delete(@RequestBody SolrSchemaDTO solrSchemaDTO) throws SolrServerException, IOException, URISyntaxException {
		log.debug("Schema Delete");
		
		SolrSchemaDTO solrResposeDTO = deleteSolrSchema.delete(solrSchemaDTO.getName());
		return ResponseEntity.status(HttpStatus.OK).body(solrResposeDTO);
	}
	
//	@PutMapping("/update")
//	@Operation(summary = "/update-schema",security = @SecurityRequirement(name = "bearerAuth"))
//	public ResponseEntity<SolrSchemaDTO>
}
