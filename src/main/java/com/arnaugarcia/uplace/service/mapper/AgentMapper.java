package com.arnaugarcia.uplace.service.mapper;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.AgentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Agent and its DTO AgentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AgentMapper extends EntityMapper<AgentDTO, Agent> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    AgentDTO toDto(Agent agent);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "properties", ignore = true)
    Agent toEntity(AgentDTO agentDTO);

    default Agent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agent agent = new Agent();
        agent.setId(id);
        return agent;
    }
}
