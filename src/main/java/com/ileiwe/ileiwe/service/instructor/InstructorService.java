package com.ileiwe.ileiwe.service.instructor;

import com.ileiwe.ileiwe.data.dto.InstructorPartyDto;
import com.ileiwe.ileiwe.data.model.Instructor;
import com.ileiwe.ileiwe.service.exception.UserAlreadyExistsException;

public interface InstructorService {
    Instructor save(InstructorPartyDto dto) throws UserAlreadyExistsException;
}
