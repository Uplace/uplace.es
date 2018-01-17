package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Agent;

import com.arnaugarcia.uplace.repository.AgentRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.service.dto.AgentDTO;
import com.arnaugarcia.uplace.service.mapper.AgentMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Agent.
 */
@RestController
@RequestMapping("/api")
public class AgentResource {

    private final Logger log = LoggerFactory.getLogger(AgentResource.class);

    private static final String ENTITY_NAME = "agent";

    private final AgentRepository agentRepository;

    private final AgentMapper agentMapper;

    public AgentResource(AgentRepository agentRepository, AgentMapper agentMapper) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
    }

    /**
     * POST  /agents : Create a new agent.
     *
     * @param agentDTO the agentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agentDTO, or with status 400 (Bad Request) if the agent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agents")
    @Timed
    public ResponseEntity<AgentDTO> createAgent(@RequestBody AgentDTO agentDTO) throws URISyntaxException {
        log.debug("REST request to save Agent : {}", agentDTO);
        if (agentDTO.getId() != null) {
            throw new BadRequestAlertException("A new agent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agent agent = agentMapper.toEntity(agentDTO);
        agent = agentRepository.save(agent);
        AgentDTO result = agentMapper.toDto(agent);
        return ResponseEntity.created(new URI("/api/agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agents : Updates an existing agent.
     *
     * @param agentDTO the agentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agentDTO,
     * or with status 400 (Bad Request) if the agentDTO is not valid,
     * or with status 500 (Internal Server Error) if the agentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agents")
    @Timed
    public ResponseEntity<AgentDTO> updateAgent(@RequestBody AgentDTO agentDTO) throws URISyntaxException {
        log.debug("REST request to update Agent : {}", agentDTO);
        if (agentDTO.getId() == null) {
            return createAgent(agentDTO);
        }
        Agent agent = agentMapper.toEntity(agentDTO);
        agent = agentRepository.save(agent);
        AgentDTO result = agentMapper.toDto(agent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agents : get all the agents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agents in body
     */
    @GetMapping("/agents")
    @Timed
    public List<AgentDTO> getAllAgents() {
        log.debug("REST request to get all Agents");
        List<Agent> agents = agentRepository.findAll();
        return agentMapper.toDto(agents);
        }

    /**
     * GET  /agents/:id : get the "id" agent.
     *
     * @param id the id of the agentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agents/{id}")
    @Timed
    public ResponseEntity<AgentDTO> getAgent(@PathVariable Long id) {
        log.debug("REST request to get Agent : {}", id);
        Agent agent = agentRepository.findOne(id);
        AgentDTO agentDTO = agentMapper.toDto(agent);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agentDTO));
    }

    /**
     * DELETE  /agents/:id : delete the "id" agent.
     *
     * @param id the id of the agentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agents/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        log.debug("REST request to delete Agent : {}", id);
        agentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
