package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.Kid;
import com.flab.kidsafer.error.exception.KidBirthYearInvalidException;
import com.flab.kidsafer.error.exception.KidNotFoundException;
import com.flab.kidsafer.mapper.KidMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KidService {

    @Autowired
    KidMapper kidMapper;

    public Kid getOneKid(int id) {
        Kid kid = kidMapper.getOneKid(id);
        if (kid == null) {
            throw new KidNotFoundException();
        }
        return kid;
    }

    public List<Kid> getAllKid(int parentId) {
        return kidMapper.getAllKid(parentId);
    }

    public int registerKid(Kid kid) {
        if (!kid.isKidMinor()) {     // 나이검증
            throw new KidBirthYearInvalidException();
        }
        kidMapper.registerKid(kid);
        return kid.getId();
    }

    public void updateKid(Kid kid) {
        kidMapper.updateKid(kid);
    }

    public void deleteKid(int id) {
        Kid kid = getOneKid(id);

        kidMapper.deleteKid(id);
    }
}
