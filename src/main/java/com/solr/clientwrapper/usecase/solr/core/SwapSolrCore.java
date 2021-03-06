package com.solr.clientwrapper.usecase.solr.core;

import com.solr.clientwrapper.domain.dto.solr.SolrResponseDTO;
import com.solr.clientwrapper.domain.port.api.SolrCoreServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SwapSolrCore {

    private final Logger log = LoggerFactory.getLogger(SwapSolrCore.class);

    private final SolrCoreServicePort solrCoreServicePort;

    public SwapSolrCore(SolrCoreServicePort solrCoreServicePort) {
        this.solrCoreServicePort = solrCoreServicePort;
    }

    public SolrResponseDTO swap(String coreOne, String coreTwo) {
        log.debug("swap");
        return solrCoreServicePort.swap(coreOne,coreTwo);
    }

}
