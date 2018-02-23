package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Agent;
import com.arnaugarcia.uplace.repository.AgentRepository;
import com.arnaugarcia.uplace.service.dto.AgentDTO;
import com.arnaugarcia.uplace.service.mapper.AgentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Agent.
 */
@Service
@Transactional
public class AgentService {

    private final Logger log = LoggerFactory.getLogger(AgentService.class);

    private final AgentRepository agentRepository;

    private final AgentMapper agentMapper;

    public AgentService(AgentRepository agentRepository, AgentMapper agentMapper) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
    }

    /**
     * Save a agent.
     *
     * @param agentDTO the entity to save
     * @return the persisted entity
     */
    public AgentDTO save(AgentDTO agentDTO) {
        log.debug("Request to save Agent : {}", agentDTO);
        Agent agent = agentMapper.toEntity(agentDTO);
        agent = agentRepository.save(agent);
        return agentMapper.toDto(agent);
    }

    /**
     * Get all the agents.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AgentDTO> findAll() {
        log.debug("Request to get all Agents");
        return agentRepository.findAll().stream()
            .map(agentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one agent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AgentDTO findOne(Long id) {
        log.debug("Request to get Agent : {}", id);
        Agent agent = agentRepository.findOne(id);
        return agentMapper.toDto(agent);
    }

    /**
     * Delete the agent by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Agent : {}", id);
        agentRepository.delete(id);
    }
}
