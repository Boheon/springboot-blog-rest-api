package com.module.blog.service.impl;

import com.module.group.Group;
import com.module.group.GroupRepository;
import com.module.lambda.BindParameterSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final GroupRepository groupRepository;

    @Override
    public Room findRoomByGroup(Group group) {

        return Optional.
                    ofNullable(
                            groupRepository.findRoomByGroupId(group.getId())).
                                orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no room with that group"));

    }
}
